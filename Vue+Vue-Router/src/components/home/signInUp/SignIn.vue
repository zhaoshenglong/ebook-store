<template>
    <form action="/" method="post">
        <div id = 'sign-in-container'>
        <div class = 'sign-in-up'>
            <label for="signin-field">Username or Email</label>
            <input class="input-control input-block" type="text" name="signin" id="signin-field" placeholder="username / email">
            <span class="clear-input"></span>
        </div>
        <div class = 'sign-in-up'>
            <label for="passwd">Password</label>
            <input class="input-control input-block" type="password" name="password" id="passwd" placeholder="password">
        </div>
        <input class="btn btn-block btn-submit" type="submit" value='sign in'/>
    </div>
    </form>
</template>
<script>
export default {
    name: 'signIn'
}
</script>
<style>
form{
    width: 300px;
    position: absolute;
    left: 10%;
    top: 40%;
    font-size: 14px;
}
form label{
    font-weight: 600;
    white-space: pre-wrap;
    display: block;
}
#sign-in-container{
    width: 100%;
    background: rgba(255, 255, 255, 0.7);
    padding: 20px 25px;
    text-align: left;
}
.sign-in-up{
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
