<template>
  <div id="book-content">
    <div id="content-container"></div>
  </div>
</template>

<script>
export default {
  name: "BookContent"
};
</script>

<style>
#book-content {
  width: 100%;
  height: 1000px;
}
#content-container {
  margin: 0 20px;
  height: 100%;
  border-bottom: 1px solid #dddddd;
  overflow-y: scroll;
}
</style>
