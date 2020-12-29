<template>
    <body>
    <div style="width: 100%; padding: 20px; height: 900px;">
        <div v-if="comments.length > 0" style="display: flex; width: 100%; height: 100%;">
            <div style="width: 50%; background: crimson">
                <div>COMMENTS</div>
                <div style="width: 40%; display: flex; flex-direction: column; align-self: center">
                    <v-textarea solo
                                v-model="commentValue"
                                label="Write comment"
                                style="opacity: 0.8"></v-textarea>

                    <v-btn x-large color="#6C63FF" @click="postComment" style="width: 200px; align-self: center" >POST</v-btn>
                </div>
            </div>

            <div style="width: 50%; background: purple; display: flex; flex-direction: column; align-items: center;">
                <comment  v-for="comment in comments" :key="comment.id"
                          :comment-date="comment.commentDate"
                          :comment-content="comment.comment"
                          :comment-username="comment.user.firstName + ' ' + comment.user.lastName"
                          :profile-picture="comment.user.pictureURL"
                          :delete-icon-visible="checkIfThisIsLoggedUserComment(comment.user.id)"
                          @delete-comment="deleteComment(comment.id)"/>
            </div>

        </div>

        <div v-if="comments.length === 0" style="display: flex; flex-direction: row">

            <div style="width: 60%">
                <img src="../assets/undraw_Mailbox_re_dvds.svg" style="width: 100%; height: 80%; opacity: 0.7">
            </div>
            <div style="width: 40%; display: flex; flex-direction: column">
                <div class="section-title">COMMENTS</div>
                <div class="claim">There is no comments to this task.</div>
               <div style="width: 80%; display: flex; flex-direction: column; align-self: center">
                    <v-textarea solo
                                v-model="commentValue"
                                label="Write comment"
                                style="opacity: 0.8"></v-textarea>

                   <v-btn x-large color="#6C63FF" @click="postComment" style="width: 200px; align-self: center" >POST</v-btn>
               </div>
            </div>
        </div>

    </div>
    </body>
</template>

<script>
    import Comment from "@/components/Comment"

    export default {
        name: "TaskComments",
        props: ['comments'],
        components: {Comment},
        data(){
            return{
                commentValue: '',
                currentUserID: 0,
            }
        },
        methods:{
            checkIfThisIsLoggedUserComment: function (value) {
                return (this.currentUserID === value)
            },
            postComment(){
                this.$emit('new-comment',{comment: this.commentValue});
            },
            deleteComment(comment){
                this.$emit('delete-comment', {commentID: comment})
            }
        },
        created(){
            let user = JSON.parse(localStorage.getItem('user'));
            this.currentUserID = user.id;
        }
    }
</script>

<style scoped>
    .section-title {
        background: #6C63FF;
        padding: 10px;
        font-size: 50px;
        margin-bottom: 20px;
        text-align: center;
        width: 650px;


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

    .description {

    }
</style>
