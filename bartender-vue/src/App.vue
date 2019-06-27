<template>
  <div id="app">
    <h1>Bartender - Cocktail passend zu Deiner Stimmung</h1>
    <img alt="Vue logo" src="./assets/logo.jpg">
    <Webcam msg="Mache ein Bild von Dir!"/>
    <Cocktail msg="Cocktail passend zu Deiner Stimmung" stimmung="fröhlich" v-bind:cocktail="{
    name: 'Tequilla Sunrise',
    url: 'https://cocktailbart.de/wp-content/uploads/2016/01/Fotolia_37105328_Subscription_Monthly_M-1024x681.jpg'
  }"/>
  </div>
</template>

<script>
import Webcam from './components/webcam.vue'
import Cocktail from './components/cocktail.vue'

export default {
  name: 'app',
  components: {
    Webcam,
    Cocktail
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
