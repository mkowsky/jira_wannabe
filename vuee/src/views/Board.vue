<template>
    <div id="container" class="board-container">
        <div style="position: absolute;top: 5%; " v-show="taskContainerVisible">
            <v-text-field solo label="Search" v-model="query"></v-text-field>
        </div>


        <div class="page-title" v-if="taskContainerVisible">BOARD
        </div>


        <side-navigation-bar/>
        <div v-if="!taskContainerVisible">

            <TaskDetails
                    v-bind:task-i-d="currentTaskID"
                    @close-task-details="taskContainerVisible = true"
            />
        </div>


        <div class="tasks-container" v-show="taskContainerVisible">


            <div class="column" style="background-color: #aaa; display: flex; flex-direction: column; min-height: 750px;">
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

            <div class="column" style="background-color: #bbb; display: flex; flex-direction: column">
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

            <div class="column" style="background-color: #ccc; display: flex; flex-direction: column">
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

            <div class="column" style="background-color: #ddd; display: flex; flex-direction: column">
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
        <v-pagination v-show="taskContainerVisible"
                      v-model="page"
                      :length="6"
                      @next="next"
                      @previous="previous"
                      @input="pageChanged($event)"
        ></v-pagination>

    </div>
</template>

<script>
    import axios from 'axios';
    import Task from '@/components/Task'
    import SideNavigationBar from '@/components/SideNavigationBar'
    import TaskDetails from "@/views/TaskDetails";


    export default {
        name: "Home",
        components: {TaskDetails, Task, SideNavigationBar},
        data() {
            return {
                page: 1,
                isPM: false,
                query: '',
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
            paginate(array, pageSize, pageNumber) {

                return array.slice((pageNumber-1) * pageSize, (pageNumber) * pageSize);

            },

            taskItemClicked(taskID) {
                console.log(taskID);
                this.taskContainerVisible = false;
                this.currentTaskID = taskID;

            },
            next() {
                console.log('nex');
            },
            previous() {
                console.log('prev');
            },
            pageChanged(value) {
                this.page=  value;
            }
        },
        computed: {
            toDo() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("TO_DO");
                    })
                    return this.paginate(filteredTasks, 3, this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("TO_DO");
                    })
                    //return filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase()))
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
            },
            codeReview() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("CODE_REVIEW");
                    })
                    return this.paginate(filteredTasks,3 , this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("CODE_REVIEW");
                    })
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
            },
            inProgress() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("IN_PROGRESS");
                    })
                    return this.paginate(filteredTasks,3 , this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("IN_PROGRESS");
                    })
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
            },
            done() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("DONE");
                    })
                    return this.paginate(filteredTasks,3 , this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("DONE");
                    })
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
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
                    this.prepareData()
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

    .page-title {
        position: absolute;
        left: 10%;
        top: 2%;
        letter-spacing: 2px;
        margin-bottom: 40px;

    }

    .tasks-container {
        margin-top: 12vh;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;

    }

    .column {
        width: 20%;

        padding: 15px;

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


        margin-bottom: 20px;
        cursor: pointer;
    }


</style>
