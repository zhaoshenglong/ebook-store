<template>
  <div>
    <div id="page-top">
      <section id="top-left" @keyup.enter="searchBook">
        <el-input placeholder="Name / Author / Isbn" v-model="search" id="search-input" clearable>
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </section>
      <section id="top-blank"></section>
      <section id="page-right">
        <el-button type="primary" @click="openBookDialog('')">Create New Book</el-button>
      </section>
    </div>
    <div id="heading">
      <div id="col1">
        <div>Picture</div>
      </div>
      <div id="col2" class="icon-container">
        <div>Name</div>
        <svg class="icon icon-sort" aria-hidden="true">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col3" class="icon-container">
        <div>Author</div>
        <svg class="icon icon-sort" aria-hidden="true">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col4" class="icon-container">
        <div>ISBN</div>
        <svg class="icon icon-sort" aria-hidden="true">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col5" class="icon-container">
        <div>Price</div>
        <svg class="icon icon-sort" aria-hidden="true">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col6" class="icon-container">
        <div>Stock</div>
        <svg class="icon icon-sort" aria-hidden="true">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col7">
        <div>Operation</div>
      </div>
    </div>
    <div>
      <book-modify
        v-for="book in bookList"
        :key="book.id"
        :book="book"
        @openBookDialog="openBookDialog"
        @deleteBook="deleteBook"
      ></book-modify>
      <book-form
        :dialogVisible="dialogVisible"
        :action="action"
        :oldBook="oldBook"
        @cancleBookDialog="cancleBookDialog"
        @updateBook="updateBook"
        @createBook="createBook"
      ></book-form>
    </div>
    <div id="book-pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="pager.total"
        :page-size="pager.size"
        :current-page.sync="pager.page"
        @current-change="changePage"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import BookModify from '../../components/admin/bookModify'
import BookForm from '../../components/admin/bookForm'
import axios from 'axios'
export default {
  components: {
    BookModify,
    BookForm
  },
  data () {
    return {
      bookList: [],
      pager: {
        page: 0,
        total: 0,
        size: 10
      },
      dialogVisible: false,
      action: 'Update Book',
      oldBook: {},
      display: '',
      search: ''
    }
  },
  mounted () {
    this.pager.page = 0
    this.fetchAllBooks(this.pager.page)
  },
  computed: {},
  methods: {
    fetchAllBooks (page) {
      this.display = ''
      axios
        .get('/api/admin/books/all', {
          params: {
            page: page
          }
        })
        .then(response => {
          const data = response.data
          this.bookList = data.content
          console.log(data)
          this.pager.total = data.totalElements
          this.pager.size = data.size
        })
        .catch(err => {
          console.log(err)
        })
    },
    changePage (page) {
      console.log('boolean', this.display === 'search')
      if (this.display === 'search') {
        console.log('display', this.display)
        this.fetchAllLike(page - 1)
      } else {
        console.log('display', this.display)
        this.fetchAllBooks(page - 1)
      }
    },
    fetchAllLike (page) {
      this.display = 'search'
      axios
        .get('/api/admin/books/search', {
          params: {
            pattern: this.search,
            page: page
          }
        })
        .then(response => {
          console.log(response.data)
          const data = response.data
          this.bookList = data.content
          this.pager.total = data.totalElements
          this.pager.size = data.size
        })
        .catch(err => {
          console.log(err)
        })
    },
    cancleBookDialog () {
      this.dialogVisible = false
    },
    openBookDialog (bookId) {
      if (bookId !== '') {
        this.dialogVisible = true
        this.bookList.forEach(book => {
          if (bookId === book.id) {
            this.oldBook = book
          }
        })
        this.action = 'Update Book'
      } else {
        this.dialogVisible = true
        this.oldBook = {}
        this.action = 'Create New Book'
      }
    },
    updateBook (book) {
      axios
        .put('/api/admin/books/modify', book)
        .then(response => {
          this.$message({
            type: 'success',
            message: '更新图书成功～',
            duration: 2000
          })
          this.cancleBookDialog()
        })
        .catch(err => {
          this.$message({
            type: 'error',
            message: '更新图书失败，我恩的服务器可能挂了',
            duration: 2000
          })
          console.log(err)
        })
    },
    deleteBook (bookId) {
      axios
        .delete('/api/admin/books/delete', {
          params: {
            id: bookId
          }
        })
        .then(response => {
          this.$message({
            type: 'success',
            message: '成功删除图书～',
            duration: 2000
          })
          console.log(response)
        })
        .catch(err => {
          this.$message({
            type: 'error',
            message: '删除图书失败，我们的服务器可能挂了:(',
            duration: 2000
          })
          console.log(err)
        })
    },
    searchBook () {
      this.display = 'search'
      this.pager.page = 0
      this.fetchAllLike(this.pager.page)
    },
    createBook (book) {
      axios
        .post('api/admin/books/create', book)
        .then(response => {
          this.$message({
            type: 'success',
            message: '创建图书成功～',
            duration: 2000
          })
          console.log(response.data)
          this.cancleBookDialog()
        })
        .catch(err => {
          this.$message({
            type: 'error',
            message: '创建图书失败，我恩的服务器可能挂了',
            duration: 2000
          })
          console.log(err)
        })
    }
  }
}
</script>

<style scoped>
div {
  font-size: 24px;
}
#page-top {
  margin: 15px 20px;
  display: flex;
}
#top-left >>> div {
  left: 15px;
  width: 250px;
  margin-left: 3%;
  font-size: 16px;
}
#top-blank {
  width: 55%;
}
#search-input {
  font-size: 16px;
}
#top-right {
  margin-right: 3%;
  width: 150px;
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
#col4 {
  width: 15%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  text-align: center;
}
#col5,
#col6 {
  width: 10%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  text-align: center;
}
#col7 {
  width: 20%;
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
#book-pagination {
  height: 50px;
  margin-bottom: 15px;
}
</style>
