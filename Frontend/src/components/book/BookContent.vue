<template>
  <div id="book-content">
    <div id="content-container">
      <div>
        <h3 class="h2-heading">作者简介</h3>
        <div id="author" class="book-info"></div>
      </div>
      <div>
        <h3 class="h2-heading">内容简介</h3>
        <div id="content" class="book-info"></div>
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
  },
  data() {
    return {
      ok: true
    };
  },
  watch: {
    book: book => {
      document.getElementById("content").innerHTML = book.contentInfo;
      document.getElementById("author").innerHTML = book.authorInfo;
    }
  }
};
</script>

<style>
#book-content {
  width: 100%;
  min-height: 800px;
  margin-top: 30px;
}
#content-container {
  margin: 30px 20px;
  height: 100%;
  border-bottom: 1px solid #dddddd;
}
.book-info {
  font-size: 20px;
  margin: 30px 10px 30px 100px;
  text-align: left;
  line-height: 30px;
}
.book-info p {
  color: #0a0a0a;
  margin: 20px;
  text-indent: 40px;
}
</style>
