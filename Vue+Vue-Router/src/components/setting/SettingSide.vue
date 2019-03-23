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
export default {
  name: "SettingSide",
  methods: {
    toProfile() {
      this.$router.push({ name: "SettingProfile", params: { userid: "123" } });
    },
    toAccount() {
      this.$router.push({ name: "SettingAccount", params: { userid: "123" } });
    },
    toAddress() {
      this.$router.push({ name: "SettingAddress", params: { userid: "123" } });
    }
  }
};
</script>