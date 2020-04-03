<template>
  <div id="sign-up-form">
    <div class="sign-inup-container">
      <div class="sign-in-up">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#iconuser"></use>
        </svg>
        <label for="signup-field">Username</label>
        <input
          class="input-control input-block"
          type="text"
          name="signup"
          id="signup-field"
          placeholder="username"
          v-model="usrName"
        >
        <svg
          class="icon clear-input"
          aria-hidden="true"
          v-show="usrName!=''"
          @click="clearInput(1)"
        >
          <use xlink:href="#icondelete"></use>
        </svg>
        <svg
          class="icon icon-verify icon-error"
          aria-hidden="true"
          v-show="!nameStatus.ok && usrName != ''"
        >
          <use xlink:href="#iconerror"></use>
        </svg>
        <svg
          class="icon icon-verify icon-success"
          aria-hidden="true"
          v-show="nameStatus.ok"
        >
          <use xlink:href="#iconcomplete"></use>
        </svg>
        <el-alert
          v-show="!nameStatus.ok && usrName != ''"
          class="alert-label"
          :title="nameStatus.msg"
          type="error"
          show-icon
        ></el-alert>
      </div>
      <div class="sign-in-up">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#iconemail"></use>
        </svg>
        <label for="email-field">Email</label>
        <input
          class="input-control input-block"
          type="email"
          name="email"
          id="email-field"
          placeholder="username"
          v-model="usrEmail"
        >
        <svg
          class="icon clear-input"
          aria-hidden="true"
          v-show="usrEmail!=''"
          @click="clearInput(2)"
        >
          <use xlink:href="#icondelete"></use>
        </svg>
        <svg
          class="icon icon-verify icon-error"
          aria-hidden="true"
          v-show="!emailStatus.ok && usrEmail != ''"
        >
          <use xlink:href="#iconerror"></use>
        </svg>
        <svg
          class="icon icon-verify icon-success"
          aria-hidden="true"
          v-show="emailStatus.ok"
        >
          <use xlink:href="#iconcomplete"></use>
        </svg>
        <el-alert
          v-show="!emailStatus.ok && usrEmail != ''"
          class="alert-label"
          :title="emailStatus.msg"
          type="error"
          show-icon
        ></el-alert>
      </div>
      <div class="sign-in-up">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#iconpassword"></use>
        </svg>
        <label for="passwd">Password</label>
        <input
          class="input-control input-block"
          type="password"
          name="password"
          id="passwd"
          placeholder="input password"
          v-model="usrPasswd"
        >
      </div>
      <div class="sign-in-up">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#iconpassword"></use>
        </svg>
        <label for="passwd-confirm">Confirm password</label>
        <input
          class="input-control input-block"
          type="password"
          name="password"
          id="passwd-confirm"
          placeholder="input password again"
          v-model="usrPasswdAgain"
        >
        <svg class="icon icon-verify icon-error" aria-hidden="true" v-show="!passwdStatus.ok">
          <use xlink:href="#iconerror"></use>
        </svg>
        <svg
          class="icon icon-verify icon-success"
          aria-hidden="true"
          v-show="passwdStatus.ok && usrPasswd != '' && usrPasswdAgain != ''"
        >
          <use xlink:href="#iconcomplete"></use>
        </svg>
        <el-alert
          v-show="!passwdStatus.ok"
          class="alert-label"
          :title="passwdStatus.msg"
          type="error"
          show-icon
        ></el-alert>
      </div>

      <button id="commit" class="btn btn-block btn-submit" @click="register">Create an account</button>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import cryptoJs from 'crypto-js'
export default {
  name: 'signIn',
  data () {
    return {
      usrName: '',
      usrEmail: '',
      usrPasswd: '',
      usrPasswdAgain: '',
      emailStatus: {
        ok: false,
        msg: ''
      },
      passwdStatus: {
        ok: true,
        msg: ''
      },
      nameStatus: {
        ok: false,
        msg: ''
      }
    }
  },
  methods: {
    clearInput (sign) {
      if (sign === 1) {
        this.usrName = ''
      } else if (sign === 2) {
        this.usrEmail = ''
      }
    },
    emailFormat () {
      var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (!reg.test(this.usrEmail)) {
        console.log('test failed')
      } else return true
    },

    register () {
      if (this.nameStatus.ok && this.passwdStatus.ok && this.emailStatus.ok) {
        var encrypt = cryptoJs.MD5(this.usrPasswd).toString()
        axios
          .post('/api/public/register', {
            name: this.usrName,
            password: encrypt,
            email: this.usrEmail
          })
          .then(response => {
            console.log(response.data)
            this.$message({
              showClose: true,
              message: '注册成功，3秒后自动跳转到登录页面',
              type: 'success',
              center: true
            })
            setTimeout(() => {
              this.$router.push('SignIn')
            }, 3000)
          })
          .catch(err => {
            console.log(err)
            this.$message({
              showClose: true,
              message: '注册失败了,抱歉，我们服务器可能挂了',
              type: 'error',
              center: true
            })
          })
      }
    }
  },
  watch: {
    usrName: function (usrName) {
      if (usrName !== '') {
        axios
          .get('/api/public/name/verify', {
            params: {
              name: this.usrName
            }
          })
          .then(response => {
            this.nameStatus.ok = response.data
            if (!this.nameStatus.ok) {
              this.nameStatus.msg = '用户名已存在！'
            }
          })
          .catch(err => {
            console.log(err)
            this.nameStatus.ok = false
            this.nameStatus.msg = '我们的服务器挂了:('
          })
      } else {
        this.nameStatus.ok = false
      }
    },
    usrEmail: function (email) {
      if (this.emailFormat()) {
        axios
          .get('/api/public/email/verify', {
            params: {
              email: this.usrEmail
            }
          })
          .then(response => {
            this.emailStatus.ok = response.data
            if (!this.emailStatus.ok) {
              this.emailStatus.msg = '邮箱地址已存在！'
            }
          })
          .catch(err => {
            console.log(err)
            this.emailStatus.ok = false
            this.emailStatus.msg = '我们的服务器挂了:('
          })
      } else {
        this.emailStatus.ok = false
        this.emailStatus.msg = '邮箱格式不正确'
      }
    },
    usrPasswd: function () {
      if (this.usrPasswd !== this.usrPasswdAgain && this.usrPasswdAgain !== '') {
        this.passwdStatus.ok = false
        this.passwdStatus.msg = '两次密码不一致哦～'
      } else {
        this.passwdStatus.ok = true
      }
    },
    usrPasswdAgain: function () {
      if (this.usrPasswd !== this.usrPasswdAgain && this.usrPasswdAgain !== '') {
        this.passwdStatus.ok = false
        this.passwdStatus.msg = '两次密码不一致哦～'
      } else {
        this.passwdStatus.ok = true
      }
    }
  }
}
</script>
<style scoped>
#sign-up-form {
  width: 350px;
  position: absolute;
  left: 15%;
  top: 30%;
  font-size: 14px;
}
#sign-up-form label {
  font-weight: 600;
  display: inline;
}
#sign-up-form label:after {
  content: "*";
  color: red;
  margin-left: 6px;
  height: 24px;
}
.sign-inup-container {
  width: 300px;
  padding: 20px 25px;
  padding-right: 35px;
  text-align: left;
  position: relative;
  background: rgba(255, 255, 255, 0.7);
}

.sign-in-up label {
  margin-bottom: 7px;
  height: 24px;
}
.sign-in-up {
  position: relative;
  width: 300px;
  margin-bottom: 15px;
}
#signup-field,
#email-field,
#passwd,
#passwd-confirm {
  width: 282px;
  margin-top: 5px;
  margin-bottom: 5px;
}
#commit {
  width: 300px;
  margin-top: 5px;
  margin-bottom: 15px;
}
.icon-verify {
  position: absolute;
  top: 30px;
  right: -28px;
}
.icon-success {
  color: green;
}
.icon-error {
  color: red;
}
.clear-input {
  position: absolute;
  right: 8px;
  top: 30px;
  width: 2em;
  height: 2em;
  opacity: 0.4;
}
.clear-input:hover {
  opacity: 1;
}
</style>
