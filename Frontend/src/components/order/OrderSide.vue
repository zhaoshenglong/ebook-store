<template>
  <div class="side-bar">
    <ul>
      <li class="side-menu underline text-normal" @click="fetchAll">All orders</li>
      <li class="side-menu underline text-normal" @click="alterShow">Filter by time:</li>
      <div v-if="showFilter" id="time-input">
        <div class="time">
          <span>From</span>
          <input class="input-control input-block" type="text" v-model="fromTime">
        </div>
        <div class="time">
          <span>To</span>
          <input class="input-control input-block" type="text" v-model="toTime">
        </div>
        <div id="filterBtn">
          <button class="btn btn-block btn-submit" @click="updateTime">过滤</button>
        </div>
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
  methods: {
    alterShow() {
      this.showFilter = true;
    },
    updateTime() {
      var begin = this.fromTime;
      var end = this.toTime;
      this.$emit("updateTime", begin, end);
    },
    fetchAll() {
      if (!this.showFilter) return;
      this.showFilter = false;
      this.$emit("fetchAll");
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
#filterBtn {
  margin: 15px 10px 5px 70px;
}
</style>

