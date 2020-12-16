<template>
    <div id="container" class="board-container">


        <side-navigation-bar @logout="modalVisible=true"/>


        <div class="tasks-container">
            <TaskBoard :tasks="tasks"
                       :projects="projects"
                       :pages="pages"
                       style="width: 100%"></TaskBoard>

        </div>


    </div>
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
            // //TODO: Moze jakies lepsze rozwiazanie?
             let user = JSON.parse(localStorage.getItem('user'));
            axios.get('http://localhost:8080/projects/get-project-name-and-id', {params:{userID:user.id}}).then(response =>{this.projects = response.data;});
            axios.get('http://localhost:8080/tasks/get-number-of-tasks-by-their-status', {params:{userID:user.id}}).then(response=>{this.pages=Math.ceil(response.data/3)});
            axios.get('http://localhost:8080/tasks/get-basic-tasks-info/' + user.id).then(response => {this.tasks = response.data});
        }

    }
</script>

<style scoped>


    * {
        box-sizing: border-box;
    }

    .board-container {
        margin-left: 10%;
        height: 100%;

    }


    .tasks-container {
        height: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;

    }


</style>
