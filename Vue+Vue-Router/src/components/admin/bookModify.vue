<template>
  <div id="book-container">
    <div class="col1" id="img-block">
      <img :src="book.img" alt="Book picture">
    </div>
    <div class="col2">
      <input type="text" :value="book.name" :disabled="disabledName" @click="activicate(1)">
    </div>
    <div class="col3">
      <input type="text" :value="book.author" :disabled="disabledAuthor" @dblclick="activicate(2)">
    </div>
    <div class="col4">
      <input type="text" :value="book.isbn" :disabled="disabledIsbn" @dblclick="activicate(3)">
    </div>
    <div class="col5">
      <input type="text" :value="book.price" :disabled="disabledPrice" @dblclick="activicate(4)">
    </div>
    <div class="col6">
      <input type="text" :value="book.remain" :disabled="disabledRemain" @dblclick="activicate(5)">
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      disabledName: "disabled",
      disabledAuthor: "disabled",
      disabledIsbn: "disabled",
      disabledPrice: "disabled",
      disabledRemain: "disabled"
    };
  },
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  methods: {
    activicate(sign) {
      console.log(sign);
    }
  }
};
</script>

<style scoped>
#book-container {
  height: 150px;
  display: flex;
  flex-direction: row;
  margin: 20px 25px;
  border: 1px solid #dddddd;
}
#img-block {
  width: 125px;
  height: 150px;
}
#img-block img {
  width: 105px;
  max-height: 145px;
  margin: 0 auto;
}
.col2,
.col3,
.col4,
.col5,
.col6 {
  width: 15%;
  display: flex;
  flex-direction: row;
}
input {
  display: block;
  padding-top: 25px;
  width: 100%;
  background: transparent;
  border: 0;
  height: 48px;
  white-space: normal;
  word-wrap: break-word;
  word-break: break-all;
  font-size: 18px;
  line-height: 24px;
  color: rgb(31, 31, 31);
  text-align: center;
}
</style>
