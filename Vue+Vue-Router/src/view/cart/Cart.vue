<template>
  <div class="view-container">
    <cart-side></cart-side>
    <div class="view-main">
      <table id="cart-container">
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
          <tr class="goods-row" v-for="book in order.books" :key="book.bookId">
            <td>
              <div class="select-block hover-pointer" @click="changeSelect(book.bookId)">
                <svg class="icon" aria-hidden="true" v-show="!book.select">
                  <use xlink:href="#iconcircle"></use>
                </svg>
                <svg class="icon icon-select" aria-hidden="true" v-show="book.select">
                  <use xlink:href="#iconselect"></use>
                </svg>
              </div>
            </td>
            <td width="50%">
              <div class="book-info-container">
                <img class="img-block" width="50px" :src="book.bookImg" alt="book picture">
                <div class="info-block">
                  <div class="link-normal">{{book.bookName}}</div>
                  <div>
                    <span class="text-verysmall">by</span>
                    <span>{{book.bookAuth}}</span>
                  </div>
                </div>
              </div>
            </td>
            <td width="10%">
              <div>¥{{book.unitPrice}}</div>
            </td>
            <td width="10%">
              <div>{{book.quantity}}</div>
            </td>
            <td width="10%">
              <div>{{book.unitPrice * book.quantity}}</div>
            </td>
            <td width="12%">
              <div>
                <svg
                  class="icon icon-delete hover-pointer"
                  aria-hidden="true"
                  @click="deleteItem(book.bookId)"
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
                <button class="btn btn-block btn-submit" @click="buyCart">Buy now</button>
              </div>
            </td>
          </tr>
        </tfoot>
      </table>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { mapState, mapMutations } from "vuex";
import CartSide from "../../components/cart/CartSide";
export default {
  name: "Cart",
  data() {
    return {
      order: {
        books: []
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
      this.order.books.forEach(book => {
        if (book.select) {
          res += book.unitPrice * book.quantity;
        }
      });
      return res;
    }
  },
  methods: {
    fetchCart() {
      this.order = {
        books: [
          {
            bookImg:
              "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
            bookId: "1235das",
            bookName: "The Journey to the West, Revised Edition, Volume 3",
            bookAuth: "Anthony C. Yu",
            unitPrice: "31.00",
            quantity: "3",
            select: true
          },
          {
            bookImg:
              "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
            bookId: "1234das",
            bookName: "The Journey to the West, Revised Edition, Volume 3",
            bookAuth: "Anthony C. Yu",
            unitPrice: "31.00",
            quantity: "3",
            select: true
          }
        ]
      };
    },
    deleteItem(id) {
      var count = 0;
      this.order.books.forEach(book => {
        if (book.bookId === id) {
          this.order.books.splice(count, 1);
        }
        count++;
      });
    },
    changeSelect(id) {
      this.order.books.forEach(book => {
        if (book.bookId === id) {
          if (book.select) book.select = false;
          else book.select = true;
        }
      });
    },
    selectAll() {
      var selected = true;
      this.order.books.forEach(book => {
        if (!book.select) {
          selected = false;
          book.select = true;
        }
      });
      if (selected) {
        this.order.books.forEach(book => {
          book.select = false;
        });
      }
    },
    ...mapMutations([
      "deleteCartItem",
      "buyCart",
      "initialCart",
      "addCart",
      "clearCart"
    ])
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
</style>
