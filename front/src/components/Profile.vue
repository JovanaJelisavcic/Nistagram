<template>
  <b-overlay :show="isLoading">
    <div class="p-3 w-100" style="min-height: 100vh;">
      <div class="w-100 d-inline-flex align-items-center justify-content-end">
        <b-button v-b-popover.bottomright="popoverData">Pratioci</b-button>
        <b-button v-if="isMe" @click="showRequests = !showRequests"
          >Zahtevi</b-button
        >
        <div>
          <div
            v-if="isMe"
            class="h4 mb-2"
            style="margin-left: 20px;"
            id="setttingIcon"
            @click="isSettingsMenu = !isSettingsMenu"
          >
            <b-icon icon="gear"></b-icon>
          </div>
        </div>
        <b-button v-if="!isMe">
          <b-icon icon="plus-circle"></b-icon> Zaprati korisnika
        </b-button>
      </div>
      <div
        class="w-100 d-flex flex-column align-items-center justify-content-center"
      >
        <p class="h1 mb-2"><b-icon icon="person-circle"></b-icon></p>
        <div>{{ username }}</div>
        <template v-if="profileInfo">
          <div v-if="profileInfo.username">{{ profileInfo.username }}</div>
          <div v-if="profileInfo.name && profileInfo.surname">
            {{ profileInfo.name + " " + profileInfo.surname }}
          </div>
          <template v-if="!isPrivateProfile">
            <div v-if="profileInfo.email">{{ profileInfo.email }}</div>
            <div v-if="profileInfo.phoneNumber">
              {{ profileInfo.phoneNumber }}
            </div>
            <div v-if="profileInfo.sex">{{ profileInfo.sex ? "M" : "F" }}</div>
            <div v-if="profileInfo.birthday">{{ profileInfo.birthday }}</div>
            <div v-if="profileInfo.website">{{ profileInfo.website }}</div>
            <div v-if="profileInfo.bio">{{ profileInfo.bio }}</div>
          </template>
        </template>
      </div>
      <div v-if="!isSettingsMenu">
        <b-row v-if="showRequests">
          Zahtevi
          <b-col cols="12"></b-col>
        </b-row>
        <template v-if="!isPrivateProfile">
          <div
            class="d-inline-flex w-100 align-items-center justify-content-center p-0 m-0 mt-3 mb-3"
          >
            <b-col cols="3" class="m-0 p-0">
              <b-button>Lajkovani</b-button>
            </b-col>
            <b-col cols="3" class="m-0 p-0">
              <b-button>Dislajkovani</b-button>
            </b-col>
          </div>
          <HomepageStories :stories="stories"></HomepageStories>
          <HomepagePosts :posts="posts"></HomepagePosts>
        </template>
        <div v-else>
          <b-icon icon="dash-circle"></b-icon>
          <span>Privatan profil</span>
        </div>
      </div>

      <div v-else>
        <SettingsForm :info="profileInfo"></SettingsForm>
      </div>
    </div>
  </b-overlay>
</template>

<script>
import SettingsForm from "./UserSettings/SettingsForm";
import { mapGetters, mapMutations } from "vuex";
import {
  getprofileContentRegistered,
  getprofileContentPublic,
  getMyProfileContent,
  getProfileInfoMe,
  getFollowersForUser,
  getProfileInfoUserRegistered,
  getProfileInfoUserPublic,
} from "../api";
import HomepageStories from "../components/Homepage/HomepageStories.vue";
import HomepagePosts from "../components/Homepage/HomepagePosts.vue";
export default {
  components: { SettingsForm, HomepageStories, HomepagePosts },
  data() {
    return {
      isLoading: false,
      isSettingsMenu: false,
      posts: [],
      stories: [],
      profileInfo: null,
      followers: [],
      showRequests: false,
      friendRequests: null,
    };
  },
  computed: {
    username: {
      get() {
        return this.$route.params.id;
      },
    },
    selectedProfileId: {
      ...mapGetters("profile", { get: "getprofileId" }),
      ...mapMutations("profile", { set: "setprofileId" }),
    },
    isMe() {
      const usr = localStorage.getItem("username");
      return usr === this.username ? true : false;
    },
    followerContent() {
      if (this.followers && Array.isArray(this.followers)) {
        let content = "";
        this.followers.forEach((f) => {
          content += `${f}, `;
        });
        return content;
      }
      return "";
    },
    popoverData() {
      return {
        title: "Pratioci",
        content: this.followerContent,
      };
    },
    isPrivateProfile() {
      return this.profileInfo &&
        this.profileInfo.privacy &&
        this.profileInfo.privacy &&
        !this.isMe
        ? true
        : false;
    },
  },
  async created() {
    this.isLoading = true;
    try {
      if (this.isMe) {
        const response2 = await getProfileInfoMe();
        console.log(response2);
        this.profileInfo = response2;

        const response = await getMyProfileContent();
        this.posts = response && response.posts ? response.posts : [];
        this.stories = response && response.stories ? response.stories : [];
      } else {
        if (this.$store.getters["login/isRegisteredUser"]) {
          const response1 = getProfileInfoUserRegistered(
            this.selectedProfileId
          );
          this.profileInfo = response1;
          if (response1 && response.privacy && response.privacy == false) {
            const response = await getprofileContentRegistered(
              this.selectedProfileId
            );
            this.posts = response && response.posts ? response.posts : [];
            this.stories = response && response.stories ? response.stories : [];
          }
        } else {
          const response1 = getProfileInfoUserPublic(this.selectedProfileId);
          this.profileInfo = response1;
          if (response1 && response.privacy && response.privacy == false) {
            const response = await getprofileContentPublic(
              this.selectedProfileId
            );
            this.posts = response && response.posts ? response.posts : [];
            this.stories = response && response.stories ? response.stories : [];
          }
        }
      }
      const response = await getFollowersForUser(this.username);
      this.followers = response;
    } catch {
      alert("Nepostojeci user, greska prilikom ucitavanja usera.");
      this.$router.push("/home");
      this.isLoading = false;
    }
    this.isLoading = false;
  },
  destroyed() {
    this.$store.commit("profile/setIsMe", false);
    this.$store.commit("profile/setprofileId", null);
  },
};
</script>

<style>
#setttingIcon:hover {
  cursor: pointer;
}
</style>
