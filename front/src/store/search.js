export default {
  namespaced: true,
  state: {
    searchResult: null,
    searchTabIndex: 0,
    searchString: null,
  },
  mutations: {
    setsearchResult(state, data) {
      state.searchResult = data;
    },
    setsearchTabIndex(state, data) {
      state.searchTabIndex = data;
    },
    setSearchString(state, data) {
      state.searchString = data;
    },
  },
  actions: {
    async logout() {},
  },
  getters: {
    getsearchResult(state) {
      return state.searchResult;
    },
    getsearchTabIndex(state) {
      return state.searchTabIndex;
    },
    getSearchString(state) {
      return state.searchString;
    },
  },
};
