<template>
  <div
    class="w-100 d-inline-flex align-items-center justify-content-between BackgroundPsychodelic"
    style="height: 70px; position: relative;"
  >
    <div
      style="margin-left: 30px; color: white; text-shadow: 0px 5px 10px #e5c5f0;"
      class="textShadowStrong"
    >
      NIŠTAGRAM
      <b-button
        style="background-color: pink;"
        @click="goToMyProfile()"
        v-if="isRegistered"
        >ME</b-button
      >
    </div>

    <div class="p-3 d-inline-flex align-items-center justify-content-between">
      <div class="p-2"><b-icon icon="search"></b-icon></div>
      <b-input
        size="sm"
        style="width: 100%;"
        v-model="searchString"
        trim
      ></b-input>
      <SearchResultBox v-if="isSearching"></SearchResultBox>
    </div>
  </div>
</template>

<script>
import SearchResultBox from "./SearchResultBox.vue";
import { mapGetters, mapMutations } from "vuex";
export default {
  components: { SearchResultBox },
  data() {
    return {};
  },
  methods: {
    goToMyProfile() {
      this.$store.commit("profile/setIsMe", true);
      const usr = localStorage.getItem("username");
      console.log(usr);
      this.$router.push(`/profile/${usr}`);
    },
  },
  computed: {
    isSearching() {
      return this.searchString && this.searchString.trim() != "" ? true : false;
    },
    searchString: {
      ...mapGetters("search", { get: "getSearchString" }),
      ...mapMutations("search", { set: "setSearchString" }),
    },
    isRegistered() {
      return this.$store.getters["login/getisRegisteredUser"] ? true : false;
    },
  },
};
</script>

<style></style>
