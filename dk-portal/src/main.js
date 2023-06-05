import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import VueBus from 'vue-bus'
import ElementUI from 'element-ui'


Vue.use(ElementUI)
Vue.use(VueBus)


Vue.config.productionTip = false

// Request interceptors for API calls
axios.interceptors.request.use(
  config => {
    let token = localStorage.getItem('x-token');
    if (token) {
      config.headers.Authentication = token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);


//axios请求基本url
axios.defaults.baseURL = "/api"



new Vue({
  router,
  render: h => h(App)
}).$mount('#app')