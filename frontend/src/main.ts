import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia';
import socketClient from './service/websocket';

import './assets/styles.scss';
import './assets/components.css';
import './assets/login.css';
import './assets/compiled.css';

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import "bootstrap-icons/font/bootstrap-icons.css";


const app = createApp(App);
app.use(createPinia());
app.use(router);
app.mount('#app');

socketClient.activate();