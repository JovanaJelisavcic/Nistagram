<template>
  <div class="align-items-center d-inline-flex w-100 overflow-auto" style="">
    <template v-if="stories.length > 0">
      <b-col
        cols="2"
        class="p-2"
        v-for="(story, index) in stories"
        :key="index"
      >
        <img :src="image_prefix + '/' + story.url[0].url" v-if="story && story.url" />
        <span v-if="story.userID">{{ story.userID }}</span>
      </b-col>
    </template>
    <div
      v-else
      class="w-100 d-inline-flex align-items-center justify-content-center"
    >
      Nema storija
    </div>
  </div>
</template>

<script>
export default {
  props: ["stories"],
  data() {
    return {
      image_prefix: process.env.VUE_APP_BAKEND_SLIKE_PUTANJA,
    };
  },
};
</script>

<style></style>
