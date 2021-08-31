import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./components/css/global.css";
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "./config/axios";
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";

Vue.prototype.$http = axios;
Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");

const token = localStorage.getItem("token");
if (token) {
  const usr = localStorage.getItem("username");
  store.commit("login/setUsername", usr);
  if (usr) store.commit("login/setisRegisteredUser", true);
  store.commit("login/setToken", token);
}