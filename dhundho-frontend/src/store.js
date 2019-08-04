import Vue from 'vue'
import Vuex from 'vuex'
import searchModule from '../localStore/searchModule.js'


Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        searchModule
    }
})