<template>
  <div class="w-100 h-100">
    <div id="siteBackground">
      <div id="leftColumn"></div>
      <div id="rightColumn"></div>
      <div class="siteContainer">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
#siteBackground {
  position: relative;
}
#leftColumn,
#rightColumn {
  width: 5%;
  height: 100%;
  position: absolute;
  box-shadow: inset 0 0 20px rgba(0, 0, 0, 0.5);
}
#leftColumn {
  left: 0;
}
#rightColumn {
  right: 0;
}
.siteContainer {
  width: 90%;
  margin: auto;
  background-color: white;
}
</style>
