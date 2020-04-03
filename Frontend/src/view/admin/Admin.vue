<template>
  <div class="admin">
    <book-header></book-header>
    <div id="admin-bar">
      <section id="admin-bar-content">
        <span class="sign-bar" v-if="signed">
          <el-dropdown trigger="hover">
            <span class="el-dropdown-link">
              <i class="el-icon-s-custom"></i>
              admin
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <span @click="closeSession">sign out</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>Welcome, Admin!
        </span>
        <span class="sign-bar" v-if="!signed">
          <i class="el-icon-s-custom"></i>
          <span>sign in</span>
        </span>
      </section>
    </div>
    <div id="admin-main">
      <div id="main-top">
        <span @click="toBooks" :class="show=='books'?focus:''">Books</span>
        <span @click="toUsers">Users</span>
        <span @click="toOrders">Orders</span>
        <span @click="toStatistics">Statistics</span>
      </div>
      <div id="view-container">
        <router-view></router-view>
      </div>
    </div>
    <book-footer></book-footer>
  </div>
</template>

<script>
import BookHeader from '../../components/page/Header'
import BookFooter from '../../components/page/Footer'
export default {
  components: {
    BookHeader,
    BookFooter
  },
  data () {
    return {
      show: 'user',
      signed: false
    }
  },
  mounted () {
    this.establishSession()
  },
  methods: {
    toBooks () {
      this.$router.push({ name: 'ManageBooks' })
    },
    toUsers () {
      this.$router.push({ name: 'ManageUsers' })
    },
    toOrders () {
      this.$router.push({ name: 'ManageOrders' })
    },
    toStatistics () {
      this.$router.push({ name: 'ManageStatistics' })
    },
    closeSession () {
      this.$store
        .dispatch('signOut')
        .then(response => {
          this.$message({
            type: 'success',
            message: '您已成功退出登录,将为您定向到登录页面...'
          })
          setTimeout(() => {
            this.$router.push('signIn')
          }, 2000)
        })
        .catch(err => {
          console.log(err)
          this.$message({
            type: 'error',
            message: '退出登录失败，我们的服务器可能挂了:('
          })
        })
    },
    establishSession () {
      this.$store
        .dispatch('getStatus')
        .then(response => {
          this.signed = true
        })
        .catch(err => {
          console.log(err)
          this.signed = false
          this.$message({
            type: 'error',
            message: '您已退出登录，请重新登录'
          })
        })
    }
  }
}
</script>

<style scoped>
.admin {
  background-image: url("../../../static/background/sunshine.jpg");
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-size: cover;
}
.admin-main {
  width: 70%;
  background: rgba(255, 255, 255, 0.7);
  margin: 0 auto;
  min-width: 1024px;
  min-height: 1024px;
}
#admin-bar {
  width: 100%;
  height: 40px;
  margin-top: 2px;
  margin-bottom: 40px;
  background: rgba(229, 252, 251, 0.8);
  font-size: 16px;
}

#admin-bar-content {
  padding-top: 10px;
}
#admin-bar span {
  margin: 0 20px;
  cursor: pointer;
}
.icon-bar {
  position: relative;
  top: 4px;
}
#admin-main {
  min-height: 1024px;
  width: 70%;
  margin: 0 auto;
  margin-bottom: 30px;
  min-width: 1024px;
}
#main-top {
  background: transparent;
  display: flex;
  flex-direction: row;
}
#main-top span {
  background: rgba(255, 255, 255, 0.7);
  justify-content: left;
  margin-right: 10px;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 4px;
  font-size: 18px;
  width: 80px;
  height: 36px;
  line-height: 36px;
  color: #181818;
  font-weight: 600;
  cursor: pointer;
}
#view-container {
  border-top: 1px solid #b9b9b9;
  background: rgba(255, 255, 255, 0.7);
  min-height: 1024px;
  width: 100%;
}
.focus {
  background: red;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
