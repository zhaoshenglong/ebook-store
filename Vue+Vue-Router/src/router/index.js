/* eslint-disable indent */
import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home/Home'
import SignIn from '@/components/home/signInUp/SignIn'
import SignUp from '@/components/home/signInUp/SignUp'
import BookStore from '@/components/books/BookStore'
import HomeSwiper from '@/components/home/HomeSwiper'
import Sidebar from '@/components/books/store/Sidebar'
import StoreMain from '@/components/books/store/StoreMain'
import BookDetail from '@/components/books/store/detail/BookDetail'
import Setting from '@/components/books/usr/config/Setting'
import SettingSide from '@/components/books/usr/config/SettingSide'
import Cart from '@/components/books/usr/Cart'
import Order from '@/components/books/usr/order/Order'
import OrderSide from '@/components/books/usr/order/OrderSide'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/home',
    name: 'Home',
    component: Home,
    children: [{
      path: '/home',
      name: 'HomeSwiper',
      component: HomeSwiper
    },
    {
      path: '/home/signIn',
      name: 'SignIn',
      component: SignIn
    },
    {
      path: '/home/signUp',
      name: 'SignUp',
      component: SignUp
    }]
  },
  {
    path: '/',
    name: 'BookStore',
    component: BookStore,
    children: [
      {
        path: '/',
        name: 'StorePage',
        components: {
          sidebar: Sidebar,
          main: StoreMain
        }
      },
      {
        path: '/detail',
        name: 'BookDetail',
        components: {
            sidebar: Sidebar,
            main: BookDetail
        }
      },
      {
        path: '/setting',
        name: 'Setting',
        components: {
            sidebar: SettingSide,
            main: Setting
        }
      },
      {
        path: '/order',
        name: 'Order',
        components: {
            sidebar: OrderSide,
            main: Order
        }
      },
      {
        path: '/cart',
        name: 'Cart',
        components: {
            sidebar: Sidebar,
            main: Cart
        }
      }
    ]
  }]
})
