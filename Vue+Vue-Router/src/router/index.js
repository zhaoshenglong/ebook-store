/* eslint-disable indent */
import Vue from 'vue'
import Router from 'vue-router'
import SignIn from '@/view/home/sign/SignIn'
import SignUp from '@/view/home/sign/SignUp'
import HomeSwiper from '@/components/home/HomeSwiper'
import StoreMain from '@/view/main/StoreMain'
import Home from '@/view/home/Home'
import BookStore from '@/view/BookStore'
import BookDetail from '@/view/detail/BookDetail'
import SettingProfile from '@/view/setting/SettingProfile'
import SettingAccount from '@/view/setting/SettingAccount'
import SettingAddress from '@/view/setting/SettingAddress'
import Cart from '@/view/cart/Cart'
import Order from '@/view/order/Order'
import AdminBooks from '@/view/admin/ManageBooks'
import AdminUsers from '@/view/admin/ManageUsers'
import AdminOrders from '@/view/admin/ManageOrders'
import Admin from '@/view/admin/Admin'
Vue.use(Router)

export default new Router({
    routes: [{
            path: '/',
            component: Home,
            children: [{
                    path: '/',
                    name: 'Home',
                    component: HomeSwiper,
                    meta: {
                        role: 'guest',
                        redirect: false
                    }
                },
                {
                    path: '/signIn',
                    name: 'SignIn',
                    component: SignIn,
                    meta: {
                        role: 'guest',
                        redirect: false
                    }
                },
                {
                    path: '/signUp',
                    name: 'SignUp',
                    component: SignUp,
                    meta: {
                        role: 'guest',
                        redirect: false
                    }
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
                        main: StoreMain
                    },
                    meta: {
                        role: 'guest',
                        redirect: false
                    }
                },
                {
                    path: 'books/detail/:bookId',
                    name: 'BookDetail',
                    components: {
                        main: BookDetail
                    },
                    meta: {
                        role: 'guest',
                        redirect: false
                    }
                },
                {
                    path: '/home/:userid/settings/Profile',
                    name: 'SettingProfile',
                    components: {
                        main: SettingProfile
                    },
                    meta: {
                        role: 'user',
                        redirect: false
                    }
                },
                {
                    path: '/home/:userid/settings/Address',
                    name: 'SettingAddress',
                    components: {
                        main: SettingAddress
                    },
                    meta: {
                        role: 'user',
                        redirect: false
                    }
                },
                {
                    path: '/home/:userid/settings/Account',
                    name: 'SettingAccount',
                    components: {
                        main: SettingAccount
                    },
                    meta: {
                        role: 'user',
                        redirect: false
                    }
                },
                {
                    path: '/home/:userid/orders',
                    name: 'Order',
                    components: {
                        main: Order
                    },
                    meta: {
                        role: 'user',
                        redirect: false
                    }
                },
                {
                    path: '/home/:userid/cart',
                    name: 'Cart',
                    components: {
                        main: Cart
                    },
                    meta: {
                        role: 'user',
                        redirect: false
                    }
                }
            ]
        },
        {
            path: '/home/admin/',
            component: Admin,
            children: [{
                    path: 'manage/books',
                    name: 'ManageBooks',
                    component: AdminBooks,
                    meta: {
                        role: 'admin',
                        redirect: false
                    }
                },
                {
                    path: 'manage/users',
                    name: 'ManageUsers',
                    component: AdminUsers,
                    meta: {
                        role: 'admin',
                        redirect: false
                    }
                },
                {
                    path: 'manage/orders',
                    name: 'ManageOrders',
                    component: AdminOrders,
                    meta: {
                        role: 'admin',
                        redirect: false
                    }
                }
            ],
            meta: {
                role: 'admin'
            }
        }
    ]
})
