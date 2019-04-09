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

Vue.use(VueAwesomeSwiper)
Vue.config.productionTip = false
Vue.prototype.axios = axios

axios.defaults.baseURL = 'http://localhost:8088/servlet_war_exploded'
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
