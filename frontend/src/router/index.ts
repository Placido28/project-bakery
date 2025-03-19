import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue';
import AboutView from '@/views/AboutView.vue';
import LoginView  from '@/views/LoginView.vue';
import SignupView from '@/views/SignupView.vue';

const routes = [
  { path: '/', name: 'Home', component: HomeView },
  { path: '/about', name: 'About', component: AboutView },
  { path: '/signin', name: 'SignIn', component: LoginView },
  { path: '/signup', name: 'SignUp', component: SignupView },
];

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
