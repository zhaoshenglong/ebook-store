<template>
  <div id="store-main">{{searchMsg}}</div>
</template>
<script>
export default {
  name: "StoreMain",
  props: {
    bookList: {
      type: Array,
      required: true
    },
    searchMsg: {
      type: String,
      required: true
    }
  },
  computed: {
    filterBooks() {
      return this.displayBooks;
    }
  }
};
</script>
<style scoped>
#store-main {
  flex: 1;
  height: 100%;
  background: #444444;
  min-height: 1000px;
  float: left;
}
</style>
