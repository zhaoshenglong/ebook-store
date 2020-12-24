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
import { mapGetters } from "vuex";
export default {
  name: "SettingAccount",
  data() {
    return {
      oldPassword: "",
      newPassword: "",
      newPasswordConfirm: "",
      user: {}
    };
  },
  components: {
    SettingSide
  },
  mounted() {
    console.log(this.$store.getters);
    this.user = this.$store.getters.getUser;
  },
  methods: {
    updatePassword() {
      if (this.newPassword !== this.newPasswordConfirm) {
        this.$message({
          type: "error",
          message: "新密码不匹配，请再确认下哦～",
          showClose: true
        });
        return;
      } else {
        /* verify old password */
        let encrypt = cryptoJs.MD5(this.oldPassword).toString();
        let verifyApiUrl = "/api/user/" + this.user.name + "/password/verify";
        let updateApiUrl = "/api/user/" + this.user.name + "/modify";
        axios
          .get(verifyApiUrl, {
            params: {
              password: encrypt
            }
          })
          .then(response => {
            let newEncrypt = cryptoJs.MD5(this.newPassword).toString();
            /* callback function update password */
            axios
              .put(updateApiUrl, {
                name: this.getUser().name,
                password: newEncrypt
              })
              .then(response => {
                this.$message({
                  type: "success",
                  message: "修改密码成功",
                  duration: 2000
                });
              })
              .catch(err => {
                this.$message({
                  type: "error",
                  message: "修改密码失败了，我们的服务器可能挂了:(",
                  duration: 2000
                });
              });
          })
          .catch(err => {
            let msg = "";
            if (err.status == 500) {
              msg = "更改密码失败，我们的服务器可能挂了:(";
            } else {
              msg = "更改密码失败，旧密码错误:(";
            }
            this.$message({
              type: "error",
              message: msg,
              showClose: true
            });
          });
      }
    },
    ...mapGetters(["getUser"])
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
