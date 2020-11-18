<template>

    <v-card outlined shaped class="content">
        <v-card-subtitle>{{commentUsername}} {{wysiwetlDate}}</v-card-subtitle>
        <v-card-text>{{commentContent}}</v-card-text>
        <v-btn @click="calculate">cl</v-btn>
    </v-card>
</template>

<script>
    export default {
        name: "Comment",
        props: {
            commentDate: {},
            commentContent: {
                type: String,
            },
            commentUsername: {
                type: String,
            }
        },
        data() {
            return {
                wysiwetlDate: String,
            }
        },
        methods: {
            calculate() {
                var time = Date.parse(this.commentDate)
                var timeNow = Date.now();
                var howLong = timeNow - time + 200;
                var date = new Date(howLong);
                console.log(howLong)
                var minutes = date.getMinutes();
                var godziny = date.getHours();

                if(minutes < 60) this.wysiwetlDate = minutes + ' min temu';
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

</style>
