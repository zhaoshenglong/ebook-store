<template>
  <div class = "setting-container">
    <h2 class="h2-heading">Public profile</h2>
    <div id = "profile-left">
          <h3 class="h3-heading">Name</h3>
          <input id= "input-name" class ="main-background input-control input-block" type="text"/>
           <h3 class="h3-heading">Email</h3>
          <input id= "input-email" class ="main-background input-control input-block" type="text"/>
          <button class="btn btn-block btn-submit">Update profile</button>
      </div>
      <div id = "profile-right">
        <h3 class="h3-heading">Profile picture</h3>
          <img src="" alt="upload avatar" title="upload avatar">
      </div>
  </div>
</template>
<script>
export default {};
</script>
<style scoped>
#profile-left{
  margin-top: 20px;
  width: 75%;
  float: left;
}
#input-name{
  width: 68%;
  max-width: 440px;
  margin-bottom: 25px; 
}
#input-email{
  width: 40%;
  max-width: 340px;
}
#profile-right{
  width: 25%;
  float: left;
  margin-top: 20px;
}
button{
  margin: 20px 0;
  width: 35%;
  max-width: 200px;;
}
</style>
