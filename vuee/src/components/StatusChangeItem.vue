<template>
    <v-timeline-item :color="color" :small="smallDot">
        <v-card style="max-width: 40%;" dark>

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
                        this.color = "green";
                        break;
                    case "TASK_STATUS_CHANGED":
                        this.color = "yellow";
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
