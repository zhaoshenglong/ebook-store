<template>
  <div id="order-container">
    <div id="order-header" :class="headerState">
      <section class="order-header-sec-left">
        <span>Costumer:</span>
        <span>
          <img id="costumer-avatar" :src="propOrder.user.avatar">
        </span>
        <el-popover placement="top-start" width="300" trigger="hover">
          <profile :user="propOrder.user"></profile>
          <span slot="reference" id="costumer-info">{{propOrder.user.name}}</span>
        </el-popover>
      </section>
      <section class="order-header-sec-mid">
        <span>Order Date:</span>
        <span>{{propOrder.createDate}}</span>
      </section>
      <section class="order-header-sec-right">
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
            <span>¥{{Number(item.bookPrice).toFixed(2)}}</span>
          </section>
        </li>
      </ul>
    </div>
    <div id="order-footer">
      <span>Total paid:</span>
      <span>RMB¥{{totalPrice}}</span>
    </div>
  </div>
</template>

<script>
import Profile from './profile'
export default {
  name: 'OrderModify',
  components: {
    Profile
  },
  data () {
    return {
      propOrder: {
        user: {
          avatar: ''
        },
        orderItemList: []
      }
    }
  },
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  mounted () {
    this.propOrder = this.$props.order
  },
  methods: {},
  computed: {
    totalPrice () {
      let res = 0
      this.propOrder.orderItemList.forEach(item => {
        res += item.quantity * item.bookPrice
      })
      return Number(res).toFixed(2)
    },
    orderState () {
      if (this.propOrder.state === 0) {
        return 'Unpaid'
      } else if (this.propOrder.state === 1) {
        return 'Paid'
      } else if (this.propOrder.state === 2) {
        return 'Deleted'
      } else {
        return 'Unknown'
      }
    },
    headerState () {
      if (this.propOrder.state === 0) {
        return 'header-unpaid'
      } else if (this.propOrder.state === 1) {
        return 'header-paid'
      } else if (this.propOrder.state === 2) {
        return 'header-deleted'
      } else {
        return 'header-unknown'
      }
    }
  },
  watch: {
    order: function (order) {
      this.propOrder = order
    }
  }
}
</script>

<style scoped>
#order-container {
  margin: 15px 20px 5px 20px;
  font-size: 18px;
  border-bottom: 1px solid #dddddd;
  color: rgb(31, 31, 31);
  padding-bottom: 2px;
}
#order-header {
  height: 32px;
  position: relative;
}
.order-header-sec-left {
  position: absolute;
  display: inline-block;
  margin: 6px 10px;
  left: 10px;
}
.order-header-sec-mid {
  position: absolute;
  display: inline-block;
  margin: 6px 10px;
  left: 40%;
}
.order-header-sec-right {
  position: absolute;
  display: inline-block;
  margin: 6px 10px;
  right: 10px;
}
#costumer-avatar {
  width: 24px;
  height: 24px;
  border-radius: 100px;
  position: absolute;
  bottom: -4px;
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
  margin: 5px 15px 2px 0;
  position: relative;
  left: 80%;
}
.header-paid {
  background: #b1e895;
}
.header-unpaid {
  background: #ffcb7c;
}
.header-deleted {
  background: #f8a2a2;
}
.header-unknown {
  background: #bbbbbb;
}
</style>
