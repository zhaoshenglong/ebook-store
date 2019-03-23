<template>
  <div id="user-container">
    <div class="col1" id="img-container">
      <img :src="user.avatar" alt="avatar">
    </div>
    <div class="col2">{{user.id}}</div>
    <div class="col3">{{user.name}}</div>
    <div class="col4">{{user.authen}}</div>
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
      forbidden: false
    };
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
}

#img-container img {
  width: 80px;
  height: 80px;
  border-radius: 100px;
  position: relative;
  top: 10px;
}
.col1 {
  width: 25%;
}
.col2 {
  width: 20%;
}
.col3 {
  flex: 1;
}
.col4 {
  width: 25%;
}
</style>
