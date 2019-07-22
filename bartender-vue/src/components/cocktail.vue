<template>
    <div v-if="cocktail.name">
        <div class="result">
            Du bist gerade:
            <div id="emotion">{{ cocktail.emotion }}</div>
        </div>
        <div class="result">
            Wir empfehlen dir einen
            <div id="cocktail-name">{{ cocktail.name }}</div>
        </div>
        <div class="cocktail-image">
            <img v-bind:src="cocktail.image" v-bind:title="cocktail.name" v-bind:alt="cocktail.name" width="90%"/>
        </div>
        <div class="klapper">
            <b-button v-b-toggle.collapse-cocktail variant="secondary">Zubereitung</b-button>
            <b-collapse id="collapse-cocktail" class="mt-2">
                <p class="result" style="font-weight: bold; text-align: center">Ingredients</p>
                <ul class="result" v-for="ingredient in cocktail.listIngredients" :key="ingredient">
                    <li>{{ingredient}}</li>
                </ul>
                <p class="result">
                    {{cocktail.zubereitung}}
                </p>
            </b-collapse>
        </div>
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
        font-size: 1.4em;
    }

    #collapse-cocktail {
        background-color: #fff;
    }

    #emotion, #cocktail-name {
        display: inline-block;
        font-weight: 900;
    }

    #cocktail-name {
        font-size: 1.6em;
    }

    .cocktail-image {
        margin-top: 10px;
        margin-bottom: 10px;
    }
</style>

