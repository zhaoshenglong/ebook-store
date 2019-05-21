<template>
  <div id="book-container">
    <div class="col1" id="img-block">
      <img :src="book.img" alt="Book picture">
    </div>
    <div class="col2">
      <p>{{book.name}}</p>
    </div>
    <div class="col3">
      <p>{{book.author}}</p>
    </div>
    <div class="col4">
      <input type="text" :value="book.isbn" :readonly="disabledIsbn">
    </div>
    <div class="col5">
      <input type="text" :value="Number(book.price).toFixed(2)" :readonly="disabledPrice">
    </div>
    <div class="col6">
      <input type="text" :value="book.stock" :readonly="disabledRemain">
    </div>
    <div class="col7">
      <el-button type="primary" @click="editBook(book.id)">Edit</el-button>
      <el-button type="danger" @click="deleteBook(book.id)">Delete</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      disabledName: "readonly",
      disabledAuthor: "readonly",
      disabledIsbn: "readonly",
      disabledPrice: "readonly",
      disabledRemain: "readonly"
    };
  },
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  methods: {
    editBook(bookId) {
      this.$emit("openBookDialog", bookId);
    },
    deleteBook(bookId) {
      this.$emit("deleteBook", bookId);
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
.col4 {
  min-width: 140px;
  width: 15%;
  display: flex;
  flex-direction: row;
}
.col5,
.col6 {
  min-width: 100px;
  width: 10%;
  display: flex;
  flex-direction: row;
}
.col7 {
  min-width: 180px;
  width: 20%;
  margin-top: 25px;
}
input {
  display: block;
  margin-top: 25px;
  width: 100%;
  border: 0;
  padding: 6px 0;
  background: transparent;
  height: 48px;
  font-size: 18px;
  overflow: auto;
  line-break: normal;
  line-height: 20px;
  color: rgb(31, 31, 31);
  text-align: center;
}
p {
  margin-top: 40px;
  width: 100%;
  padding: 6px 0;
  height: 48px;
  font-size: 18px;
  line-height: 20px;
  color: rgb(31, 31, 31);
}
</style>
