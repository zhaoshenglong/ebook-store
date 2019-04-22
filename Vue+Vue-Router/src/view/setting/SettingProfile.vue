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
            class="main-background input-control input-block disabled"
            type="text"
            disabled="disabled"
          >
          <h3 class="h3-heading">Email</h3>
          <input
            id="input-email"
            v-model="email"
            class="main-background input-control input-block"
            type="text"
          >
          <button class="btn btn-block btn-submit" @click="updateEmail">Update profile</button>
        </div>
        <div id="profile-right">
          <h3 class="h3-heading">Profile picture</h3>
          <img :src="imgUrl" alt="upload avatar" id="userAvatar">
          <div>
            <button class="btn btn-block btn-submit" style="width:100%">Upload avatar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SettingSide from "../../components/setting/SettingSide";
import axios from "axios";
import { mapState } from "vuex";

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
      var user = this.$route.params.userid;
      console.log(this.user.password);
      axios
        .get("/userServlet", {
          params: {
            action: "get",
            name: user,
            password: this.user.password
          }
        })
        .then(response => {
          console.log(response);
          const data = response.data;
          this.name = data.name;
          this.email = data.email;
          this.imgUrl = data.avatar;
        })
        .catch(err => {
          console.log(err);
        });
    },
    updateEmail() {}
  },
  computed: {
    ...mapState(["user"])
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
#input-id {
  width: 40%;
  max-width: 440px;
  margin-bottom: 25px;
  color: #666666;
  cursor: not-allowed;
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
.disabled {
  color: #666666;
  cursor: not-allowed;
}
#userAvatar {
  width: 100%;
  height: 100%;
  max-width: 150px;
}
</style>
