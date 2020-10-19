import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import routes from './router'

Vue.config.productionTip = false


var router = new VueRouter({
  routes: routes,
})

new Vue({
  router: router,
  render: h => h(App),
}).$mount('#app')
