<template>
  <div class="view-container">
    <setting-side></setting-side>
    <div class="view-main">
      <div class="setting-container">
        <h2 class="h2-heading">Account</h2>
        <h3 class="h3-heading">Old password</h3>
        <input
          id="input-name"
          class="input-passwd main-background input-control input-block"
          type="text"
        >
        <h3 class="h3-heading">New password</h3>
        <input
          id="input-name"
          class="input-passwd main-background input-control input-block"
          type="text"
        >
        <h3 class="h3-heading">Confirm new password</h3>
        <input
          id="input-name"
          class="input-passwd main-background input-control input-block"
          type="text"
        >
        <button class="btn btn-block btn-submit">Update new password</button>
      </div>
    </div>
  </div>
</template>
<script>
import SettingSide from "../../components/setting/SettingSide";
export default {
  name: "SettingAccount",
  components: {
    SettingSide
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
