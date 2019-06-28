<template>
        <div class="klapper" v-if="data.name"> 
            <b-button  v-b-toggle.collapse-1  variant="secondary" > Das haben wir über dich rausgefunden </b-button > 
            <b-collapse  id = "collapse-1"  class = "mt-2">
              <table>
                {{aufbereiteteDaten}}
              </table> 
              <div class="border">
                <b-button  v-b-toggle.collapse-2  variant="secondary" >Technische Ansicht</b-button > 
                    <b-collapse  id = "collapse-2"  class = "mt-2"> 
                        {{data}}
                    </b-collapse > 
               </div>     
            </b-collapse > 
        </div >    
</template>

<script>
export default {
  name: 'Klapper',
  props: {
    data: {},
    table: {},
    aufbereiteteDaten: {}
  }
}
</script>
<style>
    #collapse-1{
        background-color: #fff;
    }
    .border{
        border: 1px solid black;
    }
</style>
