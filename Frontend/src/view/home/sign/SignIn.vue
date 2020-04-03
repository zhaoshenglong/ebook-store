<template>
  <div id="sign-in-form">
    <div class="sign-inup-container">
      <div class="sign-in-up">
        <label for="signin-field">Username or Email</label>
        <input
          class="input-control input-block"
          type="text"
          name="signin"
          id="signin-field"
          placeholder="username / email"
          v-model="usrName"
        >
        <svg class="icon clear-input" aria-hidden="true" v-show="usrName!=''" @click="clearInput">
          <use xlink:href="#icondelete"></use>
        </svg>
      </div>
      <div class="sign-in-up">
        <label for="passwd">Password</label>
        <input
          class="input-control input-block"
          type="password"
          name="password"
          id="passwd"
          placeholder="password"
          v-model="usrPasswd"
        >
      </div>
      <button class="btn btn-block btn-submit" @click="logIn">sign in</button>
    </div>
  </div>
</template>
<script>
import cryptoJs from 'crypto-js'
import qs from 'qs'
export default {
  name: 'signIn',
  data () {
    return {
      usrName: '',
      usrPasswd: ''
    }
  },
  methods: {
    clearInput () {
      this.usrName = ''
    },
    logIn () {
      let encrypt = cryptoJs.MD5(this.usrPasswd).toString()
      let data = qs.stringify({
        username: this.usrName,
        password: encrypt
      })
      this.$store
        .dispatch('signIn', data)
        .then(user => {
          console.log(user)
          console.group('password')
          console.log(encrypt)
          this.$message({
            showClose: true,
            message: '登录成功，3秒后自动为您跳转到商品页面',
            type: 'success'
          })
          setTimeout(() => {
            if (this.usrName === 'admin') {
              this.$router.push({ name: 'ManageBooks' })
            } else this.$router.push({ name: 'StorePage' })
          }, 3000)
        })
        .catch(err => {
          const status = err.status
          const message = err.data
          if (status === 500) {
            this.$message({
              showClose: true,
              message: '登录失败，我们的服务器可能挂了:(',
              type: 'error'
            })
          } else {
            if (status === 401) {
              if (message.msg === 'User credentials have expired') {
                console.log(message)
                this.$message({
                  showClose: true,
                  message: '登录失败，您帐号已过期:(',
                  type: 'error'
                })
              } else if (message.msg === 'Bad credentials') {
                console.log(message)
                this.$message({
                  showClose: true,
                  message: '登录失败，您的密码不正确:(',
                  type: 'error'
                })
              } else if (message.msg === 'User account is locked') {
                console.log(message)
                this.$message({
                  showClose: true,
                  message: '登录失败，您的账户已被管理员锁定:(',
                  type: 'error'
                })
              } else {
                console.log(message)
                this.$message({
                  showClose: true,
                  message: '登录失败，我们遇到了未知错误:(',
                  type: 'error'
                })
              }
            }
          }
        })
    }
  }
}
</script>
<style scoped>
#sign-in-form {
  width: 350px;
  position: absolute;
  left: 15%;
  top: 40%;
  font-size: 14px;
}
.sign-inup-container {
  width: 300px;
  padding: 20px 25px;
  text-align: left;
  position: relative;
  background: rgba(255, 255, 255, 0.7);
}
#sign-in-form label {
  font-weight: 600;
  white-space: pre-wrap;
  display: block;
}
.sign-in-up {
  position: relative;
}
.sign-in-up label {
  margin-bottom: 7px;
}
#signin-field {
  width: 282px;
  margin-top: 5px;
  margin-bottom: 15px;
}
#passwd {
  width: 282px;
  margin-top: 5px;
  margin-bottom: 15px;
}
.clear-input {
  position: absolute;
  right: 8px;
  top: 24px;
  width: 2em;
  height: 2em;
  opacity: 0.4;
}
.clear-input:hover {
  opacity: 1;
}
.msg {
  width: 278px;
}
</style>
