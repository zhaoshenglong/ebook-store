<template>
  <div id="home">
    <div id="back-cover">
        <div id="logo-container">
            <img src="../../../static/logo/logo3.png" @click="goBack" alt="Turn back">
        </div>
        <a id = "alter-link" @click="alterSignInUp">{{linkLabel}}</a>
        <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data () {
      return {
        linkLabel: 'sign in'
      }
  },
  methods: {
    goBack () {
      this.$router.push('/')
    },
    alterSignInUp () {
        if (this.linkLabel === 'sign in') {
            this.linkLabel = 'sign up'
            this.$router.push('/home/signIn')
        } else {
            this.linkLabel = 'sign in'
            this.$router.push('/home/signUp')
        }
    }
  }
}
</script>

<style scoped>
#home {
    background-image: url("../../../static/background/girl.jpg");
    background-position: center;
    background-attachment: fixed;
    background-repeat: no-repeat;
    background-size: cover;
    min-height: 100%;
    min-width: 1024px;
    height: 100%;
}
#back-cover{
    min-height: 100%;
    height: 100%;
    background: linear-gradient(to right, rgba(255,255,255,1), rgba(255,255,255,0))
}
#logo-container{
    position: absolute;
    height: 74px;
    top: 16px;
    left: 200px;
    cursor: pointer;
    border-radius: 10px;
}
#logo-container:hover{
    box-shadow: 0 1px 6px 0 rgba(32,33,36,0.28); 
}
#logo-container img{
    border-radius: 10px;
}
#alter-link{
    position: relative;
    top : 35px;
    left: 400px;;
    display: block;
    font-size: 24px;
    width: 100px;
    height:34px;
    text-align: center;
    line-height: 34px;
    background: #3dd8ec;
    border: 1px solid #3dd8ec;
    border-radius: 16px;
    color: white;
    z-index: 1;
}
#alter-link:after{
    content: '';
    width: 102%;
    height: 102%;
    position: absolute;
    background: inherit;
    top: 0;
    left: 0;
    filter: blur(3px);
    opacity: 0.7;
    z-index: -1;
    border-radius: 10px;
}
#alter-link:hover{
    border: 1px solid #fff;
    cursor: pointer;
}
</style>
