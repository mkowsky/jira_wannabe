import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import routes from './router'
import store from './store'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUser } from '@fortawesome/free-solid-svg-icons'
import { faLock } from '@fortawesome/free-solid-svg-icons'
import { faWindowClose } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import VueMoment from 'vue-moment';


import Vuetify from 'vuetify';
Vue.use(Vuetify);
import vuetify from './plugins/vuetify';
import VueHighlightJS from 'vue-highlightjs'
Vue.use(VueHighlightJS);




// import { BootstrapVue} from 'bootstrap-vue'
// import { BCollapse } from 'bootstrap-vue'
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'
// Vue.component('b-collapse', BCollapse)
// Vue.use(BootstrapVue);




library.add(faUser, faLock, faWindowClose)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false


Vue.use(VueMoment);

var router = new VueRouter({
  routes: routes,
})

new Vue({
  router: router,
  store: store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
