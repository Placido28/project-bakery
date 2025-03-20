import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue';
import AboutView from '@/views/AboutView.vue';
import LoginView  from '@/views/LoginView.vue';
import SignupView from '@/views/SignupView.vue';
import { getToken } from '@/service/authService';

const routes = [
  { path: '/', name: 'Home', component: HomeView },
  { path: '/about', name: 'About', component: AboutView, meta: { requiresAuth: true } },
  { path: '/signin', name: 'SignIn', component: LoginView },
  { path: '/signup', name: 'SignUp', component: SignupView },
];

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = getToken();

  if ((to.path === '/signin' || to.path === '/signup') && token) {
    next('/dashboard'); // Redirige al dashboard si ya está autenticado
  } else if (to.meta.requiresAuth && !token) {
    next('/signin'); // Redirige al login si no está autenticado
  } else {
    next();
  }
});

export default router
