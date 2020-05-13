import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css'


import {postKeyValueRequest,postRequest,getRequest,deleteRequest,putRequest} from "./utils/api";
import {initMenu} from "./utils/menus";
// 做成方法插件，方便复用，this.xx即可调用
Vue.prototype.postKeyValueRequest=postKeyValueRequest
Vue.prototype.postRequest=postRequest
Vue.prototype.getRequest=getRequest
Vue.prototype.deleteRequest=deleteRequest
Vue.prototype.putRequest=putRequest



router.beforeEach((to, from, next) => {
  if (to.path==='/'){
      next()
  } else{
    if (window.sessionStorage.getItem("user")) {
      initMenu(router, store);
      next();
    } else {
      next('/?redirect=' + to.path);
    }
  }
})

Vue.config.productionTip = false
Vue.use(ElementUI, { size: 'small', zIndex: 3000 });

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
