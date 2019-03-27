<template>
  <div class="bookinfo-container">
    <div class="margin-block">
      <img :src="book.img" alt="Book picture" width="200px" height="290px">
      <div class="info-block">
        <div style="display:flex">
          <span class="tag-mid" v-for="tag in book.tags" :key="tag">{{tag}}</span>
        </div>
        <h3 class="h3-heading book-head">{{book.name}}</h3>
        <div class="author-block">
          by
          <span>{{book.author}}</span>
        </div>
        <div class="info-row info-isbn">{{book.isbn}}</div>
        <div class="info-row info-remain">
          Remain:
          <span>{{book.remain}}</span>
        </div>
        <div class="info-row info-price">
          Unit price:
          <span>{{book.price}}</span>
        </div>
        <div class="buy-menu">
          <div class="quantity-manage">
            <svg class="icon icon-btn" aria-hidden="true" @click="decrement">
              <use xlink:href="#iconMINUS"></use>
            </svg>
            <input type="number" v-model="selectQuantity">
            <svg class="icon icon-btn" aria-hidden="true" @click="increment">
              <use xlink:href="#iconPLUS"></use>
            </svg>
          </div>
          <div class="btn-manage">
            <button class="btn btn-block btn-submit">Buy now</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BookInfo',
  data() {
    return {
      selectQuantity: 1
    };
  },
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  methods: {
    decrement() {
      var num = parseInt(this.selectQuantity, 10);
      if (num > 1) {
        num--;
        this.selectQuantity = num;
      } else this.selectQuantity = 1;
    },
    increment() {
      var num = parseInt(this.selectQuantity, 10);
      num++;
      this.selectQuantity = num;
    }
  }
};
</script>
<style scoped>
.bookinfo-container {
  width: 100%;
  display: flex;
  font-size: 20px;
  color: #666666;
}
.margin-block {
  flex: 1;
  display: flex;
  margin: 0 25px;
  margin-top: 20px;
  border-left: 1px solid #dddddd;
  border-bottom: 1px solid #dddddd;
  position: relative;
  min-height: 320px;
}
.margin-block img {
  position: absolute;
  left: 10px;
  top: 20px;
}
.info-block {
  padding-left: 25%;
  flex: 1;
  padding-top: 20px;
}
.tag-mid {
  background: #ffffff;
  color: #35a3c4;
  display: inline-block;
  width: 80px;
  margin: 0 8px;
  line-height: 20px;
  height: 20px;
  border-radius: 2px;
  cursor: pointer;
  justify-content: left;
}
.book-head {
  margin-top: 12px;
  color: black;
}
.author-block {
  text-align: left;
  padding-left: 18px;
  font-size: 12px;
}
.author-block span {
  font-size: 18px;
  color: #525252;
}
.info-row {
  height: 24px;
  margin-bottom: 12px;
  line-height: 24px;
  text-align: left;
  padding-left: 18px;
}
.info-isbn {
  margin-top: 36px;
}
.info-remain span {
  color: #3d3d3d;
  margin-left: 30px;
  font-size: 24px;
}
.info-price span {
  color: red;
  margin-left: 15px;
  font-size: 24px;
}
.buy-menu {
  display: flex;
}
.quantity-manage {
  height: 36px;
  line-height: 36px;
  display: flex;
  border: 1px solid #b8b8b8;
}
.quantity-manage input {
  padding: 0 8px;
  flex: 1;
    text-align: center;
  width: 50px;
  border: 0;
}
.icon-btn {
  cursor: pointer;
  width: 24px;
  height: 24px;
  position: relative;
  top: 4px;
}
.btn-manage {
  margin-left: 12px;
  width: 120px;
}
</style>
