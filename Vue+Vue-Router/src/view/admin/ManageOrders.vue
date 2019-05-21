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
      orderList: []
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
