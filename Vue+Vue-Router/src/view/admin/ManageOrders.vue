<template>
  <div>
    <el-menu
      mode="horizontal"
      @select="handleSelect"
      id="order-header"
      :default-active="activeIndex"
    >
      <el-menu-item index="1">全部订单</el-menu-item>
      <el-menu-item index="2">未支付订单</el-menu-item>
      <el-menu-item index="3">已完成订单</el-menu-item>
      <el-menu-item index="4">已删除订单</el-menu-item>
      <el-menu-item index="5">
        时间过滤
        <el-date-picker
          v-model="date"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="To"
          start-placeholder="Start date"
          end-placeholder="End date"
          :picker-options="pickerOptions"
          format="yyyy-MM-dd"
          @blur="fetchOrderByDate"
        ></el-date-picker>
      </el-menu-item>
    </el-menu>
    <div id="order-list">
      <order-modify v-for="order in orderList" :key="order.id" :order="order"></order-modify>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import OrderModify from "../../components/admin/orderModify";
export default {
  name: "MangeOrders",
  components: {
    OrderModify
  },
  data() {
    return {
      orderList: [],
      pickerOptions: {
        shortcuts: [
          {
            text: "Last week",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "Last month",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "Last 3 months",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      },
      date: [],
      activeIndex: "1"
    };
  },
  mounted() {
    //this.fetchOrderByOption("all");
    this.orderList.push({
      id: "123456",
      user: {
        name: "zhaoshenglong",
        avatar: "none"
      },
      createDate: "2019-05-17",
      orderItemList: [
        {
          book: {
            name: "book1",
            author: "author1",
            isbn: "97897813511",
            img: "none"
          },
          price: 53.0,
          quantity: 5,
          id: "dwadawada"
        }
      ]
    });
  },
  methods: {
    handleSelect(key, keyPath) {
      if (key == "1") {
        this.fetchOrderByOption("all");
      } else if (key == "2") {
        this.fetchOrderByOption("paid");
      } else if (key == "3") {
        this.fetchOrderByOption("unpaid");
      } else {
        this.fetchOrderByOption("deleted");
      }
    },
    fetchOrderByDate() {
      let start, end;

      axios
        .get("/api/admin/orders/between", {
          params: {
            start: start,
            end: end
          }
        })
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchOrderByOption(option) {
      axios
        .get("/api/admin/orders/option", {
          params: {
            option: option
          }
        })
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  watch: {
    date: function(date) {
      console.log(date);
    }
  }
};
</script>

<style scoped>
#order-header {
  background: #dddddd;
  color: rgba(31, 32, 31, 1);
  margin: 25px 20px;
}
</style>
