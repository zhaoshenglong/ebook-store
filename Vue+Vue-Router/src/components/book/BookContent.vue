<template>
  <div id="book-content">
    <div id="content-container">
      <div>
        <h3>作者简介</h3>
        <div>{{book.authorInfo}}</div>
      </div>
      <div>
        <h3>内容简介</h3>
        <div>{{book.contentInfo}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BookContent",
  props: {
    book: {
      type: Object,
      required: true
    }
  }
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
