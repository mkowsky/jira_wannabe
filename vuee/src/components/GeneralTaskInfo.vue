<template>
    <div class="general-task-info">
        <div class="pm-display">
            <div class="pm-name-lighter">TASK USER</div>
            <v-avatar style="height: 80%; width: 80%;">
                <img :src="currentTask.user.pictureURL" style="object-fit: cover">
            </v-avatar>
            <div class="pm-name" @click="navigateToTaskUser">{{currentTask.user.firstName + ' ' + currentTask.user.lastName}}</div>
        </div>
        <div class="task-content">
            <div class="task-title">{{currentTask.name}}</div>
            <div style="display: flex; flex-direction: row; justify-content: space-evenly">
                <div class="task-rest"><span style="font-size: 14px; color:white;">Deadline</span>{{currentTask.expireDate |  moment("DD-MM-YYYY")}}</div>
                <div class="task-rest"><span style="font-size: 14px; color:white;">Status</span> {{currentTask.state}}</div>
                <div class="task-rest"><span style="font-size: 14px; color:white;">Priority</span> {{currentTask.taskPriority}}</div>
                <div class="task-rest"><span style="font-size: 14px; color:white;">Department</span> {{currentTask.department}}</div>
            </div>
            <div style="margin-top: 20px;color:white; width: 90%; align-self: center">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</div>
            <div>

            </div>

        </div>
    </div>
</template>

<script>


    export default {
        name: "GeneralTaskInfo",
        components:{},
        props:{
            currentTask:{
            }
        },
        methods:{
            navigateToTaskUser(){
                this.$emit('pm-name-clicked', {id: this.currentTask.user.id})
            }
        },
        created(){

        }
    }
</script>

<style scoped lang="scss">
    .general-task-info {
        background: #424242;
        width: 70%;
        height: 400px;
        display: flex;
        align-self: center;
    }

    .pm-display {
        background: rgba(225, 182, 193, 1);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-evenly;
        width: 35%;
    }

    .circle-avatar {
        border-radius: 50%;
        height: 60%;
        width: 60%;
        background: white;
        box-shadow: 0 0 10px 10px black;
    }

    .pm-name {
        cursor: pointer;
        font-weight: 300;
        font-size: 24px;
        letter-spacing: 2px;
        color: white;

    &:hover {
         color: black;
     }
    }

    .pm-name-lighter {
        font-weight: 300;
        font-size: 20px;
        letter-spacing: 2px;

    }

    .task-content {

        width: 70%;
        display: flex;
        flex-direction: column;
        padding: 20px;

    .task-title {
        align-self: center;
        color: white;
        font-size: 48px;
        font-weight: 200;
        letter-spacing: 3px;
    }

    .task-rest {
        color: white;
        font-size: 24px;
        font-weight: 200;
        letter-spacing: 3px;
        display: flex;
        flex-direction: column;
        align-items: center;
        color:rgba(235, 182, 193, 1);

    }

    }

</style>
