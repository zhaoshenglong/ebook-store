<template>
  <div class="admin">
    <book-header></book-header>
    <div id="admin-bar">
      <section>
        <span>
          <svg class="icon icon-bar" aria-hidden="true">
            <use xlink:href="#iconuser"></use>
          </svg>admin
        </span>Welcome, Admin!
      </section>
    </div>
    <div id="admin-main">
      <div id="main-top">
        <span>Books</span>
        <span>Users</span>
      </div>
      <div id="view-container">
        <router-view></router-view>
      </div>
    </div>
    <book-footer></book-footer>
  </div>
</template>

<script>
import BookHeader from "../../components/page/Header";
import BookFooter from "../../components/page/Footer";
export default {
  components: {
    BookHeader,
    BookFooter
  }
};
</script>

<style scoped>
.admin {
  background-image: url("../../../static/background/sunshine.jpg");
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-size: cover;
}
.admin-main {
  width: 70%;
  background: rgba(255, 255, 255, 0.7);
  margin: 0 auto;
  min-width: 1024px;
  min-height: 1024px;
}
#admin-bar {
  width: 100%;
  height: 40px;
  margin-top: 2px;
  margin-bottom: 40px;
  background: rgba(229, 252, 251, 0.8);
  font-size: 16px;
}
#admin-bar span {
  margin: 0 20px;
  cursor: pointer;
}
.icon-bar {
  position: relative;
  top: 4px;
}
#admin-main {
  min-height: 1024px;
  width: 70%;
  margin: 0 auto;
  min-width: 1024px;
}
#main-top {
  background: transparent;
  display: flex;
  flex-direction: row;
}
#main-top span {
  background: rgba(255, 255, 255, 0.7);
  justify-content: left;
  margin-right: 10px;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 4px;
  font-size: 18px;
  width: 80px;
  height: 36px;
  line-height: 36px;
  color: #181818;
  font-weight: 600;
}
#view-container {
  border-top: 1px solid #b9b9b9;
  background: rgba(255, 255, 255, 0.7);
  min-height: 1024px;
  width: 100%;
}
</style>
