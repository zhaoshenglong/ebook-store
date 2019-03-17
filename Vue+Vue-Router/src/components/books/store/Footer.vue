<template>
  <div id="footer">
    &copy;{{year}} Dragon
    <a
      href="https://github.com/zhaoshenglong"
      target="_blank"
      title="contact me"
    >
      <svg id="github" class="icon icon-contact" aria-hidden="true">
        <use xlink:href="#icongithub"></use>
      </svg>
    </a>
    <span class="QR-container">
      <svg
      id="weixin"
      class="icon icon-contact"
      aria-hidden="true"
      @mouseenter="weixin=true"
      @mouseleave="weixin=false"
    >
      <use xlink:href="#iconweixin"></use>
    </svg>
    <img
      width="200px"
      height="200px"
      v-show="weixin"
      id="weixin-img"
      src="../../../../static/QRcode/weixin.png"
    >
    </span>
    <span class="QR-container">
      <svg
      id="qq"
      class="icon icon-contact"
      aria-hidden="true"
      @mouseenter="qq=true"
      @mouseleave="qq=false"
    >
      <use xlink:href="#iconqq-copy-copy"></use>
    </svg>
    <img 
      width="200px"
      height="200px"
      v-show="qq"
      id="qq-img"
      src="../../../../static/QRcode/qq.jpg">
    </span>
  </div>
</template>
<script>
export default {
  name: "Footer",
  data() {
    return {
      year: 2019,
      weixin: false,
      qq: false
    }
  }
};
</script>
<style scoped>
#footer {
  height: 36px;
  font-size: 28px;
  line-height: 36px;
  text-align: center;
  background: rgba(229, 252, 251, 0.8);
  margin: 0 auto;
  padding: 10px 0;
  position: relative;
}
.icon-contact {
  width: 1.2em;
  height: 1.2em;
  margin: auto 5px;
  color: #7a7c7c;
  position: relative;
}
.QR-container{
  position: relative;
}
#github:hover {
  color: #000000;
}
#weixin:hover {
  color: #35c465;
}
#qq:hover {
  color: #35a3c4;
}
#weixin-img {
  position: absolute;
  right: -75px;
  bottom: 36px;
}
#qq-img {
  position: absolute;
  right: -75px;
  bottom: 36px;
}
</style>
