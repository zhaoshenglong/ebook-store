<template>
  <div id="order-container">
    <div id="order-header">
      <section class="order-header-sec">
        <span>Order ID:</span>
        <span>{{propOrder.id}}</span>
      </section>
      <section class="order-header-sec">
        <span>Costumer:</span>
        <span>
          <img id="costumer-avatar" :src="propOrder.user.avatar">
        </span>
        <span id="costumer-info">{{propOrder.user.name}}</span>
      </section>
      <section class="order-header-sec">
        <span>Order Date:</span>
        <span>{{propOrder.createDate}}</span>
      </section>
      <section class="order-header-sec">
        <span>State:</span>
        <span>{{orderState}}</span>
      </section>
    </div>
    <div>
      <ul>
        <li id="list-item" v-for="item in propOrder.orderItemList" :key="item.id">
          <section class="img-sec">
            <img id="item-book-image" :src="item.book.img">
          </section>
          <section class="item-sec">
            <p>{{item.book.name}}</p>
          </section>
          <section class="item-sec">
            <p>{{item.book.author}}</p>
          </section>
          <section class="item-sec">
            <span>{{item.book.isbn}}</span>
          </section>
          <section class="item-sec">
            <span>{{item.quantity}}</span>
          </section>
          <section class="item-sec">
            <span>{{item.price}}</span>
          </section>
        </li>
      </ul>
    </div>
    <div id="order-footer">
      <span>Total paid:</span>
      <span>{{totalPrice}}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderModify",
  data() {
    return {
      propOrder: {}
    };
  },
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  mounted() {
    this.propOrder = this.$props.order;
    console.log(this.propOrder);
  },
  methods: {},
  computed: {
    totalPrice() {
      let res = 0;
      this.propOrder.orderItemList.forEach(item => {
        res += item.quantity * item.price;
      });
      return res;
    },
    orderState() {
      if (this.propOrder.state === 0) {
        return "Unpaid";
      } else if (this.propOrder.state === 1) {
        return "Paid";
      } else if (this.propOrder.state === 2) {
        return "Deleted";
      } else {
        return "Unknown";
      }
    }
  },
  watch: {
    order: function(order) {
      this.propOrder = order;
      console.log(this.propOrder);
    }
  }
};
</script>

<style scoped>
#order-container {
  margin: 25px 20px;
  font-size: 18px;
  border-bottom: 1px solid #dddddd;
  color: rgb(31, 31, 31);
}
#order-header {
  background: #dddddd;
  height: 32px;
  position: relative;
}
.order-header-sec {
  display: inline-block;
  margin: 6px 10px;
}
#costumer-avatar {
  width: 24px;
  height: 24px;
  border-radius: 100px;
  position: absolute;
  top: 3px;
}
#costumer-info {
  margin-left: 28px;
}
#item-book-image {
  width: 56px;
}
.img-sec {
  position: absolute;
  left: 15px;
  top: 10px;
}
.item-sec {
  display: inline-block;
  height: 100%;
  margin: 5px 5px;
  width: 14%;
}
.item-sec span {
  display: inline-block;
  padding-top: 30px;
}
#list-item {
  height: 96px;
  position: relative;
}
#list-item section {
  height: 100%;
}
#order-footer {
  width: 20%;
  margin: 0;
  position: relative;
  left: 80%;
}
</style>
