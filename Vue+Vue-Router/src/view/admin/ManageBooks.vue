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
        <div>Remain</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div id="col7"></div>
    </div>
    <div>
      <book-modify v-for="book in filterBookList" :key="book.id" :book="book"></book-modify>
    </div>
  </div>
</template>

<script>
import BookModify from "../../components/admin/bookModify";
export default {
  components: {
    BookModify
  },
  data() {
    return {
      bookList: [
        {
          id: "135",
          name: "小王子",
          isbn: "9787020042494",
          author: " [法] 圣埃克苏佩里",
          price: "22.00",
          tags: ["Literature", "Hottest"],
          img: "https://img3.doubanio.com/view/subject/l/public/s1103152.jpg",
          remain: "55"
        },
        {
          id: "125",
          name: "天才在左 疯子在右",
          isbn: "9787307075429",
          author: " 高铭",
          price: "29.80",
          tags: ["Literature", "Lattest"],
          img: "https://img1.doubanio.com/view/subject/l/public/s6340977.jpg",
          remain: "56"
        },
        {
          id: "155",
          name: "房思琪的初恋乐园",
          isbn: "9787559614636",
          author: " 林奕含 ",
          price: "45.00",
          tags: ["Novel", "Hottest"],
          img: "https://img3.doubanio.com/view/subject/l/public/s29651121.jpg",
          remain: "56"
        },
        {
          id: "1655",
          name: "失踪的孩子",
          isbn: "9787559614636",
          author: "埃莱娜·费兰特",
          price: "62.00",
          tags: ["Novel", "Hottest"],
          img: "https://img1.doubanio.com/view/subject/l/public/s29799269.jpg",
          remain: "56"
        }
      ]
    };
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
  methods: {}
};
</script>

<style scoped>
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
