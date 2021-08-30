<template>
  <b-row class="p-3">
    <template v-if="posts.length > 0">
      <b-col cols="12" v-for="(post, index) in posts" :key="index">
        <HomepagePost :post="post"></HomepagePost>
      </b-col>
    </template>
    <div
      v-else
      class="w-100 d-inline-flex align-items-center justify-content-center"
    >
      Nema postova
    </div>
  </b-row>
</template>

<script>
import HomepagePost from "./HomepagePost.vue";
export default {
  props: ["posts"],
  components: { HomepagePost },
};
</script>

<style></style>
