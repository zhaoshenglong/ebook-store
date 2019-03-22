<template>
  <div class="side-bar">
    <ul>
      <li @click="fetchHistory" class="side-menu underline gradient-text">History</li>
      <li class="side-menu underline text-normal">Filter by time:</li>
      <li class="time"></li>
    </ul>
  </div>
</template>
<script>
export default {
  name: "SettingSide",
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
  methods: {
    fetchHistory() {}
  }
};
</script>
