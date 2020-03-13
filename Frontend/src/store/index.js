/* eslint-disable indent */
import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        signed: false,
        user: {
            name: undefined,
            avatar: undefined,
            email: undefined,
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
        SET_USER: (state, user) => {
            state.user.avatar = user.avatar
            state.user.email = user.email
            state.user.name = user.name
            state.user.role = user.name === 'admin' ? 'admin' : 'user'
            state.signed = true
        },
        SET_SIGNED: state => {
            state.signed = true
        },
        REMOVE_SIGNED: state => {
            state.signed = false
        },
        REMOVE_USER: state => {
            state.user.avatar = undefined
            state.user.email = undefined
            state.user.name = undefined
            state.user.role = 'guest'
        }
    },
    actions: {
        signIn: ({
            commit
        }, data) => {
            return new Promise((resolve, reject) => {
                axios.post('/login', data, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then(res => {
                        const message = res.data
                        commit('SET_SIGNED')
                        resolve(message)
                    })
                    .catch(err => {
                        /* Response is Ok */
                        if (err.response) {
                            reject(err.response)
                        } else if (err.request) {
                            /* Request is being dealt with */
                        } else {
                            /* Respone throw error */
                            console.log(err)
                            throw err
                        }
                    })
            })
        },
        signOut: ({
            commit
        }) => {
            return new Promise((resolve, reject) => {
                axios.post('/logout')
                    .then(response => {
                        commit('REMOVE_USER')
                        commit('REMOVE_SIGNED')
                        console.log(response)
                        resolve()
                    })
                    .catch(err => {
                        console.log(err)
                        reject(err)
                    })
            })
        },
        getStatus: ({
            commit
        }) => {
            return new Promise((resolve, reject) => {
                axios.get('/api/public/status')
                    .then(response => {
                        const data = response.data
                        if (data.name === null) {
                            commit('REMOVE_SIGNED')
                            commit('REMOVE_USER')
                            throw new Error('user not logged in')
                        } else {
                            commit('SET_USER', response.data)
                            resolve(response.data)
                        }
                    }).catch(err => {
                        commit('REMOVE_SIGNED')
                        commit('REMOVE_USER')
                        reject(err)
                    })
            })
        }
    }
})
