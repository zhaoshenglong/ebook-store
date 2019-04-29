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
          <div id="update-ava-tool" class="btn btn-block btn-submit">
            update avatar
            <input
              ref="fileInput"
              type="file"
              name="avatar"
              accept="image/jpg, image/jpeg, image/png"
              @change="updateAvatar"
              enctype="multipart/form-data"
            >
          </div>
          <div>
            <wait class="img-msg-box" :wait="imgWait" v-show="imgWait"></wait>
            <message-box
              class="img-msg-box"
              :error="imgError"
              :success="imgSuccess"
              :message="imgMsg"
              v-show="imgError || imgSuccess"
            ></message-box>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SettingSide from "../../components/setting/SettingSide";
import MessageBox from "../../components/message/MessageBox";
import Wait from "../../components/message/Wait";
import axios from "axios";
import qs from "qs";
import { mapGetters } from "vuex";
export default {
  name: "SettingProfile",
  components: {
    SettingSide,
    Wait,
    MessageBox
  },
  data() {
    return {
      imgUrl: "",
      name: "",
      email: "",
      imgError: false,
      imgSuccess: false,
      imgMsg: "",
      imgWait: false
    };
  },
  props: {
    user: {
      type: Object
    }
  },
  mounted() {
    this.fetchProfile();
  },
  methods: {
    fetchProfile() {
      let name = this.$route.params.userid;
      axios
        .get("/status")
        .then(response => {
          const data = response.data;
          this.imgUrl = data.avatar;
          this.name = data.name;
          this.email = data.email;
        })
        .catch(err => {
          console.log(err);
        });
    },
    updateAvatar() {
      this.imgWait = true;
      let file = this.$refs.fileInput.files[0];
      if (file == undefined) {
        this.imgError = true;
        this.imgSuccess = false;
        this.imgMsg = "文件上传失败";
        return;
      }
      if (file.size > 4 << 20) {
        this.imgError = true;
        this.imgSuccess = false;
        this.imgMsg = "图片大小不能超过4MB";
        return;
      }
      /* Prepare for the avatar to be uploaded */
      let img = new FormData();
      img.append("avatar", file, file.name);
      img.append("chunk", "0");
      let config = {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      };
      axios
        .post("avatarUpload", img, config)
        .then(response => {
          this.imgSuccess = true;
          this.imgError = false;
          this.imgMsg = "Update success";
          console.log(response.data);
        })
        .catch(err => {
          console.log(err);
          this.imgSuccess = false;
          this.imgError = true;
          if (err.status == 500) {
            this.imgMsg = "Server failed, Please try again";
          } else {
            this.imgMsg = "Update failed, are you signed?";
          }
        });
      this.imgWait = false;
    },
    emailFormat() {
      var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
      if (!reg.test(this.email)) {
        console.log("test failed");
        console.log(this.email);
      } else return true;
    },
    updateEmail() {
      if (this.emailFormat()) {
        axios
          .post(
            "userServlet",
            qs.stringify({
              email: this.email
            })
          )
          .then(response => {
            console.log(response.data);
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    ...mapGetters(["getUser"])
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
  max-width: 150px;
}
#update-ava-tool {
  position: relative;
  margin-top: 20px;
  z-index: 10;
}
#update-ava-tool input {
  display: inline-block;
  background: red;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
}
.img-msg-box {
  width: 100%;
  height: 80px;
}
</style>
