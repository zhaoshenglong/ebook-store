/* eslint-disable indent */
import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home/Home'
import SignIn from '@/components/home/signInUp/SignIn'
import SignUp from '@/components/home/signInUp/SignUp'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/home',
    name: 'Home',
    component: Home,
    children: [{
        path: 'signInUp',
        name: 'SignIn',
        component: SignIn
      },
      {
        path: 'signInUp',
        name: 'SignUp',
        component: SignUp
      }
    ]
  }]
})
