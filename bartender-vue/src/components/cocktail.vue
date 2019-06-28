<template>
  <div v-if="cocktail.name">
    <p class="result">Nachdem Du gerade<br><strong>{{ cocktail.emotion }}</strong><br>bist,
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
  font-size: 1.5em;
  }
.result strong {
  font-size: 2em;
}
#collapse-cocktail{
  background-color: #fff;
}

</style>

