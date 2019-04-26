<template>
  <div class="view-container">
    <setting-side></setting-side>
    <div class="view-main">
      <div class="setting-container">
        <h2 class="h2-heading">Account</h2>
        <h3 class="h3-heading">Old password</h3>
        <input
          class="input-passwd main-background input-control input-block"
          type="password"
          v-model="oldPassword"
        >
        <h3 class="h3-heading">New password</h3>
        <input
          class="input-passwd main-background input-control input-block"
          type="password"
          v-model="newPassword"
        >
        <h3 class="h3-heading">Confirm new password</h3>
        <input
          class="input-passwd main-background input-control input-block"
          type="password"
          v-model="newPasswordConfirm"
        >
        <message-box :success="success" :error="error" :message="message" v-show="error || success"></message-box>
        <wait :wait="wait" v-show="wait"></wait>
        <button class="btn btn-block btn-submit" @click="updatePassword">Update new password</button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import qs from "qs";
import cryptoJs from "crypto-js";
import SettingSide from "../../components/setting/SettingSide";
import MessageBox from "../../components/message/MessageBox";
import Wait from "../../components/message/Wait";
export default {
  name: "SettingAccount",
  data() {
    return {
      oldPassword: "",
      newPassword: "",
      newPasswordConfirm: "",
      error: false,
      success: false,
      message: "",
      wait: false
    };
  },
  components: {
    SettingSide,
    Wait,
    MessageBox
  },
  methods: {
    updatePassword() {
      this.wait = true;
      if (this.newPassword !== this.newPasswordConfirm) {
        this.error = true;
        this.success = false;
        this.message = "Two password are different.";
        this.wait = false;
        return;
      } else {
        /* verify old password */
        let encrypt = cryptoJs.MD5(this.oldPassword).toString();
        axios
          .get("userServlet", {
            params: {
              action: "verify",
              password: encrypt
            }
          })
          .then(response => {
            console.log(response.data);
            let newEncrypt = cryptoJs.MD5(this.newPassword).toString();
            /* callback function update password */
            axios
              .post(
                "userServlet",
                qs.stringify({
                  password: newEncrypt
                })
              )
              .then(response => {
                console.log(response.data);
                this.wait = false;
                this.success = true;
                this.error = false;
                this.message = "Update password success";
              })
              .catch(err => {
                console.log(err);
                this.wait = false;
                this.message = "Server failed. Please try again";
                this.error = true;
                this.success = false;
              });
          })
          .catch(err => {
            console.log(err);
            this.wait = false;
            this.error = true;
            this.success = false;
            if (err.status == 500) {
              this.message = "Server failed. Please try again";
            } else {
              this.message = "Old password is invalid";
            }
          });
      }
    }
  }
};
</script>
<style scoped>
.input-passwd {
  width: 61.8%;
  margin-bottom: 20px;
}
button {
  width: 30%;
}
</style>
