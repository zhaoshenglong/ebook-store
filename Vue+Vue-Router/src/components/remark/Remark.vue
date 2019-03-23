<template>
  <div id="book-remark">
    <div id="remark-container">
      <div id="left-user">
        <img :src="remark.userImg" alt>
        <div>{{remark.userName}}</div>
      </div>
      <div id="right-remark">
        <div id="date-heading">
          {{remark.date}}
          <span>#{{stair}}</span>
        </div>
        <p id="remark-main">{{remark.content}}</p>
        <div id="remark-footer">
          <section>
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icondianzan"></use>
            </svg>
            <span>{{remark.helpful}}</span>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "BookRemark",
  props: {
    remark: {
      type: Object,
      required: true
    },
    stair: {
      type: Number
    }
  }
};
</script>
<style scoped>
#book-remark {
  margin-top: 20px;
  width: 100%;
}
#remark-container {
  margin: 0 20px;
  border: 1px solid #dddddd;
  position: relative;
  color: #666666;
  display: flex;
  flex-direction: row;
}
#left-user {
  background: #eeeeee;
  height: 160px;
  width: 120px;
}
#left-user img {
  width: 80px;
  height: 80px;
  border-radius: 100px;
  margin: 20px auto;
}
#left-user div {
  height: 32px;
  width: 100%;
  line-height: 15px;
  font-size: 14px;
}
#right-remark {
  flex: 1;
  font-size: 18px;
  height: 160px;
  display: flex;
  flex-direction: column;
}
#date-heading {
  background: #dadada;
  height: 24px;
  text-align: left;
  padding-left: 12px;
  line-height: 24px;
  position: relative;
}
#date-heading span {
  position: absolute;
  right: 40px;
}
#remark-main {
  flex: 1;
  padding: 12px 0;
  padding-left: 12px;
  line-height: 18px;
  font-size: 16px;
  text-align: left;
  overflow-y: scroll;
}
#remark-footer {
  height: 40px;
  line-height: 40px;
  background: #e7e7e7;
  position: relative;
}
#remark-footer section {
  position: absolute;
  right: 40px;
}
</style>
