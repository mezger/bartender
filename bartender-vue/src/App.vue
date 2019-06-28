<template>
  <div id="app">
    <h1>Bartender - Cocktail passend zu Deiner Stimmung</h1>
    <img alt="Vue logo" src="./assets/logo.jpg">
    <Webcam msg="Mache ein Bild von Dir!" @cocktailFound="updateCocktail"/>
    <Cocktail msg="Cocktail passend zu Deiner Stimmung" stimmung="fröhlich" v-bind:cocktail="childData"/>
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
  },
  data() {
    return {
      childData: {}
    };
  },
  methods: {
    updateCocktail(variable) {
      this.childData= variable;
    }
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
