<template>
  <div class="view-container">
    <order-side @updateTime="updateTime" @fetchAll="fetchAllOrder"></order-side>
    <div class="view-main">
      <table id="order-table" v-if="orderList.length > 0">
        <thead id="order-top">
          <tr>
            <th>
              <div>Order detail</div>
            </th>
            <th>
              <div>Costumer</div>
            </th>
            <th>
              <div>Paid</div>
            </th>
          </tr>
        </thead>
        <tbody class="order-item" v-for="order in orderList" :key="order.id">
          <tr class="white-tr"></tr>
          <tr class="order-item-top">
            <td colspan="3">
              <div>{{order.createDate}}</div>
              <div>
                Order ID:
                <span>{{order.id}}</span>
              </div>
            </td>
          </tr>
          <tr class="book-item">
            <td width="70%">
              <ul>
                <li v-for="item in order.orderItemList" :key="item.id" class="book-desc">
                  <img :src="item.book.img" alt="Book picture" width="50px" class="desc-img">
                  <div class="desc-info">
                    <div class="link-normal desc-name">{{item.book.name}}</div>
                    <div class="desc-auth">
                      by
                      <span>{{item.book.author}}</span>
                    </div>
                    <div class="desc-unit">{{Number(item.bookPrice).toFixed(2)}}</div>
                    <div class="feedback">
                      <span title="like">
                        <svg class="icon" aria-hidden="true">
                          <use xlink:href="#iconlike"></use>
                        </svg>
                      </span>
                      <span title="feedback" @click="toRemark(item.book.id)">
                        <svg class="icon" aria-hidden="true">
                          <use xlink:href="#iconyijianfankui"></use>
                        </svg>
                      </span>
                    </div>
                  </div>
                  <div class="desc-quantity">x{{item.quantity}}</div>
                  <div class="desc-total">{{Number(item.bookPrice * item.quantity).toFixed(2)}}</div>
                </li>
              </ul>
            </td>
            <td :rowspan="order.orderItemList.length" width="15%">
              <div class="td-block">{{order.user.name}}</div>
            </td>
            <td :rowspan="order.orderItemList.length" width="15%">
              <div class="td-block">{{total(order)}}</div>
            </td>
          </tr>
        </tbody>
      </table>
      <div id="no-order-info" v-if="orderList.length == 0">
        <p>还没有订单哦！ 现在去买！</p>
        <a @click="toStore">Back to Store</a>
      </div>
      <el-pagination
        v-if="orderList.length != 0"
        :page-size="pager.size"
        :page-count="5"
        layout="prev, pager, next"
        :total="pager.total"
        background
        :hide-on-single-page="true"
        @current-change="changePage"
      ></el-pagination>
    </div>
  </div>
</template>
<script>
import OrderSide from "../../components/order/OrderSide";
import axios from "axios";
import { mapState, mapGetters } from "vuex";
import Cookies from "js-cookie";
export default {
  name: "Order",
  components: {
    OrderSide
  },
  data() {
    return {
      orderList: [],
      timeBegin: "",
      timeEnd: "",
      orderState: "",
      pager: {
        page: 0,
        size: 10,
        total: 0
      }
    };
  },
  computed: {
    filterOrder() {
      var orders = this.orderList;
      return orders;
    },
    ...mapState(["user"])
  },
  methods: {
    toStore() {
      this.$router.push({ name: "StorePage" });
    },
    changePage(page) {
      this.pager.page = page--;
      if (this.orderState == "time") {
        this.fetchOrderBetween();
      } else {
        this.fetchAllOrder();
      }
    },
    fetchAllOrder() {
      this.orderState = "";
      var apiUrl = "/api/user/" + Cookies.get("name") + "/orders";
      axios
        .get(apiUrl, {
          params: {
            page: this.pager.page
          }
        })
        .then(response => {
          console.log("response:");
          console.log(response);
          const data = response.data;
          this.orderList = data.content;
          this.pager.size = data.pageSize;
          this.pager.total = data.totalElements;
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchOrderBetween() {
      var apiUrl = "/api/user/" + Cookies.get("name") + "/orders/between";
      axios
        .get(apiUrl, {
          params: {
            page: this.pager.page,
            beg: this.timeBegin,
            end: this.timeEnd
          }
        })
        .then(response => {
          console.log(response);
          const data = response.data;
          this.orderList = data.content;
          this.pager.size = data.pageSize;
          this.pager.total = data.totalElements;
        })
        .catch(err => {
          console.log(err);
        });
    },
    transferToData(data) {
      this.orderList = new Array();
      let prevOrderId = new String();
      let order;
      prevOrderId = "";
      let count = 0;
      data.forEach(item => {
        let orderId = item[2];
        if (orderId !== prevOrderId) {
          if (prevOrderId !== "") {
            this.orderList.push(order);
          }
          order = new Object();
          order.id = orderId;
          order.createDate = item[1];
          order.books = new Array();
          order.costumer = item[0];
        }
        let book = new Object();
        book.id = item[3];
        book.img = item[4];
        book.author = item[5];
        book.isbn = item[6];
        book.name = item[7];
        book.price = item[8];
        book.quantity = item[10];
        order.books.push(book);
        prevOrderId = orderId;
        count++;
        if (count === data.length) {
          this.orderList.push(order);
        }
      });
    },
    postLike() {},
    toRemark(id) {
      this.$router.push({ name: "BookDetail", params: { bookId: id } });
    },
    updateTime(begin, end) {
      this.timeBegin = begin + " 00:00:00";
      this.timeEnd = end + " 23:59:59";
      this.orderState = "time";
      this.fetchOrderBetween();
    },
    total(order) {
      let res = 0;
      order.orderItemList.forEach(item => {
        res += item.bookPrice * item.quantity;
      });
      return Number(res).toFixed(2);
    },
    ...mapGetters(["getUser"])
  },
  mounted() {
    this.fetchAllOrder();
  }
};
</script>
<style scoped>
#order-table {
  font-size: 16px;
  color: #666;
  margin: 50px auto;
  min-width: 850px;
}
#order-top {
  height: 36px;
  line-height: 36px;
  background: #dddddd;
}
.white-tr {
  height: 20px;
}
.order-item {
  border: 1px solid #dddddd;
}
.order-item-top {
  clear: both;
  height: 26px;
  line-height: 26px;
  background: #dddddd;
}
.order-item-top div {
  float: left;
  margin: 0 25px;
}
.order-item-top span {
  color: black;
  padding-left: 12px;
}
.book-item {
  text-align: center;
  vertical-align: top;
}
.book-item td {
  vertical-align: top;
  border: 1px solid #dddddd;
}
.book-desc {
  display: flex;
  margin-top: 16px;
  border-bottom: 1px solid #dddddd;
  position: relative;
}
.desc-img {
  position: absolute;
  left: 8px;
  top: 0;
}
.desc-info {
  padding-left: 70px;
  flex: 1;
  height: 100px;
  text-align: left;
  line-height: 20px;
}
.desc-name {
  height: 40px;
  line-height: 20px;
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.desc-auth {
  font-size: 12px;
}
.desc-auth > span {
  font-size: 16px;
  color: rgb(78, 78, 78);
}
.desc-unit {
  color: rgb(255, 31, 31);
}
.desc-quantity {
  width: 50px;
}
.desc-total {
  width: 50px;
}
.td-block {
  margin-top: 16px;
  height: 100px;
  line-height: 20px;
  overflow: hidden;
}
.feedback {
  position: absolute;
  right: 130px;
  bottom: 20px;
}
.feedback span {
  padding-left: 20px;
  color: rgb(155, 155, 155);
}
.feedback span:hover {
  color: rgb(119, 119, 119);
  cursor: pointer;
}
#no-order-info {
  margin-top: 250px;
}
#no-order-info p {
  font-size: 24px;
  color: #0a0a0a;
  font-family: "Courier New", Courier, monospace;
}
#no-order-info a {
  margin-top: 20px;
  color: #35a3c4;
}
#no-order-info a:hover {
  cursor: pointer;
  color: aqua;
}
</style>
