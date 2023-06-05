import Vue from 'vue'
import VueRouter from 'vue-router'
import Register from '@/views/Register.vue'
import Login from '@/views/Login.vue'
import Home from '@/views/Home.vue'

Vue.use(VueRouter)

const routes = [{ //登录
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: Login,
    name: 'login'
  },
  { //登录
    path: '/register',
    component: Register,
    name: 'register'
  },
  //主页
  {
    path: '/home',
    component: Home,
    name: 'home'
  },
 
]

const router = new VueRouter({

  routes
})

export default router