<template>
  <div id="userSearchResult" class="p-3" style="font-size: 12px;">
    <b-tabs v-model="selectedTab" content-class="mt-3">
      <b-tab title="Profil"></b-tab>
      <b-tab title="Username"></b-tab>
      <b-tab title="Lokacija"></b-tab>
      <b-tab title="Hashtag"></b-tab>
      <b-tab title="PostHashtag"></b-tab>
    </b-tabs>
    <div class="d-inline-flex justify-content-between w-100 p-4 pb-0 pt-0">
      <b-button size="sm" class="ml-auto" @click="input = null"
        >Zatvori</b-button
      >
      <b-button size="sm" @click="submitSearchForm()">Pretrazi</b-button>
    </div>
  </div>
</template>

<script>
import {
  searchByNameRegistered,
  searchByNamePublic,
  searchByUsernameRegistered,
  searchByUsernamePublic,
  searchByLocationRegistered,
  searchByLocationPublic,
  searchByHashtagRegistered,
  searchByHashtagPublic,
  searchByProfileHashRegistered,
  searchByProfileHashPublic,
} from "../../../api";
import { mapGetters, mapMutations } from "vuex";
export default {
  data() {
    return {};
  },
  computed: {
    selectedTab: {
      ...mapGetters("search", { get: "getsearchTabIndex" }),
      ...mapMutations("search", { set: "setsearchTabIndex" }),
    },
    input: {
      ...mapGetters("search", { get: "getSearchString" }),
      ...mapMutations("search", { set: "setSearchString" }),
    },
  },
  methods: {
    async submitSearchForm() {
      if (this.$store.getters["login/isRegisteredUser"]) {
        if (this.selectedTab === 0) {
          const response = await searchByNameRegistered(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 1) {
          const response = await searchByUsernameRegistered(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 2) {
          const response = await searchByLocationRegistered(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 3) {
          const response = await searchByHashtagRegistered(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 4) {
          const response = await searchByProfileHashRegistered(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
      } else {
        if (this.selectedTab === 0) {
          const response = await searchByNamePublic(this.input);
          console.log(response);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 1) {
          const response = await searchByUsernamePublic(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 2) {
          const response = await searchByLocationPublic(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 3) {
          const response = await searchByHashtagPublic(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
        if (this.selectedTab === 4) {
          const response = await searchByProfileHashPublic(this.input);
          this.$store.commit("search/setsearchResult", response);
        }
      }
    },
  },
};
</script>

<style>
#userSearchResult {
  position: absolute;
  background-color: rgb(238, 238, 238);
  z-index: 10000;
  width: 100%;
  height: 150px;
  overflow-y: auto;
  top: 70px;
  right: 0;
  box-shadow: 0px 5px 8px #999 inset;
}
</style>
