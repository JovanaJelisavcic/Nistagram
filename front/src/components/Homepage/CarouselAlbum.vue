<template>
  <div class="w-100 h-100" style="position: relative;">
     <template
      v-if="
        currentUrlObject && currentUrlObject.url && currentUrlObject.mediaType
      "
    >
      <img
        v-if="currentUrlObject.mediaType == 'IMAGE'"
        :src="image_prefix + '/' + currentUrlObject.url"
        style="width: 100%; height: auto;"
      />
      <video
        v-if="currentUrlObject.mediaType == 'VIDEO'"
        controls
        autoplay="false"
        muted="true"
      >
        <source
          :src="image_prefix + '/' + currentUrlObject.url"
          type="video/mp4"
        />
        Your browser does not support the video tag.
      </video>
    </template>
    <img
      v-else
      src="../../assets/post.jpg"
      style="width: 100%; height: auto;"
    />
    <div
      class="arrow-left"
      @click="left()"
      style="position: absolute; top: 50%; left: 5px;"
    >
      <b-icon icon="arrow-left-square"></b-icon>
    </div>
    <div
      class="arrow-right"
      @click="right()"
      style="position: absolute; top: 50%; right: 5px;"
    >
      <b-icon icon="arrow-right-square"></b-icon>
    </div>
    <b-row
      class="p-0 m-0 dots-bottom align-items-center justify-content-center w-100"
    >
      <b-col
        cols="1"
        v-for="index in imageUrlArray.length"
        :key="index"
        class="p-0 m-0"
      >
        <b-icon icon="dot"></b-icon>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  props: ["imageUrlArray"],
  data() {
    return {
      currentImageIndex: 0,
      image_prefix: process.env.VUE_APP_BAKEND_SLIKE_PUTANJA,
    };
  },
  methods: {
    left() {
      if (this.currentImageIndex == 0) {
        this.currentImageIndex = this.imageUrlArray.length - 1;
      } else {
        this.currentImageIndex--;
      }
    },
    right() {
      if (this.currentImageIndex == this.imageUrlArray.length - 1) {
        this.currentImageIndex = 0;
      } else {
        this.currentImageIndex++;
      }
    },
  },
  computed: {
    currentUrlObject() {
      if (this.imageUrlArray && this.imageUrlArray[this.currentImageIndex]) {
      return this.imageUrlArray[this.currentImageIndex] &&
          this.imageUrlArray[this.currentImageIndex].url
          ? this.imageUrlArray[this.currentImageIndex]
          : "../../assets/post.jpg";
      }
      return "../../assets/post.jpg";
    },
  },
};
</script>

<style>
.arrow-left,
.arrow-right,
.dots-bottom {
  z-index: 1000000;
  color: violet;
  font-size: 20px;
}
.arrow-left:hover {
  cursor: pointer;
}
.arrow-right:hover {
  cursor: pointer;
}
</style>
