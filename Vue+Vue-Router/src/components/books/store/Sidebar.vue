<template>
  <div id="book-side">
    <ul>
      <li
        class="menu gradient-text"
        v-for="item in menuList"
        :key="item.id"
        @click="display(item.id)"
      >{{item.name}}</li>
    </ul>
  </div>
</template>
<script>
export default {
  name: "BookSide",
  data() {
    return {
      menuList: [
        {
          id: "1",
          name: "Latest"
        },
        {
          id: "2",
          name: "Hottest"
        },
        {
          id: "3",
          name: "Literature"
        },
        {
          id: "4",
          name: "Story"
        },
        {
          id: "5",
          name: "Technology"
        },
        {
          id: "6",
          name: "History"
        },
        {
          id: "7",
          name: "English"
        },
        {
          id: "8",
          name: "Novel"
        }
      ]
    };
  },
  methods: {
    display(id) {
      this.$emit("changeDisplay", id);
    }
  }
};
</script>
<style scoped>
#book-side {
  height: 100%;
  min-height: 1000px;
  width: 25%;
  float: left;
  padding-top: 50px;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  background: -webkit-linear-gradient(
    to right,
    rgba(229, 252, 251, 0.7),
    rgba(0, 255, 255, 1)
  );
  background: linear-gradient(
    to right,
    rgba(229, 252, 251, 0.7),
    rgba(255, 255, 255, 0.7)
  );
}

.menu {
  font-weight: 600;
  font-size: 40px;
  text-align: left;
  margin-top: 15px;
  margin-bottom: 25px;
  padding: 10px 20px;
  position: relative;
}
.gradient-text {
  background: -webkit-linear-gradient(white, #444444);
  background-clip: text;
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
}
.menu:hover,
.gradient-text:hover {
  cursor: pointer;
  background: #444444;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
}
.menu:before {
  content: "";
  border-bottom: 2px solid #35a3c4;
  width: 5px;
  transition: all 0.25s;
  height: 100%;
  position: absolute;
  left: -10px;
  bottom: 5px;
}
.menu:hover::before {
  width: 100%;
}
</style>
