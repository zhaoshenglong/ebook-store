<template>
  <div class="view-container">
    <cart-side></cart-side>
    <div class="view-main">
      <table id="cart-container" v-if="cart.length > 0">
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
          <tr class="goods-row" v-for="book in cart" :key="book.id">
            <td width="8%">
              <div class="select-block hover-pointer" @click="changeSelect(book.id)">
                <svg class="icon" aria-hidden="true" v-show="!book.select">
                  <use xlink:href="#iconcircle"></use>
                </svg>
                <svg class="icon icon-select" aria-hidden="true" v-show="book.select">
                  <use xlink:href="#iconselect"></use>
                </svg>
              </div>
            </td>
            <td width="40%">
              <div class="book-info-container">
                <img class="img-block" width="50px" :src="book.img" alt="book picture">
                <div class="info-block">
                  <div class="link-normal">{{book.name}}</div>
                  <div style="margin-top:15px">
                    <span class="text-verysmall">by</span>
                    <span>{{book.author}}</span>
                  </div>
                </div>
              </div>
            </td>
            <td width="10%">
              <div>¥{{book.price}}</div>
            </td>
            <td width="20%">
              <div id="quantity-container">
                <div id="quantity-num">{{book.quantity}}</div>
                <div id="modify-container">
                  <div>
                    <svg class="icon icon-btn" aria-hidden="true" @click="increment(book)">
                      <use xlink:href="#iconPLUS"></use>
                    </svg>
                  </div>
                  <div>
                    <svg class="icon icon-btn" aria-hidden="true" @click="decrement(book)">
                      <use xlink:href="#iconMINUS"></use>
                    </svg>
                  </div>
                </div>
              </div>
            </td>
            <td width="10%">
              <div>{{book.price * book.quantity}}</div>
            </td>
            <td width="12%">
              <div>
                <svg
                  class="icon icon-delete hover-pointer"
                  aria-hidden="true"
                  @click="deleteItem(book)"
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
      <div id="no-cart-info" v-if="cart.length == 0">
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
export default {
  name: "Cart",
  data() {
    return {
      cart: []
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
      this.cart.forEach(book => {
        if (book.select) {
          res += book.price * book.quantity;
        }
      });
      return res;
    }
  },
  methods: {
    fetchCart() {
      axios
        .get("cartServlet")
        .then(response => {
          console.log(response);
          response.data.forEach(item => {
            var book = new Object();
            book.id = item[0];
            book.price = item[1];
            book.name = item[2];
            book.author = item[4];
            book.img = item[5];
            book.quantity = item[6];
            book.select = true;
            book.stock = item[7];
            this.cart.push(book);
          });
        })
        .catch(err => {
          console.log(err);
        });
    },
    toStore() {
      this.$router.push({ name: "StorePage" });
    },
    increment(book) {
      if (book.quantity >= book.stock) {
        alert("库存就这么多啦！");
        return;
      } else {
        axios
          .post(
            "cartServlet",
            qs.stringify({
              action: "modify",
              orderItem:
                "{" +
                "bookId:" +
                book.id +
                "," +
                "quantity:" +
                book.quantity +
                "}"
            })
          )
          .then(response => {
            book.quantity++;
            console.log(response.data);
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    decrement(book) {
      if (book.quantity > 0) {
        axios
          .post(
            "cartServlet",
            qs.stringify({
              action: "modify",
              orderItem:
                "{" +
                "bookId:" +
                book.id +
                "," +
                "quantity:" +
                book.quantity +
                "}"
            })
          )
          .then(response => {
            book.quantity--;
            console.log(response.data);
          })
          .catch(err => {
            console.log(err);
          });
      } else return;
    },
    deleteItem(book) {
      if (confirm("确定要删除吗？")) {
        var count = 0;
        this.cart.forEach(b => {
          if (b.id === book.id) {
            this.cart.splice(count, 1);
          }
          count++;
        });
        axios
          .post(
            "cartServlet",
            qs.stringify({
              action: "delete",
              orderItem:
                "{" +
                "bookId:" +
                book.id +
                "," +
                "quantity:" +
                book.quantity +
                "}"
            })
          )
          .then(response => {
            console.log(response);
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    changeSelect(id) {
      this.cart.forEach(book => {
        if (book.id === id) {
          if (book.select) book.select = false;
          else book.select = true;
        }
      });
    },
    selectAll() {
      var selected = true;
      this.cart.forEach(book => {
        if (!book.select) {
          selected = false;
          book.select = true;
        }
      });
      if (selected) {
        this.cart.forEach(book => {
          book.select = false;
        });
      }
    },
    buy() {
      var order = new Array();
      this.cart.forEach(book => {
        if (book.select) {
          var orderItem = new Object();
          orderItem.bookId = book.id;
          orderItem.quantity = book.quantity;
          order.push(orderItem);
        }
      });
      if (order.length > 0) {
        axios.post("orderServlet", order).then(response => {
          this.cart.forEach(book => {
            if (book.select) {
              this.cart.splice(this.cart.indexOf(book), 1);
            }
          });
        });
      }
    }
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
