<template>
  <div id="user-container">
    <div class="col1" id="img-container">
      <img :src="user.avatar" alt="avatar">
    </div>
    <div class="col2">
      <p id="name-area">{{user.name}}</p>
    </div>
    <div class="col3">
      <p id="email-area">{{user.email}}</p>
    </div>
    <div class="col4">{{user.consume}}</div>
    <div class="col5" :style="styleColor">{{stateText}}</div>
    <div class="col6">
      <svg class="icon icon-on" aria-hidden="true" v-show="activated" @click="switchOnOff">
        <use xlink:href="#icontoggleon"></use>
      </svg>
      <svg class="icon icon-off" aria-hidden="true" v-show="!activated" @click="switchOnOff">
        <use xlink:href="#icontoggle-off"></use>
      </svg>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      required: true
    },
    state: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      activated: true
    }
  },
  computed: {
    stateText () {
      if (this.activated) return 'Activated'
      else return 'Forbidden'
    },
    styleColor () {
      if (this.activated) {
        return {
          color: 'green'
        }
      } else {
        return {
          color: 'red'
        }
      }
    }
  },
  mounted: function () {
    this.activated = !this.$props.user.state
  },
  methods: {
    switchOnOff () {
      if (this.activated) {
        this.$emit('updateUser', {
          name: this.user.name,
          state: true
        })
      } else {
        this.$emit('updateUser', {
          name: this.user.name,
          state: false
        })
      }
    }
  },
  watch: {
    user: function (newUser) {
      this.activated = !newUser.state
    },
    state: function (newState) {
      this.activated = !newState
    }
  }
}
</script>

<style scoped>
#user-container {
  height: 100px;
  margin: 15px 10px;
  line-height: 24px;
  display: flex;
  flex-direction: row;
  font-size: 24px;
  border-bottom: 1px solid #dddddd;
}

#img-container img {
  width: 80px;
  height: 80px;
  border-radius: 100px;
  position: relative;
  top: 10px;
}
.col1 {
  width: 20%;
}
.col2,
.col3,
.col3,
.col4,
.col5 {
  width: 15%;
  padding-top: 24px;
}
.col6 {
  width: 10%;
  padding-top: 10px;
}
.icon-on {
  color: green;
  position: relative;
  top: 8px;
}
.icon-off {
  color: red;
  position: relative;
  top: 8px;
}
p {
  word-wrap: break-word;
  width: 140px;
  height: 80px;
  font-size: 20px;
  line-height: 24px;
  line-break: normal;
}
</style>
