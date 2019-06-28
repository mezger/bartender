<template>
  <div id="app">
   <b-container fluid>
    <h1>Bartender - Cocktail passend zu Deiner Stimmung</h1>
      <b-row> 
          <!--<img alt="Vue logo" src="./assets/strand.jpg"> -->
          <b-col>
            <Webcam msg="Mache ein Bild von Dir!" @cocktailFound="updateCocktail"/>
          </b-col>  
        <b-col> 
          <Cocktail msg="Cocktail passend zu Deiner Stimmung" :stimmung="stimmung" v-bind:cocktail="childData" />
        </b-col>  
      </b-row>   
    </b-container>  
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
      childData: {},
      stimmung: String
    };
  },
  methods: {
    updateCocktail(variable) {
      debugger;
      const emotions = variable.rekognitionResult.emotions.sort((a, b)=> {
         console.log(a.weight + " " + b.weight);
         if (a.weight< b.weight) {
          return 1;
        }
        if (a.weight > b.weight) {
          return -1;
        }
        return 0;
      });
      let stimmung = "nicht analysierbar";
      switch (emotions[0].emotion) {
        case "HAPPY":
          this.stimmung = "glücklich";
          break;
        case "SAD":
          this.stimmung = "traurig";
          break;
        case "ANGRY":
          this.stimmung = "wütend";
          break;
        case "CONFUSED":
          this.stimmung = "verwiirt";
          break;
        case "DISGUSTED":
          this.stimmung = "angewidert";
          break;
        case "SURPRISED":
          this.stimmung = "überrascht";
          break;
        case "CALM":
          this.stimmung = "ruhig";
          break;
        default:
          this.stimmung = "nicht analysierbar";
      }
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
  margin-top: 0px;
  background-image: url("./assets/strand.jpg");
}
h1{
    padding-bottom: 30px;
    padding-top: 30px;
    font-size: 3.5rem;
}
.container-fluid{
  padding-bottom: 10%;
}
</style>
