<template>
  <div class="side-bar">
    <ul>
      <li
        v-for="item in menuList"
        :key="item.id"
        :class="['side-menu', 'gradient-text', 'underline']"
        @click="display(item.name)"
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
          id: "0",
          name: "All"
        },
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
  props: {
    displayTag: {
      type: String,
      default: "All"
    }
  },
  methods: {
    display(tag) {
      this.$emit("changeDisplayTag", tag);
    }
  },
  computed: {
    displayTagName() {
      var name = this.displayTag;
      return name;
    }
  }
};
</script>
<style scoped>
.focus {
  color: #2d2d2d;
  background: red;
}
</style>
