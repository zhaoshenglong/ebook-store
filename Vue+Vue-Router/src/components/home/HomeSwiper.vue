<template>
    <div id="swiper-container">
        <swiper :options="swiperOption">
        <swiper-slide v-for="swiper in swiperList" :key="swiper.title"
            :style="{backgroundImage: 'url(' + swiper.imgUrl + ')',
            backgroundSize: '380px 500px', backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat', backgroundColor: '#e6f7f3'}"
            data-swiper-autoplay="2000">
        </swiper-slide>
        <div class="swiper-pagination swiper-pagination-blue" slot="pagination"></div>
        <div class="swiper-button-prev swiper-button-blue" slot="button-prev"></div>
        <div class="swiper-button-next swiper-button-blue" slot="button-next"></div>
      </swiper>
    </div>
</template>
<script>
export default {
    name: 'HomeSwiper',
    data () {
        return {
            swiperOption: {
                spaceBetween: 30,
                effect: 'fade',
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                },
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev'
                },
                autoplay: {
                    disableOnInteraction: false,
                }
            },
            swiperList: [
                {
                    title: 'Literature',
                    description: 'The most classic literature!',
                    imgUrl: '../../../static/swiper/swiper2.jpg'
                },
                {
                    title: 'Technology',
                    description: 'Want to know latest technology?',
                    imgUrl: '../../../static/swiper/swiper4.jpg'
                },
                {
                    title: 'Novel',
                    description: 'Famous chinese science fiction author',
                    imgUrl: '../../../static/swiper/swiper1.jpg'
                },
                {
                    title: 'Prose',
                    description: 'Beautiful and elegant',
                    imgUrl: '../../../static/swiper/swiper3.jpg'
                },
                {
                    title: 'Story',
                    description: 'The best-selling story book!',
                    imgUrl: '../../../static/swiper/swiper5.jpg'
                }
            ]
        }
    }
}
</script>
<style>
#swiper-container{
    position: absolute;
    top: 30%;
    left: 10%;
    width: 500px;
    height: 500px;
}
.swiper-container{
    height: 100%;
    width: 100%;
    margin: 0 auto;
}
.swiper-silde{
    color: #fff;
    font-size: 18px;
    width: 100%;
    height: 100%;
}
</style>
