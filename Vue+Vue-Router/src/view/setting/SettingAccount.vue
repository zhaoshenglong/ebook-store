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
export default {
  name: "SettingAccount",
  data() {
    return {
      oldPassword: "",
      newPassword: "",
      newPasswordConfirm: ""
    };
  },
  components: {
    SettingSide
  },
  methods: {
    updatePassword() {
      if (this.newPassword !== this.newPasswordConfirm) {
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
              })
              .catch(err => {
                console.log(err);
              });
          })
          .catch(err => {
            console.log(err);
            if (err.status == 500) {
            } else {
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
