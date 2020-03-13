<template>
  <div>
    <el-menu
      mode="horizontal"
      @select="handleSelect"
      id="order-header"
      :default-active="activeIndex"
    >
      <el-menu-item index="1">书籍统计</el-menu-item>
      <el-menu-item index="2">用户统计</el-menu-item>
      <el-menu-item>
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
      </el-menu-item>
      <el-menu-item>
        <el-button type="primary" @click="analyse">分析统计</el-button>
      </el-menu-item>
    </el-menu>
    <el-table
      v-show="viewKey == 1"
      :data="books"
      border
      style="width: 100%"
      :default-sort="{prop: 'sale', order: 'descending'}"
    >
      <el-table-column prop="name" label="NAME" width="180"></el-table-column>
      <el-table-column prop="isbn" label="ISBN" width="180"></el-table-column>
      <el-table-column prop="sale" label="SALE" sortable></el-table-column>
    </el-table>
    <el-table
      v-show="viewKey == 2"
      :data="users"
      border
      style="width: 100%"
      :default-sort="{prop: 'partConsume', order: 'descending'}"
    >
      <el-table-column prop="name" label="NAME" width="180"></el-table-column>
      <el-table-column prop="totalConsume" label="TOTAL CONSUME" width="180" sortable></el-table-column>
      <el-table-column prop="partConsume" label="PART CONSUME" sortable></el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "ManageStatistics",
  mounted() {
    this.init();
    this.fetchAllBooks();
    this.fetchAllUsers();
  },
  data() {
    return {
      activeIndex: "1",
      viewKey: 1,
      books: [],
      users: [],
      orders: [],
      date: [],
      searchDate: [],
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
      }
    };
  },

  methods: {
    init() {
      this.searchDate.push("2000-01-01" + " 00:00:00");
      this.searchDate.push("2020-12-11" + " 23:59:59");
    },
    handleSelect(key, keyPath) {
      if (key == "1") {
        this.viewKey = 1;
      } else if (key == "2") {
        this.viewKey = 2;
      } else {
        //...
      }
    },
    fetchAllBooks() {
      axios
        .get("/api/admin/books/statistics/all", {
          params: {
            start: this.date[0],
            end: this.date[1]
          }
        })
        .then(response => {
          const data = response.data;
          this.books = data;
          console.log(data);
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchAllUsers() {
      console.log(this.searchDate[0]);
      axios
        .get("/api/admin/users/statistics/all", {
          params: {
            start: this.searchDate[0],
            end: this.searchDate[1]
          }
        })
        .then(response => {
          const data = response.data;
          this.users = data;
          console.log(this.date[0]);
          console.log(data);
        })
        .catch(err => {
          console.log(err);
        });
    },
    analyseUsers() {
      this.fetchAllUsers();
    },
    analyseBooks() {
      // do nothing
    },
    analyse() {
      if (this.viewKey == 1) {
        this.analyseBooks();
      } else {
        this.analyseUsers();
      }
    },
    setSearchDate() {
      let start, end;
      start = this.date[0];
      end = this.date[1];
      start = this.convertDateFmt(start);
      end = this.convertDateFmt(end);
      this.searchDate = new Array(2);
      this.searchDate[0] = start + " 00:00:00";
      this.searchDate[1] = end + " 23:59:59";
    },
    convertDateFmt(date) {
      let mon = date.getMonth() + 1;
      if (mon < 10) {
        mon = "0" + mon;
      }
      return date.getFullYear() + "-" + mon + "-" + date.getDate();
    }
  }
};
</script>

<style>
#order-header {
  margin: 10px 0 5px 0;
}
</style>
