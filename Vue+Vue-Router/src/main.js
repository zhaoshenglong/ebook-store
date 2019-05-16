// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable indent */
import Vue from 'vue'
import App from './App'
/* Router & Store */
import router from './router'
import store from './store'
/* Global css and svg */
import './assets/css/reset.css'
import './assets/iconfont/iconfont'
/* Global use swiper */
import VueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/dist/css/swiper.css'
import axios from 'axios'
import Cookies from 'js-cookie'
import ElementUI, {
    Message
} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)
Vue.use(VueAwesomeSwiper)
Vue.config.productionTip = false
Vue.prototype.axios = axios

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true

router.beforeEach((to, from, next) => {
    let role = Cookies.get('role')
    let logged = Cookies.get('logged')
    /* Go to guest page, allowed all guests */
    if (to.meta.role === 'guest') {
        next()
    } else if (to.meta.role === 'user') {
        /* Go to user Page, need logged in, if not, then redirect to signIn */
        if (logged) {
            next()
        } else {
            Message({
                type: 'warning',
                message: '您还没有登录哦，3秒后为您跳转到登录页面',
                duration: 3000
            })
            setTimeout(() => {
                next({
                    name: 'SignIn'
                })
            }, 3000)
        }
    } else if (to.meta.role === 'admin') {
        /* Go to admin Page, need logged in, if not, then redirect to signIn */
        if (logged) {
            if (role === 'admin') {
                next()
            }
        } else {
            Message({
                type: 'warning',
                message: '抱歉的通知您，您没有该权限'
            })
            setTimeout(() => {}, 3000)
        }
    } else {
        next({
            name: 'SignIn'
        })
    }
    next()
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    components: {
        App
    },
    template: '<App/>'
})
