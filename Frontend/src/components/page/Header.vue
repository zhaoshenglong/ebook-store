<template>
  <div id="header">
    <div id="banner">
      <img :src="imgUrl">
    </div>
  </div>
</template>
<script>
export default {
  name: 'Header',
  data () {
    return {
      imgUrl: '../../../static/logo/headPic.png'
    }
  }
}
</script>
<style scoped>
#header {
  padding-top: 12px;
  width: 100%;
  height: 80px;
  background: rgba(229, 252, 251, 0.8);
}
#banner {
  margin: 0 auto;
  position: relative;
  height: 66px;
  width: 1017px;
}
#banner:before,
#banner:after {
  border: 0 solid transparent;
  transition: all 0.25s;
  content: "";
  height: 24px;
  position: absolute;
  width: 24px;
}
#banner:before {
  border-top: 2px solid #35a3c4;
  left: 0px;
  top: -5px;
}
#banner:after {
  border-bottom: 2px solid #35a3c4;
  bottom: -5px;
  right: 0px;
}
#banner:hover {
  background-color: #35a3c4;
}
#banner:hover:before,
#banner:hover:after {
  height: 100%;
  width: 100%;
}
</style>
