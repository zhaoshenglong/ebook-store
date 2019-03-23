<template>
  <div id="user-container">
    <div class="col1" id="img-container">
      <img :src="user.avatar" alt="avatar">
    </div>
    <div class="col2">{{user.id}}</div>
    <div class="col3">{{user.name}}</div>
    <div class="col4" :style="styleColor">{{state}}</div>
    <div class="col5">
      <svg class="icon icon-on" aria-hidden="true" v-show="authen" @click="switchOnOff">
        <use xlink:href="#icontoggleon"></use>
      </svg>
      <svg class="icon icon-off" aria-hidden="true" v-show="!authen" @click="switchOnOff">
        <use xlink:href="#icontoggle-off"></use>
      </svg>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      authen: true
    };
  },
  computed: {
    state() {
      if (this.authen) return "Activated";
      else return "Forbidden";
    },
    styleColor() {
      if (this.authen)
        return {
          color: "green"
        };
      else
        return {
          color: "red"
        };
    }
  },
  mounted: function() {
    this.authen = this.$props.user.authen;
  },
  methods: {
    switchOnOff() {
      if (this.authen) this.authen = false;
      else this.authen = true;
    }
  }
};
</script>

<style>
#user-container {
  height: 100px;
  margin: 25px 20px;
  line-height: 100px;
  display: flex;
  flex-direction: row;
  font-size: 24px;
  border-bottom: 1px solid #dddddd;
}

#img-container img {
  width: 80px;
  height: 80px;
  border-radius: 100px;
  position: relative;
  top: 10px;
}
.col1 {
  width: 20%;
}
.col2 {
  width: 15%;
}
.col3 {
  flex: 1;
}
.col4 {
  width: 15%;
}
.col5 {
  width: 20%;
}
.icon-on {
  color: green;
  position: relative;
  top: 8px;
}
.icon-off {
  color: red;
  position: relative;
  top: 8px;
}
</style>
