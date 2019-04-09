<template>
  <div id="book-container" @click="disableInput">
    <div class="col1" id="img-block">
      <img :src="book.img" alt="Book picture">
    </div>
    <div class="col2">
      <input type="text" :value="book.name" :readonly="disabledName" @dblclick="activicate(1)">
    </div>
    <div class="col3">
      <input type="text" :value="book.author" :readonly="disabledAuthor" @dblclick="activicate(2)">
    </div>
    <div class="col4">
      <input type="text" :value="book.isbn" :readonly="disabledIsbn" @dblclick="activicate(3)">
    </div>
    <div class="col5">
      <input type="text" :value="book.price" :readonly="disabledPrice" @dblclick="activicate(4)">
    </div>
    <div class="col6">
      <input type="text" :value="book.remain" :readonly="disabledRemain" @dblclick="activicate(5)">
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
    activicate(s) {
      var sign = parseInt(s);
      var e = window.event;
      console.log(s);
      e.target.className = "input-block input-control";
      switch (sign) {
        case 1:
          this.disabledName = null;
          break;
        case 2:
          this.disabledAuthor = null;
          break;
        case 3:
          this.disabledIsbn = null;
          break;
        case 4:
          this.disabledPrice = null;
          break;
        case 5:
          this.disabledRemain = null;
          break;
        default:
          break;
      }
    },
    disableInput() {
      var e = window.event;
      if (e.target.tagName != "INPUT") {
        this.disabledName = "readonly";
        this.disabledAuthor = "readonly";
        this.disabledIsbn = "readonly";
        this.disabledPrice = "readonly";
        this.disabledRemain = "readonly";
        var list = document.getElementsByTagName("INPUT");
        var len = list.length;
        for (var i = 0; i < len; i++) {
          if (list[i].className != "") {
            list[i].className = "";
          }
        }
      }
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
  margin-top: 25px;
  width: 100%;
  border: 0;
  padding: 6px 0;
  background: transparent;
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
