<template>
  <div ref="msg" :class="['msg-box', error? 'msg-err':'', success?'msg-suc':'']">
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
  },
  methods: {
    setHeight() {}
  },
  mounted() {
    this.setHeight();
  }
};
</script>
<style scoped>
.msg-box p {
  width: 100%;
  display: block;
  padding-top: 20px;
}
</style>
