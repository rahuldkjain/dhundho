import searchApi from '../src/apis/searchApi.js'
export default {
    state: {
        searchResult: "",
    },
    getters: {
        getSearchResult: (state) => state.searchResult
    },
    mutations: {
        SET_RESULT: (state, result) => {
            state.searchResult = result.data
        }
    },
    actions: {
        search: (context, searchTerm) => {
            // console.log('data in action'+payload)
            searchApi.searchItem((result) => {
                context.commit('SET_RESULT', result.data)
            }, searchTerm)
        }
    }
}