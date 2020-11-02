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


        <div>
            <input type="text" placeholder="Write comment" v-model="commentValue" style="margin-bottom: 20px;"/>
            <button @click="submitNewComment">SUBMIT</button>
            <div>Current comments: {{currentTask.comments.length}}</div>
            <div v-for="comment in comments" :key="comment.id" style="margin-bottom: 30px;">

                <div style="font-weight: bold; font-size: 20px">{{comment.user.username}}</div>
                <div>{{comment.commentDate | moment("DD/MM/YYYY hh:mm")}}</div>
                <div >{{comment.comment}}<font-awesome-icon icon="window-close" class="comment-delete" v-if="sprawdz(comment.user.id)" @click="startDeletionProcess(comment.id)"></font-awesome-icon></div>
            </div>
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
</template>

<script>

    import axios from "axios";
    import Modal from "@/components/Modal";


    export default {
        name: "TaskDetails",
        components: {Modal},
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
            deleteComment(){
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
            sprawdz: function(value){
                return (this.currentUserID === value)
            },
            startDeletionProcess(value){
                this.currentCommentID = value;
                this.deleteCommentModal = true;
            }
        },
        created() {
            let user = JSON.parse(localStorage.getItem('user'));
            this.currentUserID= user.id
            axios.get('http://localhost:8080/tasks/get-task/' + this.taskID).then(response => {
                this.currentTask = response.data;
                this.comments = this.currentTask.comments;
                console.log(this.comments);

            })
        },
        computed: {

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
    .comment-delete{
        margin-left: 10px;

    }
    .comment-delete:hover{
        color: crimson;
        cursor: pointer;
    }

</style>
