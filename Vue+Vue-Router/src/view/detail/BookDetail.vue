<template>
  <div class="view-main">
    <book-info :book="book"></book-info>
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
import axios from "axios";
import { mapState } from "vuex";
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
        .get("/bookServlet", {
          params: {
            id: this.book.id
          }
        })
        .then(response => {
          this.book = response.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchBookRemark() {},
    postRemark() {}
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
