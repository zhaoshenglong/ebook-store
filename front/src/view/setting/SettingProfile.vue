<template>
  <div class="view-container">
    <setting-side></setting-side>
    <div class="view-main">
      <div class="setting-container">
        <h2 class="h2-heading">Public profile</h2>
        <div id="profile-left">
          <h3 class="h3-heading">Name</h3>
          <input
            id="input-name"
            v-model="name"
            class="main-background input-control input-block"
            type="text"
          >
          <h3 class="h3-heading">Email</h3>
          <input
            id="input-email"
            v-model="email"
            class="main-background input-control input-block"
            type="text"
          >
          <button class="btn btn-block btn-submit">Update profile</button>
        </div>
        <div id="profile-right">
          <h3 class="h3-heading">Profile picture</h3>
          <img src alt="upload avatar" title="upload avatar">
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SettingSide from "../../components/setting/SettingSide";
export default {
  name: "SettingProfile",
  components: {
    SettingSide
  },
  data() {
    return {
      imgUrl: "",
      name: "",
      email: ""
    };
  },
  mounted: function() {
    this.fetchProfile();
  },
  methods: {
    fetchProfile() {
      this.name = "zhaoshenglong";
      this.email = "G245078728@sjtu.edu.cn";
      this.imgUrl = "";
    }
  }
};
</script>
<style scoped>
#profile-left {
  margin-top: 20px;
  width: 75%;
  float: left;
}
#input-name {
  width: 40%;
  max-width: 440px;
  margin-bottom: 25px;
}
#input-email {
  width: 68%;
  max-width: 340px;
}
#profile-right {
  width: 25%;
  float: left;
  margin-top: 20px;
}
button {
  margin: 20px 0;
  width: 35%;
  max-width: 200px;
}
</style>
