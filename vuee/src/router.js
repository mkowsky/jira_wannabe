import Vue from 'vue';
import Router from 'vue-router';
import TemplateView from "@/views/TemplateView";
import Login from "@/views/Login";
import Board from "@/views/Board";
import TaskManagement from "@/views/TaskManagement";
import Profile from "@/views/Profile";


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

function checkIfModerator(to, from, next) {
    var isAuthenticated = false;
    var isModerator = false;

    if (localStorage.getItem('user')) {
        isAuthenticated = true;
        let user = JSON.parse(localStorage.getItem('user'));
        if (user.roles.includes('ROLE_PROJECT_MANAGER')) {
            console.log('admin');
            isModerator = true;
        } else {
            console.log('nie admin');
            isModerator = false;
        }
    } else {
        isAuthenticated = false;
        isModerator = false;
        console.log('do loginu');
        next('/login');
        return;
    }

    if (isAuthenticated && isModerator) {
        next()
    } else {
        next('/accesDenied');
        console.log('blok');
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
        path: '/board',
        beforeEnter: guardMyroute,
        component: Board,
        meta: {
            guest: false
        }
    },
    {
        path: '/test',
        component: TemplateView,
    },
    {
        path: '/task-management',
        component: TaskManagement,
        beforeEnter: checkIfModerator
    },
    {
        path: '/profile',
        component: Profile
    },
    {
        name: 'profileDetails',
        path: '/profile/:userID',
        component: Profile,

    }

]

export default routes
