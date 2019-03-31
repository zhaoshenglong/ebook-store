<template>
  <div id="order-container">
    <section id="order-id-sec" class="col1">
      <span>{{order.id}}</span>
    </section>
    <section id="consumer-img-sec" class="col2">
      <img :src="order.consumer.img" alt="user picture">
    </section>
    <section id="consumer-name-sec" class="col5">
      <div>{{order.consumer.name}}</div>
    </section>
    <section id="date-sec" class="col3">
      <span>{{order.date}}</span>
    </section>
    <section id="img-container" class="col4">
      <div id="img-mask">
        <div id="book-scroll">
          <img
            v-for="book in order.books"
            :key="book.isbn"
            :src="book.img"
            :alt="book.name"
            :title="book.name"
          >
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "OrderModify",
  data() {
    return {};
  },
  props: {
    order: {
      type: Object,
      required: true
    }
  }
};
</script>

<style scoped>
#order-container {
  height: 150px;
  margin: 25px 20px;
  line-height: 150px;
  display: flex;
  flex-direction: row;
  font-size: 24px;
  border-bottom: 1px solid #dddddd;
  color: rgb(31, 31, 31);
}

.col1,
.col3 {
  width: 200px;
}
.col2,
.col5 {
  width: 250px;
}
.col4 {
  width: 300px;
}
#consumer-img-sec img {
  display: block;
  height: 110px;
  border-radius: 100px;
  margin: 5px auto;
}
#img-container {
  height: 130px;
  width: 310px;
  overflow: hidden;
}
#img-mask {
  margin-left: 5px;
  width: 300px;
  height: 100%;
  position: relative;
  box-shadow: 0 0 10px rgba(255, 255, 255, 1);
}
#img-mask:before {
  content: "";
  width: 100px;
  height: 130px;
  background: linear-gradient(
    to right,
    rgba(255, 255, 255, 1),
    rgba(255, 255, 255, 0)
  );
  z-index: 2;
  position: absolute;
  left: 0;
}
#img-mask:after {
  content: "";
  width: 100px;
  height: 130px;
  background: linear-gradient(
    to left,
    rgba(255, 255, 255, 1),
    rgba(255, 255, 255, 0)
  );
  z-index: 2;
  position: absolute;
  right: 0;
  top: 0;
}
/* Why can not assign height & width here ? */
#book-scroll {
  overflow-x: scroll;
  overflow-y: visible;
  white-space: nowrap;
}
#book-scroll img {
  height: 120px;
  width: 90px;
  display: inline-block;
  margin: 5px 5px;
  border-radius: 8px;
}
</style>
