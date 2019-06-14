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
      <el-menu-item index="5">搜索</el-menu-item>
    </el-menu>

    <div id="search-option" v-show="searchVisible">
      <span class="input-label">用户名</span>
      <el-input
        class="input-area"
        placeholder="输入用户名"
        prefix-icon="el-icon-search"
        v-model="search.user"
      ></el-input>

      <span class="input-label">书籍</span>
      <el-input
        class="input-area"
        placeholder="书名／isbn／作者"
        prefix-icon="el-icon-search"
        v-model="search.book"
      ></el-input>

      <span class="input-label">时间</span>
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
        @blur="setSearchDate"
      ></el-date-picker>

      <el-button
        class="search-btn"
        type="primary"
        icon="el-icon-search"
        @click="fetchOrderBySearch"
      >Search</el-button>
    </div>
    <div id="order-list">
      <order-modify v-for="order in orderList" :key="order.id" :order="order"></order-modify>
      <el-pagination
        background
        layout="prev, pager, next"
        :total="pager.total"
        :page-size="pager.size"
        :current-page.sync="pager.page"
        @current-change="changePage"
      ></el-pagination>
    </div>
    <!-- Miss statistics -->
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
      activeIndex: "1",
      pager: {
        page: 0,
        total: 0,
        size: 10
      },
      displayPage: "all",
      search: {
        user: "",
        book: "",
        date: []
      },
      searchVisible: false
    };
  },
  mounted() {
    this.fetchOrderByOption("all", 0);
  },
  methods: {
    changePage(page) {
      this.pager.page = page;
      page--;
      if (this.displayPage === "time") {
        this.fetchOrderByDate(page);
      } else if (this.displayPage === "all") {
        this.fetchOrderByOption("all", page);
      } else if (this.displayPage === "paid") {
        this.fetchOrderByOption("paid", page);
      } else if (this.displayPage === "unpaid") {
        this.fetchOrderByOption("unpaid", page);
      } else if (this.displayPage === "unpaid") {
        this.fetchOrderByOption("deleted", page);
      } else if (this.displayPage === "search") {
        this.fetchOrderBySearch();
      }
    },
    handleSelect(key, keyPath) {
      this.pager.page = 0;
      if (key == "5") {
        this.showSearch(true);
      } else {
        this.showSearch(false);
      }
      if (key == "1") {
        this.fetchOrderByOption("all", this.pager.page);
      } else if (key == "2") {
        this.fetchOrderByOption("paid", this.pager.page);
      } else if (key == "3") {
        this.fetchOrderByOption("unpaid", this.pager.page);
      } else if (key == "4") {
        this.fetchOrderByOption("deleted", this.pager.page);
      } else {
        //...
      }
    },
    setSearchDate(page) {
      this.displayPage = "search";
      let start, end;
      start = this.date[0];
      end = this.date[1];
      start = this.convertDateFmt(start);
      end = this.convertDateFmt(end);
      console.log(start);
      console.log(end);
      this.search.date = new Array(2);
      this.search.date[0] = start + " 00:00:00";
      this.search.date[1] = end + " 23:59:59";
    },
    convertDateFmt(date) {
      let mon = date.getMonth() + 1;
      if (mon < 10) {
        mon = "0" + mon;
      }
      return date.getFullYear() + "-" + mon + "-" + date.getDate();
    },
    fetchOrderByOption(option, page) {
      axios
        .get("/api/admin/orders/option", {
          params: {
            option: option,
            page: page
          }
        })
        .then(response => {
          const data = response.data;
          this.orderList = data.content;
          console.log(response);
          this.displayPage = option;
          this.pager.total = data.totalElements;
          this.pager.size = data.size;
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchOrderBySearch(page) {
      axios
        .get("/api/admin/orders/search", {
          params: {
            user: this.search.user,
            book: this.search.book,
            start: this.search.date[0],
            end: this.search.date[1]
          }
        })
        .then(response => {
          const data = response.data;
          this.orderList = data;
          console.log(response);
          this.displayPage = "search";
          this.pager.total = this.orderList.length;
          this.pager.size = this.pager.total;
        })
        .catch(err => {
          console.log(err);
        });
      console.log(this.search);
    },
    showSearch(flag) {
      this.searchVisible = flag;
    }
  },
  watch: {
    date: function(olddate) {
      console.log(olddate);
    }
  }
};
</script>

<style scoped>
#order-header {
  background: #dddddd;
  color: rgba(31, 32, 31, 1);
  margin: 25px 20px 5px 20px;
}
.input-label {
  display: inline-block;
  width: 80px;
  font-size: 18px;
  padding-top: 10px;
}
.input-area {
  width: 130px;
  display: inline-block;
}
#search-option {
  display: flex;
  justify-content: center;
}
.search-btn {
  margin-left: 15px;
}
</style>
