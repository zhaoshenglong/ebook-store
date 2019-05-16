<template>
  <div class="view-container">
    <div id="search-bar">
      <img class="easing-variables" src="../../../static/logo/logo1.png">
      <div id="search-container">
        <input
          type="text"
          v-model="searchMsg"
          placeholder="书名 / 作者 / ISBN"
          @keyup.enter="changeBookListByLike"
        >
        <svg id="search" class="icon" aria-hidden="true">
          <use xlink:href="#iconsearch"></use>
        </svg>
      </div>
    </div>
    <side-bar @changeDisplayTag="changeBookListByTag" :displayTag="dispalyTag"></side-bar>
    <div class="view-main">
      <div id="store-main">
        <ul id="book-list">
          <li
            class="book-item"
            v-for="book in bookList"
            :key="book.isbn"
            @mouseenter="showCart = book.id"
            @mouseleave="showCart = ''"
          >
            <img
              class="book-img-normal"
              :src="book.img"
              :title="book.name"
              @click="toDetail(book.id)"
              alt="book picture"
            >
            <div class="book-des-container">
              <div>
                <div id="book-name">
                  <span class="link-normal" @click="toDetail(book.id)">{{book.name}}</span>
                </div>
                <div class="book-info-normal">
                  <span>作者 ：</span>
                  <span>{{book.author}}</span>
                </div>
                <div class="book-info-normal">
                  <span>ISBN :</span>
                  <span>{{book.isbn}}</span>
                </div>
                <div class="book-info-normal">
                  <span>库存 :</span>
                  <span>{{book.stock}}本</span>
                </div>
              </div>

              <div class="shop-bar">
                <span class="book-price">
                  <b>¥</b>
                  {{book.price}}
                </span>
                <svg
                  class="icon cart-large"
                  aria-hidden="true"
                  @click="addCart(book.id)"
                  v-show="showCart===book.id"
                >
                  <use xlink:href="#iconiconfontcart-copy"></use>
                </svg>
              </div>
            </div>
          </li>
        </ul>
        <div>
          <el-pagination
            :page-size="pager.size"
            :page-count="5"
            layout="prev, pager, next"
            :total="pager.total"
            background
            :hide-on-single-page="true"
            @current-change="changePage"
          ></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SideBar from "../../components/page/Sidebar";
import axios from "axios";
import qs from "qs";
import { mapGetters } from "vuex";
export default {
  name: "StoreMain",
  components: {
    SideBar
  },
  data() {
    return {
      bookList: [],
      dispalyTag: "All",
      searchMsg: "",
      showCart: "",
      bookState: "",
      pager: {
        page: 0,
        size: 10,
        total: 10
      }
    };
  },
  methods: {
    toDetail(bookId) {
      this.$router.push({
        name: "BookDetail",
        params: { bookId: bookId }
      });
    },
    changeBookListByTag(tag) {
      this.dispalyTag = tag;
      this.pager.page = 0;
      if (tag == "All") {
        this.fetchBooks();
      } else {
        this.bookState = "tag";
        this.fetchBooksByTag();
      }
    },
    changeBookListByLike() {
      this.pager.page = 0;
      this.bookState = "like";
      this.fetchBooksLike();
    },

    // There is a bug with changing current page
    changePage(page) {
      this.pager.page = page - 1;
      if (this.bookState == "tag") {
        this.fetchBooksByTag();
      } else if (this.bookState == "like") {
        this.fetchBooksLike();
      } else {
        this.fetchBooks();
      }
    },
    cartTrue() {
      this.showCart = true;
    },
    cartFalse() {
      this.showCart = false;
    },
    addCart(bookId) {
      var apiUrl = "/api/user/" + this.getUser.name + "/orders/item/add";
      axios
        .post(apiUrl, {
          data: {
            bookId: bookId,
            quantity: 1
          }
        })
        .then(response => {
          console.log(response);
          this.$message({
            message: "成功加入购物车",
            type: "success",
            duration: 800
          });
        })
        .catch(err => {
          console.log(err);
          this.$message({
            message: "加入购物车失败,我们的服务器挂了",
            type: "error",
            duration: 800
          });
        });
    },
    fetchBooks() {
      axios
        .get("/api/public/books/tag/all", {
          params: {
            page: this.pager.page
          }
        })
        .then(response => {
          const data = response.data;
          console.log(data);
          this.bookList = data.content;
          this.pager.total = data.totalElements;
          this.pager.size = data.pageSize;
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchBooksByTag() {
      axios
        .get("/api/public/books/tag/" + this.dispalyTag, {
          params: {
            page: this.pager.page
          }
        })
        .then(response => {
          const data = response.data;
          console.log(data);
          this.bookList = data.content;
          this.pager.total = data.totalElements;
          this.pager.size = data.pageSize;
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchBooksLike() {
      axios
        .get("/api/public/books/search", {
          params: {
            page: this.pager.page,
            search_text: this.searchMsg
          }
        })
        .then(response => {
          const data = response.data;
          console.log(data);
          this.bookList = data.content;
          this.pager.total = data.totalElements;
          this.pager.size = data.pageSize;
        })
        .catch(err => {
          console.log(err);
        });
    },
    ...mapGetters(["getUser"])
  },
  computed: {},
  mounted() {
    this.pager.page = 0;
    this.fetchBooks();
  }
};
</script>
<style scoped>
#store-main {
  flex: 1;
  min-height: 1000px;
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
  width: 140px;
  height: 200px;
  padding: 0 25px;
}
.book-img-normal:hover {
  cursor: pointer;
}
.book-des-container {
  flex: 1;
  text-align: left;
}
.book-info-normal span {
  display: inline-block;
  font-size: 18px;
  margin-top: 12px;
}
#book-name {
  margin: 5px 0 20px 20px;
  font-weight: 100;
  font-size: 24px;
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
.minus-pink {
  color: rgb(111, 120, 124);
}
.cart-large {
  width: 2em;
  height: 2em;
  color: red;
  cursor: pointer;
}
#search-bar {
  width: 1017px;
  height: 86px;
  margin: 0 auto;
  display: flex;
  position: absolute;
  top: -108px;
  left: 0;
}
#search-bar img {
  width: 148px;
  margin: 2px 16px;
  margin-left: 98px;
  border-radius: 10px;
}
#search-bar img:hover {
  cursor: pointer;
}
#search-bar #search-container {
  margin: 17px 170px 17px 32px;
  flex: 1;
  display: flex;
  width: 600px;
  height: 50px;
  border-radius: 24px;
  background: #fff;
  position: relative;
}
#search-bar #search-container:hover {
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.28);
}
#search-container input {
  flex: 1;
  height: 34px;
  padding: 7px 25px;
  border: 0;
  border-radius: 24px;
  border: 1px solid #d8eaf6;
  font-size: 16px;
}
#search-container input:focus {
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.28);
}
#search {
  position: absolute;
  right: 15px;
  top: 10px;
  width: 0.6em;
  height: 0.6em;
}
</style>
