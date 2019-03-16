<template>
    <form action="/" method="post" id="sign-up-form">
        <div class = 'sign-inup-container'>
        <div class = 'sign-in-up'>
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#iconuser"></use>
            </svg>
            <label for="signup-field">Username</label>
            <input class="input-control input-block" type="text" name="signup"
            id="signup-field" placeholder="username" v-model="usrName">
            <svg class="icon clear-input" aria-hidden="true"
            v-show="usrName!=''" @click="clearInput(1)">
                <use xlink:href="#icondelete"></use>
            </svg>
        </div>
        <div class = 'sign-in-up'>
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#iconemail"></use>
            </svg>
            <label for="email-field">Email</label>
            <input class="input-control input-block" type="email" name="email"
            id="email-field" placeholder="username" v-model="usrEmail">
            <svg class="icon clear-input" aria-hidden="true"
            v-show="usrEmail!=''" @click="clearInput(2)">
                <use xlink:href="#icondelete"></use>
            </svg>
        </div>
        <div class = 'sign-in-up'>
            <label for="passwd">Password</label>
            <input class="input-control input-block" type="password"
            name="password" id="passwd" placeholder="input password" v-model="usrPasswd">
        </div>
        <div class = 'sign-in-up'>
            <label for="passwd-confirm">Confirm password</label>
            <input class="input-control input-block" type="password"
            name="password" id="passwd-confirm" placeholder="input password again" v-model="usrPasswdAgain">
        </div>
        <input class="btn btn-block btn-submit" type="submit" value='sign up'/>
    </div>
    </form>
</template>
<script>
export default {
    name: 'signIn',
    data () {
        return {
            usrName: '',
            usrEmail: '',
            usrPasswd: '',
            usrPasswdAgain: ''
        }
    },
    methods: {
        clearInput (sign) {
            if (sign == 1){
                this.usrName = ''
            } else if (sign == 2){
                this.usrEmail = ''
            }
        },
        verifyUsrName() {},
        verifyEmail() {},
        verifyPasswd() {},
    }
}
</script>
<style scoped>
#sign-up-form{
    width: 300px;
    position: absolute;
    left: 15%;
    top: 30%;
    font-size: 14px;
}
#sign-up-form label{
    font-weight: 600;
    display: inline;
}

#clear-input{
    position: absolute;
    right: 32px;
    top: 50px;
    width: 2em;
    height: 2em;
    opacity: 0.4;
}
#clear-input:hover{
    opacity: 1;
}
.sign-in-up{
    position: relative;
    margin: 6px auto 16px;
}
.sign-in-up label{
    margin-bottom: 7px;
}
#signup-field, #email-field,
#passwd, #passwd-confirm{
    width: 282px;
    margin-top: 5px;
    margin-bottom: 15px;
}
</style>
