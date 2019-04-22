<template>
  <div class="view-container">
    <div id="search-bar">
      <img class="easing-variables" src="../../../static/logo/logo1.png">
      <div id="search-container">
        <input type="text" v-model="searchMsg" placeholder="书名 / 作者 / ISBN">
        <svg id="search" class="icon" aria-hidden="true">
          <use xlink:href="#iconsearch"></use>
        </svg>
      </div>
    </div>
    <side-bar @changeDisplayTag="changeDisplayTag" :displayTag="dispalyTag"></side-bar>
    <div class="view-main">
      <div id="store-main">
        <ul id="book-list">
          <li class="book-item" v-for="book in filterBooks" :key="book.isbn">
            <img
              class="book-img-normal"
              :src="book.img"
              :title="book.name"
              @click="toDetail(book.id)"
              alt="book picture"
            >
            <div class="book-des">
              <div class="book-info-normal">
                <div class="book-name">
                  <span class="link-normal">{{book.name}}</span>
                </div>
                <div class="book-autho">
                  <b>by</b>
                  {{book.author}}
                </div>
              </div>

              <div class="shop-bar">
                <span class="book-price">
                  <b>¥</b>
                  {{book.price}}
                </span>
                <svg class="icon cart-large" aria-hidden="true">
                  <use xlink:href="#iconiconfontcart-copy"></use>
                </svg>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
import SideBar from "../../components/page/Sidebar";
import axios from "axios";
export default {
  name: "StoreMain",
  components: {
    SideBar
  },
  data() {
    return {
      bookList: [],
      dispalyTag: "All",
      baseUrl: "http://localhost:8088/img?kind=book&name=",
      searchMsg: ""
    };
  },
  methods: {
    toDetail(bookId) {
      this.$router.push({
        name: "BookDetail",
        params: { bookId: bookId }
      });
    },
    changeDisplayTag(tag) {
      this.dispalyTag = tag;
      this.changeListByName();
    },
    fetchBooks() {
      axios
        .get("/bookServlet", {
          params: {
            id: "all"
          }
        })
        .then(response => {
          const data = response.data;
          console.log(data);
          this.bookList = data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    changeListByName() {
      console.log(this.filterBooks.length);
      console.log(this.filterBooks);
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
      } else {
        if (this.dispalyTag === "All") {
          this.bookList.forEach(ele => {
            books.push(ele);
          });
        } else {
          this.bookList.forEach(ele => {
            var tag = this.dispalyTag;
            if (ele.tag == this.dispalyTag) books.push(ele);
          });
        }
      }
      return books;
    }
  },
  mounted() {
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
.minus-pink {
  color: rgb(111, 120, 124);
}
.cart-large {
  width: 2em;
  height: 2em;
  color: red;
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
