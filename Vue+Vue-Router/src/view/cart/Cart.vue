<template>
  <div class="view-container">
    <cart-side></cart-side>
    <div class="view-main">
      <table id="cart-container" v-if="cart.orderItemList.length > 0">
        <thead>
          <th>
            <div @click="selectAll">
              <svg class="icon select-all-block hover-pointer" aria-hidden="true">
                <use xlink:href="#icontotal_selection"></use>
              </svg>
            </div>
          </th>
          <th>
            <div>Goods</div>
          </th>
          <th>
            <div>Unit price</div>
          </th>
          <th>
            <div>Quantity</div>
          </th>
          <th>
            <div>Total</div>
          </th>
          <th>
            <div>Functions</div>
          </th>
        </thead>
        <tbody>
          <tr class="goods-row" v-for="item in cart.orderItemList" :key="item.id">
            <td width="8%">
              <div class="select-block hover-pointer" @click="changeSelect(item.id)">
                <svg class="icon" aria-hidden="true" v-show="!item.select">
                  <use xlink:href="#iconcircle"></use>
                </svg>
                <svg class="icon icon-select" aria-hidden="true" v-show="item.select">
                  <use xlink:href="#iconselect"></use>
                </svg>
              </div>
            </td>
            <td width="40%">
              <div class="book-info-container">
                <img class="img-block" width="50px" :src="item.book.img" alt="book picture">
                <div class="info-block">
                  <div class="link-normal">{{item.book.name}}</div>
                  <div style="margin-top:15px">
                    <span class="text-verysmall">by</span>
                    <span>{{item.book.author}}</span>
                  </div>
                </div>
              </div>
            </td>
            <td width="10%">
              <div>¥{{item.book.price}}</div>
            </td>
            <td width="20%">
              <div id="quantity-container">
                <div id="quantity-num">{{item.quantity}}</div>
                <div id="modify-container">
                  <div>
                    <svg class="icon icon-btn" aria-hidden="true" @click="increment(item)">
                      <use xlink:href="#iconPLUS"></use>
                    </svg>
                  </div>
                  <div>
                    <svg class="icon icon-btn" aria-hidden="true" @click="decrement(item)">
                      <use xlink:href="#iconMINUS"></use>
                    </svg>
                  </div>
                </div>
              </div>
            </td>
            <td width="10%">
              <div>{{item.book.price * item.quantity}}</div>
            </td>
            <td width="12%">
              <div>
                <svg
                  class="icon icon-delete hover-pointer"
                  aria-hidden="true"
                  @click="deleteItem(item)"
                >
                  <use xlink:href="#icondelete1"></use>
                </svg>
              </div>
            </td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td colspan="4">
              <div id="total">
                Total Price:
                <span>{{total}}</span>
              </div>
            </td>
            <td colspan="2">
              <div id="buy-btn">
                <button class="btn btn-block btn-submit" @click="buy">Buy now</button>
              </div>
            </td>
          </tr>
        </tfoot>
      </table>
      <div id="no-cart-info" v-if="cart.orderItemList.length == 0">
        <p>购物车还没有东西哦，现在去买！</p>
        <a @click="toStore">Back to Store</a>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import CartSide from "../../components/cart/CartSide";
import qs from "qs";
import { mapGetters } from "vuex";
import Cookies from "js-cookie";
export default {
  name: "Cart",
  data() {
    return {
      cart: {
        orderItemList: []
      }
    };
  },
  components: {
    CartSide
  },
  mounted: function() {
    this.fetchCart();
  },
  computed: {
    total() {
      var res = 0;
      this.cart.orderItemList.forEach(item => {
        if (item.select) {
          res += item.book.price * item.quantity;
        }
      });
      return res;
    }
  },
  methods: {
    fetchCart() {
      var apiUrl = "/api/user/" + Cookies.get("name") + "/cart";
      axios
        .get(apiUrl)
        .then(response => {
          console.log(response);
          const data = response.data;
          data.orderItemList.forEach(item => {
            let i = new Object();
            i.id = item.id;
            i.orderId = item.orderId;
            i.quantity = item.quantity;
            i.select = true;
            i.book = new Object();
            i.book.name = item.book.name;
            i.book.author = item.book.name;
            i.book.price = item.book.price;
            i.book.bookId = item.book.id;
            i.book.img = item.book.img;
            i.book.stock = item.book.stock;
            this.cart.orderItemList.push(i);
          });
          console.log(this.cart.orderItemList);
        })
        .catch(err => {
          console.log(err);
        });
    },
    toStore() {
      this.$router.push({ name: "StorePage" });
    },
    increment(item) {
      if (item.quantity >= item.book.stock) {
        this.$message({
          message: "库存不够啦，不可以在增加咯～",
          type: "warning",
          duration: 1000
        });
        return;
      } else {
        var apiUrl = "/api/user/" + Cookies.get("name") + "/orders/item/set";
        axios
          .put(apiUrl, {
            id: item.id,
            quantity: item.quantity + 1
          })
          .then(response => {
            item.quantity++;
            console.log(response.data);
          })
          .catch(err => {
            console.log(err);
            this.$message({
              message: "修改失败，我们的服务器可能挂了",
              type: "error",
              duration: 800
            });
          });
      }
    },
    decrement(item) {
      if (item.quantity > 0) {
        var apiUrl = "/api/user/" + Cookies.get("name") + "/orders/item/set";
        axios
          .put(apiUrl, {
            id: item.id,
            quantity: item.quantity - 1
          })
          .then(response => {
            item.quantity--;
            console.log(response.data);
          })
          .catch(err => {
            console.log(err);
            this.$message({
              message: "修改失败，我们的服务器可能挂了",
              type: "error",
              duration: 800
            });
          });
      } else {
        this.$message({
          message: "数量都0了，你是想脱光我吗？",
          type: "warning",
          duration: 800
        });
        return;
      }
    },
    deleteItem(item) {
      if (confirm("确定要删除吗？")) {
        var apiUrl =
          "/api/user/" +
          Cookies.get("name") +
          "/orders/item/delete?id=" +
          item.id;
        axios
          .delete(apiUrl)
          .then(response => {
            console.log(response);
            this.$message({
              type: "success",
              message: "删除成功",
              duration: 800
            });
            var count = 0;
            this.cart.orderItemList.forEach(i => {
              if (i.id === item.id) {
                this.cart.orderItemList.splice(count, 1);
              }
              count++;
            });
          })
          .catch(err => {
            console.log(err);
            this.$message({
              type: "error",
              message: "删除失败，我们的服务器挂了",
              duration: 800
            });
          });
      }
    },
    changeSelect(id) {
      this.cart.orderItemList.forEach(item => {
        if (item.id === id) {
          if (item.select) item.select = false;
          else item.select = true;
        }
      });
    },
    selectAll() {
      var selected = true;
      this.cart.orderItemList.forEach(item => {
        if (!item.select) {
          selected = false;
          item.select = true;
        }
      });
      if (selected) {
        this.cart.orderItemList.forEach(item => {
          item.select = false;
        });
      }
    },
    buy() {
      var order = new Array();
      this.cart.orderItemList.forEach(item => {
        if (item.select) {
          var orderItem = new Object();
          orderItem.id = item.id;
          orderItem.quantity = item.quantity;
          order.push(orderItem);
        }
      });
      if (order.length > 0) {
        var apiUrl = "/api/user/" + Cookies.get("name") + "/orders/buy";
        axios
          .post(apiUrl, order)
          .then(response => {
            this.cart.orderItemList = this.cart.orderItemList.filter(i => {
              return !i.select;
            });
          })
          .catch(err => {
            this.$message({
              type: "error",
              message: "购买失败，我们的服务器又挂了:(",
              duration: 3000
            });
          });
      }
    },
    ...mapGetters(["getUser"])
  }
};
</script>
<style scoped>
#cart-container {
  text-align: center;
  vertical-align: top;
  font-size: 16px;
  color: #666666;
  margin: 0 15px;
  margin-top: 20px;
  border: 1px solid #dddddd;
}
.book-info-container {
  display: flex;
  position: relative;
  height: 85px;
  margin-top: 15px;
}
.img-block {
  position: absolute;
  left: 0;
  top: 0;
}
.info-block {
  flex: 1;
  padding-left: 70px;
  text-align: left;
}
thead {
  height: 36px;
  background: #dddddd;
}
thead th {
  height: 36px;
  line-height: 36px;
}
#quantity-container {
  margin: 0 auto;
  max-width: 100px;
  height: 56px;
  display: flex;

  border: 1px solid #dddddd;
  position: relative;
}
#quantity-num {
  color: #35a3c4;
  font-size: 24px;
  line-height: 56px;
  flex: 1;
}
#modify-container div {
  border-left: 1px solid #dddddd;
}
#modify-container div:hover {
  color: #0a0a0a;
  cursor: pointer;
}
#modify-container > div:first-child {
  border-bottom: 1px solid #dddddd;
}
.select-all-block {
  position: relative;
  top: 8px;
}
.goods-row {
  height: 100px;
  border-bottom: 1px solid #dddddd;
}
.goods-row td {
  vertical-align: middle;
}
.select-block {
  width: 40px;
  height: 40px;
  margin: 0 auto;
  position: relative;
  top: 5px;
}
.text-verysmall {
  font-size: 12px;
}
.icon-delete {
  color: red;
}
.icon-select {
  color: #28a745;
}
.hover-pointer:hover {
  cursor: pointer;
}
tfoot td {
  vertical-align: middle;
}
#total {
  height: 36px;
  font-size: 20px;
  line-height: 36px;
  text-align: right;
  margin-right: 20px;
}
#total span {
  display: inline-block;
  min-width: 48px;
  font-size: 26px;
  color: black;
}
#buy-btn {
  height: 36px;
}
#no-cart-info {
  margin-top: 250px;
}
#no-cart-info p {
  font-size: 24px;
  color: #0a0a0a;
  font-family: "Courier New", Courier, monospace;
}
#no-cart-info a {
  margin-top: 20px;
  color: #35a3c4;
}
#no-cart-info a:hover {
  cursor: pointer;
  color: aqua;
}
</style>
