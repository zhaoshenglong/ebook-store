<template>
  <div>
    <div></div>
    <book-remark></book-remark>
  </div>
</template>
<script>
import BookRemark from "./Remark";
export default {
  name: "BookDetail",
  props: {
    book: {
      type: Object,
      required: true
    }
  },

  components: {
    BookRemark
  }
};
</script>
<style scoped>
</style>
