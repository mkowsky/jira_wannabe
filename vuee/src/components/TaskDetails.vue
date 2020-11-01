<template>
    <div id="wrap">
        <div style="font-size: 30px;">HELLO FROM TASK DETAILS</div>
        <div class="template">title
            <div class="bold">{{currentTask.name}}</div>
        </div>
        <div class="template">description
            <div class="bold">{{currentTask.description}}</div>
        </div>
        <div class="template">department
            <div class="bold">{{currentTask.department}}</div>
        </div>
        <div class="template">status
            <div class="bold">{{currentTask.state}}</div>
        </div>
        <div class="template">deadline
            <div class="bold">{{currentTask.expireDate}}</div>
        </div>
        <div class="template">manager
            <div class="bold">{{currentTask.taskManager.username}}</div>
        </div>
        <div class="template">users
            <div class="bold" v-for="user in currentTask.users" :key="user.id">{{user.firstName}} {{user.lastName}}
            </div>
        </div>


    </div>
</template>

<script>
    import Task from "@/model/task";
    import axios from "axios";


    export default {
        name: "TaskDetails",
        props: {
            taskID: {
                required: true,
            }
        },
        data() {
            return {
                currentTask: Task,
            }
        },
        methods: {},
        created() {
            axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                this.currentTask = response.data;
                console.log(this.currentTask);
            })
        }
    }
</script>

<style scoped>

    .template {
        font-size: 22px;

    }

    .bold {
        font-weight: bold;
        margin-bottom: 20px;
    }
</style>
