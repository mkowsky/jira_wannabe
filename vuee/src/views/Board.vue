<template>
    <div id="container" class="board-container">


        <side-navigation-bar @logout="modalVisible=true"/>


        <div class="tasks-container">
            <TaskBoard :tasks="tasks" style="width: 100%"></TaskBoard>

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
                isPM: false,
                user: Object,
                tasks: [],
            }
        },
        methods: {

        },
        created() {
            //TODO: Moze jakies lepsze rozwiazanie?
            this.user = JSON.parse(localStorage.getItem('user'));
            console.log(this.user);
            for (let i = 0; i < this.user.roles.length; i++) {
                if ((this.user.roles[i] === "ROLE_PROJECT_MANAGER") || (this.user.roles[i] === "ROLE_ADMIN")) {
                    this.isPM = true;
                    break;
                } else this.isPM = false;
            }
            if (this.isPM) {
                console.log('get-all');
                axios.get('http://localhost:8080/tasks/get-basic-tasks-info').then(response => {
                    this.tasks = response.data;
                    console.log(response.data)
                })
            } else {
                console.log('get-user-tasks')
                axios.get('http://localhost:8080/tasks/get-user-tasks/' + this.user.id).then(response => {
                    this.tasks = response.data;
                    console.log(response.data);
                })

            }
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
