/* eslint-disable indent */
import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home/Home'
import SignIn from '@/components/home/signInUp/SignIn'
import SignUp from '@/components/home/signInUp/SignUp'
import BookStore from '@/components/books/BookStore'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/home',
    name: 'Home',
    component: Home,
    children: [{
        path: '/home/signIn',
        name: 'SignIn',
        component: SignIn
      },
      {
        path: '/home/signUp',
        name: 'SignUp',
        component: SignUp
      }
    ]
  },
  {
    path: '/',
    name: 'BookStore',
    component: BookStore
  }]
})
