<template>
    <body>
    <div style="width: 100%; height: 80vh; display: flex; align-items: center; padding: 30px;">

        <div style="width: 30%; height: 70%; display: flex; flex-direction: column;">
            <div class="section-title">ACTIONS</div>
            <div class="claim">MANAGE YOUR TASKS</div>
            <div class="subclaim">All the changes will be implemented immediately and visible in task timeline.</div>
        </div>

        <div style="width:70%; display: flex; flex-direction: row; justify-content: center">
            <div class="actions-box">
                <p class="actions-box-title">CHANGE STATE</p>
                <font-awesome-icon icon="exchange-alt" class="actions-box-icon"></font-awesome-icon>
                <p class="actions-box-text">Job is already done? Change task state so others can see it!</p>
                <v-btn class="actions-box-button" color="#EBB6C1" @click="changeState">CHANGE</v-btn>
            </div>
            <div class="actions-box">
                <p class="actions-box-title">PASS TASK</p>
                <font-awesome-icon icon="users" class="actions-box-icon"></font-awesome-icon>
                <p class="actions-box-text">Need help? Pass task to someone else from your team!</p>
                <v-btn class="actions-box-button" color="#F08080" @click="passToSomeoneElse">PASS</v-btn>
            </div>
            <div class="actions-box">
                <p class="actions-box-title">REPORT ISSUE</p>
                <font-awesome-icon icon="times-circle" class="actions-box-icon"></font-awesome-icon>
                <p class="actions-box-text">You faced any difficulties during work on this task?</p>
                <v-btn class="actions-box-button" color="#DC143C" @click="reportIssue">
                    REPORT
                </v-btn>
            </div>
            <div class="actions-box">
                <p class="actions-box-title">CHANGE DEADLINE</p>
                <font-awesome-icon icon="calendar" class="actions-box-icon"></font-awesome-icon>
                <p class="actions-box-text">Too much work left?</p>
                <v-btn class="actions-box-button" color="#AC474C" @click="changeDeadline">CHANGE</v-btn>
            </div>
        </div>
    </div>
    </body>
</template>

<script>
    export default {
        name: "TaskActions",
        methods:{
            reportIssue(){
                this.$emit('report-issue');
            },
            changeDeadline(){
                this.$emit('change-deadline')
            },
            passToSomeoneElse(){
                this.$emit('pass-task')
            },
            changeState(){
                this.$emit('change-state');
            }

        }

    }
</script>

<style scoped>


    .actions-box {
        width: 260px;
        height: 340px;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 15px;
        margin-right: 20px;
    }

    .actions-box-title {
        font-size: 23px;
        align-self: center;
        font-weight: 400;
        text-align: center;
    }

    .actions-box-icon {
        font-size: 70px;
        align-self: center;
        opacity: 0.8;
    }

    .actions-box-text {
        font-size: 16px;
        align-self: center;
        text-align: center;
        font-style: italic;
    }

    .actions-box-button {
        width: 180px;
        font-size: 18px;
        align-self: center;
        opacity: 0.8;
    }

    .section-title {
        background: #6C63FF;
        padding: 10px;
        font-size: 50px;
        margin-bottom: 20px;
        text-align: center;

    }

    .claim {
        font-size: 44px;
        font-weight: 800;
        text-align: center;
        margin-bottom: 30px;
    }

    .subclaim {
        font-size: 36px;
        text-align: center;
        font-style: italic;
        opacity: 0.8;
        margin-bottom: 80px;
    }
</style>
