<template>
  <div>
    <div>
      <svg class="icon minus-pink" aria-hidden="true">
        <use xlink:href="#iconminus"></use>
      </svg>
      <span class="quantity">3</span>
      <svg class="icon plus-blue" aria-hidden="true">
        <use xlink:href="#iconplus"></use>
      </svg>
    </div>
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
