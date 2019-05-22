<template>
  <div>
    <div id="heading">
      <div class="col1">Avatar</div>
      <div class="col2 icon-container">
        Name
        <svg class="icon icon-sort" aria-hidden="true">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div class="col3 icon-container">
        Email
        <svg class="icon icon-sort" aria-hidden="true">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div class="col4">总消费</div>
      <div class="col5">State</div>
      <div class="col6">Operation</div>
    </div>
    <user-modify
      v-for="user in userList"
      :key="user.name"
      :user="user"
      @updateUser="updateUser"
      :state="user.state"
    ></user-modify>
    <div>
      <el-pagination
        :page-size="pager.size"
        layout="prev, pager, next"
        background
        :total="pager.total"
        @current-change="changPage(page)"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import UserModify from "../../components/admin/userModify";
import axios from "axios";
export default {
  components: {
    UserModify
  },
  data() {
    return {
      userList: [],
      pager: {
        size: 0,
        page: 0,
        total: 0
      },
      display: "",
      search: ""
    };
  },
  mounted() {
    this.fetchAllUsers(0);
  },
  computed: {},
  methods: {
    changePage(page) {
      if (this.display === "search") {
        this.fetchSearchUsers(page);
      } else {
        this.fetchAllUsers(page);
      }
    },
    fetchAllUsers(page) {
      this.display = "";
      axios
        .get("/api/admin/users/all", {
          params: {
            page: this.pager.page
          }
        })
        .then(response => {
          const data = response.data;
          this.userList = data.content;
          this.pager.size = data.pageSize;
          this.pager.total = data.totalElements;
          this.pager.page = page;
          console.log(response.data);
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchSearchUsers(page) {
      this.display = "search";
      axios
        .get("/api/admin/users/all", {
          params: {
            page: this.pager.page
          }
        })
        .then(response => {
          console.log(response.data);
          const data = response.data;
          this.userList = data.content;
          this.pager.size = data.pageSize;
          this.pager.total = data.totalElements;
          this.pager.page = page;
        })
        .catch(err => {
          console.log(err);
        });
    },
    updateUser(data) {
      axios
        .put("/api/admin/users/modify", data)
        .then(response => {
          this.userList.forEach(user => {
            if (user.name === data.name) {
              user.state = !user.state;
            }
          });
        })
        .catch(err => {
          console.log(err);
          this.$message({
            type: "error",
            message: "修改失败，服务器可能挂了:("
          });
        });
    }
  }
};
</script>

<style scoped>
#heading {
  margin: 25px 20px;
  background: #e7e7e7;
  height: 36px;
  line-height: 36px;
  display: flex;
  flex-direction: row;
  font-size: 24px;
  color: rgb(31, 31, 31);
}

.col1 {
  width: 20%;
}
.col2 {
  width: 15%;
}
.col3 {
  width: 15%;
}
.col4 {
  width: 15%;
}
.col5 {
  width: 15%;
}
.col6 {
  width: 10%;
}
.icon-container {
  display: flex;
  justify-content: center;
}
.icon-sort {
  cursor: pointer;
  font-size: 16px;
  position: relative;
  margin-left: 5px;
  top: 5px;
}
</style>
