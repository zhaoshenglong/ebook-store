<template>
  <div class="bookinfo-container">
    <div class="margin-block">
      <img :src="book.img" alt="Book picture" width="200px" height="290px">
      <div class="info-block">
        <div style="display:flex">
          <span class="tag-mid">{{book.tag}}</span>
        </div>
        <h3 class="h3-heading book-head">{{book.name}}</h3>
        <div class="info-row">
          <span>作者 ：</span>
          <span>{{book.author}}</span>
        </div>
        <div class="info-row">
          <span>ISBN：</span>
          <span>{{book.isbn}}</span>
        </div>
        <div class="info-row">
          <span>库存 ：</span>
          <span>{{book.stock}}</span>
        </div>
        <div class="info-row">
          <span>单价 ：</span>
          <span>{{Number(book.price).toFixed(2)}}</span>
        </div>
        <div class="buy-menu">
          <div class="quantity-manage">
            <svg class="icon icon-btn" aria-hidden="true" @click="decrement">
              <use xlink:href="#iconMINUS"></use>
            </svg>
            <input type="number" v-model="quantity">
            <svg class="icon icon-btn" aria-hidden="true" @click="increment">
              <use xlink:href="#iconPLUS"></use>
            </svg>
          </div>
          <div class="btn-manage">
            <button
              class="btn btn-block btn-submit"
              @click="invokeAddCart(book.id, quantity)"
            >Add to cart</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'BookInfo',
  data () {
    return {
      quantity: 1
    }
  },
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  methods: {
    decrement () {
      var num = parseInt(this.quantity, 10)
      if (num > 1) {
        num--
        this.quantity = num
      } else this.quantity = 1
    },
    increment () {
      var num = parseInt(this.quantity, 10)
      num++
      this.quantity = num
    },
    invokeAddCart (id, quantity) {
      this.$emit('addCart', id, quantity)
    }
  }
}
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
  margin: 12px 0 20px 24px;
  color: #0a0a0a;
  font-size: 26px;
}
.info-row {
  height: 24px;
  margin-bottom: 12px;
  line-height: 24px;
  text-align: left;
  padding-left: 18px;
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
