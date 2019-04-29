/* eslint-disable indent */
import Vue from 'vue'
import Vuex from 'vuex'
import Cookies from 'js-cookie'
import axios from 'axios'
Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        signed: false,
        user: {
            name: '',
            avatar: '',
            email: '',
            role: 'guest'
        }
    },
    getters: {
        getSigned: state => {
            return state.signed
        },
        getUser: state => {
            return state.user
        }
    },
    mutations: {
        setUser: (state, user) => {
            state.user.name = user.name
            state.user.avatar = user.avatar
            state.user.role = user.name === 'admin' ? 'admin' : 'user'
            state.signed = true
            Cookies.set('role', state.user.role)
        },
        signOut: state => {
            state.signed = false
            Cookies.set('logged', false)
        },
        signIn: (state) => {
            axios.get('/status')
                .then(response => {
                    console.log(response)
                    let user = response.data
                    state.user.name = user.name
                    state.user.avatar = user.avatar
                    state.user.email = user.email
                    state.user.role = user.name === 'admin' ? 'admin' : 'user'
                    state.signed = true
                }).catch(err => {
                    console.log(err)
                })
        }
    },
    actions: {
        signIn: (context) => {
            context.commit('signIn')
        }
    }
})
