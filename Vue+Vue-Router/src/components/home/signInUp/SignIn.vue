<template>
    <form action="/" method="post" id="sign-in-form">
        <div class = 'sign-inup-container'>
        <div class = 'sign-in-up'>
            <label for="signin-field">Username or Email</label>
            <input class="input-control input-block" type="text" name="signin"
            id="signin-field" placeholder="username / email" v-model="usrName">
            <svg class="icon clear-input" aria-hidden="true"
            v-show="usrName!=''" @click="clearInput">
                <use xlink:href="#icondelete"></use>
            </svg>
        </div>
        <div class = 'sign-in-up'>
            <label for="passwd">Password</label>
            <input class="input-control input-block" type="password"
            name="password" id="passwd" placeholder="password" v-model="usrPasswd">
        </div>
        <input class="btn btn-block btn-submit" type="submit" value='sign in'/>
    </div>
    </form>
</template>
<script>
export default {
    name: 'signIn',
    data () {
        return {
            usrName: '',
            usrPasswd: '',
        }
    },
    methods: {
        clearInput () {
            this.usrName = ''
        }
    }
}
</script>
<style scoped>
#sign-in-form{
    width: 300px;
    position: absolute;
    left: 15%;
    top: 40%;
    font-size: 14px;
}
#sign-in-form label{
    font-weight: 600;
    white-space: pre-wrap;
    display: block;
}
.sign-in-up{
    position: relative;
    margin: 6px auto 16px;
}
.sign-in-up label{
    margin-bottom: 7px;
}
#signin-field{
    width: 282px;
    margin-top: 5px;
    margin-bottom: 15px;
}
#passwd{
    width: 282px;
    margin-top: 5px;
    margin-bottom: 15px;   
}

</style>
