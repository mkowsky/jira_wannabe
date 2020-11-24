<template>
    <v-timeline-item :color="color" :small="smallDot">
        <v-card>

            <v-card-subtitle>{{changeDate | moment("DD/MM/YYYY HH:mm")}}</v-card-subtitle>
            <v-card-text>{{changeDescription}}</v-card-text>
        </v-card>
    </v-timeline-item>


</template>

<script>
    export default {
        name: "StatusChangeItem",
        data() {
            return {
                color: 'pink',
                smallDot: false,
            }
        },
        props: {
            changeDate: {
                required: true,
            },
            changeDescription: {
                required: true,
            },
            changeType: {
                required: true,
            }
        },
        methods: {
            colorPick() {
                switch (this.changeType) {
                    case "TASK_CREATED":
                        this.color = "#424242";
                        break;
                    case "TASK_STATUS_CHANGED":
                        this.color = "rgba(225, 182, 193, 1)";
                        break;
                }
            }
        },
        created() {
            this.colorPick();
        }
    }
</script>

<style scoped>

</style>
