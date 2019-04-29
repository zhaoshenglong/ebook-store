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
      <message-box
        class="msg"
        :error="msgError"
        :success="msgSuccess"
        :message="message"
        v-show="msgError || msgSuccess"
      ></message-box>
      <wait class="msg" :wait="wait" v-show="wait"/>
    </div>
  </div>
</template>
<script>
import cryptoJs from "crypto-js";
import axios from "axios";
import qs from "qs";
import MessageBox from "../../../components/message/MessageBox";
import Wait from "../../../components/message/Wait";
import { mapGetters, mapMutations } from "vuex";
import Cookies from "js-cookie";
export default {
  name: "signIn",
  components: {
    Wait,
    MessageBox
  },
  data() {
    return {
      usrName: "",
      usrPasswd: "",
      msgError: false,
      msgSuccess: false,
      message: "",
      wait: false
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
      this.wait = true;
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
          this.wait = false;
          this.msgError = false;
          this.msgSuccess = true;
          this.message = "Success log in, heading to store page...";
          setTimeout(() => {
            const data = response.data;
            console.log(data);
            if (response.status == 200) {
              this.setUser({
                name: data.name,
                avatar: data.avatar,
                password: encrypt
              });
              Cookies.set("user", {
                name: data.name,
                avatar: data.avatar,
                password: data.password,
                role: data.name === "admin" ? "admin" : "user"
              });
              if (data.name == "admin") {
                this.$router.push({ name: "ManageBooks" });
              } else this.$router.push({ name: "StorePage" });
            }
            this.msgSuccess = false;
          }, 3000);
        })
        .catch(err => {
          console.log(err);
          this.wait = false;
          this.msgError = true;
          if (err.status == 500) {
            this.message = "Server failed, please try again";
          }
          this.message = "Sign in failed, please try again";
        });
    },
    ...mapGetters(["getUser"]),
    ...mapMutations(["setUser"])
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
.msg {
  width: 278px;
}
</style>
