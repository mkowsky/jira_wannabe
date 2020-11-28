<template>
    <div class="task-details-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="task-details-content">
            <v-tabs @change="changeContent($event)" grow color="rgba(235, 182, 193, 1)">
                <v-tab>General</v-tab>
                <v-tab>Task timeline</v-tab>
                <v-tab>Task users</v-tab>
                <v-tab>Task comments</v-tab>
                <v-tab style="margin-left: 10%" @click="closeTaskDetails">
                    <font-awesome-icon icon="window-close" class="icon-nav"/>
                </v-tab>
            </v-tabs>


            <div class="task-details-flex-container" v-show="generalInfoVisible">

                <general-task-info :current-task="currentTask"
                                   @pm-name-clicked="navigateToUserProfile($event)"></general-task-info>


                <div class="task-description">
                    <expansion-panel :panel-title="'Task actions'" :expanded="0" >
                        <template slot="content">
                            <div style="display: flex; justify-content: space-evenly">
                                <v-btn @click="changeStateVisible = true">CHANGE STATE</v-btn>
                                <v-btn>PASS TO SOMEONE ELSE</v-btn>

                            </div>
                        </template>
                    </expansion-panel>

                </div>
            </div>


            <div class="task-timeline-flex-container" v-show="timelineVisible">
                <div class="task-timeline">
                    <div class="task-timeline-item">
                        <v-timeline >
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


            <div class="task-users-flex-container" v-show="usersVisible">

                <div class="task-users-wrapper">
                    <user-card v-for="user in currentTask.users"
                               :key="user.id"
                               :nickname="user.firstName +' '+ user.lastName"
                               :position="'Developer'"
                               v-bind:userid="user.id"
                               @navigate-to-profile="navigateTo($event)"/>
                </div>
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
                                    :delete-icon-visible="checkIfThisIsLoggedUserComment(comment.user.id)"
                                    @delete-comment="deleteComment(comment.id)"/>

                        </div>

                    </div>
                </div>

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

            </div>
        </div>

    </div>


</template>

<script>

    import axios from "axios";
    import UserCard from "../components/UserCard";
    import ExpansionPanel from "../components/ExpansionPanel";
    import Comment from "../components/Comment";
    import StatusChangeItem from "@/components/StatusChangeItem";
    import Modal from "@/components/Modal";
    import GeneralTaskInfo from "@/components/GeneralTaskInfo";
    import SideNavigationBar from "@/components/SideNavigationBar";


    export default {
        name: "TaskDetails",
        components: {SideNavigationBar, GeneralTaskInfo, Modal, StatusChangeItem, Comment, ExpansionPanel, UserCard,},
        props: {
            taskID: {
                required: true,

            }
        },
        data() {
            return {
                states: ["IN_PROGRESS", "DONE", "CODE_REVIEW", "TO_DO"],
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

                changeStateVisible: false,
            }
        },
        methods: {
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
                axios.post('http://localhost:8080/tasks/change-task-state', null, {
                    params: {
                        newState: this.newState,
                        taskID: this.currentTask.id
                    }
                }).then(response => {
                    console.log(response.status);
                })
                this.newState = "";
            },
            cancelStateChange(){
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
        },
        created() {
            let user = JSON.parse(localStorage.getItem('user'));
            this.currentUserID = user.id
            axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                this.currentTask = response.data;
                this.comments = this.currentTask.comments;
                this.taskChanges = this.currentTask.taskChanges;
                this.taskChanges.reverse();
                console.log(this.taskChanges);
                this.comments.reverse();
                console.log(this.comments);
            })
        },
        computed: {}
    }
</script>

<style scoped lang="scss">
    .task-details-container {
        height: 100%;
    }
    .task-details-content{
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
        margin-top: 2%;
        height: 100%;
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

    .task-users-flex-container {
        margin-top: 2%;
        height: 100%;

    }

    .task-users-wrapper{

    }



</style>
