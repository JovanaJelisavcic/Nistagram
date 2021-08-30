import Vue from "vue";
import VueRouter from "vue-router";
import App from "../App.vue";
import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "App",
    component: App,
    redirect: "/login",
    meta: {},
  },
  {
    path: "/profile/:id",
    name: "profile",
    component: require("../components/Profile.vue").default,
    meta: {},
  },
  {
    path: "/home",
    name: "Homepage",
    component: require("../components/Homepage/Homepage.vue").default,
    meta: {},
  },
  {
    path: "/login",
    name: "Login",
    component: require("../views/Login").default,
    meta: {},
  },
];

store.commit("login/fillState");

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
