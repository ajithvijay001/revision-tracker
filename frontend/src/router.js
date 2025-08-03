import {createRouter, createWebHistory} from 'vue-router';
const Login = () => import('./views/Login.vue');
const Register = () => import('./views/Register.vue');
const Dashboard = () => import('./components/Test.vue');
const routes =[
    {
        path: '/',
        redirect:'/login'    
    },
    {
        path:'/login',
        name: 'Login',
        component: Login
    },
    {
        path:'/register',
        name: 'Register',
        component: Register
    },
    {
        path:'/dashboard',
        name: 'Dashboard',
        component: Dashboard
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router;