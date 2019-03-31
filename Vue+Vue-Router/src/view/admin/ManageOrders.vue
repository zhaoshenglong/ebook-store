<template>
  <div>
    <div id="order-header">
      <section class="col1 icon-container">
        <div>Order ID</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </section>
      <section class="col2">
        <div>Avatar</div>
      </section>
      <section class="col3 icon-container">
        <div>User name</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </section>
      <section class="col4 icon-container">
        <div>Order date</div>
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </section>
      <section class="col5">
        <div>Order content</div>
      </section>
    </div>
    <div id="order-list">
      <order-modify v-for="order in orderList" :key="order.id" :order="order"></order-modify>
    </div>
  </div>
</template>

<script>
import OrderModify from "../../components/admin/orderModify";
export default {
  name: "MangeOrders",
  components: {
    OrderModify
  },
  data() {
    return {
      orderList: [
        {
          id: "012",
          date: "13/03/2019",
          consumer: {
            img:
              "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554043098900&di=182bfc32ef9cabd07522cdb1485ee6fc&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201706%2F22%2F20170622131955_h4eZS.thumb.700_0.jpeg",
            name: "zsl"
          },
          books: [
            {
              img:
                "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
              name: "The Journey to the West, Revised Edition, Volume 3",
              isbn: "0123"
            },
            {
              img:
                "https://img3.doubanio.com/view/subject/l/public/s1103152.jpg",
              name: "小王子",
              isbn: "1234"
            },
            {
              img:
                "https://img3.doubanio.com/view/subject/l/public/s29651121.jpg",
              name: "房思琪的初恋乐园",
              isbn: "2345"
            }
          ]
        },
        {
          id: "123",
          date: "28/03/2019",
          consumer: {
            img:
              "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554043098900&di=8a30d9e8e48de1d571df717cfbc53bd3&imgtype=0&src=http%3A%2F%2Fimg1.mydrivers.com%2Fimg%2F20181105%2FSc468f356-6839-4b7b-8bd4-4dd06b218478.jpg",
            name: "hxc"
          },
          books: [
            {
              img:
                "https://img1.doubanio.com/view/subject/l/public/s29799269.jpg",
              name: "失踪的孩子",
              isbn: "3456"
            },
            {
              img:
                "https://img1.doubanio.com/view/subject/l/public/s2768378.jpg",
              name: "三体",
              isbn: "4567"
            },
            {
              img:
                "https://img3.doubanio.com/view/subject/l/public/s2153661.jpg",
              name: "苏菲的世界",
              isbn: "7890"
            },
            {
              img:
                "https://images-na.ssl-images-amazon.com/images/I/51rA-Zqu2-L._SX331_BO1,204,203,200_.jpg",
              name: "The Journey to the West, Revised Edition, Volume 3",
              isbn: "0123"
            },
            {
              img:
                "https://img3.doubanio.com/view/subject/l/public/s1103152.jpg",
              name: "小王子",
              isbn: "1234"
            },
            {
              img:
                "https://img3.doubanio.com/view/subject/l/public/s29651121.jpg",
              name: "房思琪的初恋乐园",
              isbn: "2345"
            }
          ]
        }
      ]
    };
  }
};
</script>

<style scoped>
#order-header {
  background: #dddddd;
  height: 30px;
  line-height: 30px;
  color: rgba(31, 32, 31, 1);
  font-size: 24px;
  text-align: center;
  display: flex;
  flex-direction: row;
  margin: 25px 20px;
}
.col1,
.col4 {
  width: 200px;
}
.col3,
.col2 {
  width: 250px;
}
.col5 {
  width: 300px;
}
.icon-container {
  display: flex;
  justify-content: center;
}
.icon-sort {
  cursor: pointer;
  font-size: 16px;
  position: relative;
  top: 4px;
}
</style>
