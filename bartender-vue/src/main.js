import Vue from 'vue'
import App from './App.vue'

window.$ = require('jquery')
window.JQuery = require('jquery')

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
