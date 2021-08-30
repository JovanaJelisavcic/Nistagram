<template>
  <b-card
    style="width : 250px; height: 270px; background-color: rgba(70,70,70,0.3);"
    class="text-white textShadowStrong postPreview"
    v-if="post"
  >
    <div
      :class="{
        'post.type && post.type == allPostTypes.post': 'postImage w-100 shadow',
        'post.type && post.type != allPostTypes.post':
          'storyImage w-100 shadow',
      }"
    >
      <img :src="post.image_url" v-if="post.image_url" />
      <img
        class="w-100 h-100"
        src="../../../public/images/photo-1557683316-973673baf926.jpg"
        v-else
      />
    </div>
    <div
      class="w-100 d-inline-flex align-items-center justify-content-start p-1"
      style="height: 20px; max-height: 20px;"
    >
      <div style="padding-right: 5px;">
        <b-icon icon="hand-thumbs-up"></b-icon>0
      </div>
      <div><b-icon icon="hand-thumbs-down"></b-icon>0</div>
    </div>
    <div
      class="p-1"
      style="max-height: 70px; min-height: 70px; overflow-y : hidden;"
      v-if="post.type && post.type == allPostTypes.post"
    >
      <div v-if="post.text" style="font-size : 12px;">
        {{ post.text }}
      </div>
    </div>
  </b-card>
</template>

<script>
import { postTypes } from "../../assets/constants";
export default {
  props: ["post"],
  data() {
    return {
      allPostTypes: postTypes,
    };
  },
};
</script>

<style>
.postImage {
  height: 180px;
}
.storyImage {
  height: 230px;
}
.postPreview:hover {
  cursor: pointer;
}
</style>
