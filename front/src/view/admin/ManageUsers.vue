<template>
  <div>
    <div id="heading">
      <div class="col1">Avatar</div>
      <div class="col2 icon-container">
        ID
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('id')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div class="col3 icon-container">
        Name
        <svg class="icon icon-sort" aria-hidden="true" @click="sortUser('name')">
          <use xlink:href="#iconsort"></use>
        </svg>
      </div>
      <div class="col4">Authentication</div>
      <div class="col5">Management</div>
    </div>
    <user-modify v-for="user in filterUserList" :key="user.id" :user="user"></user-modify>
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
      userList: [
        {
          id: "165456",
          avatar:
            "https://b-ssl.duitang.com/uploads/item/201410/16/20141016202155_5ycRZ.thumb.1900_0.jpeg",
          authen: true,
          name: "+QQ15646541"
        },
        {
          id: "15165",
          avatar:
            "http://img4.imgtn.bdimg.com/it/u=3630436912,4127152828&fm=26&gp=0.jpg",
          authen: true,
          name: "Monkey.D.Luffy"
        }
      ]
    };
  },
  computed: {
    filterUserList() {
      var filterList = [];
      this.userList.forEach(user => {
        filterList.push(user);
      });
      return filterList;
    }
  },
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
  flex: 1;
}
.col4 {
  width: 15%;
}
.col5 {
  width: 20%;
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
