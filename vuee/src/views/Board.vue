<template>
    <body>

    <side-navigation-bar @logout="modalVisible=true"/>

    <div class="tasks-container">
        <TaskBoard :tasks="tasks"
                   :projects="projects"
                   :pages="pages"
                   style="width: 100%"></TaskBoard>

    </div>


    </body>
</template>

<script>
    import axios from 'axios';
    import SideNavigationBar from '@/components/SideNavigationBar'
    import TaskBoard from "@/components/TaskBoard";



    export default {
        name: "Home",
        components: {TaskBoard, SideNavigationBar},
        data() {
            return {
                tasks: [],
                projects: [],
                pages: 0,
            }
        },
        created() {
            let user = JSON.parse(localStorage.getItem('user'));
            axios.get('http://localhost:8080/projects/get-project-name-and-id', {params: {userID: user.id}}).then(response => {
                this.projects = response.data;
                console.log(this.projects);
            });
            axios.get('http://localhost:8080/tasks/get-number-of-tasks-by-their-status', {params: {userID: user.id}}).then(response => {
                this.pages = Math.ceil(response.data / 3)
            });
            axios.get('http://localhost:8080/tasks/get-basic-tasks-info/' + user.id).then(response => {
                this.tasks = response.data
            });
        }

    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";



    * {
        box-sizing: border-box;
    }

    body {
        height: 100%;
        font-family: 'Montserrat', sans-serif;
        margin-left: 12%;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }





</style>
