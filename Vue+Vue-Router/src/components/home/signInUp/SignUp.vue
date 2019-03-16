<template>
    <form action="/" method="post" id="sign-up-form">
        <div class = 'sign-inup-container'>
        <div class = 'sign-in-up'>
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#iconuser"></use>
            </svg>
            <label for="signup-field">Username</label>
            <input class="input-control input-block" type="text" name="signup"
            id="signup-field" placeholder="username" v-model="usrName"
            @input="verifyUsrName">
            <svg class="icon clear-input" aria-hidden="true"
            v-show="usrName!=''" @click="clearInput(1)">
                <use xlink:href="#icondelete"></use>
            </svg>
            <svg class="icon icon-verify icon-error" aria-hidden="true"
                v-show="nameVerified==1">
                <use xlink:href="#iconerror"></use>
            </svg>
            <svg class="icon icon-verify icon-success" aria-hidden="true"
                v-show="nameVerified==-1">
                <use xlink:href="#iconcomplete"></use>
            </svg>
        </div>
        <div class = 'sign-in-up'>
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#iconemail"></use>
            </svg>
            <label for="email-field">Email</label>
            <input class="input-control input-block" type="email" name="email"
            id="email-field" placeholder="username" v-model="usrEmail"
            @input="verifyEmail">
            <svg class="icon clear-input" aria-hidden="true"
            v-show="usrEmail!=''" @click="clearInput(2)">
                <use xlink:href="#icondelete"></use>
            </svg>
            <svg class="icon icon-verify icon-error" aria-hidden="true"
                v-show="emailVerified==1">
                <use xlink:href="#iconerror"></use>
            </svg>
            <svg class="icon icon-verify icon-success" aria-hidden="true"
                v-show="emailVerified==-1">
                <use xlink:href="#iconcomplete"></use>
            </svg>
        </div>
        <div class = 'sign-in-up'>
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#iconpassword"></use>
            </svg>
            <label for="passwd">Password</label>
            <input class="input-control input-block" type="password"
            name="password" id="passwd" placeholder="input password" v-model="usrPasswd">
        </div>
        <div class = 'sign-in-up'>
            <svg class="icon" aria-hidden="true">
                <use xlink:href="#iconpassword"></use>
            </svg>
            <label for="passwd-confirm">Confirm password</label>
            <input class="input-control input-block" type="password"
            name="password" id="passwd-confirm" placeholder="input password again"
            v-model="usrPasswdAgain" @forminput="verifyPasswd">
            <svg class="icon icon-verify icon-error" aria-hidden="true"
                v-show="passwdVerified==1">
                <use xlink:href="#iconerror"></use>
            </svg>
            <svg class="icon icon-verify icon-success" aria-hidden="true"
                v-show="passwdVerified==-1">
                <use xlink:href="#iconcomplete"></use>
            </svg>
        </div>
        
        <input id = "commit" class="btn btn-block btn-submit" type="submit" value='Create an count'/>
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
            usrPasswdAgain: '',
            nameVerified: 0,
            emailVerified: 0,
            passwdVerified: 0
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
        verifyUsrName() {
            if (this.usrName != '')
                return 1
        },
        verifyEmail() {
            if (this.usrEmail != '')
                return 1
        },
        verifyPasswd() {
            if (this.usrPasswdAgain != '' && this.usrPasswd != ''){
                if (this.usrPasswd != this.usrPasswdAgain)
                    return -1
                return 1
            }
            return 0
        }
    }
}
</script>
<style scoped>
#sign-up-form{
    width: 350px;
    position: absolute;
    left: 15%;
    top: 30%;
    font-size: 14px;
}
#sign-up-form label{
    font-weight: 600;
    display: inline;
}
#sign-up-form label:after{
    content: '*';
    color: red;
    margin-left: 6px;
    height: 24px;
}
.sign-inup-container{
	width: 300px;
    padding: 20px 25px;
    padding-right: 35px; 
	text-align: left;
	position: relative;
	background: rgba(255, 255, 255, 0.7);
}
.sign-in-up label{
    margin-bottom: 7px;
    height: 24px;
}
.sign-in-up{
    position: relative;
    width: 300px;
    height: 80px;
}
#signup-field, #email-field,
#passwd, #passwd-confirm{
    width: 282px;
    margin-top: 5px;
    margin-bottom: 15px;
}
#commit{
    width: 300px;
    margin-top: 5px;
    margin-bottom: 15px;
}
.icon-verify{
    position: absolute;
    top:30px;
    right:-28px;
}
.icon-success{
    color:green;
}
.icon-error{
    color:red;
}
.clear-input {
    position: absolute;
    right: 8px;
    top: 30px;
    width: 2em;
    height: 2em;
    opacity: 0.4;
}
.clear-input:hover {
    opacity: 1;
}
</style>
