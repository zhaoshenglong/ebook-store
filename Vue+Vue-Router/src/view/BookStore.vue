<template>
  <div id="store-container">
    <div id="store-cover">
      <BookHeader/>
      <div id="info-bar">
        <div id="info-container">
          <span class="info-item" id="login" @click="toSignIn">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#iconuser"></use>
            </svg>
            sign in
          </span>
          <span class="info-item" id="register" @click="toSignUp">sign up</span>
          <span class="info-item" id="order" @click="toOrder">
            Your order
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#iconorder"></use>
            </svg>
          </span>
          <span class="info-item" id="cart" @click="toCart">
            Your cart
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#iconiconfontcart-copy"></use>
            </svg>
          </span>
          <span class="info-item" id="want">favicon</span>
          <span class="info-item" id="set" @click="toSetting">
            setting
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#iconconfig"></use>
            </svg>
          </span>
          <span class="clear"></span>
        </div>
      </div>
      <div id="search-bar">
        <img
          class="easing-variables"
          src="../../static/logo/logo1.png"
          @click="toBookStore"
          title="Back to store"
        >
        <div id="search-container">
          <input type="text" v-model="searchMsg">
          <svg id="search" class="icon" aria-hidden="true">
            <use xlink:href="#iconsearch"></use>
          </svg>
        </div>
      </div>
      <div id="main-cover">
        <router-view :searchMsg="searchMsg" name="main"></router-view>
      </div>
      <BookFooter/>
    </div>
  </div>
</template>
<script>
import BookHeader from "../components/page/Header";
import BookFooter from "../components/page/Footer";
export default {
  name: "BookStore",
  data() {
    return {
      searchMsg: ""
    };
  },
  components: {
    BookHeader,
    BookFooter
  },
  methods: {
    toSignIn() {
      this.$router.push("/signIn");
    },
    toSignUp() {
      this.$router.push("/signUp");
    },
    toOrder() {
      this.$router.push({ name: "Order", params: { userid: "123" } });
    },
    toCart() {
      this.$router.push({ name: "Cart", params: { userid: "123" } });
    },
    toSetting() {
      this.$router.push({ name: "SettingProfile", params: { userid: "123" } });
    },
    toBookStore() {
      this.$router.push({ path: "/books" });
    }
  }
};
</script>
<style scoped>
#store-container {
  position: relative;
  z-index: 1;
  width: 100%;
  min-width: 1024px;
  min-height: 100%;
  height: 100%;
  background-image: url("../../static/background/sunshine.jpg");
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
}
#store-cover {
  width: 100%;
  height: 100%;
  background: linear-gradient(
    to bottom,
    rgba(255, 255, 255, 0.4),
    rgba(255, 255, 255, 0.05)
  );
}
#info-bar {
  width: 100%;
  height: 36px;
  margin-top: 3px;
  margin-bottom: 12px;
  background: rgba(229, 252, 251, 0.8);
}
#info-container {
  width: 1024px;
  height: 32px;
  padding: 2px 0;
  margin: 0 auto;
}
.info-item {
  float: left;
  display: block;
  border: 1px solid transparent;
  height: 32px;
  line-height: 32px;
  text-align: center;
  font-size: 14px;
  margin: 0 auto;
}
.info-item:hover {
  cursor: pointer;
  opacity: 0.7;
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.5);
  border-top: 1px solid #35a3c4;
  border-bottom: 1px solid #35a3c4;
}
.icon {
  position: relative;
  top: 4px;
}
#login {
  margin-left: 160px;
  position: relative;
}
#login:after {
  content: "/";
  position: absolute;
  right: -12px;
  top: 0;
}
#register {
  margin-left: 24px;
}
#order {
  margin-left: 320px;
  width: 110px;
}
#cart {
  margin-left: 16px;
}
#want {
  margin-left: 16px;
}
#set {
  margin-left: 32px;
}
.clear {
  clear: both;
}
#search-bar {
  width: 1017px;
  height: 86px;
  margin: 0 auto;
  display: flex;
}
#search-bar img {
  width: 148px;
  margin: 2px 16px;
  margin-left: 98px;
  border-radius: 10px;
}
#search-bar img:hover {
  cursor: pointer;
}
#search-bar #search-container {
  margin: 17px 170px 17px 32px;
  flex: 1;
  display: flex;
  width: 600px;
  height: 50px;
  border-radius: 24px;
  background: #fff;
  position: relative;
}
#search-bar #search-container:hover {
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.28);
}
#search-container input {
  flex: 1;
  height: 34px;
  padding: 7px 25px;
  border: 0;
  border-radius: 24px;
  border: 1px solid #d8eaf6;
  font-size: 16px;
}
#search-container input:focus {
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.28);
}
#search {
  position: absolute;
  right: 15px;
  top: 10px;
  width: 0.6em;
  height: 0.6em;
}
#main-cover {
  display: flex;
  background: rgba(229, 252, 251, 0.7);
  min-width: 1004px;
  width: 60%;
  height: 100%;
  min-height: 1000px;
  margin: 36px auto;
  position: relative;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  border-left: 20px solid #35a3c4;
}
</style>
