<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <Webcam msg="Bartender - Mache ein Bild von Dir!"/>
    <Cocktail msg="Bartender - Cocktail passend zu Deiner Stimmung"/>
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
