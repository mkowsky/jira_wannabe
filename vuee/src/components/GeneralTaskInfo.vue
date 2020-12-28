<template>
    <div class="general-task-info">

        <div style="height: 100%; width: 80%; display: flex; flex-direction: column; align-items: center; justify-content: space-evenly">

            <div class="flex-box">

            <div class="content-title-background">
                <div class="content-title">BASIC INFO</div>
            </div>

            <div style="display: flex; flex-direction: row; justify-content: space-around; width: 100%; flex-wrap: wrap">
                <div class="task-rest"><span class="span-claim">Deadline</span>
                    {{currentTask.expireDate | moment("DD-MM-YYYY")}}
                </div>
                <div class="task-rest">
                    <span class="span-claim">Status</span>
                    {{currentTask.state}}
                </div>
                <div class="task-rest">
                    <span class="span-claim">Priority</span>
                    {{currentTask.taskPriority}}
                </div>
                <div class="task-rest">
                    <span class="span-claim">Department</span>
                    {{currentTask.department}}
                </div>
            </div>
            </div>
            <div class="flex-box">
                <div class="content-title-background">
                    <div class="content-title">DESCRIPTION</div>
                </div>

                <div style="color:black; align-self: center; font-size: 16px; font-weight: 300; text-align: center">
                    Lorem Ipsum is simply dummy
                    text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy
                    text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a
                    type specimen book. It has survived not only five centuries, but also the leap into electronic
                    typesetting, remaining essentially unchanged.
                </div>
            </div>

            <div class="flex-box">
                <div class="content-title-background">
                    <div class="content-title">ACITONS</div>
                </div>

                <div style="display: flex; flex-direction: row;">
                    <div class="actions-box">
                        <p class="actions-box-title">CHANGE STATE</p>
                        <p class="actions-box-text">Job is already done? Change task state so others can see it!</p>
                        <v-btn class="actions-box-button" color="#EBB6C1" @click="changeState()">CHANGE</v-btn>
                    </div>
                    <div  class="actions-box">
                        <p class="actions-box-title">PASS TASK</p>
                        <p class="actions-box-text">Need help? Pass task to someone else from your team!</p>
                        <v-btn class="actions-box-button" color="#F08080" @click="passTask()">PASS</v-btn>
                    </div>
                    <div  class="actions-box">
                        <p class="actions-box-title">REPORT ISSUE</p>
                        <p class="actions-box-text">You faced any difficulties during work on this task?</p>
                        <v-btn class="actions-box-button" color="#DC143C" @click="reportIssue()">REPORT</v-btn>
                    </div>
                    <div  class="actions-box">
                        <p class="actions-box-title">CHANGE DEADLINE</p>
                        <p class="actions-box-text">Too much work left?</p>
                        <v-btn class="actions-box-button" color="#AC474C" @click="changeDeadline()">CHANGE</v-btn>
                    </div>
                </div>
            </div>
        </div>

        <div style="width: 2%; background: #424242;"></div>

        <div style="width: 18%; display: flex; flex-direction: column; padding: 20px;">

            <!--<div style="color: black">{{currentTask.name}}</div>-->
            <div style="color: black; text-transform: uppercase; font-weight: 800; font-size: 22px;">task</div>
            <div style="color: black; font-size: 50px; font-weight: 800">is</div>
            <div style="color: black; font-size: 24px; margin-bottom: 20px;">assigned to</div>
            <v-avatar style="height: 150px; width: 150px; align-self: center; margin-bottom: 20px;">
                <img :src="currentTask.user.pictureURL" style="object-fit: cover">
            </v-avatar>
            <div @click="navigateToTaskUser" style="cursor: pointer; font-size: 30px; font-weight: 400">
                {{currentTask.user.firstName}}
            </div>
            <div @click="navigateToTaskUser" style="cursor: pointer; font-size: 38px; font-weight: 800;">
                {{currentTask.user.lastName}}
            </div>
        </div>


    </div>
</template>

<script>


    export default {
        name: "GeneralTaskInfo",
        components: {},
        props: {
            currentTask: {}
        },
        methods: {
            navigateToTaskUser() {
                this.$emit('pm-name-clicked', {id: this.currentTask.user.id})
            },
            changeState(){
                this.$emit('change-state')
            },
            passTask(){
                this.$emit('pass-task')
            },
            reportIssue(){
                this.$emit('report-issue')
            },
            changeDeadline(){
                this.$emit('change-deadline')
            },
        },
    }
</script>

<style scoped lang="scss">
    @import url("https://fonts.googleapis.com/css?family=Roboto:400,400i,700");

    .general-task-info {
        position: relative;
        overflow: hidden;
        width: 70%;
        height: 60%;
        align-self: center;
        display: flex;
        box-shadow: 0 0 15px black;


    }

    .span-claim {
        font-weight: 400;
        font-size: 14px;
        color: black;
    }

    .task-rest {
        font-size: 24px;
        font-weight: 400;
        letter-spacing: 3px;
        display: flex;
        flex-direction: column;
        align-items: center;

        color: #DC143C;
        /*color: #F08080;*/

    }

    .content-title {
        font-size: 22px;
        color: white;
        text-transform: uppercase;
        margin-bottom: 10px;
    }
    .content-title-background{
        width: 700px;
        background: rgba(0, 0, 0, 0.7);
        height: 30px;
        display: flex;
        justify-content: center;
        align-self: center;
        margin-bottom: 10px;
    }


    .flex-box {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 80%;
    }

    .actions-box {
        width: 170px;
        height: 170px;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 10px;
        margin-right: 10px;
    }
    .actions-box-text{
        font-size: 13px;
        align-self: center;
        text-align: center;
        font-style: italic;
    }
    .actions-box-title{
        font-size: 16px;
        align-self: center;
        font-weight: 400;
    }
    .actions-box-button{
        width: 80px;
        align-self: center;
        opacity: 0.8;

    }

    /*.general-task-info:before{*/
    /*content: '';*/
    /*position: absolute;*/
    /*background: #bababa;*/
    /*border-radius: 50%;*/
    /*!*width: 1400px;*!*/
    /*!*height: 1400px;*!*/
    /*width: 100%;*/
    /*height: 180%;*/
    /*transform: translate3d(40%, 22%, 0)*/
    /*}*/

    /*.pm-display {*/
    /*background: rgba(225, 182, 193, 1);*/
    /*display: flex;*/
    /*flex-direction: column;*/
    /*align-items: center;*/
    /*justify-content: space-evenly;*/
    /*width: 35%;*/
    /*}*/

    /*.circle-avatar {*/
    /*border-radius: 50%;*/
    /*height: 60%;*/
    /*width: 60%;*/
    /*background: white;*/
    /*box-shadow: 0 0 10px 10px black;*/
    /*}*/

    /*.pm-name {*/
    /*cursor: pointer;*/
    /*font-weight: 300;*/
    /*font-size: 24px;*/
    /*letter-spacing: 2px;*/
    /*color: white;*/

    /*&:hover {*/
    /*color: black;*/
    /*}*/
    /*}*/

    /*.pm-name-lighter {*/
    /*font-weight: 300;*/
    /*font-size: 20px;*/
    /*letter-spacing: 2px;*/

    /*}*/

    /*.task-content {*/

    /*width: 70%;*/
    /*display: flex;*/
    /*flex-direction: column;*/
    /*padding: 20px;*/

    /*.task-title {*/
    /*align-self: center;*/
    /*color: white;*/
    /*font-size: 48px;*/
    /*font-weight: 200;*/
    /*letter-spacing: 3px;*/
    /*}*/


    /*}*/

</style>
