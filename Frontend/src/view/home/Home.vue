<template>
  <div id="home">
    <div id="back-cover">
      <div id="banner">
        <img id="logo" src="../../../static/logo/logo3.png" @click="goBack" alt="Turn back">
        <a id="sign-in" class="sign-link" @click="signIn">sign in</a>
        <a id="sign-up" class="sign-link" @click="signUp">sign up</a>
      </div>
      <transition name="component-fade" mode="out-in">
        <router-view/>
      </transition>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    goBack () {
      this.$router.push({ name: 'StorePage' })
    },
    signIn () {
      this.$router.push({ name: 'SignIn' })
    },
    signUp () {
      this.$router.push({ name: 'SignUp' })
    }
  }
}
</script>

<style scoped>
#home {
  background-image: url("../../../static/background/girl.jpg");
  background-position: center;
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-size: cover;
  min-height: 100%;
  height: 1024px;
  min-width: 1024px;
}
#back-cover {
  min-height: 100%;
  background: linear-gradient(
    to right,
    rgba(255, 255, 255, 1),
    rgba(255, 255, 255, 0)
  );
}
#banner {
  width: 100%;
  height: 100px;
  background: linear-gradient(
    to right,
    rgba(255, 255, 255, 1),
    rgba(255, 255, 255, 0.4)
  );
}
#logo {
  position: relative;
  width: 160px;
  height: 74px;
  top: 16px;
  left: 10%;
  cursor: pointer;
  border-radius: 10px;
  float: left;
}
#logo:hover {
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.28);
}

.sign-link {
  position: relative;
  top: 35px;
  display: block;
  margin: 0 16px 0 8px;
  float: left;
  left: 200px;
  font-size: 24px;
  width: 100px;
  height: 34px;
  text-align: center;
  line-height: 34px;
  background: #3dd8ec;
  border: 1px solid #3dd8ec;
  border-radius: 16px;
  color: white;
  z-index: 1;
}
.sign-link:after {
  content: "";
  width: 102%;
  height: 102%;
  position: absolute;
  background: inherit;
  top: 0;
  left: 0;
  filter: blur(3px);
  opacity: 0.7;
  z-index: -1;
  border-radius: 10px;
}
.sign-link:hover {
  border: 1px solid #fff;
  cursor: pointer;
}
#sign-up {
  background: #67ff80;
  border: 1px solid #67ff80;
}
</style>
