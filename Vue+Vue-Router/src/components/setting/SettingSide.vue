<template>
  <div class="side-bar">
    <ul>
      <li @click="toProfile" class="side-menu underline gradient-text">Profile</li>
      <li @click="toAddress" class="side-menu underline gradient-text">Address</li>
      <li @click="toAccount" class="side-menu underline gradient-text">Account</li>
    </ul>
  </div>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "SettingSide",
  methods: {
    toProfile() {
      this.$router.push({
        name: "SettingProfile",
        params: { userid: this.user.name }
      });
    },
    toAccount() {
      this.$router.push({
        name: "SettingAccount",
        params: { userid: this.user.name }
      });
    },
    toAddress() {
      this.$router.push({
        name: "SettingAddress",
        params: { userid: this.user.name }
      });
    }
  },
  computed: {
    ...mapState(["user"])
  }
};
</script>