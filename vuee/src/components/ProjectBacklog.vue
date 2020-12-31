<template>
    <body>
    <div class="backlog-grid-wrapper">
        <div class="grid-item-1">

            <div class="grid-item-claim">Upcoming Events</div>
            <div>ITEM SUBCLAIM</div>
            <div style="width: 180px; height: 180px; align-self: center">
                <img src="../assets/images/001-calendar.svg" style="width: 100%; height: 100%; opacity: 0.4; ">
            </div>
            <div class="grid-item-description">You have no upcoming events</div>
        </div>
        <div class="grid-item-2">
            <div class="grid-item-claim">Daily Stats</div>
            <div class="grid-item-subclaim">ITEM SUBCLAIM</div>
            <div style="width: 180px; height: 180px; align-self: center">
                <img src="../assets/images/001-bar-chart.svg" style="width: 100%; height: 100%; opacity: 0.4; ">
            </div>
            <div class="grid-item-description">There is no data to display at this moment</div>
        </div>
        <div class="grid-item-3">
            <div class="grid-item-claim">Your Activity In This Project</div>
            <div style="display: flex; align-items: center">
                <p style="margin-right: 10px; font-size: 20px;">Showing:</p>
                <div style="width: 200px;">
                    <v-select solo label="Select" :items="showing" v-model="actualShowing"></v-select>
                </div>
            </div>
            <template v-if="actualShowing === 'Work'">
                <div style="display: flex; flex-direction: row; align-items: center">
                    <p style="margin-right: 10px; font-size: 20px;">Sort by:</p>
                    <div style="width: 200px;">
                        <v-select solo label="Select" :items="sort" v-model="actualSort"></v-select>
                    </div>
                </div>
                <Task v-for="task in showUsersTasks"
                      :key="task.taskID"
                      v-bind:task-name="task.taskName"
                      v-bind:project-name="task.projectName"
                      v-bind:task-user="task.userFullName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.userPictureURL"
                      @task-item-clicked="navigateToTask(task.taskID)"
                      style="margin-bottom: 10px; box-shadow: 0 0 10px black;"/>

                <v-pagination v-if="userTasksLength > 1"
                              v-model="page"
                              :length="userTasksLength"
                              @input="pageChanged($event)"
                              color="rgba(235, 182, 193, 1)"
                              circle
                              style="top: 90%"
                ></v-pagination>

            </template>
            <template v-if="actualShowing === 'Activity'">
                <div style="height: 100%; width: 100%; display: flex; flex-direction: column; justify-content: center; align-items: center">
                    <div style="width: 220px; height: 220px; align-self: center; margin-bottom: 30px; ">
                        <img src="../assets/images/002-warning.svg"
                             style="width: 100%; height: 100%; opacity: 0.4; ">
                    </div>
                    <div class="grid-item-subclaim" style="align-self: center">No recent activity was
                        found.
                    </div>
                </div>

            </template>

        </div>
        <div class="grid-item-5">
            <div class="grid-item-claim">RECENT CHANGES IN PROJECT</div>
            <v-card style="margin-bottom: 10px;">
                <template v-for="(taskChange, index) in recentProjectChanges">
                    <recent-activity-item :key="taskChange.id"
                                          :task-i-d="taskChange.taskID"
                                          :change-description="taskChange.changeDescription"
                                          :change-date="taskChange.changeDate"
                                          :change-type="taskChange.changeType"
                                          :task-key="taskChange.taskKEY"
                                          :task-title="taskChange.taskName"
                                          :picture-u-r-l="taskChange.pictureURL"></recent-activity-item>

                    <v-divider
                            v-if="index < recentProjectChanges.length - 1"
                            :key="index"
                    ></v-divider>
                </template>
            </v-card>
        </div>
    </div>
    </body>
</template>

<script>
    import Task from "./Task"
    import RecentActivityItem from "./RecentActivityItem";
    export default {
        name: "ProjectBacklog",
        components:{Task, RecentActivityItem},
        props: ['userTasks', 'recentProjectChanges'],
        data() {
            return {
                actualShowing: 'Work',
                showing: ['Work', 'Activity'],
                sort: ['Lowest priority', 'Highest Priority', 'Longest Deadline', 'Shortest Deadling', 'Default'],
                actualSort: 'Default',
                page: 1,
            }
        },
        methods:{
            paginate(array, pageSize, pageNumber) {
                return array.slice((pageNumber - 1) * pageSize, (pageNumber) * pageSize);
            },
        },
        computed:{
            showUsersTasks() {
                return this.paginate(this.userTasks, 3, this.page)

            },
            userTasksLength(){
                return Math.ceil(this.userTasks.length/3);
            }
        }
    }
</script>

<style scoped>


    body {
        font-family: 'Montserrat', sans-serif;
    }

    * {
        box-sizing: border-box;
    }


    .backlog-grid-wrapper {
        display: grid;
        height: 100%;
        grid-gap: 30px;
        grid-template-columns: 1fr 1fr 1fr;
        grid-template-rows: auto;
        grid-template-areas: "item3 item2 item1" "item3 item5 item5";
    }

    .grid-item-1 {
        grid-area: item1;
        background: white;
        box-shadow: 0 0 10px black;
        height: 350px;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        padding: 20px;
    }

    .grid-item-2 {
        grid-area: item2;
        background: white;
        box-shadow: 0 0 10px black;
        height: 350px;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        padding: 20px;
    }

    .grid-item-3 {
        padding: 20px;
        grid-area: item3;
        background: white;
        box-shadow: 0 0 10px black;
        height: 100%;
        display: flex;
        flex-direction: column;
    }

    .grid-item-5 {
        grid-area: item5;
        background: white;
        box-shadow: 0 0 10px black;
        height: 450px;
        padding: 20px;
        overflow-y: auto;
    }

    .grid-item-claim {
        font-size: 26px;
        opacity: 0.9;
    }

    .grid-item-subclaim {
        font-size: 20px;
        margin-bottom: 10px;
    }

    .grid-item-description {
        font-size: 18px;
        align-self: center;
        opacity: 0.5;
    }
</style>
