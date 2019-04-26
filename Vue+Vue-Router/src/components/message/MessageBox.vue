<template>
  <div :class="['msg-box', error? 'msg-err':'', success?'msg-suc':'']">
    <p>{{message}}</p>
  </div>
</template>

<script>
export default {
  name: "ErrorMessage",
  props: {
    message: {
      type: String,
      required: true
    },
    error: {
      type: Boolean,
      default: false,
      required: true
    },
    success: {
      type: Boolean,
      default: true
    }
  }
};
</script>
<style scoped>
.msg-box p {
  width: 100%;
  height: 40px;
  display: block;
  margin: 20px auto;
  line-height: 40px;
}
</style>
