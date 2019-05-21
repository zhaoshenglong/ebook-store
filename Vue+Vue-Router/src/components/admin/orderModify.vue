<template>
  <div id="order-container"></div>
</template>

<script>
export default {
  name: "OrderModify",
  data() {
    return {
      propOrder: {}
    };
  },
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  mounted() {
    this.propOrder = this.$props.order;
    console.log(this.propOrder);
  },
  methods: {},
  computed: {
    totalPrice() {}
  },
  watch: {
    order: function(order) {
      this.propOrder = order;
      console.log(this.propOrder);
    }
  }
};
</script>

<style scoped>
#order-container {
  margin: 25px 20px;
  width: 100%;
  font-size: 18px;
  border-bottom: 1px solid #dddddd;
  color: rgb(31, 31, 31);
}
</style>
