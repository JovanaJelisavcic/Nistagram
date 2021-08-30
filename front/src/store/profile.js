export default {
  namespaced: true,
  state: {
    profileId: null,
    isMe: false,
  },
  mutations: {
    setIsMe(state, data) {
      state.isMe = data;
    },
    setprofileId(state, data) {
      state.profileId = data;
    },
  },
  actions: {
    async getprofileIdData() {},
  },
  getters: {
    getprofileId(state) {
      return state.profileId;
    },
    getIsMe(state) {
      return state.isMe;
    },
  },
};
