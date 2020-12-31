<template>
    <body>
    <side-navigation-bar></side-navigation-bar>

        <div class="task-details-content">
            <v-tabs @change="changeContent($event)" grow height="80" :color="colorAccent">
                <v-tab style="font-size: 24px;">General</v-tab>
                <v-tab style="font-size: 24px;">Task timeline</v-tab>
            </v-tabs>


            <div class="task-details-flex-container" v-show="generalInfoVisible">

                <task-info :current-task="currentTask"
                           style="height: 85vh; margin-top: 150px; padding: 10px;"
                           @navigate-to-user-profile="navigateToUserProfile($event)"/>

               <task-actions @report-issue="issueReportVisible = true"
                            @change-deadline="changeDeadlineVisible=true"
                            @pass-task="startPassTaskProcess"
                            @change-state="changeStateVisible=true"
                             style="height: 100vh; margin-top: 150px; padding: 10px;"/>


               <task-issues :task-issues="taskIssues"
                            @report-issue="issueReportVisible = true"
                            style="height: 100vh; margin-top: 200px;"/>

                <task-comments :comments="comments"
                               @new-comment="submitNewComment($event)"
                               @delete-comment="deleteComment($event)"
                               style="height: 100vh; margin-top: 200px;"/>
            </div>

            <div  v-show="timelineVisible">

                <TaskTimeline :task-changes="taskChanges"></TaskTimeline>

            </div>
        </div>


        <Dialog :dialog="passTaskVisible"
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
        </Dialog>
        <Dialog :dialog="changeStateVisible"
               :dialog-title="'Change task status'"
               :dialog-content="'Change current task status. All changes will be visible in task timeline tab.'"
               @modal-cancel="cancelStateChange()"
               @modal-agree="changeTaskState()"
        >
            <template slot="body">
                <label>TASK STATE</label>
                <v-select solo dense label="CHOSE STATE" :items="states" v-model="newState"></v-select>
            </template>
        </Dialog>
        <Dialog :dialog="issueReportVisible"
               :dialog-title="'Issue report'"
               :dialog-content="'Please provide us with issue type and short descripiton for issue you have faced.'"
               @modal-agree="reportIssue()"
               @modal-cancel="cancelIssue()"
        >
            <template slot="body">
                <v-select solo label="Issue type" :items="issueType" v-model="issType"></v-select>
                <v-select solo label="Issue type" :items="priorities" v-model="issPrio" item-text="name"
                          item-value="value"></v-select>
                <v-textarea solo label="Issue description" v-model="issueDescription"></v-textarea>
            </template>
        </Dialog>
        <Dialog :dialog="changeDeadlineVisible"
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
        </Dialog>

        <v-overlay v-show="showOverlay">
            <v-progress-circular
                    indeterminate
                    size="74"
            >Loading
            </v-progress-circular>
        </v-overlay>

        <Dialog :dialog="taskStatusChanged"
               :dialog-title="'Task status changed.'"
               :dialog-content="'Task status has been changed succesfully. All changes are now visible in task timeline tab.'"
               @modal-agree="taskStatusChanged = false"
               :one-button="true"
               :agree-button="'Okey'"
        >
        </Dialog>



    </body>
</template>

<script>

    import axios from "axios";
    import colors from "@/assets/css/colors"



    import SideNavigationBar from "@/components/SideNavigationBar";
    import TaskIssues from "@/components/TaskIssues";
    import TaskComments from "@/components/TaskComments";
    import TaskInfo from "@/components/TaskInfo";
    import TaskActions from "@/components/TaskActions";
    import Dialog from "@/components/Dialog";
    import TaskTimeline from "@/components/TaskTimeline";


    export default {
        name: "TaskDetails",
        components: {
            TaskTimeline,
            Dialog, TaskActions, TaskInfo, TaskComments, TaskIssues, SideNavigationBar,},
        props: {
            taskID: {
                required: true,

            }
        },
        data() {
            return {
                colorAccent: colors.ACCENT,
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

                taskIssues: [],

                issuePicked: false,
                currentIssue: '',
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
                this.$router.push({name: 'profileDetails', params: {userID: event.userID}})
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

            submitNewComment(event) {
                axios.post('http://localhost:8080/comments/new-comment', {
                    userID: this.currentUserID,
                    comment: event.comment,
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

            deleteComment(event) {

                axios.post('http://localhost:8080/comments/delete-comment', null, {
                    params: {
                        commentID: event.commentID,
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
            axios.get('http://localhost:8080/tasks/get-all-issues-for-task/' + this.taskID).then(response => {
                this.taskIssues = response.data;
            })

        },
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";


    body {
        font-family: 'Montserrat', sans-serif;
    }




    .task-details-content {
        margin-left: 12%;
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








</style>
