/* eslint-disable indent */
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        signed: false,
        user: {
            name: 'zsl',
            passwd: '123456789',
            avatar: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1460921328,2825444385&fm=27&gp=0.jpg'
        },
        admin: {
            name: 'admin',
            passwd: '11111111',
            avatar: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4208386305,57701306&fm=27&gp=0.jpg'
        },
        cart: []
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
        }
    },
    mutations: {
        signIn: (state, user) => {
            state.user.name = user.name
            state.user.passwd = user.passwd
            state.user.avatar = 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1460921328,2825444385&fm=27&gp=0.jpg'
            state.signed = true
        },
        signOut: state => {
            state.signed = false
        },
        addCart: (state, good) => {
            var book = {}
            book.name = good.name
            book.isbn = good.isbn
            book.img = good.img
            book.author = good.author
            book.price = good.price
            state.cart.push(book)
        },
        deleteCart: (state, good) => {
            var count = 0
            state.cart.forEach(book => {
                if (book.isbn === good.isbn) {
                    state.cart.splice(count, 1)
                }
                count++
            })
        },
        clearCart: state => {
            state.cart = []
        }
    }
})
