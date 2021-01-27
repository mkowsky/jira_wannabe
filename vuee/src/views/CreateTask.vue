<template>
    <body>

    <side-navigation-bar></side-navigation-bar>

    <div class="container">


        <div class="task-create-box">

            <!--<div class="left">-->
                <!--<div class="left-claim">Task creator</div>-->

                <!--<div class="left-subclaim">SUBCLAIM</div>-->
                <!--<img src="../assets/images/undraw_Taking_notes_re_bnaf.svg" style="width: 100%; ">-->
                <!--<div class="left-dsc">DESCRIPTION</div>-->

            <!--</div>-->

            <v-card class="center" outlined>
                <task-creation style="height: 100%;"
                               @create-task="createTask($event)"
                               @task-render="test($event)"
                               @get-team-members="getProjectTeamMembers($event)"
                               :users="users"
                               :projects="projects"></task-creation>
            </v-card>

            <v-card class="right" outlined>

                <div class="right-claim">
                    Live rendering
                </div>
                <TaskComponent :task-name="task.taskTitle"
                               :priority="task.taskPriority"
                               :project-name="task.taskProject"
                ></TaskComponent>


            </v-card>

        </div>


    </div>


    <Dialog :dialog="modalVisible" :dialog-content="'New task has been created succesfully.'"
           :dialog-title="'Task has been created.'"
           :agree-button="'Ok'"
           :one-button="true"
           @modal-agree="modalVisible=false"></Dialog>
    <v-overlay v-show="showOverlay">
        <v-progress-circular
                indeterminate
                size="74"
        >Loading
        </v-progress-circular>
    </v-overlay>

    </body>
</template>

<script>
    import axios from 'axios';
    import SideNavigationBar from "@/components/SideNavigationBar";
    import TaskService from '../services/task.service'

    import TaskCreation from "@/components/TaskCreation";
    import TaskComponent from "@/components/Task";
    import Task from "@/model/task";
    import Dialog from "@/components/Dialog";


    export default {
        name: "TaskManagement",
        components: {Dialog, TaskCreation,  SideNavigationBar, TaskComponent},
        data() {
            return {

                user: null,


                modalVisible: false,


                showOverlay: false,
                createTaskVisible: true,
                tasks: [],
                users: [],
                projects: [],
                task: new Task(null, null, null, "Title", "Description", "TO_DO", null, null),
                taskCopy: new Task(null, null, null, "Title", "Description", "TO_DO", null, null),

            }
        },
        methods: {
            getAllProjects() {
                axios.get('http://localhost:8080/projects/list-all').then(response => {
                    console.log(response.data);
                    this.projects = response.data;
                })
            },
            getProjectTeamMembers(value) {
                axios.get('http://localhost:8080/projects/get-all-team-members-personal-data/' + value.taskID).then(response => {
                    this.users = response.data;
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
                TaskService.createNewTaks(this.task, value.user).then(this.displayModal());
            },
            test(value) {
                console.log(value);
                var change = value.changeValue;
                console.log(change);
                if (value.task[change] === "") this.task[change] = this.taskCopy[change];
                else this.task[change] = value.task[change];
            },

        },
        created() {
            this.user = JSON.parse(localStorage.getItem('user'));
            this.getAllProjects();

        },
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";



    body {
        font-family: 'Montserrat', sans-serif;
        height: 100vh;

    }

    .container {
        margin-left: 12%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .task-create-box {
        align-self: center;
        width: 1400px;
        height: 800px;
        display: flex;

    }

    .left {
        display: flex;
        flex-direction: column;
        width: 30%;
        height: 100%;
    }

    .left-claim {
        font-size: 50px;
        letter-spacing: 2px;
        font-weight: 300;
    }

    .left-subclaim {
        font-size: 36px;
        font-weight: 200;
    }

    .left-dsc {
        font-size: 22px;

    }

    .center {
        width: 45%;

    }

    .right {
        width: 25%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

    }

    .right-claim {
        font-size: 28px;
        font-weight: 300;
        letter-spacing: 2px;
        align-self: center;
        margin-bottom: 10px;
    }


</style>
