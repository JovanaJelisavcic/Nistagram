<template>
  <div class="align-items-center d-inline-flex w-100 overflow-auto" style="max-height: 200px; height: 200px;">
    <template v-if="stories.length > 0">
      <b-col
        cols="2"
        class="p-2 h-200"
        v-for="(story, index) in stories"
        :key="index"
      ><template v-if="story.url && story.url[0] && story.url[0].url">
        <img
          v-if="story.url[0].mediaType == 'IMAGE'"
          :src="image_prefix + '/' + story.url[0].url"
         style="height: 200px; width: 200px;"
        />
         <video v-if="story.url[0].mediaType == 'VIDEO'" controls autoplay=false muted=true style="height: 200px; width: 200px;"  >
            <source :src="image_prefix + '/' + story.url[0].url" type="video/mp4">
            Your browser does not support the video tag.
        </video> 
         <span v-if="story.userID">{{ story.userID }}</span>
      </template>
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
