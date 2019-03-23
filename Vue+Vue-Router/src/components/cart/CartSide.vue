<template>
  <div class="side-bar">
    <ul>
      <li class="side-menu underline gradient-text">Current</li>
    </ul>
  </div>
</template>
<script>
export default {
  name: "CartSide",
  mounted: function() {
    this.fetchCart();
  },
  methods: {
    fetchCart() {}
  }
};
</script>
