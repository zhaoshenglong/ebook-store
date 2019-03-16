<template>
    <div id = 'store-container'>
        <BookHeader/>
        <div>Internal</div>
        <router-view name = "sidebar"></router-view>
        <router-view name = "main"></router-view>
        <BookFooter/>
    </div>
</template>
<script>
import BookHeader from './store/Header'
import BookFooter from './store/Footer'
export default {
    name: 'BookStore',
    components: {
        BookHeader,
        BookFooter
    }
}
</script>
<style scoped>

</style>
