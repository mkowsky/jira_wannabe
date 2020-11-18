<template>
    <div id="container" class="board-container">

        <div class="page-title" v-if="taskContainerVisible">BOARD
            <div>{{user.firstName}} {{user.lastName}}</div>
            <v-btn @click="logout">LOGOUT</v-btn>
        </div>




        <side-navigation-bar/>
        <div v-if="!taskContainerVisible">

            <TaskDetails
                    v-bind:task-i-d="currentTaskID"
                    @close-task-details="taskContainerVisible = true"
                    />
        </div>


        <div class="tasks-container" v-show="taskContainerVisible">

            <div class="row">
                <div class="column" style="background-color: #aaa">
                    <div class="column-title">TO DO</div>
                    <Task v-for="task in toDo"
                          :key="task.id"
                          v-bind:title="task.name"
                          v-bind:description="task.description"
                          v-bind:project-manager="task.taskManager.firstName + ' ' + task.taskManager.lastName"
                          v-bind:priority="task.taskPriority"
                          class="gap"
                          @task-item-clicked="taskItemClicked(task.id)"/>

                </div>

                <div class="column" style="background-color: #bbb">
                    <div class="column-title">IN PROGRESS</div>
                    <Task v-for="task in inProgress"
                          :key="task.id"
                          v-bind:title="task.name"
                          v-bind:description="task.description"
                          v-bind:project-manager="task.taskManager.firstName + ' ' + task.taskManager.lastName"
                          v-bind:priority="task.taskPriority"
                          class="gap"
                          @task-item-clicked="taskItemClicked(task.id)"/>

                </div>

                <div class="column" style="background-color: #ccc">
                    <div class="column-title">CODE REVIEW</div>
                    <Task v-for="task in codeReview"
                          :key="task.id"
                          v-bind:title="task.name"
                          v-bind:description="task.description"
                          v-bind:project-manager="task.taskManager.firstName + ' ' + task.taskManager.lastName"
                          v-bind:priority="task.taskPriority"
                          class="gap"
                          @task-item-clicked="taskItemClicked(task.id)"/>

                </div>

                <div class="column" style="background-color: #ddd">
                    <div class="column-title">DONE</div>
                    <Task v-for="task in done"
                          :key="task.id"
                          v-bind:title="task.name"
                          v-bind:description="task.description"
                          v-bind:project-manager="task.taskManager.firstName + ' ' + task.taskManager.lastName"
                          v-bind:priority="task.taskPriority"
                          class="gap"
                          @task-item-clicked="taskItemClicked(task.id)"/>


                </div>
            </div>
        </div>


    </div>
</template>

<script>
    import axios from 'axios';
    import Task from '@/components/Task'
    import SideNavigationBar from '@/components/SideNavigationBar'
    import TaskDetails from "@/components/TaskDetails";


    export default {
        name: "Home",
        components: {TaskDetails, Task, SideNavigationBar},
        data() {
            return {
                isPM: false,

                user: Object,
                taskContainerVisible: true,
                toDoTasks: [],
                inProgressTasks: [],
                codeReviewTasks: [],
                doneTasks: [],
                tasks: [],
                taskName: "",
                taskDescription: "",
                taskState: "",
                currentTaskID: "",
                userID: null,
                appUsers: [],
                cellTable: ['TO DO', 'IN PROGRESS', 'CODE REVIEW', 'DONE']
            }
        },
        methods: {
            logout() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            },
            taskItemClicked(taskID) {
                console.log(taskID);
                this.taskContainerVisible = false;
                this.currentTaskID = taskID;

            },
        },
        computed: {
            toDo() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.state.includes("TO_DO");
                })
                return filteredTasks
            },
            codeReview() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.state.includes("CODE_REVIEW");
                })
                return filteredTasks
            },
            inProgress() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.state.includes("IN_PROGRESS");
                })
                return filteredTasks
            },
            done() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.state.includes("DONE");
                })
                return filteredTasks
            }
        },
        created() {
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
                axios.get('http://localhost:8080/tasks/list-all').then(response => {
                    this.tasks = response.data;
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
    .board-container{
        margin-left: 10%;
        height: 100%;

    }

    .page-title {
        position: absolute;
        left: 10%;
        top: 2%;
        letter-spacing: 2px;
        margin-bottom: 40px;

    }

    .tasks-container {
        position: relative;
        left: 10%;
        top: 130px;

    }

    .column {
        margin-right: 20px;
        float: left;
        width: 20%;
        padding: 5px;

    }


    .row::after {
        content: "";
        clear: both;
        display: table;
    }


    .column-title {
        position: relative;
        left: 50%;
        transform: translateX(-50%);
        margin-top: 10px;
        font-size: 30px;
        margin-bottom: 30px;
        font-weight: lighter;
        font-family: 'Courier New', Courier, monospace;
        letter-spacing: 1px;
    }

    .gap {
        position: relative;
        left: 60%;
        transform: translateX(-50%);
        margin-bottom: 20px;
        cursor: pointer;
    }


</style>
