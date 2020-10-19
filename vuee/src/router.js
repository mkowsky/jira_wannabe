import Vue from 'vue';
import Router from 'vue-router';
import TemplateView from "@/views/TemplateView";


Vue.use(Router);


const routes = [

    {
        path: '/test',
        component: TemplateView,
    },

]

export default routes
