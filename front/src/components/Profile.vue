<template>
  <b-overlay :show="isLoading">
    <div class="p-3 w-100" style="min-height: 100vh;">
       <b-button v-b-modal.createPostModal v-if="isMe">Napravi post</b-button>
      <b-modal
        id="createPostModal"
        @ok.prevent="createPost"
        @cancel="clearModals"
      >
        <b-input placeholder="Opis" v-model="description"></b-input>
        <b-input placeholder="Lokacija" v-model="location"></b-input>
        <b-form-file multiple v-model="postFiles"></b-form-file>
      </b-modal>
      <b-button v-b-modal.createStoryModal v-if="isMe">Napravi stori</b-button>
      <b-modal
        id="createStoryModal"
        @ok.prevent="createStory"
        @cancel="clearModals"
      >
        <b-form-file multiple v-model="storyFiles"></b-form-file>
      </b-modal>
      <div class="w-100 d-inline-flex align-items-center justify-content-end">
          <div class="mr-3">
          <b-icon icon="person"></b-icon>
          <span v-if="followers.length">{{ followers.length }}</span>
        </div>
        <b-button v-if="!isPrivateProfile" v-b-popover.bottomright="popoverData"
          >Pratioci</b-button
        >
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
         </div>
      <div v-if="!isSettingsMenu">
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
import axios from "axios";
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
      postFiles: [],
      location: null,
      description: null,
      storyFiles: [],
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
   methods: {
    clearModals() {
      this.postFiles = [];
      this.location = null;
      this.description = null;
      this.storyFiles = [];
    },
    async createPost() {
      let data = {
        userID: this.username,
        url: this.postFiles.map((file) => file.name),
        location: this.location,
        description: this.description,
      };
      let formData = new FormData();
      formData.append("file", this.postFiles ? this.postFiles : []);
      formData.append("data", JSON.stringify(data));
      try {
        await axios({
          method: "post",
          url: "/feed/posts/post",
          data: formData,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        const response = await getMyProfileContent();
        this.posts = response && response.posts ? response.posts : [];
        this.$bvModal.hide("createPostModal");
        alert("Uspesno");
      } catch {
        alert("Doslo je do greske");
      }
    },
    async createStory() {
      let data = {
        userID: this.username,
        url: this.storyFiles.map((file) => file.name),
      };
      let formData = new FormData();
      formData.append("file", this.storyFiles ? this.storyFiles : []);
      formData.append("data", JSON.stringify(data));
      try {
        await axios({
          method: "post",
          url: "/feed/posts/story",
          data: formData,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        const response = await getMyProfileContent();
        this.posts = response && response.stories ? response.stories : [];
        this.$bvModal.hide("createStoryModal");
        alert("Uspesno");
      } catch {
        alert("Doslo je do greske");
      }
    },
  },
  async created() {
    this.isLoading = true;
    try {
      if (this.isMe) {
        const response2 = await getProfileInfoMe();
        this.profileInfo = response2;

        const response = await getMyProfileContent();
        this.posts = response && response.posts ? response.posts : [];
        this.stories = response && response.stories ? response.stories : [];
      } else {
        if (this.$store.getters["login/getisRegisteredUser"]) {
          const response1 = await getProfileInfoUserRegistered(
            this.selectedProfileId
          );
          this.profileInfo = response1;
          if (response1 && response1.privacy == false) {
            const response = await getprofileContentRegistered(
              this.selectedProfileId
            );
            this.posts = response && response.posts ? response.posts : [];
            this.stories = response && response.stories ? response.stories : [];
          }
        } else {
          const response1 = await getProfileInfoUserPublic(
            this.selectedProfileId
          );
          this.profileInfo = response1;
          if (response1 && response1.privacy == false) {
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
