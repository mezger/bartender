<template>
  <div v-if="cocktail.name">
    <div class="result">
      Du bist gerade: <div id="emotion">{ cocktail.emotion }}</div>
    </div>
    <p class="result">
      Wir empfehlen dir einen
    </p>
    <p>
      <img v-bind:src="cocktail.image" v-bind:title="cocktail.name" v-bind:alt="cocktail.name" width="50%"/>
    </p>
    <p class="result">
      <strong>{{ cocktail.name }}</strong>
    </p>
    <div class="klapper">
      <b-button  v-b-toggle.collapse-cocktail  variant="secondary">Zubereitung </b-button >
      <b-collapse  id = "collapse-cocktail"  class = "mt-2">
        <p class="result" style="font-weight: bold; text-align: center">Ingredients</p>
        <ul class="result" v-for="ingredient in cocktail.listIngredients" :key="ingredient">
          <li>{{ingredient}}</li>
        </ul>
        <p class="result">
          {{cocktail.zubereitung}}
        </p>
      </b-collapse >
    </div >
  </div>
</template>

<script>
export default {
  name: 'Cocktail',
  props: {
    msg: String,
    cocktail: {},
    stimmung: {}
  }
}
</script>

<style scoped>
.result {
  font-size: 1.2em;
  }
#collapse-cocktail{
  background-color: #fff;
}
#emotion{
  display: inline-block;
  font-weight: bold;
}
</style>

