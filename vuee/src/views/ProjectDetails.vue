<template>
    <body>
    <div class="project-details-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="project-details-content">

            <v-tabs @change="changeContent($event)" grow height="80">
                <v-tab style="font-size: 24px;">BACKLOG</v-tab>
                <v-tab style="font-size: 24px;">BOARD</v-tab>
                <v-tab style="font-size: 24px;">WORKFLOW</v-tab>
                <v-tab style="font-size: 24px;">TEAM</v-tab>
                <v-tab style="margin-left: 10%" @click="closeProjectDetails">
                    <font-awesome-icon icon="window-close" class="icon-nav"/>
                </v-tab>
            </v-tabs>

            <div v-show="backlogContentVisible" style="padding: 20px;">
                <div class="backlog-grid-wrapper">
                    <div class="grid-item-1">

                        <div class="grid-item-claim">Upcoming Events</div>
                        <div>ITEM SUBCLAIM</div>
                        <div style="width: 180px; height: 180px; align-self: center">
                            <img src="../assets/001-calendar.svg" style="width: 100%; height: 100%; opacity: 0.4; ">
                        </div>
                        <div class="grid-item-description">You have no upcoming events</div>
                    </div>
                    <div class="grid-item-2">
                        <div class="grid-item-claim">Daily Stats</div>
                        <div class="grid-item-subclaim">ITEM SUBCLAIM</div>
                        <div style="width: 180px; height: 180px; align-self: center">
                            <img src="../assets/001-bar-chart.svg" style="width: 100%; height: 100%; opacity: 0.4; ">
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
                            ></v-pagination>

                        </template>
                        <template v-if="actualShowing === 'Activity'">
                            <div style="height: 100%; width: 100%; display: flex; flex-direction: column; justify-content: center; align-items: center">
                                <div style="width: 220px; height: 220px; align-self: center; margin-bottom: 30px; ">
                                    <img src="../assets/002-warning.svg"
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
                            <template v-for="(taskChange, index) in showRecentChanges">
                                <recent-activity-item :key="taskChange.id"
                                                      :task-i-d="taskChange.taskID"
                                                      :change-description="taskChange.changeDescription"
                                                      :change-date="taskChange.changeDate"
                                                      :change-type="taskChange.changeType"
                                                      :task-key="taskChange.taskKEY"
                                                      :task-title="taskChange.taskName"
                                                      :picture-u-r-l="taskChange.pictureURL"></recent-activity-item>

                                <v-divider
                                        v-if="index < recentTaskChanges.length - 1"
                                        :key="index"
                                ></v-divider>
                            </template>

                        </v-card>
                        <v-pagination v-if="changesLength > 1"
                                      v-model="changesPage"
                                      :length="changesLength"
                                      @input="pageChanged($event)"
                                      color="rgba(235, 182, 193, 1)"
                                      circle
                        ></v-pagination>
                    </div>
                </div>
            </div>

            <div v-show="boardContentVisible">
                <TaskBoard
                        :tasks="tasks"
                        :pages="pages"
                        style="margin-top: 15px;"></TaskBoard>
            </div>

            <div v-show="releasesContentVisible">


                    <div style="height: 90vh; padding: 20px; display: flex; ">

                        <div style="align-self: center; width: 55%;">
                            <div style="font-weight: 400; font-size: 42px;">{{projectDetails.projectName}}</div>
                            <!--<img src="../assets/minion6.svg" style="width: 80%; height: auto; position: relative">-->
                            <!--<div style="font-style: italic; font-size: 20px; opacity: 0.8; font-weight: 600; position: relative; ">More complex statistics you can find at the bottom of this page.</div>-->
                        </div>

                            <div style="align-self: center; display: flex; flex-direction: column; width: 600px;">
                                <div style="font-size: 32px; font-weight: 800; align-self: center">Project Issues</div>
                                <div style="font-size: 22px; font-weight: 500; align-self: center; margin-bottom: 20px;">Currently we have 34 issues in this project</div>
                                <div style="align-self: center; display: flex; margin-bottom: 20px;">
                                    <div class="task-box">
                                        <v-chip>OPEN</v-chip>
                                        <font-awesome-icon icon="times-circle" color="#424242" style="opacity: 0.7" class="fa-3x"></font-awesome-icon>
                                        <span style="font-size: 22px; opacity: 0.8; font-weight: 800">17</span>

                                    </div>
                                    <div class="task-box">
                                        <v-chip>DONE</v-chip>
                                        <font-awesome-icon icon="check-circle" color="pink" style="opacity: 0.8;" class="fa-3x"></font-awesome-icon>
                                        <span style="font-size: 22px; opacity: 0.8; font-weight: 800">17</span>
                                    </div>
                                </div>
                                <div style="font-weight: 400; font-size: 21px; opacity: 0.8; text-align: center; align-self: center">Some random text which you are not even lookin at just for design purposes of this page to make it looks better.</div>
                            </div>








                    </div>



                <div class="issues-section">
                    <div class="issues-table">
                        <div style="font-size: 22px; font-weight: 600; ">Issues in project</div>
                        <div style="display: flex; align-items: center">

                            <div>
                                <v-checkbox label="Show only OPEN"></v-checkbox>
                            </div>

                        </div>
                        <v-simple-table>
                            <template>
                                <thead>
                                <tr>
                                    <th class="text-left">
                                        KEY
                                    </th>
                                    <th class="text-left">
                                        TYPE
                                    </th>
                                    <th class="text-left">
                                        STATUS
                                    </th>
                                    <th class="text-left">
                                        DATE
                                    </th>
                                    <th class="text-left">
                                        PRIORITY
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr style="cursor: pointer"
                                    v-for="issue in projectIssues"
                                    :key="issue.issueID"
                                    @click="issueItemClicked(issue.issueID)"
                                >
                                    <td>{{ issue.issueKEY }}</td>
                                    <td>{{ issue.issueType }}</td>
                                    <td>{{ issue.issueStatus }}</td>
                                    <td>{{ issue.issueDate }}</td>
                                    <td>{{ issue.issuePriority }}</td>
                                </tr>
                                </tbody>
                            </template>
                        </v-simple-table>
                    </div>
                    <div class="issues-description">
                        <template v-if="!issuePicked"
                                  style="display: flex; flex-direction: column; justify-content: space-between">
                            <div style="font-weight: 400; font-size: 32px; color: #424242;">
                                Issue Details
                            </div>
                            <div style="height: 210px; width: 210px; align-self: center">
                                <img src="../assets/minion3.svg"
                                     style="width: 100%; height: 100%; opacity: 0.6">
                            </div>
                            <div style="font-size: 20px; opacity: 0.8; align-self: center; margin-bottom: 10px;">No
                                data available
                            </div>
                            <div style="font-style: italic; font-size: 16px; color: black; opacity: 0.6; align-self: center">
                                Pick item form issue table to see its details
                            </div>
                        </template>

                        <template v-if="issuePicked">
                            <div style="font-size: 26px; opacity: 0.8; font-weight: 400;">ISSUE
                                <span style="color: blue; opacity: 0.8">{{currentIssue.issueKEY}}</span>
                            </div>

                            <div style="font-size: 22px; opacity: 0.8; font-weight: 400; margin-bottom: 10px;">Details
                            </div>
                            <div style="display: flex; justify-content: space-between; margin-bottom: 10px;">
                                <div style="margin-right: 15px; display: flex; flex-direction: column; align-items: center">
                                    <span style="font-weight: 600;">Type</span>
                                    <span>{{currentIssue.issueType}}</span>
                                </div>
                                <div style="margin-right: 15px; display: flex; flex-direction: column; align-items: center">
                                    <span style="font-weight: 600;">Status</span>
                                    <span>{{currentIssue.issueStatus}}</span>
                                </div>
                                <div style="margin-right: 15px; display: flex; flex-direction: column; align-items: center">
                                    <span style="font-weight: 600;">Date</span>
                                    <span>{{currentIssue.issueDate}}</span>
                                </div>
                                <div style="margin-right: 15px; display: flex; flex-direction: column; align-items: center">
                                    <span style="font-weight: 600;">Priority</span>
                                    <span>{{currentIssue.issuePriority}}</span>
                                </div>

                            </div>
                            <div style="font-size: 22px; opacity: 0.8; font-weight: 400; margin-bottom: 10px;">
                                Descripiton
                            </div>
                            <div style="margin-bottom: 10px;">{{currentIssue.issueDescription}}</div>
                            <div style="font-size: 22px; opacity: 0.8; font-weight: 400;">Reported by</div>
                            <div style="font-size: 18px; display: flex; align-items: center">
                                <v-avatar style="height: 50px; width: 50px; margin-right: 10px;">
                                    <img :src="currentIssue.pictureURL" style="object-fit: cover">
                                </v-avatar>
                                {{currentIssue.userFullName}} for task
                                <span style="color: blue; opacity: 0.8; margin-left: 10px; cursor: pointer">{{currentIssue.taskKEY}}</span>

                            </div>

                        </template>

                    </div>
                </div>
                <div style="height: 500px; background: #cacaca;">
                    <div style="width: 600px;">
                        <div>There are currently 98 tasks in project</div>
                        <div style="display: flex;  justify-content: space-between;">
                            <div class="task-box">
                                <span style="font-size: 22px; opacity: 0.8; font-weight: 800">17</span>
                                <span style="font-style: italic; font-weight: 400;">are</span>
                                <v-chip color="#EBB6C1">TO DO</v-chip>
                            </div>
                            <div class="task-box">
                                <span style="font-size: 22px; opacity: 0.8; font-weight: 800">17</span>
                                <span style="font-style: italic; font-weight: 400;">are</span>
                                <v-chip  color="#F08080">IN PROGRESS</v-chip>
                            </div>
                            <div class="task-box">
                                <span style="font-size: 22px; opacity: 0.8; font-weight: 800">17</span>
                                <span style="font-style: italic; font-weight: 400;">are</span>
                                <v-chip color="#DC143C">IN REVIEW</v-chip>
                            </div>
                            <div class="task-box">
                                <span style="font-size: 22px; opacity: 0.8; font-weight: 800">17</span>
                                <span style="font-style: italic; font-weight: 400;">are</span>
                                <v-chip color="#AC474C">DONE</v-chip>
                            </div>
                        </div>
                        <div>

                        </div>
                    </div>
                </div>

            </div>

            <div v-show="teamContentVisible">
                <div id="section1" style="background: #424242; height: 90vh;">

                </div>
                <div id="section2" style="display: flex; flex-wrap: wrap; height: 90vh;">
                    <div v-for="user in projectUsers" :key="user.id"
                         style="flex: 1 0 20%; margin-bottom: 20px;">
                        <UserCard
                                :nickname="user.firstName + ' ' + user.lastName"
                                :position="'Developer'"
                                :userid="user.id"
                                :profile-picture="user.pictureURL"
                                @navigate-to-profile="navigateTo($event)"></UserCard>
                    </div>
                </div>

            </div>
        </div>



    </div>



    </body>
</template>

<script>
    import TaskBoard from "../components/TaskBoard";
    import axios from 'axios';

    import SideNavigationBar from "@/components/SideNavigationBar";

    import RecentActivityItem from "@/components/RecentActivityItem";
    import Task from "@/components/Task";
    import UserCard from "@/components/UserCard";


    export default {
        name: "ProjectDetails",
        components: {UserCard, Task, RecentActivityItem, SideNavigationBar, TaskBoard},
        props: {
            projectID: {}
        },
        data() {
            return {
                actualShowing: 'Work',
                showing: ['Work', 'Activity'],

                sort: ['Lowest priority', 'Highest Priority', 'Longest Deadline', 'Shortest Deadling', 'Default'],
                actualSort: 'Default',

                page: 1,
                userTasksLength: 0,
                changesPage: 1,
                changesLength: 0,

                teamContentVisible: false,
                boardContentVisible: false,
                backlogContentVisible: true,
                releasesContentVisible: false,

                tasks: [],
                tabNumber: 0,
                projectDetails: [],
                projectUsers: [],
                recentTaskChanges: [],
                pages: 0,
                userTasks: [],
                projectIssues: [],
                currentIssue: [],
                issuePicked: false,

            }
        },
        methods: {
            fillData() {
                this.datacollection = {
                    labels: [this.getRandomInt(), this.getRandomInt()],
                    datasets: [
                        {
                            label: 'Data One',
                            backgroundColor: '#f87979',
                            data: [this.getRandomInt(), this.getRandomInt()]
                        }, {
                            label: 'Data One',
                            backgroundColor: '#f87979',
                            data: [this.getRandomInt(), this.getRandomInt()]
                        }
                    ]
                }
            },
            getRandomInt() {
                return Math.floor(Math.random() * (50 - 5 + 1)) + 5
            },

            issueItemClicked(value) {
                this.issuePicked = true;
                this.projectIssues.filter((issue) => {
                    if (issue.issueID === value) this.currentIssue = issue;
                })
            },
            paginate(array, pageSize, pageNumber) {
                return array.slice((pageNumber - 1) * pageSize, (pageNumber) * pageSize);
            },
            changeContent(event) {
                this.tabNumber = event;
                this.teamContentVisible = false;
                this.boardContentVisible = false;
                this.backlogContentVisible = false;
                this.releasesContentVisible = false;
                switch (this.tabNumber) {
                    case 0:
                        this.backlogContentVisible = true;
                        break;
                    case 1:
                        this.boardContentVisible = true;
                        break;
                    case 2:
                        this.releasesContentVisible = true;
                        break;
                    case 3:
                        this.teamContentVisible = true;
                        break;
                }

            },
            closeProjectDetails() {
                this.$router.push({name: 'projects'})
            },
            navigateTo(value) {
                this.$router.push({name: 'profileDetails', params: {userID: value}})
            },
        },
        computed: {
            showUsersTasks() {
                return this.paginate(this.userTasks, 3, this.page)
            },
            showRecentChanges() {
                return this.paginate(this.recentTaskChanges, 5, this.changesPage);
            }
        },
        created() {

            axios.get('http://localhost:8080/tasks/list-all-tasks-in-project/' + this.projectID).then(response => {
                this.tasks = response.data;
            });
            axios.get('http://localhost:8080/tasks/get-number-of-tasks-by-their-status-for-project/' + this.projectID).then(response => {
                this.pages = Math.ceil(response.data / 4)
            });

            axios.get('http://localhost:8080/projects/get-project-details/' + this.projectID).then(response => {
                this.projectDetails = response.data;
                console.log(this.projectDetails);
                this.projectUsers = response.data.projectUsers;
            })


            axios.get('http://localhost:8080/tasks/test/12/' + this.projectID).then(response => {
                this.userTasks = response.data;
                this.userTasksLength = Math.ceil(response.data.length / 3);
            });


            axios.get('http://localhost:8080/tasks/get-all-task-changes-for-project/' + this.projectID).then(response => {
                this.recentTaskChanges = response.data;
                this.changesLength = Math.ceil(response.data.length / 5);
            });

            //get all issues for project
            axios.get('http://localhost:8080/projects/get-all-issues-for-project/' + this.projectID).then(response => {
                this.projectIssues = response.data;
            })

        }
    }
</script>

<style scoped>
    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800&display=swap');

    body {
        font-family: 'Montserrat', sans-serif;
    }

    * {
        box-sizing: border-box;
    }

    .project-details-container {
        height: 100%;
    }

    .project-details-content {
        margin-left: 10%;
    }

    .icon-nav {
        font-size: 40px;

    }

    .icon-nav:hover {
        color: lightpink;
    }

    .divider {
        width: 100%;
        height: 1px;
        background: #424242;
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
        padding: 20px;

    }

    .grid-item-claim {
        font-size: 26px;
        opacity: 0.9;
    }

    .grid-item-subclaim {
        font-size: 20px;
        color: #424242;
        margin-bottom: 10px;
    }

    .grid-item-description {
        font-size: 18px;
        align-self: center;
        opacity: 0.5;
    }

    .grid-wrapper {
        width: 100%;
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;
        grid-template-rows: auto;
        grid-template-areas: "header1 content content" "header1 content content";
        grid-gap: 20px;

    }

    .grid-header1 {
        grid-area: header1;
        height: 300px;
        background: white;
        box-shadow: 0 0 10px black;

    }

    .grid-header2 {
        grid-area: header2;
        height: 300px;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;

    }

    .grid-header3 {
        grid-area: header3;
        height: 300px;
        background: white;
        box-shadow: 0 0 10px black;

    }

    .grid-content {
        grid-area: content;
        width: 100%;
        display: flex;
        justify-content: center;
        align-self: center;
        flex-wrap: wrap;

    }

    .issues-section {
        padding: 20px;
        height: 90vh;
        display: grid;
        grid-template-columns: 1.8fr 1fr;
        grid-template-rows: auto;
        grid-gap: 20px;
        grid-template-areas: "tbl dsc";
    }

    .issues-table {
        grid-area: tbl;
        box-shadow: 0 0 10px black;
        background: white;
        height: 600px;
        padding: 15px;
        display: flex;
        flex-direction: column;

    }

    .issues-description {
        grid-area: dsc;
        box-shadow: 0 0 10px black;
        background: white;
        height: 350px;
        padding: 15px;
        display: flex;
        flex-direction: column;

    }


    .-header {
        display: flex;
        flex-direction: column;
    }

    .task-box {
        width: 200px;
        height: 200px;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        padding: 10px;
        align-items: center;
        border-radius: 25px;
        margin-right: 15px;
    }


</style>
