<template>
    <v-timeline-item :color="color" :small="smallDot">
        <v-card>

            <v-card-subtitle v-bind:style="{background: color, padding: '7px', opacity: '0.7'}">
                <div style="display: flex; ">
                    <font-awesome-icon :icon="currentIcon" style="color: white; margin-right: 10px; font-size: 18px;"></font-awesome-icon>
                    <span style="color: white;">{{title}}</span>
                    <span style="color: white; margin-left: auto">{{changeDate | moment("DD/MM/YYYY HH:mm")}}</span>
                </div>
                </v-card-subtitle>
            <v-card-text style="padding: 5px;">{{changeDescription}}</v-card-text>
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
                currentIcon: '',
                title: '',
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
                        this.color = "black";
                        this.smallDot = true;
                        this.currentIcon = 'pencil-alt';
                        this.title = "TASK CREATED";
                        break;
                    case "TASK_STATUS_CHANGED":
                        this.color = "rgba(225, 182, 193, 1)";
                        this.smallDot = true;
                        this.currentIcon = 'exchange-alt';
                        this.title = "TASK STATUS CHANGED";
                        break;
                    case "TASK_USERS":
                        this.color = "#AC474C";
                        this.smallDot = true;
                        this.currentIcon = 'users';
                        this.title = "TASK USER CHANGED";
                        break;
                    case "TASK_ISSUE":
                        this.color = "#DC143C";
                        this.smallDot = true;
                        this.currentIcon = 'times-circle';
                        this.title = "TASK ISSUE";
                        break;
                    case "TASK_DEADLINE":
                        this.color = "#424242";
                        this.smallDot = true;
                        this.currentIcon = 'calendar';
                        this.title = "TASK DEADLINE CHANGED";
                        break;
                    case "TASK_COMMENT":
                        this.color = "#F08080";
                        this.smallDot = true;
                        this.currentIcon = 'comments';
                        this.title = "NEW TASK COMMENT";
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
.menu{
    color: lightcoral;
}
</style>
