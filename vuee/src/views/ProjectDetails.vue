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
                <ProjectBacklog :user-tasks="userTasks" :recent-project-changes="recentTaskChanges"></ProjectBacklog>
            </div>

            <div v-show="boardContentVisible">
                <TaskBoard
                        :tasks="tasks"
                        :pages="pages"
                        style="margin-top: 15px;"></TaskBoard>
            </div>

            <div v-show="workflowContentVisible">


                <div style="height: 90vh; padding: 20px; display: flex; justify-content: center ">

                    <div style="align-self: center; display: flex; flex-direction: column; width: 600px;">
                        <div style="font-size: 42px; font-weight: 700; align-self: center">Project Issues</div>
                        <div style="font-size: 26px; font-weight: 500; align-self: center; margin-bottom: 20px;">
                            Currently we have {{projectIssues.length}} issues in this project
                        </div>
                        <div style="align-self: center; display: flex; margin-bottom: 20px;">
                            <div class="issue-box">
                                <div style="opacity: 0.7; font-size: 30px;">OPEN</div>
                                <font-awesome-icon icon="times-circle" color="#424242" style="opacity: 0.7"
                                                   class="fa-4x"></font-awesome-icon>
                                <span style="font-size: 60px; opacity: 0.8; font-weight: 800">{{openIssues}}</span>
                                <span style="font-style: italic; opacity: 0.7; color: black; font-size: 15px; text-align: center">Open issues are issues that are currently still worked on</span>

                            </div>
                            <div class="issue-box">
                                <div style="opacity: 0.7; font-size: 30px;">DONE</div>
                                <font-awesome-icon icon="check-circle" color="#FFB6C1" style="opacity: 0.8;"
                                                   class="fa-4x"></font-awesome-icon>
                                <span style="font-size: 60px; opacity: 0.8; font-weight: 800">{{closedIssues}}</span>
                                <span style="font-style: italic; opacity: 0.7; color: black; font-size: 15px; text-align: center">Done issues are issues that have been overcame.</span>
                            </div>
                        </div>
                        <div style="font-weight: 400; font-size: 21px; opacity: 0.8; text-align: center; align-self: center">
                            Some random text which you are not even lookin at just for design purposes of this page to
                            make it looks better.
                        </div>
                    </div>
                </div>


                <div class="issues-section">
                    <div class="issues-table">
                        <div style="font-size: 22px; font-weight: 600; ">Issues in project</div>
                        <div style="display: flex; align-items: center">

                            <div>
                                <v-checkbox label="Show only OPEN" v-model="onlyOpen"
                                            @change="showOnlyOpen()"></v-checkbox>
                            </div>

                        </div>
                        <v-data-table
                                :headers="headers"
                                :items="showFilteredIssues"
                                class="elevation-1"
                                @click:row="handleClick"

                        ></v-data-table>
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
                                <span style="color: blue; opacity: 0.8; margin-left: 10px; cursor: pointer"
                                      @click="navigateToTask(currentIssue.taskKEY)">{{currentIssue.taskKEY}}</span>

                            </div>

                        </template>

                    </div>
                </div>
                <div style="height: 90vh; display: flex; justify-content: center">

                        <div style="display: flex; flex-direction: column; width: 30%;">
                            <div class="sample" @click="changeText(1)"><span id="1" class="span-inactive">01.</span>Slash Commands</div>
                            <div class="sample" @click="changeText(2)"><span id="2" class="span-inactive">02.</span>Real-time Chat</div>
                            <div class="sample" @click="changeText(3)"><span id="3" class="span-inactive">03.</span>Task Tray</div>
                            <div class="sample" @click="changeText(4)"><span id="4" class="span-inactive">04.</span>Assign Comments</div>
                            <div class="sample" @click="changeText(5)"><span id="5" class="span-inactive">05.</span>Inbox</div>
                        </div>

                    <div style=" height: 500px; box-shadow: 0 0 10px black; width: 50%;">

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


    import UserCard from "@/components/UserCard";
    import ProjectBacklog from "@/components/ProjectBacklog";


    export default {
        name: "ProjectDetails",
        components: {ProjectBacklog, UserCard, SideNavigationBar, TaskBoard},
        props: {
            projectID: {}
        },
        data() {
            return {



                teamContentVisible: false,
                boardContentVisible: false,
                backlogContentVisible: true,
                workflowContentVisible: false,

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

                openIssues: 0,
                closedIssues: 0,
                onlyOpen: false,

                headers: [
                    {text: 'Key', value: 'issueKEY'},
                    {text: 'Type', value: 'issueType'},
                    {text: 'Status', value: 'issueStatus'},
                    {text: 'Date', value: 'issueDate'},
                    {text: 'Priority', value: 'issuePriority'},

                ],

            }
        },
        methods: {
            changeText(value){

                for(let i = 1; i < 6; i++){
                    document.getElementById(i).classList.remove('span-active');
                    document.getElementById(i).parentElement.classList.remove('sample-active');
                    document.getElementById(i).parentElement.classList.add('sample');
                }
                document.getElementById(value).classList.remove('span-inactive');
                document.getElementById(value).classList.add('span-active');

                document.getElementById(value).parentElement.classList.remove('sample');
                document.getElementById(value).parentElement.classList.add('sample-active');

            },
            handleClick(row) {
                this.issuePicked = true;
                this.projectIssues.filter((issue) => {
                    if (issue.issueID === row.issueID) this.currentIssue = issue;
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
                this.workflowContentVisible = false;
                switch (this.tabNumber) {
                    case 0:
                        this.backlogContentVisible = true;
                        break;
                    case 1:
                        this.boardContentVisible = true;
                        break;
                    case 2:
                        this.workflowContentVisible = true;
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
            countIssues() {
                for (let i = 0; i < this.projectIssues.length; i++) {
                    if (this.projectIssues[i].issueStatus.includes("OPEN")) {
                        this.openIssues++;
                    } else if (this.projectIssues[i].issueStatus.includes("CLOSED")) {
                        this.closedIssues++;
                    }
                }
            },
            navigateToTask(taskKEY) {
                axios.get('http://localhost:8080/tasks/get-task-id-with-task-key/' + taskKEY).then(response => {
                    this.$router.push({name: 'taskDetails', params: {taskID: response.data}})
                })
            }
        },
        computed: {
            showFilteredIssues() {
                if (this.onlyOpen) return this.projectIssues.filter((issue => {
                    return issue.issueStatus.includes("OPEN");
                }));
                else return this.projectIssues;
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
                this.countIssues();
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

    .backlog-grid-wrapper {
        display: grid;
        height: 100%;
        grid-gap: 30px;
        grid-template-columns: 1fr 1fr 1fr;
        grid-template-rows: auto;
        grid-template-areas: "item3 item2 item1" "item3 item5 item5";
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
        overflow-y: auto;

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

    .issue-box {
        width: 250px;
        height: 320px;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        padding: 20px;
        align-items: center;
        border-radius: 25px;
        margin-right: 15px;
    }

    .sample{
        width: 450px;
        padding: 15px 0 15px 20px;
        font-size: 32px;
        font-weight: 800;
        color: #424242;
        opacity: 0.4;
        cursor: pointer;

    }
    .sample:hover{
        opacity: 0.8;
    }
    .sample-active{
        width: 450px;
        padding: 15px 0 15px 20px;
        font-size: 32px;
        font-weight: 800;
        color: #424242;
        border-left: 10px solid purple;
        opacity: 1;
        cursor: pointer;
    }
    .span-inactive{
        margin-right: 20px;
    }
    .span-active{
        color: purple;
        margin-right: 20px;
    }

</style>
