<template>
  <div id="profile">
    <div id="p-left">
      <img :src="user.avatar">
    </div>
    <div id="p-right">
      <section class="p-sec">
        <span>Name:</span>
        <span>{{user.name}}</span>
      </section>
      <section id="p-sec">
        <span>Email:</span>
        <span>{{user.email}}</span>
      </section>
      <section id="p-sec">
        <span>Bill:</span>
        <span>{{user.totalBill}}</span>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  props: {
    user: {
      type: Object,
      required: true
    }
  }
}
</script>

<style>
#profile {
  font-size: 18px;
  position: relative;
}
#p-left {
  position: absolute;
  left: 2px;
  top: 10px;
}
#p-left img {
  width: 50px;
  height: 50px;
  border-radius: 100px;
}
#p-right {
  margin-left: 60px;
}
</style>
