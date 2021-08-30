<template>
  <div id="resultsOverlay">
    <b-button @click="searchResults = null" class="mb-3 mt-3"
      >Povratak</b-button
    >
    <template v-if="searchTabIndex >= 0 && searchTabIndex < 2">
      <b-row class="p-0 m-0">
        <b-col cols="12" class="p-0 m-0">
          <b-row
            v-for="(item, index) in searchResults"
            :key="index"
            class="p-0 m-0"
          >
            <b-col
              @click="goToProfile(item)"
              class="profilcic p-3 m-0 border"
              cols="12"
            >
              <h5>{{ item.username }}</h5>
              <span>{{ item.name + " " + item.surname }}</span>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </template>
    <template v-if="searchTabIndex >= 2 && searchTabIndex < 5">
      <HomepagePosts :posts="searchResults"></HomepagePosts>
    </template>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
import HomepagePosts from "./HomepagePosts";

export default {
  components: { HomepagePosts },
  computed: {
    searchResults: {
      ...mapGetters("search", { get: "getsearchResult" }),
      ...mapMutations("search", { set: "setsearchResult" }),
    },
    searchTabIndex: {
      ...mapGetters("search", { get: "getsearchTabIndex" }),
      ...mapMutations("search", { set: "setsearchTabIndex" }),
    },
  },
  methods: {
    goToProfile(profile) {
      this.$store.commit(
        "profile/setprofileId",
        profile && profile.userID ? profile.userID : null
      );
      this.$router.push(`/profile/${profile.userID}`);
    },
  },
};
</script>

<style>
.profilcic {
  background-color: lightblue;
}
.profilcic:hover {
  cursor: pointer;
}
#resultsOverlay {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  z-index: 100000000000;
  background-color: white;
}
</style>
