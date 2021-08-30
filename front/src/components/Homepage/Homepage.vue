<template>
  <div style="min-height: 100vh; position: relative;">
    <UserSearch></UserSearch>
    <template v-if="!searchResults">
      <HomepageStories :stories="HomepageStories"></HomepageStories>
      <HomepagePosts :posts="HomepagePosts"></HomepagePosts>
    </template>
    <template v-else>
      <HomepageSearchResults></HomepageSearchResults>
    </template>
  </div>
</template>

<script>
import UserSearch from "./Search/UserSearch.vue";
import HomepagePosts from "./HomepagePosts";
import HomepageStories from "./HomepageStories";
import HomepageSearchResults from "./HomepageSearchResults";
import { fetchHomepageUnregistered, fetchHomepageRegistered } from "../../api";
import { mapGetters, mapMutations } from "vuex";

export default {
  components: {
    UserSearch,
    HomepagePosts,
    HomepageStories,
    HomepageSearchResults,
  },
  data() {
    return {
      HomepagePosts: [],
      HomepageStories: [],
    };
  },
  computed: {
    searchResults: {
      ...mapGetters("search", { get: "getsearchResult" }),
      ...mapMutations("search", { set: "setsearchResult" }),
    },
  },
  async mounted() {
    if (this.$store.getters["login/isRegisteredUser"]) {
      const response = await fetchHomepageRegistered();
      this.HomepagePosts = response && response.posts ? response.posts : [];
      this.HomepageStories =
        response && response.stories ? response.stories : [];
    } else {
      const response = await fetchHomepageUnregistered();
      this.HomepagePosts = response && response.posts ? response.posts : [];
      console.log(this.HomepagePosts);
      this.HomepageStories =
        response && response.stories ? response.stories : [];
    }
  },
};
</script>

<style></style>
