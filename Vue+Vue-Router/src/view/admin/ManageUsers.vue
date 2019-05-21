<template>
  <div>
    <div id="heading">
      <div class="col1">Avatar</div>
      <div class="col2 icon-container">
        Name
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('name')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div class="col3 icon-container">
        Email
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div class="col4">总消费</div>
      <div class="col5">State</div>
      <div class="col6">Operation</div>
    </div>
    <user-modify v-for="user in userList" :key="user.id" :user="user"></user-modify>
  </div>
</template>

<script>
import UserModify from "../../components/admin/userModify";
export default {
  components: {
    UserModify
  },
  data() {
    return {
      userList: []
    };
  },
  computed: {},
  methods: {
    sortUser(sign) {
      if (sign === "id") {
        this.userList.sort((u1, u2) => {
          return u1.id - u2.id;
        });
      } else {
        this.userList.sort((u1, u2) => {
          if (u1.name > u2.name) return 1;
          else if (u1.name <= u2.name) return -1;
        });
      }
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
