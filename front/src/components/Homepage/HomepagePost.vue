<template>
  <b-row class="p-3">
    <b-col cols="6" v-if="post">
      <template v-if="post.postType === 'post'">
        <img
          
          :src="image_prefix + '/' + post.url[0].url"
          style="width: 100%; height: auto;"
        />
     
      </template>
      <template v-if="post.postType === 'album'">
        <CarouselAlbum :imageUrlArray="post.url"></CarouselAlbum>
      </template>
    </b-col>
    <b-col cols="6" v-if="post">
      <b-row
        class="h2 font-weight-bold p-0 m-0 UserNamePost"
        @click="goToProfile(post)"
      >
        {{ post.userID }}</b-row
      >
      <div class="d-inline-flex align-items-center justify-content-end w-100">
        <b-icon icon="map"></b-icon>
        <span class="">{{ post.location }}</span>
      </div>
      <b-row class="p-0 m-0 mt-3"> Desc: {{ post.description }}</b-row>
      <b-row class="p-0 m-0 mt-3">
        <b-col cols="2" class="m-0 p-0 mr-2"
          ><span
            ><b-icon
              icon="hand-thumbs-up"
              style="margin-right: 5px !important"
            ></b-icon
            >{{ post.numOfLikes }}</span
          ></b-col
        >
        <b-col cols="2" class="m-0 p-0 mr-2"
          ><span
            ><b-icon
              icon="hand-thumbs-down"
              style="margin-right: 5px !important"
            ></b-icon
            >{{ post.disliked.length }}</span
          ></b-col
        >
        <b-col cols="2" class="m-0 p-0 mr-2"
          ><span
            ><b-icon icon="cloud" style="margin-right: 5px !important"></b-icon
            >{{ post.numOfComments }}</span
          ></b-col
        >
      </b-row>
      <b-row class="p-0 m-0 mt-3"> Profile tags:</b-row>
      <b-row class="align-items-center justify-content-start p-0 m-0 ">
        <span
          class="profileTag"
          v-for="(pt, index) in post.profiletags"
          :key="index"
          @click="`$router.push('/profile/${pt.userID}')`"
        >
          @{{ pt }}
        </span>
      </b-row>
      <label class="mt-3">
        #Hashtags:
      </label>
      <b-row class="align-items-center justify-content-start p-0 m-0 ">
        <span
          class="hashTags"
          v-for="(ht, index) in post.hashtags"
          :key="index"
        >
          #{{ ht }}
        </span>
      </b-row>
    </b-col>
  </b-row>
</template>

<script>
import CarouselAlbum from "./CarouselAlbum.vue";
export default {
  props: ["post"],
  components: { CarouselAlbum },
  data() {
    return {
      image_prefix: process.env.VUE_APP_BAKEND_SLIKE_PUTANJA,
    };
  },
  methods: {
    goToProfile(profile) {
      console.log(profile);
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
.UserNamePost:hover {
  cursor: pointer;
  text-shadow: 1px 3px 5px violet;
}
.profileTag,
.hashTags {
  color: violet;
}
.profileTag:hover {
  cursor: pointer;
}
</style>
