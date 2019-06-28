<template>
  <div>
    <h2>{{ msg }}</h2>
    <p>Nachdem Du gerade {{ stimmung }} bist, empfehlen wir Dir einen {{ cocktail.name }}</p>
    <p>
      <img v-bind:src="cocktail.image" v-bind:title="cocktail.name" v-bind:alt="cocktail.name" width="50%"/>
      </p>
  </div>
</template>

<script>
export default {
  name: 'Cocktail',
  props: {
    msg: String,
    stimmung: String,
    cocktail: Object
  }
}
</script>
