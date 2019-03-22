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
import SettingProfile from '@/components/books/usr/config/SettingProfile'
import SettingAccount from '@/components/books/usr/config/SettingAccount'
import SettingAddress from '@/components/books/usr/config/SettingAddress'
import SettingSide from '@/components/books/usr/config/SettingSide'
import Cart from '@/components/books/usr/cart/Cart'
import Order from '@/components/books/usr/order/Order'
import OrderSide from '@/components/books/usr/order/OrderSide'

Vue.use(Router)

export default new Router({
    routes: [{
            path: '/',
            component: Home,
            children: [{
                    path: '/',
                    name: 'Home',
                    component: HomeSwiper
                },
                {
                    path: '/signIn',
                    name: 'SignIn',
                    component: SignIn
                },
                {
                    path: '/signUp',
                    name: 'SignUp',
                    component: SignUp
                }
            ]
        },
        {
            path: '/books',
            component: BookStore,
            children: [{
                    path: '/books',
                    name: 'StorePage',
                    components: {
                        sidebar: Sidebar,
                        main: StoreMain
                    }
                },
                {
                    path: '/books/:userid',
                    name: 'StorePageSigned',
                    components: {
                        sidebar: Sidebar,
                        main: StoreMain
                    }
                },
                {
                    path: '/detail/:bookName',
                    name: 'BookDetail',
                    components: {
                        sidebar: Sidebar,
                        main: BookDetail
                    }
                },
                {
                    path: '/home/:userid/setting/Profile',
                    name: 'SettingProfile',
                    components: {
                        sidebar: SettingSide,
                        main: SettingProfile
                    }
                },
                {
                    path: '/home/:userid/setting/Address',
                    name: 'SettingAddress',
                    components: {
                        sidebar: SettingSide,
                        main: SettingAddress
                    }
                },
                {
                    path: '/home/:userid/setting/Account',
                    name: 'SettingAccount',
                    components: {
                        sidebar: SettingSide,
                        main: SettingAccount
                    }
                },
                {
                    path: '/home/:userid/order',
                    name: 'Order',
                    components: {
                        sidebar: OrderSide,
                        main: Order
                    }
                },
                {
                    path: '/home/:userid/cart',
                    name: 'Cart',
                    components: {
                        sidebar: Sidebar,
                        main: Cart
                    }
                }
            ]
        }
    ]
})
