<template>
    <div class="task-details-container">
        <font-awesome-icon icon="window-close" class="icon-nav" @click="closeTaskDetails"/>
        <div class="task-details-flex-container">

            <div class="general-task-info">
                <div class="pm-display">
                    <div class="pm-name-lighter">PROJECT MANAGER</div>
                    <div class="circle-avatar"></div>
                    <div class="pm-name"
                         @click="navigateToUserProfile(currentTask.taskManager.id)">Mateusz Pietrzykowski
                    </div>
                </div>
                <div class="content">

                    <div class="task-title">{{currentTask.name}}</div>
                    <div class="task-rest">Deadline {{currentTask.expireDate}}</div>
                    <div class="task-rest"> Status {{currentTask.state}}</div>
                    <div class="task-rest">Department {{currentTask.department}}</div>
                </div>
            </div>

            <div class="task-description">
              <expansion-panel :panelTitle="'Task description'">
                  <template slot="content">
                      {{currentTask.description}}
                  </template>
              </expansion-panel>
            </div>

            <div class="task-description">
              <expansion-panel :panelTitle="'Task timeline'">
                  <template slot="content">History content</template>
              </expansion-panel>
            </div>

            <div class="task-description">
             <expansion-panel :panelTitle="'Current task users'">
                 <template slot="content">
                     <div class="task-users-wrapper">
                         <user-card v-for="user in currentTask.users"
                                    :key="user.id"
                                    :nickname="user.firstName +' '+ user.lastName"
                                    :position="'Developer'"
                                    :user-id="user.id"
                                    @navigate-to-profile="navigateTo($event)"/>
                     </div>
                 </template>
             </expansion-panel>



            </div>
            <div class="task-description">
                <expansion-panel :panelTitle="'Comments'">
                    <template slot="content">
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
                            >SUBMIT</v-btn>
                            <div>Current comments: {{currentTask.comments.length}}</div>
                            <div v-for="comment in comments" :key="comment.id" style="margin-bottom: 30px;">

                                <div style="font-weight: bold; font-size: 20px">{{comment.user.username}}</div>
                                <div>{{comment.commentDate | moment("DD/MM/YYYY hh:mm")}}</div>
                                <div>{{comment.comment}}
                                    <font-awesome-icon icon="window-close" class="comment-delete"
                                                       v-if="sprawdz(comment.user.id)"
                                                       @click="startDeletionProcess(comment.id)"></font-awesome-icon>
                                </div>
                            </div>

                        </div>
                    </template>
                </expansion-panel>
            </div>


            <Modal v-show="deleteCommentModal" @close-modal="deleteCommentModal=false">
                <template slot="header">Usuń komentarz</template>
                <template slot="body">Jesteś pewny ze chcesz usunac komentarz?</template>
                <template slot="footer">
                    <button @click="deleteComment()" style="margin-right: 50px;">YES</button>
                    <button @click="deleteCommentModal=false">NO</button>
                </template>

            </Modal>
        </div>

    </div>


</template>

<script>

    import axios from "axios";
    import UserCard from "@/components/UserCard";
    import Modal from "@/components/Modal";
    import ExpansionPanel from "@/components/ExpansionPanel";


    export default {
        name: "TaskDetails",
        components: {ExpansionPanel, UserCard, Modal},
        props: {
            taskID: {
                required: true,
            }
        },
        data() {
            return {
                deleteCommentModal: false,
                currentCommentID: 0,
                currentUserID: "",
                comments: [],
                currentTask: [],
                commentValue: "",

            }
        },
        methods: {
            closeTaskDetails() {
                console.log('close');
                this.$emit('close-task-details');
            },
            navigateTo(value) {
                value.userID
                console.log(';l')
                console.log(value);
            },
            navigateToUserProfile(value) {
                console.log(value);
                this.$router.push({name: 'profileDetails', params: {userID: value}})
            },
            deleteComment() {
                console.log(this.currentCommentID);
                axios.post('http://localhost:8080/comments/delete-comment', null, {
                    params: {
                        commentID: this.currentCommentID
                    }
                }).then(response => {
                    this.deleteCommentModal = false;
                    console.log(response.status);
                    axios.get('http://localhost:8080/comments/get-comments-for-task/' + this.taskID).then(response => {
                        this.comments = response.data

                    })
                })
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

                    })
                })
                this.commentValue = "";

            },
            sprawdz: function (value) {
                return (this.currentUserID === value)
            },
            startDeletionProcess(value) {
                this.currentCommentID = value;
                this.deleteCommentModal = true;
            }
        },
        created() {
            let user = JSON.parse(localStorage.getItem('user'));
            this.currentUserID = user.id
            axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                this.currentTask = response.data;
                this.comments = this.currentTask.comments;
                console.log(this.comments);

            })
        },
        computed: {}
    }
</script>

<style scoped lang="scss">
    .task-details-container {
        height: 100vh;
    }


    .icon-nav {
        position: absolute;
        left: 95%;
        top: 1%;
        cursor: pointer;
        font-size: 40px;
    }

    .icon-nav:hover {
        color: lightpink;
    }

    .task-details-flex-container {
        margin-top: 5%;
        background: gray;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;

    }

    .general-task-info {
        background: black;
        width: 70%;
        height: 400px;
        display: flex;
    }

    .pm-display {
        background: lightpink;
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

    .content {
        display: flex;
        flex-direction: column;

        .task-title {
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
        }

    }


    .task-description {
        width: 70%;

    }

    .task-users-wrapper {
        display: flex;
        flex-direction: row;
        align-items: flex-end;
    }


</style>
