import Vue from 'vue';
import Router from 'vue-router';
import TemplateView from "@/views/TemplateView";
import Login from "@/views/Login";
import Home from "@/views/Home";


Vue.use(Router);

function guardMyroute(to, from, next) {
    var isAuthenticated = false;

    if (localStorage.getItem('user'))
        isAuthenticated = true;
    else
        isAuthenticated = false;

    if (isAuthenticated) {
        next();
    } else {
        console.log('nie mozna');
        next('/login');
    }
}

const routes = [

    {
        path: '/login',
        component: Login,
        meta: {
            guest: true
        }
    },
    {
        path: '/home',
        beforeEnter: guardMyroute,
        component: Home,
        meta: {
            guest: false
        }
    },
    {
        path: '/test',
        component: TemplateView,
    },

]

export default routes
