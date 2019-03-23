<template>
  <div>
    <book-remark></book-remark>
  </div>
</template>
<script>
import BookRemark from "../../components/remark/Remark";
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
.quantity {
  font-size: 32px;
}
.plus-blue {
  color: #35a3c4;
}
</style>
