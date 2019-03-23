<template>
  <div class="view-container">
    <order-side></order-side>
    <div class="view-main">
      <table id="order-table">
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
        <tbody class="order-item" v-for="order in filterOrder" :key="order.id">
          <tr class="white-tr"></tr>
          <tr class="order-item-top">
            <td colspan="3">
              <div>{{order.orderTime}}</div>
              <div>
                Order ID:
                <span>{{order.id}}</span>
              </div>
            </td>
          </tr>
          <tr class="book-item">
            <td width="70%">
              <ul>
                <li v-for="book in order.books" :key="book.bookId" class="book-desc">
                  <img :src="book.bookImg" alt="Book picture" width="50px" class="desc-img">
                  <div class="desc-info">
                    <div class="link-normal desc-name">{{book.bookName}}</div>
                    <div class="desc-auth">
                      by
                      <span>{{book.bookAuth}}</span>
                    </div>
                    <div class="desc-unit">{{book.unitPrice}}</div>
                    <div class="feedback">
                      <span title="like">
                        <svg class="icon" aria-hidden="true">
                          <use xlink:href="#iconlike"></use>
                        </svg>
                      </span>
                      <span title="feedback">
                        <svg class="icon" aria-hidden="true">
                          <use xlink:href="#iconyijianfankui"></use>
                        </svg>
                      </span>
                    </div>
                  </div>
                  <div class="desc-quantity">x{{book.quantity}}</div>
                  <div class="desc-total">{{book.unitPrice * book.quantity}}</div>
                </li>
              </ul>
            </td>
            <td :rowspan="order.books.length" width="15%">
              <div class="td-block">{{order.desc.costumer}}</div>
            </td>
            <td :rowspan="order.books.length" width="15%">
              <div class="td-block">{{order.desc.paid}}</div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import OrderSide from "../../components/order/OrderSide";
export default {
  name: "Order",
  components: {
    OrderSide
  },
  data() {
    return {
      orderList: []
    };
  },
  mounted: function() {
    this.orderList = [
      {
        id: "0126546",
        orderTime: "13/03/2019 ",
        desc: {
          costumer: "zhaoshenglong",
          paid: "123"
        },
        books: [
          {
            bookImg:
              "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
            bookId: "1235das",
            bookName: "The Journey to the West, Revised Edition, Volume 3",
            bookAuth: "Anthony C. Yu",
            unitPrice: "31.00",
            quantity: "3"
          },
          {
            bookImg:
              "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
            bookId: "1234das",
            bookName: "The Journey to the West, Revised Edition, Volume 3",
            bookAuth: "Anthony C. Yu",
            unitPrice: "31.00",
            quantity: "3"
          }
        ]
      },
      {
        id: "0146",
        orderTime: "13/03/2019 ",
        desc: {
          costumer: "zhnglong",
          paid: "13"
        },
        books: [
          {
            bookImg:
              "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
            bookId: "1245",
            bookName: "The Journey to the West, Revised Edition, Volume 3",
            bookAuth: "Anthony C. Yu",
            unitPrice: "31.00",
            quantity: "3"
          },
          {
            bookImg:
              "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
            bookId: "1345",
            bookName: "The Journey to the West, Revised Edition, Volume 3",
            bookAuth: "Anthony C. Yu",
            unitPrice: "31.00",
            quantity: "3"
          }
        ]
      }
    ];
  },
  computed: {
    filterOrder() {
      var orders = this.orderList;
      return orders;
    }
  }
};
</script>
<style scoped>
#order-table {
  font-size: 16px;
  color: #666;
  margin: 0 20px;
  margin-top: 30px;
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
</style>
