import Vue from "vue";
import Vuex from "vuex";
import login from "./login";
import search from "./search";
import profile from "./profile";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    login,
    search,
    profile,
  },
});
