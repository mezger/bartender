<template>
        <div class="klapper" v-if="data.name"> 
            <b-button  v-b-toggle.collapse-1  variant="secondary" > Das haben wir über dich rausgefunden </b-button > 
            <b-collapse  id = "collapse-1"  class = "mt-2">
              <table id="tabelle">
                <tr v-for="(value, key) in table" :key="key">
                    <td class="border">{{key}}</td><td class="border">{{value}}</td>
                </tr>
              </table> 
              <div class="border">
                <b-button  v-b-toggle.collapse-2  variant="secondary" >Technische Ansicht</b-button > 
                    <b-collapse  id = "collapse-2"  class = "mt-2"> 
                      <div class="json">
                        <pre>{{ data | pretty }}</pre>
                      </div>
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
    table: {}
  },
  filters: {
    pretty: function(value) {
      return JSON.stringify(value, null, 2);
    }
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
    table {
        width: 100%;
        border-collapse: collapse;
        font-size: 20px;
    }
    .json {
      text-align: left;
    }
    .json pre {
      white-space: pre-wrap;
    }
</style>
