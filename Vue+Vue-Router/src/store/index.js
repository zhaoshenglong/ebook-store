/* eslint-disable indent */
import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        signed: false,
        user: {
            name: '',
            avatar: '',
            password: '',
            role: ''
        },
        cart: {
            id: '',
            user: '',
            createDate: '',
            orderItemList: []
        },
        cartChanged: false
    },
    getters: {
        getSigned: state => {
            return state.signed
        },
        getCart: state => {
            return state.cart
        },
        getUser: state => {
            return state.user
        },
        getCartState: state => {
            return state.cartChanged
        }
    },
    mutations: {
        setUser: (state, user) => {
            state.user.name = user.name
            state.user.avatar = user.avatar
            state.user.password = user.password
            state.user.role = user.name === 'admin' ? 'admin' : 'user'
            state.signed = true
        },
        signOut: state => {
            state.signed = false
        },
        setCart: (state, cart) => {
            state.cart.id = cart.id
            state.cart.user = cart.use
            state.cart.createDate = cart.createDate
            state.cart.orderItemList = cart.orderItemList
        },
        addCartItem: (state, good) => {
            var book = {}
            book.id = good.id
            book.quantity = good.quantity
            state.cart.orderItemList.push(book)
        },
        deleteCartItem: (state, good) => {
            var index = 0
            state.cart.orderItemList.forEach(book => {
                if (book.id === good.id) {
                    state.cart.splice(index, 1)
                }
                index++
            })
        }
    }
})
