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
          <el-alert title="支持jpeg/jpg,大小不超过4KB" type="info" show-icon :closable="false"></el-alert>
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
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import SettingSide from '../../components/setting/SettingSide'
import axios from 'axios'
import { mapGetters } from 'vuex'
export default {
  name: 'SettingProfile',
  components: {
    SettingSide
  },
  data () {
    return {
      imgUrl: '',
      name: '',
      email: ''
    }
  },
  props: {
    user: {
      type: Object
    }
  },
  mounted () {
    this.fetchProfile()
  },
  methods: {
    fetchProfile () {
      this.imgUrl = this.getUser().avatar
      this.email = this.getUser().email
      this.name = this.getUser().name
    },
    updateAvatar () {
      let file = this.$refs.fileInput.files[0]
      if (file === undefined) {
        this.$message({
          type: 'error',
          message: '上传失败，未选中文件',
          duration: 1000
        })
        return
      }
      console.log(file.size)
      if (file.size > 4 << 20) {
        this.$message({
          type: 'error',
          message: '上传失败，文件大小不能超过4MＢ',
          duration: 1000
        })
        return
      }
      /* Prepare for the avatar to be uploaded */
      let img = new FormData()
      img.append('avatar', file, file.name)
      img.append('chunk', '0')
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      let apiUrl = '/api/user/' + this.getUser().name + '/avatars/upload'
      axios
        .post(apiUrl, img, config)
        .then(response => {
          console.log(response.data)
          this.$message({
            type: 'success',
            message: '上传成功',
            duration: 1000
          })
          setTimeout(() => {
            window.location.reload(true)
          }, 1000)
        })
        .catch(err => {
          console.log(err)
          if (err.status === 500) {
            this.$message({
              type: 'error',
              message: '上传失败，我们的服务器挂了:(',
              duration: 1000
            })
          } else {
            this.$message({
              type: 'error',
              message: '上传失败， 是不是未登录？',
              duration: 1000
            })
          }
        })
    },
    emailFormat () {
      var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (!reg.test(this.email)) {
        console.log('test failed')
        console.log(this.email)
      } else return true
    },
    updateEmail () {
      if (this.emailFormat()) {
        let apiUrl = '/api/user/' + this.getUser().name + '/modify'
        axios
          .put(apiUrl, { email: this.email, name: this.name })
          .then(response => {
            this.$message({
              type: 'success',
              message: '更新邮箱成功',
              duration: 1000
            })
          })
          .catch(err => {
            this.$message({
              type: 'error',
              message: '更新邮箱失败，我们的服务器挂了:(',
              duration: 1000
            })
            console.log(err)
          })
      }
    },
    ...mapGetters(['getUser'])
  }
}
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
