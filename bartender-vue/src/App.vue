<template>
  <div id="app">
    <b-container fluid>
      <b-row>
        <b-col>
          <img id="logo" src="./assets/Logo_Hackathon.png">
         <h1>Smart Bartender - Cocktail passend zu Deiner Stimmung</h1>
        </b-col>
      </b-row>
      <b-row class="content">
        <b-col xs="12" lg="6">
          <Webcam msg="Mache ein Bild von Dir!" @cocktailFound="updateCocktail"/>
        </b-col>
        <b-col xs="12" lg="6">
          <Cocktail msg="Cocktail passend zu Deiner Stimmung" v-bind:cocktail="childData"/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Webcam from './components/webcam.vue';
import Cocktail from './components/cocktail.vue';

export default {
  name: 'app',
  components: {
    Webcam,
    Cocktail
  },
  data() {
    return {
      childData: {},
      stimmung: {}
    };
  },
  methods: {
    updateCocktail(variable) {
      this.childData = variable;
    }
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
#logo{
  float: left;
  margin-top: 10px;
}
h1 {
  padding-bottom: 15px;
  padding-top: 15px;
  font-size: 2.5rem;
}
.container-fluid {
  padding-bottom: 10%;
}
.content{
  background-image: url("./assets/strand.jpg");
  background-repeat: no-repeat;
}
  .content > .col-lg-6{
    padding-top: 15px;
  }
</style>
