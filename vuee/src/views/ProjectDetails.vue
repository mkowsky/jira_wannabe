<template>
    <div class="project-details-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="project-details-content">

            <v-tabs @change="changeContent($event)" grow color="rgba(235, 182, 193)" height="80">
                <v-tab style="font-size: 24px;">BACKLOG</v-tab>
                <v-tab style="font-size: 24px;">BOARD</v-tab>
                <v-tab style="font-size: 24px;">RELEASES</v-tab>
                <v-tab style="font-size: 24px;">TEAM</v-tab>
                <v-tab style="margin-left: 10%" @click="closeProjectDetails">
                    <font-awesome-icon icon="window-close" class="icon-nav"/>
                </v-tab>
            </v-tabs>

            <div v-show="backlogContentVisible" style="display: flex; flex-direction: row; justify-content: flex-end; padding: 20px;">

                <div class="recent-changes-wrapper">
                    <div style="align-self: center; margin-bottom: 20px; font-size: 30px; font-weight: 300;">RECENT CHANGES IN PROJECT</div>
                    <v-card class="list-wrapper">

                        <template v-for="(taskChange, index) in recentTaskChanges">
                            <v-list-item :key="taskChange.id">

                                <v-list-item-icon>
                                    <v-icon
                                            color="pink"
                                    >
                                        mdi-star
                                    </v-icon>
                                </v-list-item-icon>

                                <v-list-item-content>
                                    <v-list-item-title><span style="color: blue; margin-right: 20px; font-weight: 300">{{taskChange.taskKEY}}</span>
                                        <span style="margin-right: 20px;">{{taskChange.taskName}}</span> {{taskChange.changeDescription}} </v-list-item-title>
                                    <v-list-item-subtitle>{{taskChange.changeDate | moment("HH:mm DD-MM-YYYY")}}</v-list-item-subtitle>


                                </v-list-item-content>

                                <v-list-item-avatar>
                                    <v-img src="https://upload.wikimedia.org/wikipedia/commons/3/3e/KeiraKnightleyByAndreaRaffin2011.jpg"></v-img>
                                </v-list-item-avatar>

                            </v-list-item>
                            <v-divider
                                    v-if="index < recentTaskChanges.length - 1"
                                    :key="index"
                            ></v-divider>
                        </template>

                    </v-card>
                </div>







            </div>


            <div v-show="boardContentVisible">
                <TaskBoard
                        :tasks="tasks"
                        style="height: 95vh;"></TaskBoard>
            </div>

            <div v-show="releasesContentVisible">
                <project-releases-content style="height: 90vh;"></project-releases-content>
            </div>

            <div v-show="teamContentVisible">

                <div style="display: flex; flex-direction: column;  justify-content: center; margin-top: 5%">

                    <div style="align-self: center; margin-bottom: 20px;">
                        <UserCard
                                :nickname="projectDetails.projectManager.firstName + ' ' + projectDetails.projectManager.lastName"
                                :position="'Project Manager'"
                                :userid="projectDetails.projectManager.id"
                                :profile-picture="projectDetails.projectManager.pictureURL"
                                @navigate-to-profile="navigateTo($event)"></UserCard>
                    </div>

                    <div class="divider" style="margin-bottom: 20px;"></div>

                    <div style="display: flex; flex-wrap: wrap; align-self: center">

                        <div v-for="user in projectUsers" :key="user.id" style="flex: 1 0 15%; margin-right: 30px;">
                            <UserCard
                                    :nickname="user.firstName+ ' ' + user.lastName"
                                    :position="'Developer'"
                                    :userid="user.id"
                                    :profile-picture="user.pictureURL"
                                    @navigate-to-profile="navigateTo($event)"></UserCard>
                        </div>
                    </div>
                </div>


            </div>
        </div>


    </div>
</template>

<script>
    import TaskBoard from "../components/TaskBoard";
    import axios from 'axios';
    import UserCard from "../components/UserCard";
    import SideNavigationBar from "@/components/SideNavigationBar";
    import ProjectReleasesContent from "@/components/ProjectReleasesContent";

    export default {
        name: "ProjectDetails",
        components: {ProjectReleasesContent, SideNavigationBar, UserCard, TaskBoard},
        props: {
            projectID: {
                type: Number,
            }
        },
        data() {
            return {
                teamContentVisible: false,
                boardContentVisible: false,
                backlogContentVisible: true,
                releasesContentVisible: false,

                tasks: [],
                tabNumber: 0,
                projectDetails: [],
                projectUsers: [],
                recentTaskChanges: [],
            }
        },
        methods: {
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
                // console.log(this.tabNumber);
                // //TODO: CZEMU TUTAJ MUZSE ZEROWAC A W BOARD DZIALA BEZ TEGO?
                // this.tabNumber = 0;
                // // this.$emit('close-project-details');
                // // console.log(this.tabNumber);
            },
            navigateTo(value) {
                this.$router.push({name: 'profileDetails', params: {userID: value}})
            },
        },
        created() {
            console.log('??');
            axios.get('http://localhost:8080/tasks/list-all-tasks-in-project/' + this.projectID).then(response => {
                this.tasks = response.data;
                console.log(this.tasks);
            })

            axios.get('http://localhost:8080/projects/get-project-details/' + this.projectID).then(response => {
                this.projectDetails = response.data;
                this.projectUsers = response.data.projectUsers;
            })

            axios.get('http://localhost:8080/tasks/get-all-task-changes-for-project/' + this.projectID).then(response => {
                this.recentTaskChanges = response.data;
                console.log(this.recentTaskChanges);
            });
        }
    }
</script>

<style scoped>
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
    .recent-changes-wrapper{
        display: flex;
        flex-direction: column;
        width: 55%;


    }
</style>
