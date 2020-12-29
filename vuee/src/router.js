import Vue from 'vue';
import Router from 'vue-router';

import Login from "@/views/Login";
import Board from "@/views/Board";
import TaskCreation from "@/views/TaskCreation";
import Profile from "@/views/Profile";
import Projects from "@/views/Projects";
import ProjectDetails from "@/views/ProjectDetails";
import TaskDetails from "@/views/TaskDetails";
import ProjectCreation from "@/views/ProjectCreation";
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
        path: '',
        component: Home
    },
    {
        path: '/login',
        component: Login,
        meta: {
            guest: true
        }
    },
    {
        name: 'board',
        path: '/board',
        beforeEnter: guardMyroute,
        component: Board,
        meta: {
            guest: false
        }
    },

    {
        path: '/task-creation',
        component: TaskCreation,
        beforeEnter: checkIfModerator
    },
    {
        path: '/project-creation',
        component: ProjectCreation,
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

    },
    {
        name: 'projects',
        path: '/projects',
        component: Projects
    },
    {
        name: 'projectDetails',
        path: '/projects/project-:projectID',
        component: ProjectDetails,
        props: true,
    },
    {
        name: 'taskDetails',
        path: '/task/task-:taskID',
        component: TaskDetails,
        props: true,
    }


]

export default routes
