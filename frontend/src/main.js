import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router';



// Vue.config.productionTip = false;

// new Vue({
//     router,
//     store,
//     render: (h) => h(App)
// }).$mount('#app');

createApp(App).use(router).mount('#app')