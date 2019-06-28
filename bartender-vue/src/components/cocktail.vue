<template>
  <div v-if="cocktail.name">
    <p class="result">Nachdem Du gerade<br><strong>{{ stimmung }}</strong><br>bist,
    </p>
    <p class="result">
      empfehlen wir Dir einen
    </p>
    <p>
      <img v-bind:src="cocktail.image" v-bind:title="cocktail.name" v-bind:alt="cocktail.name" width="50%"/>
    </p>
    <p class="result">
      <strong>{{ cocktail.name }}</strong>
    </p>
    <p class="result" style="font-weight: bold; text-align:">Ingredients</p>
    <ul class="result" v-for="ingredient in cocktail.listIngredients">
      <li>{{ingredient}}</li>
    </ul>
    <p class="result">
      {{cocktail.zubereitung}}
    </p>
  </div>
</template>

<script>
export default {
  name: 'Cocktail',
  props: {
    msg: String,
    cocktail: {}
    stimmung: {},

  }
}
</script>

<style scoped>
.result {
  font-size: 1.5em;
  }
.result strong {
  font-size: 2em;
}
</style>

