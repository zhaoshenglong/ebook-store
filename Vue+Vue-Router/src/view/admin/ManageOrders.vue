<template>
  <div>
    <el-menu
      mode="horizontal"
      @select="handleSelect"
      id="order-header"
      :default-active="activeIndex"
    >
      <el-menu-item index="1">全部订单</el-menu-item>
      <el-menu-item index="2">已完成订单</el-menu-item>
      <el-menu-item index="3">未支付订单</el-menu-item>
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
    <!-- Misss search -->
    <div id="order-list">
      <order-modify v-for="order in orderList" :key="order.id" :order="order"></order-modify>
    </div>
    <!-- Misss statistics -->
    <div id="statisticcs"></div>
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
    this.fetchOrderByOption("all");
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
      start = this.date[0];
      end = this.date[1];
      console.log(start);
      console.log(end);
      start = this.convertDateFmt(start);
      end = this.convertDateFmt(end);
      axios
        .get("/api/admin/orders/between", {
          params: {
            start: start + "00:00:00",
            end: end + "23:59:59",
            page: 0
          }
        })
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        });
    },
    convertDateFmt(date) {
      return (
        date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate()
      );
    },
    fetchOrderByOption(option) {
      axios
        .get("/api/admin/orders/option", {
          params: {
            option: option,
            page: 0
          }
        })
        .then(response => {
          this.orderList = response.data.content;
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
