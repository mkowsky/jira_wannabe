<template>
    <div class="task-details-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="task-details-content">
            <v-tabs @change="changeContent($event)" grow color="rgba(235, 182, 193, 1)" >
                <v-tab style="font-size: 24px;">General</v-tab>
                <v-tab style="font-size: 24px;">Task timeline</v-tab>
                <v-tab style="font-size: 24px;">?????</v-tab>
            </v-tabs>


            <div class="task-details-flex-container" v-show="generalInfoVisible">

                <general-task-info :current-task="currentTask"
                                   @pm-name-clicked="navigateToUserProfile($event)"></general-task-info>


                <div class="task-description">
                    <expansion-panel :panel-title="'Task actions'" :expanded="0">
                        <template slot="content">
                            <div style="display: flex; justify-content: space-evenly">
                                <v-btn @click="changeStateVisible = true" v-if="changeStatePossible">CHANGE STATE
                                </v-btn>
                                <v-btn @click="startPassTaskProcess()">PASS TO SOMEONE ELSE</v-btn>
                                <v-btn @click="issueReportVisible = true">REPORT AN ISSUE</v-btn>
                                <v-btn @click="changeDeadlineVisible = true">CHANGE DEADLINE</v-btn>
                            </div>
                        </template>
                    </expansion-panel>
                </div>

                <div>
                    <v-text-field
                            label="Write a comment"
                            solo
                            v-model="commentValue"
                    ></v-text-field>
                    <v-btn
                            elevation="2"
                            rounded
                            x-large
                            @click="submitNewComment"
                    >SUBMIT
                    </v-btn>

                    <div style="margin-top: 20px;">
                        <div v-for="comment in comments" :key="comment.id" style="margin-bottom: 10px;">

                            <comment
                                    :comment-date="comment.commentDate"
                                    :comment-content="comment.comment"
                                    :comment-username="comment.user.firstName + ' ' + comment.user.lastName"
                                    :profile-picture="comment.user.pictureURL"
                                    :delete-icon-visible="checkIfThisIsLoggedUserComment(comment.user.id)"
                                    @delete-comment="deleteComment(comment.id)"/>

                        </div>

                    </div>
                </div>
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

                    <div style="width: 40%; padding: 10px; display: flex; flex-direction: column;; margin-top: 50px;">
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


            <div class="code-flex-container" v-show="usersVisible">
                ?????


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


</template>

<script>

    import axios from "axios";

    import ExpansionPanel from "../components/ExpansionPanel";
    import Comment from "../components/Comment";
    import StatusChangeItem from "@/components/StatusChangeItem";
    import Modal from "@/components/Modal";
    import GeneralTaskInfo from "@/components/GeneralTaskInfo";
    import SideNavigationBar from "@/components/SideNavigationBar";

    export default {
        name: "TaskDetails",
        components: {SideNavigationBar, GeneralTaskInfo, Modal, StatusChangeItem, Comment, ExpansionPanel,},
        props: {
            taskID: {
                required: true,

            }
        },
        data() {
            return {
                generalInfoVisible: true,
                timelineVisible: false,
                usersVisible: false,

                states: [],
                newState: "",

                passPerson: "",
                projectUsers: [],

                issueReportVisible: false,
                issueType: ["DESCRIPTION", "DEADLINE", "OTHERS"],
                issType: '',

                currentUserID: "",
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
                axios.post('http://localhost:8080/tasks/report-new-issue-for-task', null, {
                    params: {
                        taskID: this.currentTask.id,
                        issueDescription: this.issueDescription,
                        issueType: this.issType,
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


    .my-editor {
        background: #2d2d2d;
        color: #ccc;
        font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
        font-size: 14px;
        line-height: 1.5;
        padding: 5px;

    }

    .prism-editor__textarea {
        outline: none;
        border: none;
    }

    .prism-editor__textarea:focus {
        outline: none;
    }

    .task-details-container {
        height: 100%;
    }

    .task-details-content {
        margin-left: 10%;
        height: 100%;
    }

    .icon-nav {
        font-size: 40px;
    }

    .icon-nav:hover {
        color: lightpink;
    }

    .task-details-flex-container {
        margin-top: 2%;
        height: 100%;
        display: flex;
        flex-direction: column;

        justify-content: center;
    }


    .task-description {
        width: 70%;
        align-self: center;

    }

    .task-timeline-flex-container {

        height: 95%;
        display: flex;


    }

    .task-timeline {
        background: darkred;
        display: flex;
        justify-content: center;
        width: 50%;
    }

    .task-timeline-item {
        width: 100%;
        align-self: center;
    }

    .code-flex-container {
        margin-top: 2%;
        height: 90%;
        display: flex;
    }

    .code-inner-flex {

        width: 100%;
        display: flex;
        flex-direction: column;
    }

    .task-users-flex-container {
        margin-top: 2%;
        height: 100%;

    }

    .task-users-wrapper {

    }


</style>
