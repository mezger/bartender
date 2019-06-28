<template>
  <div v-if="cocktail.name">
    <p class="result">Nachdem Du gerade<br><strong>{{ stimmung }}</strong><br>bist,
    </p>
    <p class="result">
      empfehlen wir Dir einen<br>
      <img v-bind:src="cocktail.image" v-bind:title="cocktail.name" v-bind:alt="cocktail.name" width="50%"/><br>
      <strong>{{ cocktail.name }}</strong>
    </p>
  </div>
</template>

<script>
export default {
  name: 'Cocktail',
  props: {
    msg: String,
    stimmung: String,
    cocktail: {}
  }
}
</script>
<style scoped>
p.result {
  font-size: 1.5em;
  }
p.result strong {
  font-size: 2em;
}
</style>

