import axios from "../config/axios";
import { login } from "../api";
export default {
  namespaced: true,
  state: {
    email: null,
    first_name: null,
    last_name: null,
    username: null,
    password: null,
    token: null,
    status: null,
    isRegisteredUser: false,
  },
  mutations: {
    setisRegisteredUser(state, value) {
      state.isRegisteredUser = value;
    },
    setUsername(state, value) {
      state.username = value;
    },
    setToken(state, data) {
      state.token = data;
    },
    firstLogin(state) {
      state.status = "FIRST_LOGIN";
    },
    loginSuccess(state, data) {
      const { token, username } = data;
      state.username = username;
      state.token = token;
      localStorage.setItem("token", state.token);
      localStorage.setItem("username", state.username);
      axios.defaults.headers.common["authorization"] = `Bearer ${state.token}`;
      state.isRegisteredUser = true;
    },
    fillState(state) {
      state.token = localStorage.getItem("token");
      if (state.token) state.isRegisteredUser = true;
    },
    loginFailed(state) {
      state.status = "FAILED";
    },
    clearToken(state) {
      localStorage.removeItem("token");
      localStorage.removeItem("username");
      state.isRegisteredUser = false;
      state.username = null;
      state.token = null;
    },
    resetStatus(state) {
      state.status = null;
    },
  },
  actions: {
    async login({ commit }, { email, password }) {
      if (axios.defaults) {
        delete axios.defaults.headers.common["authorization"];
      }
      try {
        const response = await login({
          username: email,
          password: password,
        });
        if (response) {
          if (response.jwtToken) {
            commit("loginSuccess", {
              token: response.jwtToken ? response.jwtToken : null,
              username: email,
            });
            return true;
          }
        }
      } catch (e) {
        commit("loginFailed");
        return false;
      }
      return false;
    },
    async logout({ commit }) {
      //Todo
      commit("resetStatus");
      commit("clearToken");
    },
  },
  getters: {
    getisRegisteredUser(state) {
      return state.isRegisteredUser;
    },
    getEmail(state) {
      return state.email;
    },
    check(state) {
      let token = state.token;
      return !!token;
    },
    getUsername(state) {
      return state.username;
    },
  },
};
