import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        signed: false,
        signId: ''
    },
    getters: {
        getSigned: state => {
            return state.signed
        },
        getSignId: state => {
            return state.signId
        }
    }
})
