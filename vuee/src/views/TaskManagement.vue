<template>
    <div id="container">
        <div class="page-title">TASK MANAGEMENT</div>

        <ul class="list-nav">
            <li @click="createTaskVisible=true">CREATE TASK</li>
            <li @click="createTaskVisible=false">EDIT TASK</li>
        </ul>

        <div id="taskCreate" v-show="createTaskVisible">

            <v-select solo dense label="Chose Department" :items="departments" item-text="name" item-value="name"
                      v-model="task.department"></v-select>
            <v-select solo dense label="Chose Priority" :items="priorities" item-text="value" item-value="value"
                      v-model="task.taskPriority"></v-select>
            <v-text-field solo label="Title" v-model="task.taskTitle" ref="taskTitle"></v-text-field>
            <v-textarea solo label="Task description" v-model="task.taskDescription"></v-textarea>
            <div>
                <label>TASK STATE</label>
                <v-select solo dense label="TO_DO" disabled></v-select>
            </div>


            <div class="input-wrapper">
                <datepicker placeholder="Deadline"
                            value="Date"
                            :disabledDates="disabledDates"
                            input="null"
                            id="kalendarz"
                            v-model="task.taskDeadline"/>

            </div>

            <div class="input-wrapper">
                <Autocomplete v-bind:items="users"
                              :filterby="'firstName'"
                              :grid-display="false"
                              :placehold="'Search fot user with name...'"
                              style="width: 300px;"
                              @list-item-clicked="listItemKlik"></Autocomplete>
                <div style="position: absolute; left: 400px;">CHOSEN PEOPLE
                    <ul>
                        <li v-for="people in chosenPeople" :key="people.name">
                            <font-awesome-icon icon="user" @click="iconClicked(people.id)"></font-awesome-icon>
                            {{people.name}}
                        </li>
                    </ul>
                </div>
            </div>

            <v-btn @click="createTask" :disabled="isConfirmButtonDisabled">CONFIRM</v-btn>
            <button @click="clear">CLEAR</button>
        </div>


        <div id="editTask" v-show="!createTaskVisible">
            <div style=" position: absolute; left: 20%; top: 30%; width: 400px">
                <Autocomplete :items="tasks" :filterby="'name'" :grid-display="true"
                              :placehold="'Search fot task with name...'" @grid-item-clicked="openTaskDetails"
                              :key="render"
                              style="position: absolute; left: 50%; transform: translateX(-50%); top: 20%"></Autocomplete>
            </div>
        </div>

        <div v-if="gridItemClicked" class="task-details-window">
            <button @click="deleteTask">DELETE TASK</button>
            <button @click="gridItemClicked=false">CLOSE</button>
        </div>


        <side-navigation-bar></side-navigation-bar>

    </div>
</template>

<script>
    import axios from 'axios';
    import Autocomplete from "@/components/Autocomplete";
    import SideNavigationBar from "@/components/SideNavigationBar";
    import Datepicker from 'vuejs-datepicker';
    import TaskService from '../services/task.service'
    import Task from "@/model/task";


    export default {
        name: "TaskManagement",
        components: {SideNavigationBar, Autocomplete, Datepicker},
        data() {
            return {
                user: null,
                task: new Task(null, null, null, null, null, "TO_DO", null, null),
                disabledDates: {
                    to: new Date(Date.now() - 8640000)
                },
                render: 0,
                deletedUsers: [],
                taskPriority: "",
                taskDeparment: "",
                taskTitle: "",
                taskDescription: "",
                taskState: "TO_DO",
                taskDeadline: "",
                chosenPeopleIDS: [],


                currentTaskID: null,

                users: [],
                gridItemClicked: false,
                chosenPeople: [],
                departments: [
                    {name: 'BACKEND'},
                    {name: 'FRONTEND'},
                    {name: 'MARKETING'},
                    {name: 'DATABASE'},
                    {name: 'ECOMMERCE'},
                ],
                priorities: [
                    {value: 1},
                    {value: 2},
                    {value: 3},
                    {value: 4},
                ],
                times: [
                    {name: '1 DAY'},
                    {name: '2 DAYS'},
                    {name: '3 DAYS'},
                    {name: '4 DAYS'},
                    {name: '5 DAYS'},
                    {name: '6 DAYS'},
                    {name: '7 DAYS'},
                ],
                createTaskVisible: true,
                tasks: [],


            }
        },
        methods: {
            //TODO: dodać po0le priorytet do TASK i na jego podstawie kolor tasku
            iconClicked(value) {
                console.log(value);
                for (let i = 0; i < this.chosenPeople.length; i++) {
                    if (this.chosenPeople[i].id === value) {
                        for (let j = 0; j < this.deletedUsers.length; j++) {
                            if (this.chosenPeople[i].id === this.deletedUsers[j].id) {
                                this.users.push({
                                    id: this.deletedUsers[j].id,
                                    firstName: this.deletedUsers[j].firstName,
                                    lastName: this.deletedUsers[j].lastName
                                })
                                this.deletedUsers.splice(j, 1);
                            }
                        }
                        this.chosenPeople.splice(i, 1);
                    }
                }
            },
            listItemKlik(value, identity) {
                console.log('user name: ' + value + 'id: ' + identity);
                this.chosenPeople.push({name: value, id: identity});
                for (let i = 0; i < this.users.length; i++) {
                    if (this.users[i].id === identity) {
                        this.deletedUsers.push({
                            id: this.users[i].id,
                            firstName: this.users[i].firstName,
                            lastName: this.users[i].lastName
                        });
                        this.users.splice(i, 1);
                    }
                }
                console.log(this.deletedUsers);
            },
            openTaskDetails(value) {
                console.log('task id: ' + value);
                this.gridItemClicked = true;
                for (let i = 0; i < this.tasks.length; i++) {
                    if (this.tasks[i].id === value) this.currentTaskID = this.tasks[i].id;
                }


            },
            clear() {
                this.task.taskPriority = "",
                    this.task.department = "",
                    this.task.taskTitle = "";
                this.task.taskDescription = "";
                this.task.taskDeadline = "";
                this.chosenPeopleIDS = [];
                this.chosenPeople = [];
                this.deletedUsers = [];
            },

            getAllUsers() {
                this.users = null;
                axios.get('http://localhost:8080/users/get-names').then(response => {
                    for (let i = 1; i < response.data.length; i++) {
                        console.log(i + 'push');
                        this.users.push({
                            id: response.data[i].id,
                            firstName: response.data[i].firstName,
                            lastName: response.data[i].lastName
                        })
                    }

                })
            },
            createTask() {
                this.task.taskManagerID = this.user.id;

                // //to gdzies przeniesc bo tutaj nie bedzie pasowac
                for (let i = 0; i < this.chosenPeople.length; i++) {
                    this.chosenPeopleIDS[i] = this.chosenPeople[i].id
                }

                TaskService.createNewTaks(this.task, this.chosenPeopleIDS).then(this.clear);
            },
            deleteTask() {
                console.log(this.currentTaskID);
                TaskService.delteTask(this.currentTaskID).then(this.render++);

            },
        },
        created() {
            this.user = JSON.parse(localStorage.getItem('user'));
            axios.get('http://localhost:8080/tasks/list-all').then(response => {
                this.tasks = response.data;
            });
            axios.get('http://localhost:8080/users/get-names').then(response => {
                for (let i = 1; i < response.data.length; i++) {
                    this.users.push({
                        id: response.data[i].id,
                        firstName: response.data[i].firstName,
                        lastName: response.data[i].lastName
                    })
                }

            })
        },
        computed: {
            isConfirmButtonDisabled() {
                if ((this.task.taskPriority) && (this.task.department) && (this.task.taskTitle) && (this.task.taskDescription) && (this.task.taskDeadline)) return false;// && (this.chosenPeople.length > 0)) return false
                else return true
            },

        }
    }
</script>

<style scoped>

    #container {
        top: 0;
        left: 0;
        position: absolute;
        width: 100%;
        height: 100%;

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

    #taskCreate {
        position: absolute;
        top: 30%;
        left: 50%;
        transform: translateX(-50%);

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
