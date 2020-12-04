<template>
    <div class="task-management-container">



        <div class="task-create-wrapper">

            <v-card class="task-create-box">

                <v-card class="left">
                    <div style="position: absolute; color: white; opacity: 0.8; font-size: 26px; font-weight: 100; letter-spacing: 2px;">TASK CREATOR</div>
                    <img src="https://images.unsplash.com/photo-1528739431815-0a7344c63409?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80"
                         style="height: 100%;width: 100%; object-fit: cover">
                </v-card>

                <v-card class="center" outlined>
                    <task-creation style="height: 100%;"
                                   @create-task="createTask($event)"
                                   @task-render="test($event)"
                                   :autocomplete-data="users"></task-creation>
                </v-card>

                <v-card class="right" outlined>
                    <div style="display: flex; flex-direction: column">
                        <div style="font-size:28px;font-weight:300;letter-spacing:2px;align-self: center; margin-bottom: 10px; ">
                            Live rendering
                        </div>
                        <TaskComponent :title="task.taskTitle"
                                       :priority="task.taskPriority"
                                       :description="task.taskDescription"></TaskComponent>
                    </div>

                </v-card>

            </v-card>
        </div>





        <side-navigation-bar></side-navigation-bar>
        <Modal :dialog="modalVisible" :dialog-content="'Nowy task zostal pomyslnie stworzony.'"
               :dialog-title="'Task utworzony'"
               :agree-button="'Ok'"
               :one-button="true"
               @modal-agree="modalVisible=false"></Modal>
        <v-overlay v-show="showOverlay">
            <v-progress-circular
                    indeterminate
                    size="74"
            >Loading
            </v-progress-circular>
        </v-overlay>
    </div>
</template>

<script>
    import axios from 'axios';
    import SideNavigationBar from "@/components/SideNavigationBar";
    import TaskService from '../services/task.service'
    import Modal from "@/components/Modal";
    import TaskCreation from "@/components/TaskCreation";
    import TaskComponent from "@/components/Task";
    import Task from "@/model/task";


    export default {
        name: "TaskManagement",
        components: {TaskCreation, Modal, SideNavigationBar, TaskComponent},
        data() {
            return {

                user: null,


                modalVisible: false,


                showOverlay: false,
                createTaskVisible: true,
                tasks: [],
                users: [],
                task: new Task(null, null, null, "Title", "Description", "TO_DO", null, null),
                taskCopy: new Task(null, null, null, "Title", "Description", "TO_DO", null, null),

            }
        },
        methods: {
            getAllUsers() {
                axios.get('http://localhost:8080/users/get-names').then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        this.users.push({
                            id: response.data[i].id,
                            name: response.data[i].firstName + ' ' + response.data[i].lastName
                        })
                    }
                })
            },

            displayModal() {
                this.modalVisible = true;
                this.showOverlay = false;
            },

            createTask(value) {
                this.showOverlay = true;
                this.task = value.task;
                this.task.taskManagerID = this.user.id;
                TaskService.createNewTaks(this.task, value.users).then(this.displayModal());
            },
            test(value) {
                var change = value.changeValue;
                if(value.task[change] === "") this.task[change] = this.taskCopy[change];
                else this.task[change] = value.task[change];
            }
        },
        created() {
            this.user = JSON.parse(localStorage.getItem('user'));

        },
        mounted() {
            this.getAllUsers();
        }

    }
</script>

<style scoped>
    * {
        box-sizing: border-box;
    }

    .task-management-container {
        margin-left: 10%;
        height: 100%;
    }

    .task-create-wrapper {
        height: 100%;
        display: flex;
        justify-content: center;
        flex-direction: column;

    }

    .task-create-box {
        align-self: center;
        width: 1400px;
        height: 850px;
        display: flex;

    }

    .left {
        background: black;
        opacity: 0.9;
        width: 25%;
        height: 100%;
    }

    .center {
        width: 40%;
    }

    .right {
        width: 35%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;

    }


    .page-title {
        position: absolute;
        left: 10%;
        top: 2%;
        font-size: 30px;
        letter-spacing: 2px;

    }

    .list-nav {
        list-style: none;
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        top: 10%;
    }

    .list-nav li {
        float: left;
        margin-right: 50px;
        font-size: 30px;
    }

    .list-nav li:hover {
        color: lightpink;
        cursor: pointer;
    }


    .input-wrapper {
        position: relative;
        margin-bottom: 30px;
    }


    .task-details-window {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate3d(-50%, -50%, 0);
        width: 500px;
        height: 500px;
        background: crimson;
    }

    .button {
        height: 40px;
        padding: 0 80px 0 80px;
        border-radius: 25px;
        letter-spacing: 5px;
        font-size: 20px;
        overflow: hidden;
        outline: none;
    }


</style>
