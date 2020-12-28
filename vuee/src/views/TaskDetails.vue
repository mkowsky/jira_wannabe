<template>
    <body>
    <div class="task-details-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="task-details-content">
            <v-tabs @change="changeContent($event)" grow color="rgba(235, 182, 193, 1)">
                <v-tab style="font-size: 24px;">General</v-tab>
                <v-tab style="font-size: 24px;">Task timeline</v-tab>
            </v-tabs>


            <div class="task-details-flex-container" v-show="generalInfoVisible">

                <div style="width: 100%; background: #bababa; height: 200px; display: flex; justify-content: center; align-items: center">
                    <div class="claim" style="text-transform: uppercase">{{currentTask.name}}</div>
                </div>

                <div style="width: 100%; height: 500px; padding: 20px;">

                    <div class="grid-wrapper">
                        <div class="grid-title">TASK DETAILS</div>
                        <div class="description-card">
                            <div class="--claim">
                                TASK DESCRIPTION
                            </div>
                            <div class="--subclaim">
                                TASK SUBCLAIM
                            </div>
                            <div class="--content">
                                Lorem Ipsum is simply dummy
                                text of the printing and typesetting industry. Lorem Ipsum has been the industry's
                                standard dummy
                                text ever since the 1500s, when an unknown printer took a galley of type and scrambled
                                it to make a
                                type specimen book. It has survived not only five centuries, but also the leap into
                                electronic
                                typesetting, remaining essentially unchanged.
                            </div>
                        </div>
                        <div class="deadline-card">
                            <div class="-title">DEADLINE</div>
                            <div class="-content"> {{currentTask.expireDate | moment("DD-MM-YYYY")}}</div>
                        </div>
                        <div class="priority-card">
                            <div class="-title">PRIORITY</div>
                            <div class="-content">
                                {{currentTask.taskPriority}}
                            </div>
                        </div>
                        <div class="status-card">
                            <div class="-title">STATUS</div>
                            <div class="-content"> {{currentTask.state}}</div>
                        </div>
                        <div class="template-grid" style="display: flex; justify-content: center; align-items: center">
                            <div class="claim">TEMPLATE</div>
                        </div>
                        <div class="image-grid">

                                    <div style="display: flex; flex-direction: column;">
                                        <div style="color: black; text-transform: uppercase; font-weight: 600; font-size: 50px;">task</div>
                                        <div style="color: black;  font-size: 60px; font-weight: 400; margin-top: -20px;">is</div>
                                        <div style="color: black; font-size: 60px; font-weight: 200; margin-top: -40px;">assigned to </div>
                                    </div>

                                    <div style="display:flex; ">
                                        <v-avatar style="height: 150px; width: 150px;">
                                            <img :src="currentTask.user.pictureURL" style="object-fit: cover">
                                        </v-avatar>
                                        <div style="display: flex; flex-direction: column; align-self: flex-end">
                                            <div @click="navigateToUserProfile"
                                                 style="cursor: pointer; font-size: 46px; font-weight: 400">
                                                {{currentTask.user.firstName}}
                                            </div>
                                            <div @click="navigateToUserProfile"
                                                 style="cursor: pointer; font-size: 56px; font-weight: 800; margin-top: -30px;">
                                                {{currentTask.user.lastName}}
                                            </div>
                                        </div>
                                    </div>



                        </div>
                    </div>

                </div>

                <div style="width: 100%; height: 300px; background: #bababa;">
                <div class="claim">ACTIONS</div>
                    <div style="display: flex; flex-direction: row; justify-content: center">
                        <div class="actions-box">
                            <p class="actions-box-title">CHANGE STATE</p>
                            <font-awesome-icon icon="exchange-alt" class="actions-box-icon"></font-awesome-icon>
                            <p class="actions-box-text">Job is already done? Change task state so others can see it!</p>
                            <v-btn class="actions-box-button" color="#EBB6C1">CHANGE</v-btn>
                        </div>
                        <div class="actions-box">
                            <p class="actions-box-title">PASS TASK</p>
                            <font-awesome-icon icon="users" class="actions-box-icon"></font-awesome-icon>
                            <p class="actions-box-text">Need help? Pass task to someone else from your team!</p>
                            <v-btn class="actions-box-button" color="#F08080">PASS</v-btn>
                        </div>
                        <div class="actions-box">
                            <p class="actions-box-title">REPORT ISSUE</p>
                            <font-awesome-icon icon="times-circle" class="actions-box-icon"></font-awesome-icon>
                            <p class="actions-box-text">You faced any difficulties during work on this task?</p>
                            <v-btn class="actions-box-button" color="#DC143C" @click="issueReportVisible = true">REPORT</v-btn>
                        </div>
                        <div class="actions-box">
                            <p class="actions-box-title">CHANGE DEADLINE</p>
                            <font-awesome-icon icon="calendar" class="actions-box-icon"></font-awesome-icon>
                            <p class="actions-box-text">Too much work left?</p>
                            <v-btn class="actions-box-button" color="#AC474C">CHANGE</v-btn>
                        </div>
                    </div>
                </div>
                <div style="height: 200px; background: lightgrey; width: 100%">
                    <div class="claim">ATTACHED FILES</div>
                </div>
                <div style="height: 200px; background: #bababa; width: 100%">
                    <div class="claim">ISSUES</div>
                </div>
                <div style="width: 100%; background: #eeeeee; padding: 20px; display: flex; flex-direction: column; align-items: center;">
                    <div class="claim">COMMENTS</div>
                    <div>
                        <div style="display: flex;">
                        <v-textarea
                                label="Write a comment"
                                solo
                                v-model="commentValue"
                                style="width: 400px;"></v-textarea>
                        <v-btn
                                elevation="2"
                                rounded
                                x-large
                                @click="submitNewComment">SUBMIT</v-btn>
                    </div>

                        <div>
                            <v-timeline dense>
                                <v-timeline-item
                                        v-for="comment in comments" :key="comment.id"
                                        small
                                >
                                        <comment
                                                :comment-date="comment.commentDate"
                                                :comment-content="comment.comment"
                                                :comment-username="comment.user.firstName + ' ' + comment.user.lastName"
                                                :profile-picture="comment.user.pictureURL"
                                                :delete-icon-visible="checkIfThisIsLoggedUserComment(comment.user.id)"
                                                @delete-comment="deleteComment(comment.id)"/>

                                </v-timeline-item>
                            </v-timeline>

                        </div>

                    </div>

                </div>

                <!--<general-task-info :current-task="currentTask"-->
                <!--@pm-name-clicked="navigateToUserProfile($event)"-->
                <!--@change-state="changeStateVisible= true"-->
                <!--@pass-task="startPassTaskProcess()"-->
                <!--@report-issue="issueReportVisible = true"-->
                <!--@change-deadline="changeDeadlineVisible = true"-->
                <!--&gt;</general-task-info>-->

                <!--<div class="task-description">-->


            </div>

            <div style="height: 95%;" v-show="timelineVisible">

                <div style="width: 100%; height: 100%;display: flex;">


                    <div style="display: flex; flex-direction: column; justify-content: flex-end; width: 60%; padding: 20px;">
                        <div class="task-timeline-item">
                            <v-timeline>
                                <status-change-item v-for="change in taskChanges"
                                                    :key="change.id"
                                                    :change-date="change.changeDate"
                                                    :change-description="change.changeDescription"
                                                    :change-type="change.changeType">
                                </status-change-item>
                            </v-timeline>
                        </div>
                    </div>

                    <div style="width: 40%; padding: 10px; display: flex; flex-direction: column; margin-top: 50px; ">
                        <div class="claim"
                             style="font-size: 44px; font-weight: 800; align-self: center; font-family: Roboto, monospace">
                            Track task changes!
                        </div>

                        <div class="subclaim"
                             style="font-size: 26px; font-weight: 200; width: 400px; align-self: center">
                            <span style="font-weight: 400">Task timeline</span> is a palce where you can check <span
                                style="font-weight: 400">all previous</span> task <span
                                style="font-weight: 400">changes</span>
                            with their exact dates and time. This helps you to <span style="font-weight: 400">track all the proces</span>
                            from
                            task creation to its end!
                        </div>

                        <img src="../assets/timeline.svg" style="width: 90%; margin-top: 40px; align-self: flex-end">
                    </div>
                </div>

            </div>
        </div>


        <Modal :dialog="passTaskVisible"
               :dialog-title="'Pass task to someone else'"
               :dialog-content="'Chose person from your team to which you want to pass this task. After you press confirm this task will no longer be assigned to you.'"
               @modal-cancel="cancelPass()"
               @modal-agree="passTask()"
        >
            <template slot="body">
                <v-select solo dense label="CHOSE PERSON" :items="projectUsers" v-model="passPerson">
                    <template slot="selection" slot-scope="data">
                        <!-- HTML that describe how select should render selected items -->
                        {{ data.item.fullName }}
                    </template>
                    <template slot="item" slot-scope="data">
                        {{ data.item.fullName }}
                    </template>

                </v-select>
            </template>
        </Modal>
        <Modal :dialog="changeStateVisible"
               :dialog-title="'Change task status'"
               :dialog-content="'Change current task status. All changes will be visible in task timeline tab.'"
               @modal-cancel="cancelStateChange()"
               @modal-agree="changeTaskState()"
        >
            <template slot="body">
                <label>TASK STATE</label>
                <v-select solo dense label="CHOSE STATE" :items="states" v-model="newState"></v-select>
            </template>
        </Modal>
        <Modal :dialog="issueReportVisible"
               :dialog-title="'Issue report'"
               :dialog-content="'Please provide us with issue type and short descripiton for issue you have faced.'"
               @modal-agree="reportIssue()"
               @modal-cancel="cancelIssue()"
        >
            <template slot="body">
                <v-select solo label="Issue type" :items="issueType" v-model="issType"></v-select>
                <v-select solo label="Issue type" :items="priorities" v-model="issPrio" item-text="name" item-value="value"></v-select>
                <v-textarea solo label="Issue description" v-model="issueDescription"></v-textarea>
            </template>
        </Modal>
        <Modal :dialog="changeDeadlineVisible"
               :dialog-title="'Change task deadline'"
               :dialog-content="'Choose new task deadline from calendar below.'"
               @modal-cancel="cancelDeadlineChange()"
               @modal-agree="newDeadline()"
        >
            <template slot="body">
                <v-date-picker v-model="newTaskDeadline"
                               color="rgba(225, 182, 193)"
                               :min="currentDate"></v-date-picker>
            </template>
        </Modal>

        <v-overlay v-show="showOverlay">
            <v-progress-circular
                    indeterminate
                    size="74"
            >Loading
            </v-progress-circular>
        </v-overlay>

        <Modal :dialog="taskStatusChanged"
               :dialog-title="'Task status changed.'"
               :dialog-content="'Task status has been changed succesfully. All changes are now visible in task timeline tab.'"
               @modal-agree="taskStatusChanged = false"
               :one-button="true"
               :agree-button="'Okey'"
        >
        </Modal>

    </div>

    </body>
</template>

<script>

    import axios from "axios";


    import StatusChangeItem from "@/components/StatusChangeItem";
    import Modal from "@/components/Modal";
    import Comment from "@/components/Comment"

    import SideNavigationBar from "@/components/SideNavigationBar";


    export default {
        name: "TaskDetails",
        components: {SideNavigationBar, Modal, StatusChangeItem, Comment},
        props: {
            taskID: {
                required: true,

            }
        },
        data() {
            return {
                generalInfoVisible: true,
                timelineVisible: false,


                states: [],
                newState: "",

                passPerson: "",
                projectUsers: [],

                issueReportVisible: false,
                issueType: ["DESCRIPTION", "DEADLINE", "OTHERS"],
                issType: '',
                issPrio: '',
                issueDescription: '',
                priorities: [
                    {value: "ABLE_TO_WAIT", name: "ABLE TO WAIT"},
                    {value: "IMPORTANT", name: "IMPORTANT"},
                    {value: "URGENT", name: "URGENT"},
                    {value: "IMMEDIATE", name: 'IMMEDIATE'},
                ],
                currentUserID: '',
                comments: [],
                currentTask: [],
                commentValue: "",
                taskChanges: [],


                changeStateVisible: false,
                changeStatePossible: true,

                showOverlay: false,
                taskStatusChanged: false,


                passTaskVisible: false,

                changeDeadlineVisible: false,
                newTaskDeadline: '',
                currentDate: this.$moment().format("YYYY-MM-DD"),
            }
        },
        methods: {
            changeContent(tabNumber) {
                this.generalInfoVisible = false;
                this.timelineVisible = false;
                this.usersVisible = false;
                switch (tabNumber) {
                    case 0:
                        this.generalInfoVisible = true;
                        break;
                    case 1:
                        this.timelineVisible = true;
                        break;
                    case 2:
                        this.usersVisible = true;
                        break;
                }
            },
            navigateToUserProfile(event) {
                this.$router.push({name: 'profileDetails', params: {userID: event.id}})
            },

            changeTaskState() {
                this.changeStateVisible = false;
                this.showOverlay = true;
                axios.post('http://localhost:8080/tasks/change-task-state', null, {
                    params: {
                        newState: this.newState,
                        taskID: this.currentTask.id
                    }
                }).then(this.getTask());
                this.newState = "";
            },
            cancelPass() {
                this.passTaskVisible = false;
                this.passPerson = "";
            },
            passTask() {
                axios.post('http://localhost:8080/tasks/change-task-person', null, {
                    params: {
                        taskID: this.currentTask.id,
                        newTaskUser: this.passPerson.id,
                        previousTaskUser: this.currentUserID
                    }
                }).then(response => {
                    console.log(response.status);
                    this.passTaskVisible = false;
                    this.$router.push({name: 'board'})
                    //this.$router.push({name: 'projectDetails', params: {projectID: this.currentTask.project.id}})
                })
            },
            cancelStateChange() {
                this.changeStateVisible = false;
                this.newState = "";
            },

            submitNewComment() {
                axios.post('http://localhost:8080/comments/new-comment', {
                    userID: this.currentUserID,
                    comment: this.commentValue,
                    commentDate: null,
                    taskID: this.currentTask.id,


                }).then(response => {
                    console.log(response.status);
                    axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                        this.currentTask = response.data;
                        this.comments = this.currentTask.comments;
                        this.comments.reverse();
                    })
                })
                this.commentValue = "";

            },
            checkIfThisIsLoggedUserComment: function (value) {
                return (this.currentUserID === value)
            },
            deleteComment(commentToDeleteID) {
                axios.post('http://localhost:8080/comments/delete-comment', null, {
                    params: {
                        commentID: commentToDeleteID
                    }
                }).then(response => {
                    this.deleteCommentModal = false;
                    console.log(response.status);
                    axios.get('http://localhost:8080/comments/get-comments-for-task/' + this.taskID).then(response => {
                        this.comments = response.data

                    })
                })
            },
            getTask() {
                axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                    this.currentTask = response.data;
                    console.log(this.currentTask);
                    this.comments = this.currentTask.comments;
                    this.taskChanges = this.currentTask.taskChanges;
                    this.taskChanges.reverse();
                    console.log(this.taskChanges);
                    this.comments.reverse();
                    console.log(this.comments);
                    this.showOverlay = false;
                    this.taskStatusChanged = true;
                })
            },
            checkStates() {
                switch (this.currentTask.state) {
                    case "TO_DO":
                        this.states = ["IN_PROGRESS", "CODE_REVIEW", "DONE"]
                        break;
                    case "IN_PROGRESS":
                        this.states = ["CODE_REVIEW", "DONE"];
                        break;
                    case "CODE_REVIEW":
                        this.states = ["DONE"];
                        break;
                    case "DONE":
                        this.changeStatePossible = false;
                        break;
                }

            },
            prepareProjectUsers() {
                for (let i = 0; i < this.projectUsers.length; i++) {
                    if (this.projectUsers[i].id === this.currentUserID) {
                        this.projectUsers.splice(i, 1);
                    }
                    if (this.projectUsers[i].id === this.currentTask.user.id) {
                        this.projectUsers.splice(i, 1);
                    }
                }
            },
            startPassTaskProcess() {
                axios.get('http://localhost:8080/tasks/get-all-project-members/' + this.currentTask.id).then(response => {
                    this.projectUsers = response.data;
                    this.prepareProjectUsers();
                    this.passTaskVisible = true;
                })
            },
            cancelIssue() {
                this.issueDescription = '';
                this.issueReportVisible = false;
            },
            reportIssue() {
                let user = JSON.parse(localStorage.getItem('user'));
                axios.post('http://localhost:8080/tasks/report-new-issue-for-task', null, {
                    params: {
                        taskID: this.currentTask.id,
                        issueDescription: this.issueDescription,
                        issueType: this.issType,
                        issuePriority: this.issPrio,
                        reportingUserID: user.id
                    }
                }).then(response => {
                    console.log(response.status);
                    this.issueReportVisible = false;
                    this.getTask();
                });
            },
            cancelDeadlineChange() {
                this.newTaskDeadline = '',
                    this.changeDeadlineVisible = false;
            },
            newDeadline() {
                axios.post('http://localhost:8080/tasks/change-task-deadline', null, {
                    params: {
                        newTaskDeadline: this.newTaskDeadline,
                        taskID: this.currentTask.id
                    }
                }).then(response => {
                    console.log(response.status);
                    this.changeDeadlineVisible = false;
                    this.newTaskDeadline = '';
                    this.getTask()
                });
            }
        },
        created() {
            let user = JSON.parse(localStorage.getItem('user'));
            this.currentUserID = user.id;
            axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                this.currentTask = response.data;
                console.log(this.currentTask);
                this.comments = this.currentTask.comments;
                this.taskChanges = this.currentTask.taskChanges;
                this.taskChanges.reverse();
                this.comments.reverse();
                this.checkStates();
            })

        },
    }
</script>

<style scoped lang="scss">
    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800&display=swap');

    body {
        font-family: 'Montserrat', sans-serif;
    }


    .task-details-container {
        height: 100%;
    }

    .task-details-content {
        margin-left: 10%;
        height: 100%;
    }

    .task-details-flex-container {
        height: 100%;
        display: flex;
        flex-direction: column;
    }

    .task-timeline-flex-container {
        height: 100%;
        display: flex;
    }

    .task-timeline-item {
        width: 100%;
    }


    .task-users-flex-container {
        margin-top: 2%;
        height: 100%;
    }

    .actions-box {
        width: 240px;
        height: 200px;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 5px;
        margin-right: 10px;
    }

    .actions-box-title {
        font-size: 22px;
        align-self: center;
        font-weight: 500;
    }

    .actions-box-icon {
        font-size: 40px;
        align-self: center;
        opacity: 0.8;
    }

    .actions-box-text {
        font-size: 13px;
        align-self: center;
        text-align: center;
        font-style: italic;
    }

    .actions-box-button {
        width: 80px;
        align-self: center;
        opacity: 0.8;
    }

    .claim {
        font-weight: 600;
        font-size: 40px;
        letter-spacing: 3px;
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
        /*color: rgba(235, 182, 193, 1);*/
        color: #DC143C;
        /*color: #F08080;*/
    }

    .grid-wrapper {
        display: grid;
        grid-template-columns: 200px 200px 700px 500px;
        grid-template-rows: auto;
        grid-column-gap: 20px;
        grid-row-gap: 10px;
        grid-template-areas: "title deadline dsc img"
                             "title prio dsc img"
                             "title status template img";

    }

    .grid-title {
        font-size: 30px;
        font-weight: 400;
        grid-area: title;
        display: flex;
        align-items: center;
        justify-content: center;
        background: white;
        box-shadow: 0 0 10px black;
        writing-mode: vertical-lr;
        text-orientation: mixed;
        transform: rotate(180deg);

    }

    .description-card {
        grid-area: dsc;
        box-shadow: 0 0 10px black;
        background: white;
        height: 300px;
        padding: 20px;

        .--claim {
            font-size: 20px;
            color: black;
        }

        .--subclaim {
            font-size: 18px;
            color: #424242;
        }

        .--content {
            font-size: 16px;
            font-style: italic;
            width: 80%;
        }
    }

    .deadline-card {
        grid-area: deadline;
        box-shadow: 0 0 10px black;
        background: white;
        height: 150px;

        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px;
    }

    .priority-card {
        grid-area: prio;
        box-shadow: 0 0 10px black;
        background: white;
        height: 150px;

        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px;
    }

    .status-card {
        grid-area: status;
        box-shadow: 0 0 10px black;
        background: white;
        height: 150px;

        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px;
    }
    .image-grid{
        grid-area: img;
        height: 100%;
        background: white;
        box-shadow: 0 0 10px black;
        padding: 10px;
    }
    .template-grid{
        grid-area: template;
        height: 150px;
        background: white;
        box-shadow: 0 0 10px black;
        padding: 10px;

    }
    .-title{
        font-size: 24px;
        font-weight: 600;
    }
    .-content{
        font-size: 32px;
        font-weight: 800;
    }

</style>
