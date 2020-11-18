<template>
<div>


    <v-card outlined shaped class="content">
        <v-card-subtitle style="font-size: 18px;"><v-avatar color="grey" size="30" style="margin-right: 10px;">

        </v-avatar>{{commentUsername}} <span style="position: absolute; left: 85%; font-size: 14px;">{{wysiwetlDate}}</span>  <font-awesome-icon @click="isDialogVisible = true" v-show="deleteIconVisible" class="icon" style="position: absolute; left: 95%" icon="window-close"></font-awesome-icon></v-card-subtitle>

        <v-card-text>{{commentContent}}</v-card-text>
    </v-card>

    <Modal :dialog="isDialogVisible" @modal-cancel="isDialogVisible = false" @modal-agree="deleteComment"></Modal>
</div>
</template>

<script>
    import Modal from "@/components/Modal"
    export default {
        name: "Comment",
        components:{Modal,},
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

                if(godziny < 2) this.wysiwetlDate = minutes + ' min temu';
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
        width: 80%;
    }
    .icon:hover{
        color: crimson;
        cursor: pointer;
    }

</style>
