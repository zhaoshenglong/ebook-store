<template>
  <div>
    <div id="heading">
      <div id="col1">
        <div>Picture</div>
      </div>
      <div id="col2" class="icon-container">
        <div>Name</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col3" class="icon-container">
        <div>Author</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col4" class="icon-container">
        <div>ISBN</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col5" class="icon-container">
        <div>Price</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col6" class="icon-container">
        <div>Stock</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col7"></div>
    </div>
    <div>
      <book-modify v-for="book in filterBookList" :key="book.isbn" :book="book"></book-modify>
    </div>
  </div>
</template>

<script>
import BookModify from "../../components/admin/bookModify";
import axios from "axios";
export default {
  components: {
    BookModify
  },
  data() {
    return {
      bookList: [],
      pager: {
        page: 0,
        total: 0,
        size: 10
      }
    };
  },
  mounted() {
    this.fetchAllBooks();
    console.log(this.bookList);
  },
  computed: {
    filterBookList() {
      var filterList = [];
      this.bookList.forEach(book => {
        filterList.push(book);
      });
      return filterList;
    }
  },
  methods: {
    fetchAllBooks() {
      axios
        .get("/api/admin/books/all", {
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
    fetchAllLike() {}
  }
};
</script>

<style scoped>
div {
  font-size: 24px;
}
#heading {
  margin: 20px 25px;
  background: #e7e7e7;
  height: 36px;
  line-height: 36px;
  display: flex;
  flex-direction: row;
  font-size: 24px;
  color: rgba(31, 31, 31, 1);
}
#col1 {
  width: 125px;
}
#col2,
#col3,
#col4,
#col5,
#col6 {
  width: 15%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  text-align: center;
}
#col7 {
  flex: 1;
}
.icon-container {
  display: flex;
  justify-content: center;
}
.icon-sort {
  cursor: pointer;
  font-size: 16px;
  position: relative;
  margin-left: 5px;
  top: 5px;
}
</style>
