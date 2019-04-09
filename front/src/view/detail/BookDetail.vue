<template>
  <div class="view-main">
    <book-info :book="bookInfo"></book-info>
    <book-content></book-content>
    <book-remark
      v-for="(remark, index) in bookRemark"
      :key="remark.id"
      :remark="remark"
      :stair="index+1"
    ></book-remark>
  </div>
</template>
<script>
import BookRemark from "../../components/remark/Remark";
import BookInfo from "../../components/book/BookInfo";
import BookContent from "../../components/book/BookContent";
export default {
  name: "BookDetail",
  data() {
    return {
      bookInfo: {
        img:
          "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
        name: "The Journey to the West, Revised Edition, Volume 3",
        id: "151546545",
        isbn: "ISBN950310257",
        price: "31.00",
        remain: "49",
        author: "Anthony C. Yu",
        tags: ["Hottest", "Novel"]
      },
      bookRemark: [
        {
          id: "12",
          userName: "+QQ1546784",
          userImg:
            "https://b-ssl.duitang.com/uploads/item/201410/16/20141016202155_5ycRZ.thumb.1900_0.jpeg",
          date: "13/03/2019",
          helpful: 9,
          content: "hhdhaishdihawoih"
        },
        {
          id: "123",
          userName: "Monkey.D.Luffy",
          userImg:
            "http://img4.imgtn.bdimg.com/it/u=3630436912,4127152828&fm=26&gp=0.jpg",
          date: "16/03/2019",
          helpful: 33,
          content:
            "I love this book so much that I collect so many different souvenir kinds."
        }
      ]
    };
  },
  mounted: function() {
    this.fetchBookData();
  },
  components: {
    BookRemark,
    BookInfo,
    BookContent
  },
  methods: {
    fetchBookData() {}
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
</style>
