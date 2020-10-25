<template>
    <div id="container">
        <div>WELCOME HOME</div>
        <button @click="newTask()">NEW TASK</button>
        <button @click="getAllTasks()">GETTASKS</button>


       <router-link to="/test">TEST</router-link>

    <side-navigation-bar />
        <div class="tasks-container">

            <div class="row">
                <div class="column" style="background-color: #aaa">
                    <div class="column-title">TO DO</div>
                    <Task v-bind:title="'Example'" class="gap" v-bind:description="'XDDDDDDDDDD DDDDDDDDDDDDD asdasdsadsadsadadasdsadas daDDDdddDDsadasdsad sadsadsad '"
                    v-bind:project-manager="'Matuesz Pietrzykowski'"/>
                    <Task v-bind:title="'Example'" class="gap" />
                    <Task v-bind:title="'Example'" class="gap" />
                </div>

                <div class="column" style="background-color: #bbb">
                    <div class="column-title">IN PROGRESS</div>
                    <Task v-bind:title="'Example'" class="gap" />
                    <Task v-bind:title="'Example'" class="gap" />
                    <Task v-bind:title="'Example'" class="gap" />
            </div>

                <div class="column" style="background-color: #ccc">
                    <div class="column-title">CODE REVIEW</div>
                    <Task v-bind:title="'Example'" class="gap" />
                    <Task v-bind:title="'Example'" class="gap" />
                    <Task v-bind:title="'Example'" class="gap" />
                </div>

                <div class="column" style="background-color: #ddd">
                    <div class="column-title">DONE</div>
                    <Task v-bind:title="'Example'" class="gap" />
                    <Task v-bind:title="'Example'" class="gap" />
                    <Task v-bind:title="'Example'" class="gap" />

                </div>
            </div>
        </div>


        <div v-if="newTaskContainerVisible" id="newTaskContainer">
            <button @click="close">CLOSE</button>
            <Task v-bind:title="'Example'" />
            <div>
                <label style="color:white;">Nazwa</label>
                <input type="text" v-model="taskName">
            </div>
            <div>
                <label style="color:white;">Description</label>
                <input type="text" v-model="taskDescription">
            </div>

            <button @click="createTask()">CREATE TASK</button>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import Task from '@/components/Task'
    import SideNavigationBar from '@/components/SideNavigationBar'


    export default {
        name: "Home",
        components: { Task, SideNavigationBar},
        data() {
            return {
                taskName: "",
                taskDescription: "",
                newTaskContainerVisible: false,
                userID: null,
                appUsers: [],
                cellTable: ['TO DO', 'IN PROGRESS', 'CODE REVIEW', 'DONE']
            }
        },
        methods: {
            newTask() {
                var user = JSON.parse(localStorage.getItem('user'));
                console.log(user);
                this.userID = user.id;
                this.newTaskContainerVisible = true;
            },
            createTask() {
                console.log('wywolanie funkcji');
                axios.post('http://localhost:8080/tasks/create-new', {
                        taskManagerID: this.userID,
                        name: this.taskName,
                        description: this.taskDescription
                    }
                ).then(response => {
                    console.log(response.status);
                });
                this.close();
            },
            getAllTasks() {
                axios.get('http://localhost:8080/tasks/list-all').then(response => {
                    console.log(response.data);
                })
            },
            close() {
                this.newTaskContainerVisible = false;
                this.taskName = "";
                this.taskDescription = "";
            }
        },
    }
</script>

<style scoped>
    * {
        box-sizing: border-box;


    }

    .tasks-container{
        position: relative;
        left: 10%;
        top: 50px;

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



    #newTaskContainer {
        position: absolute;
        width: 500px;
        height: 500px;
        background: black;
        top: 30%;
        left: 50%;
        transform: translateX(-50%);
    }




    .column-title{
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

    .gap{
        position: relative;
        left: 60%;
        transform: translateX(-50%);
        margin-bottom: 20px;
    }

</style>
