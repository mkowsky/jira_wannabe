<template>
    <div class="task-details-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="task-details-content">
            <v-tabs @change="changeContent($event)" grow color="rgba(235, 182, 193, 1)">
                <v-tab>General</v-tab>
                <v-tab>Task timeline</v-tab>
                <v-tab>CODE</v-tab>
                <v-tab>Task comments</v-tab>

            </v-tabs>


            <div class="task-details-flex-container" v-show="generalInfoVisible">

                <general-task-info :current-task="currentTask"
                                   @pm-name-clicked="navigateToUserProfile($event)"></general-task-info>


                <div class="task-description">
                    <expansion-panel :panel-title="'Task actions'" :expanded="0">
                        <template slot="content">
                            <div style="display: flex; justify-content: space-evenly">
                                <v-btn @click="changeStateVisible = true">CHANGE STATE</v-btn>
                                <v-btn @click="passTaskVisible = true">PASS TO SOMEONE ELSE</v-btn>

                            </div>
                        </template>
                    </expansion-panel>


                </div>
            </div>


            <div class="task-timeline-flex-container" v-show="timelineVisible">
                <div class="task-timeline">
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
            </div>


            <div class="code-flex-container" v-show="usersVisible">
                <div class="code-inner-flex">
                    <div style="display: flex; flex-direction: row; padding: 20px; justify-content: space-evenly; width: 30%">
                        <v-btn @click="applyCodeVisible = true">ENTER CODE</v-btn>
                        <v-btn @click="issueReportVisible = true">REPORT ISSUE</v-btn>
                        <v-btn v-if="currentTask.state === 'CODE_REVIEW'">CODE ACCEPTATION</v-btn>

                    </div>

                    <prism-editor
                            class="my-editor"
                            v-model="code"
                            :highlight="highlighter"
                            :line-numbers="lineNumbers"
                            readonly
                    ></prism-editor>
                </div>


                <Modal :dialog="applyCodeVisible"
                       :dialog-title="'Apply code'"
                       :dialog-content="'Please paste your code below.'"
                       @modal-cancel="cancelCodeChange()"
                       @modal-agree="applyCodeChange()"
                       :width="800"
                >
                    <template slot="body">

                        <prism-editor
                                class="my-editor"
                                style="height: 300px;"
                                v-model="codeChange"
                                :highlight="highlighter"
                                :line-numbers="lineNumbers"
                        ></prism-editor>
                    </template>
                </Modal>

                <Modal :dialog="issueReportVisible"
                       :dialog-title="'Issue report'"
                       :dialog-content="'Please provide short descripiton for issue you have faced.'"
                       @modal-agree="reportIssue()"
                       @modal-cancel="cancelIssue()"
                >
                    <template slot="body">
                        <v-textarea solo label="Issue description" v-model="issueDescription"></v-textarea>
                    </template>
                </Modal>


            </div>


            <div class="task-users-flex-container" v-show="commentsVisible">
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
                        {{ data.item.firstName }} {{ data.item.lastName }}
                    </template>
                    <template slot="item" slot-scope="data">
                        {{ data.item.firstName }} {{ data.item.lastName }}
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


    import {PrismEditor} from "vue-prism-editor";
    import "vue-prism-editor/dist/prismeditor.min.css"; // import the styles somewhere

    // import highlighting library (you can use any library you want just return html string)
    import {highlight, languages} from "prismjs/components/prism-core";
    import "prismjs/components/prism-clike";
    import "prismjs/components/prism-javascript";
    import "prismjs/themes/prism-tomorrow.css"; // import syntax highlighting styles
    export default {
        name: "TaskDetails",
        components: {SideNavigationBar, GeneralTaskInfo, Modal, StatusChangeItem, Comment, ExpansionPanel, PrismEditor},
        props: {
            taskID: {
                required: true,

            }
        },
        data() {
            return {
                issueDescription: '',
                code: 'console.log("Hello World")',
                lineNumbers: true,
                codeChange: '',
                passPerson: "",
                projectUsers: [],
                states: [],
                newState: "",
                currentUserID: "",
                comments: [],
                currentTask: [],
                commentValue: "",
                taskChanges: [],
                tabNumber: 0,
                generalInfoVisible: true,
                timelineVisible: false,
                usersVisible: false,
                commentsVisible: false,

                applyCodeVisible: false,
                changeStateVisible: false,

                showOverlay: false,
                taskStatusChanged: false,
                issueReportVisible: false,

                passTaskVisible: false,
            }
        },
        methods: {
            reportIssue() {
                axios.post('http://localhost:8080/tasks/report-new-issue-for-task', null, {
                    params: {
                        taskID: this.currentTask.id,
                        issueDescription: this.issueDescription,
                    }
                }).then(response => {
                    console.log(response.status);
                    this.issueReportVisible = false;
                    this.getTask();
                });

            },
            cancelIssue() {
                this.issueDescription = '';
                this.issueReportVisible = false;
            },
            cancelCodeChange() {
                this.code = 'console.log("Hello World")',
                    this.applyCodeVisible = false;
            },
            applyCodeChange() {
                this.code = this.codeChange;
                this.applyCodeVisible = false;
            },
            applyCode(code) {
                console.log(code.split(/\r\n|\r|\n/).length)
            },
            highlighter(code) {
                return highlight(code, languages.js);
            },
            changeContent(event) {

                console.log(event)
                this.tabNumber = event;
                this.generalInfoVisible = false;
                this.timelineVisible = false;
                this.usersVisible = false;
                this.commentsVisible = false;
                switch (this.tabNumber) {
                    case 0:
                        this.generalInfoVisible = true;
                        break;
                    case 1:
                        this.timelineVisible = true;
                        break;
                    case 2:
                        this.usersVisible = true;
                        break;
                    case 3:
                        this.commentsVisible = true;
                        break;
                }
            },
            changeTaskState() {
                this.changeStateVisible = false;
                this.showOverlay = true;
                axios.post('http://localhost:8080/tasks/change-task-state', null, {
                    params: {
                        newState: this.newState,
                        taskID: this.currentTask.id
                    }
                }).then(response => {
                    console.log(response.status);
                    this.getTask();
                });
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
                        newTaskUser: this.passPerson.id
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
            closeTaskDetails() {
                //TODO: tutaj jakos powrot do proejktu ogaranc
                // this.$router.push({name: 'projectDetails', params:{projectID: this.currentTask.projectID}})
            },
            navigateTo(value) {
                this.$router.push({name: 'profileDetails', params: {userID: value}})
            },
            navigateToUserProfile(value) {
                console.log(value);
                this.$router.push({name: 'profileDetails', params: {userID: value}})
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
                        console.log('to do')
                        break;
                    case "IN_PROGRESS":
                        this.states = ["CODE_REVIEW", "DONE"];
                        console.log('2');
                        break;
                    case "CODE_REVIEW":
                        this.states = ["DONE"];
                        console.log('3');
                        break;
                }

            },
            prepareProjectUsers() {
                for (let i = 0; i < this.projectUsers.length; i++) {
                    if (this.projectUsers[i].id === this.currentUserID) {
                        this.projectUsers.splice(i, 1);
                    }
                }
            },

        },
        created() {
            let user = JSON.parse(localStorage.getItem('user'));
            this.currentUserID = user.id
            axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                this.currentTask = response.data;
                this.comments = this.currentTask.comments;
                this.taskChanges = this.currentTask.taskChanges;
                this.taskChanges.reverse();
                this.comments.reverse();
                this.projectUsers = this.currentTask.project.projectUsers;
                this.checkStates();
                this.prepareProjectUsers();
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
        flex-direction: column;
        justify-content: flex-end;

    }

    .task-timeline {
        display: flex;
        justify-content: center;
    }

    .task-timeline-item {
        width: 50%;
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
