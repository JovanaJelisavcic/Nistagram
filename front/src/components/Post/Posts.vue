<template>
  <div class="w-100 pt-2">
    <div
      class="w-100 h4 d-inline-flex align-items-center justify-content-end "
      v-if="!isPrivateProfile"
    >
      <div id="likedPostsFilter"><b-icon icon="hand-thumbs-up"></b-icon></div>
      <div style="color: #ccc; padding-right: 5px; padding-left: 5px;">|</div>
      <div id="dislikedPostsFilter">
        <b-icon icon="hand-thumbs-down"></b-icon>
      </div>
    </div>
    <div
      class="w-100 d-inline-flex justify-content-around align-items-start p-3 BackgroundPsychodelic"
      style="max-height: 600px; min-height: 600px; height:600px; overflow-y: auto;"
    >
      <b-row v-if="!isPrivateProfile">
        <b-col cols="3" v-for="(item, index) in posts" :key="index">
          <PostPreview :post="item" class="m-1"></PostPreview>
        </b-col>
      </b-row>
      <div
        class="w-100 h-100 d-inline-flex align-items-center justify-content-center textShadowStrong"
        v-else
      >
        <div class="text-white " style="font-size: 100px;">
          <b-icon icon="shield-lock"></b-icon>
        </div>
        <div style="margin-left: 30px; font-size: 30px; color: white;">
          Sadržaj je nedostupan jer je nalog privatan
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { postTypes } from "../../assets/constants";
import PostPreview from "../Post/PostPreview";
export default {
  components: { PostPreview },
  data() {
    return {
      isPrivateProfile: false,
      posts: [
        {
          type: postTypes.post,
          text:
            "Bacon ipsum dolor amet boudin chislic short ribs, salami sirloin cupim fatback flank. Pork cupim filet mignon doner bacon spare ribs short loin andouille prosciutto short ribs rump jerky. Pork belly frankfurter bresaola beef. Sirloin cupim short loin brisket, kevin pork belly landjaeger pig. Pancetta turkey tenderloin, salami ham tongue spare ribs shank andouille jerky brisket. Alcatra leberkas bresaola pork belly beef tongue capicola doner ham shankle pancetta swine. Salami turducken venison, porchetta meatball beef ribeye flank.",
        },
        {
          type: postTypes.post,
          text:
            "Bacon ipsum dolor amet boudin chislic short ribs, salami sirloin cupim fatback flank. Pork cupim filet mignon doner bacon spare ribs short loin andouille prosciutto short ribs rump jerky. Pork belly frankfurter bresaola beef. Sirloin cupim short loin brisket, kevin pork belly landjaeger pig. Pancetta turkey tenderloin, salami ham tongue spare ribs shank andouille jerky brisket. Alcatra leberkas bresaola pork belly beef tongue capicola doner ham shankle pancetta swine. Salami turducken venison, porchetta meatball beef ribeye flank.",
        },
        {
          type: postTypes.post,
          text:
            "Bacon ipsum dolor amet boudin chislic short ribs, salami sirloin cupim fatback flank. Pork cupim filet mignon doner bacon spare ribs short loin andouille prosciutto short ribs rump jerky. Pork belly frankfurter bresaola beef. Sirloin cupim short loin brisket, kevin pork belly landjaeger pig. Pancetta turkey tenderloin, salami ham tongue spare ribs shank andouille jerky brisket. Alcatra leberkas bresaola pork belly beef tongue capicola doner ham shankle pancetta swine. Salami turducken venison, porchetta meatball beef ribeye flank.",
        },
        {
          type: postTypes.post,
          text:
            "Bacon ipsum dolor amet boudin chislic short ribs, salami sirloin cupim fatback flank. Pork cupim filet mignon doner bacon spare ribs short loin andouille prosciutto short ribs rump jerky. Pork belly frankfurter bresaola beef. Sirloin cupim short loin brisket, kevin pork belly landjaeger pig. Pancetta turkey tenderloin, salami ham tongue spare ribs shank andouille jerky brisket. Alcatra leberkas bresaola pork belly beef tongue capicola doner ham shankle pancetta swine. Salami turducken venison, porchetta meatball beef ribeye flank.",
        },
        {
          type: postTypes.post,
          text:
            "Bacon ipsum dolor amet boudin chislic short ribs, salami sirloin cupim fatback flank. Pork cupim filet mignon doner bacon spare ribs short loin andouille prosciutto short ribs rump jerky. Pork belly frankfurter bresaola beef. Sirloin cupim short loin brisket, kevin pork belly landjaeger pig. Pancetta turkey tenderloin, salami ham tongue spare ribs shank andouille jerky brisket. Alcatra leberkas bresaola pork belly beef tongue capicola doner ham shankle pancetta swine. Salami turducken venison, porchetta meatball beef ribeye flank.",
        },
      ],
    };
  },
};
</script>

<style>
#likedPostsFilter:hover {
  color: rebeccapurple;
  cursor: pointer;
}

#dislikedPostsFilter:hover {
  color: rebeccapurple;
  cursor: pointer;
}
</style>
