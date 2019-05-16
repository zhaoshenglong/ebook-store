<template>
  <div class="view-main">
    <book-info :book="book" @addCart="addCart"></book-info>
    <book-content :book="book"></book-content>
    <book-remark
      v-for="(remark, index) in bookRemark"
      :key="remark.id"
      :remark="remark"
      :stair="index+1"
    ></book-remark>
    <div id="remark-area-container">
      <textarea name="remark" id="remark-area" placeholder="评价" @keyup.enter="postRemark"></textarea>
    </div>
  </div>
</template>
<script>
import qs from "qs";
import axios from "axios";
import { mapGetters } from "vuex";
import BookRemark from "../../components/remark/Remark";
import BookInfo from "../../components/book/BookInfo";
import BookContent from "../../components/book/BookContent";
export default {
  name: "BookDetail",
  data() {
    return {
      book: {},
      bookRemark: []
    };
  },
  components: {
    BookRemark,
    BookInfo,
    BookContent
  },
  methods: {
    fetchBookData() {
      this.book.id = this.$route.params.bookId;
      axios
        .get("/api/public/books/id/" + this.book.id)
        .then(response => {
          this.book = response.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    addCart(id, quantity) {
      var apiUrl = "/api/user/" + this.getUser().name + "/orders/item/add";
      axios
        .post(apiUrl, {
          data: {
            bookId: id,
            quantity: quantity
          }
        })
        .then(response => {
          console.log(response.data);
          this.$message({
            message: "成功加入购物车！",
            duration: 800,
            type: "success"
          });
        })
        .catch(err => {
          console.log(err);
          this.$message({
            message: "加入购物车失败，我们的服务器挂了",
            duration: 800,
            type: "error"
          });
        });
    },
    fetchBookRemark() {},
    postRemark() {},
    ...mapGetters(["getUser"])
  },
  mounted() {
    this.fetchBookData();
    this.fetchBookRemark();
  }
};
</script>
<style scoped>
.quantity {
  font-size: 32px;
}
.plus-blue {
  color: #35a3c4;
}
#remark-area-container {
  margin: 25px 20px;
  display: flex;
  height: 100px;
}
#remark-area {
  flex: 1;
  padding: 10px 12px;
  color: #666666;
  font-size: 16px;
}
#remark-area:focus {
  border-color: #3dd8ec;
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.28);
}
</style>
