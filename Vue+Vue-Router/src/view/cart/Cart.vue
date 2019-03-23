<template>
  <div class="view-container">
    <cart-side></cart-side>
    <div class="view-main">Cart</div>
  </div>
</template>
<script>
import CartSide from "../../components/cart/CartSide";
export default {
  name: "Cart",
  data() {
    return {
      order: [
        {
          totalPrice: "123",
          date: "13/03/2019",
          quantity: "8",
          nameList: [],
          imgList: [],
          orderId: "01"
        }
      ]
    };
  },
  components: {
    CartSide
  }
};
</script>
<style scoped>
</style>
