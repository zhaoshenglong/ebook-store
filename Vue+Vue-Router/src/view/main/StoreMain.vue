<template>
  <div class="view-container">
    <side-bar @changeDisplayTag="changeDisplayTag"></side-bar>
    <div class="view-main">
      <div id="store-main">
        <ul id="book-list">
          <li class="book-item" v-for="book in filterBooks" :key="book.isbn">
            <img
              height="267"
              class="book-img-normal"
              :src="book.src"
              :title="book.name"
              @click="toDetail(book.name)"
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
              <div>
                <a href="http://localhost:8088/servlet_war_exploded/bookRequest">link</a>
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
      dispalyTag: "All"
    };
  },
  props: {
    searchMsg: {
      type: String,
      required: true
    }
  },
  methods: {
    toDetail(bookName) {
      this.$router.push({
        name: "BookDetail",
        params: { bookName }
      });
    },
    changeDisplayTag(tag) {
      this.dispalyTag = tag;
    },
    fetchBooks() {
      axios
        .get("/bookRequest")
        .then(response => {
          const data = response.data;
          this.bookList = data;
        })
        .catch(err => {
          console.log(err);
        });
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
        console.log(this.bookList);
        if (this.dispalyTag === "All") {
          this.bookList.forEach(ele => {
            books.push(ele);
          });
        } else {
          this.bookList.forEach(ele => {
            var tag = this.dispalyTag;
            if (ele.tags.indexOf(tag) >= 0) books.push(ele);
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
.minus-pink {
  color: rgb(111, 120, 124);
}
.cart-large {
  width: 2em;
  height: 2em;
  color: red;
}
</style>
