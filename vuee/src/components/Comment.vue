<template>
<div>


    <v-card outlined  class="content">
        <v-card-subtitle style="font-size: 18px;"><v-avatar color="grey" size="30" style="margin-right: 10px;">
            <img :src="profilePicture" style="object-fit: cover">
        </v-avatar>{{commentUsername}} <span style="position: absolute; left: 70%; font-size: 14px;">{{wysiwetlDate}}</span>  <font-awesome-icon @click="isDialogVisible = true" v-show="deleteIconVisible" class="icon" style="position: absolute; left: 95%" icon="window-close"></font-awesome-icon></v-card-subtitle>

        <v-card-text>{{commentContent}}</v-card-text>
    </v-card>

    <Dialog :dialog="isDialogVisible" @modal-cancel="isDialogVisible = false" @modal-agree="deleteComment" :dialog-content="'Czy jestes pewny ze chcesz usunac komentarz?'"
    :dialog-title="'Usun komentarz'"
    ></Dialog>
</div>
</template>

<script>

    import Dialog from "@/components/Dialog";
    export default {
        name: "Comment",
        components:{Dialog},
        props: {
            commentDate: {},
            commentContent: {
                type: String,
            },
            commentUsername: {
                type: String,
            },
            deleteIconVisible:{
                type: Boolean,
                default: false
            },
            profilePicture:{
                type: String,
            }
        },
        data() {
            return {
                wysiwetlDate: String,
                isDialogVisible: false,
            }
        },
        methods: {
            deleteComment(){
                this.$emit('delete-comment');
                this.isDialogVisible = false;
            },
            calculate() {
                var time = Date.parse(this.commentDate)
                var timeNow = Date.now();
                var howLong = timeNow - time + 300;
                var date = new Date(howLong);
                console.log(howLong)
                var minutes = date.getMinutes();
                var godziny = date.getHours();

                if(howLong > 86400000) this.wysiwetlDate = this.commentDate;
                else if(godziny < 2) this.wysiwetlDate = minutes + ' min temu';
                else this.wysiwetlDate = godziny + ' godz temu';
                // this.wysiwetlDate = minutes;
            },

        },
        created(){
            this.calculate();
        }
    }
</script>

<style scoped>
    .content{
        width:
                600px;
    }
    .icon:hover{
        color: crimson;
        cursor: pointer;
    }

</style>
