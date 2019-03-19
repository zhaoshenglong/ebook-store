<template>
  <div id="store-main">
    <ul id="book-list">
      <li class="book-item" v-for="book in filterBooks" :key="book.name">
        <img
          height="267"
          class="book-img-normal"
          :src="book.imgUrl"
          title="The Journey to the West, Revised Edition, Volume 3"
        >
        <div class="book-des">
          <div class="book-info-normal">
            <div class="book-name link-normal">{{book.name}}</div>
            <div class="book-autho">
              <b>by</b>
              {{book.autho}}
            </div>
          </div>
          <div>
            <span class="book-price">
              <b>¥</b>
              {{book.price}}
            </span>
          </div>
          <div class="shop-bar">
            <svg class="icon minus-pink" aria-hidden="true">
              <use xlink:href="#iconminus"></use>
            </svg>
            <span class="quantity">3</span>
            <svg class="icon plus-blue" aria-hidden="true">
              <use xlink:href="#iconplus"></use>
            </svg>
            <svg class="icon cart-large" aria-hidden="true">
              <use xlink:href="#iconiconfontcart-copy"></use>
            </svg>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  name: "StoreMain",
  props: {
    bookList: {
      type: Array,
      required: true
    },
    searchMsg: {
      type: String,
      required: true
    }
  },
  computed: {
    filterBooks() {
      var books = [];
      if (this.searchMsg != "") {
        this.bookList.forEach(ele => {
          if (ele.name.match(this.searchMsg)) {
            books.push(ele);
          }
        });
      } else books = this.bookList;
      return books;
    }
  }
};
</script>
<style scoped>
#store-main {
  flex: 1;
  min-height: 1000px;
  float: left;
  background: linear-gradient(
    to right,
    rgba(255, 255, 255, 0.7),
    rgba(229, 252, 251, 0.7)
  );
}
#book-list {
  padding: 25px 20px;
  margin-top: 30px;
}
.book-item {
  color: #444444;
  font-size: 20px;
  display: flex;
  padding-top: 20px;
  padding-bottom: 50px;
  border-bottom: 1px solid #bbbbbb;
  position: relative;
}
.book-img-normal {
  max-width: 100%;
  height: 200px;
  padding: 0 25px;
}
.book-des {
  flex: 1;
  text-align: left;
}
.book-name {
  margin-bottom: 20px;
  margin-top: 5px;
  font-weight: 100;
}
.book-autho {
  font-size: 14px;
  color: #444444;
  margin-top: 10px;
  margin-bottom: 25px;
}
.book-price {
  color: red;
  font-size: 32px;
}
.book-price b {
  font-size: 24px;
  font-weight: 120;
}
.shop-bar {
  position: absolute;
  bottom: 50px;
}

.quantity {
  font-size: 32px;
}
.cart-large {
  width: 2em;
  height: 2em;
  color: red;
}
.plus-blue {
  color: #35a3c4;
}
.minus-pink {
  color: rgb(111, 120, 124);
}
</style>
