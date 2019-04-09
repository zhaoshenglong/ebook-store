<template>
  <div class="side-bar">
    <ul>
      <li @click="fetchHistory" class="side-menu underline gradient-text">History</li>
      <li></li>
      <li class="side-menu underline text-normal" @click="alterShow">Filter by time:</li>
      <div v-if="showFilter" id="time-input">
        <li class="time">
          <span>From</span>
          <input class="input-control input-block" type="text" v-model="fromTime">
        </li>
        <li class="time">
          <span>To</span>
          <input class="input-control input-block" type="text" v-model="toTime">
        </li>
      </div>
    </ul>
  </div>
</template>
<script>
export default {
  name: "SettingSide",
  data() {
    return {
      fromTime: "",
      toTime: "",
      showFilter: false
    };
  },
  mounted: function() {
    this.fetchHistory();
  },
  methods: {
    fetchHistory() {},
    alterShow() {
      this.showFilter = true;
    }
  }
};
</script>
<style scoped>
.time {
  font-size: 15px;
  width: 100%;
  margin-top: 10px;
  border: 0;
  display: flex;
  height: 36px;
  line-height: 36px;
}
.time input {
  flex: 1;
}
.time span {
  display: inline-block;
  width: 48px;
  margin-right: 10px;
  text-align: right;
}
#time-input {
  position: relative;
  bottom: 20px;
}
</style>

