<template>
    <v-list-item>
        <v-list-item-icon>
            <font-awesome-icon :icon="icon"></font-awesome-icon>
        </v-list-item-icon>

        <v-list-item-content>
            <v-list-item-title style="display: flex; justify-content: space-between">
                <span style="color: blue; font-weight: 300; cursor: pointer;width: 120px;" @click="navigateToTask()">{{taskKey}}</span>
                <span style="width: 200px; text-align: center">{{taskTitle}}</span>
                <span style="width: 600px;">{{changeDescription}}</span>
                <span style="font-size: 14px; opacity: 0.7; width: 90px; text-align: end">{{checkDate}}</span>
            </v-list-item-title>
        </v-list-item-content>

        <v-list-item-avatar>
            <v-img :src="pictureURL"></v-img>
        </v-list-item-avatar>

    </v-list-item>

</template>

<script>
    export default {
        name: "RecentActivityItem",
        props: {
            taskID:{},
            changeType: {},
            taskKey: {},
            changeDescription: {},
            taskTitle: {},
            changeDate: {},
            pictureURL: {},
        },
        data() {
            return {
                icon: '',
                description: '',
            }
        },
        methods: {
            activityType() {
                switch (this.changeType) {
                    case 'TASK_CREATED':
                        this.icon = 'pencil-alt';
                        this.description = 'Task has been created.';
                        break;
                    case 'TASK_COMMENT':
                        this.icon = 'comments';
                        this.description = 'New comment has been added.';
                        break;
                    case 'TASK_USERS':
                        this.icon = 'users';
                        this.description = this.changeDescription;
                        break;
                    case "TASK_STATUS_CHANGED":
                        this.icon = 'exchange-alt';
                        this.description = this.changeDescription;
                        break;
                    case "TASK_ISSUE":
                        this.icon = 'times-circle';
                        this.description = 'New issue has been reported.';
                        break;
                    case "TASK_DEADLINE":
                        this.icon = 'calendar';
                        this.description = 'Deadline has been changed.';
                        break;
                }
            },
            navigateToTask(){
                console.log(this.taskID);
            }
        },
        created() {
            this.activityType()
        },
        computed:{
            checkDate() {
                var time = Date.parse(this.changeDate)
                var timeNow = Date.now();
                var howLong = timeNow - time;
                if(howLong > 86400000){
                    return this.$moment(this.changeDate).format("HH:mm DD-MM")
                } else return this.$moment(this.changeDate).format("HH:mm");
            },
        }
    }
</script>

<style scoped>

</style>
