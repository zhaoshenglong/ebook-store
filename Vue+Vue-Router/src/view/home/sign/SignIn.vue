<template>
  <div id="sign-in-form">
    <div class="sign-inup-container">
      <div class="sign-in-up">
        <label for="signin-field">Username or Email</label>
        <input
          class="input-control input-block"
          type="text"
          name="signin"
          id="signin-field"
          placeholder="username / email"
          v-model="usrName"
        >
        <svg class="icon clear-input" aria-hidden="true" v-show="usrName!=''" @click="clearInput">
          <use xlink:href="#icondelete"></use>
        </svg>
      </div>
      <div class="sign-in-up">
        <label for="passwd">Password</label>
        <input
          class="input-control input-block"
          type="password"
          name="password"
          id="passwd"
          placeholder="password"
          v-model="usrPasswd"
        >
      </div>
      <button class="btn btn-block btn-submit" @click="verifyUser">sign in</button>
    </div>
  </div>
</template>
<script>
import cryptoJs from "crypto-js";
import axios from "axios";
import qs from "qs";
import { mapGetters, mapMutations } from "vuex";
export default {
  name: "signIn",
  data() {
    return {
      usrName: "",
      usrPasswd: ""
    };
  },
  methods: {
    clearInput() {
      this.usrName = "";
    },
    toStore() {
      var name = this.usrName;
      if (name == "admin") {
        this.$router.push({ name: "ManageBooks" });
      } else {
        this.setUser({ name: name });
        user = this.getUser();
        this.$router.push({
          name: "StorePageSigned",
          params: { userid: user.name }
        });
      }
    },
    verifyUser() {
      var encrypt = cryptoJs.MD5(this.usrPasswd).toString();
      axios
        .get("/userServlet", {
          params: {
            action: "get",
            name: this.usrName,
            password: encrypt
          }
        })
        .then(response => {
          const data = response.data;
          console.log(data);
          if (data.status != "404") {
            this.setUser({
              name: data.name,
              avatar: data.avatar,
              password: encrypt
            });
            if (data.name == "admin") {
              this.$router.push({ name: "ManageBooks" });
            } else this.$router.push({ name: "StorePage" });
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    ...mapGetters(["getUser"]),
    ...mapMutations(["setUser", "setCart"])
  }
};
</script>
<style scoped>
#sign-in-form {
  width: 350px;
  position: absolute;
  left: 15%;
  top: 40%;
  font-size: 14px;
}
.sign-inup-container {
  width: 300px;
  padding: 20px 25px;
  text-align: left;
  position: relative;
  background: rgba(255, 255, 255, 0.7);
}
#sign-in-form label {
  font-weight: 600;
  white-space: pre-wrap;
  display: block;
}
.sign-in-up {
  position: relative;
}
.sign-in-up label {
  margin-bottom: 7px;
}
#signin-field {
  width: 282px;
  margin-top: 5px;
  margin-bottom: 15px;
}
#passwd {
  width: 282px;
  margin-top: 5px;
  margin-bottom: 15px;
}
.clear-input {
  position: absolute;
  right: 8px;
  top: 24px;
  width: 2em;
  height: 2em;
  opacity: 0.4;
}
.clear-input:hover {
  opacity: 1;
}
</style>
