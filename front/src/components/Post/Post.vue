<template>
  <div style="width : 100px; height: 100px;" v-if="post">
    <div class="width: 80px; height: 80px;">
      <img :src=post.image_url v-if="post.image_url"/>
      <img src="../../../public/images/photo-1557683316-973673baf926.jpg" v-else />
    </div>
    <div class="max-height: 20px; min-height: 20px;" v-if="post && post.type && post.type == allPostTypes.post">
      <div v-if="post.text">
          {{ post.text }}
      </div>
    </div>
  </div>
</template>

<script>
import { postTypes } from '../../assets/constants';
export default {
  props:['post'],
  data(){
    return{
      allPostTypes : postTypes,
    }
  }

}
</script>

<style>

</style>