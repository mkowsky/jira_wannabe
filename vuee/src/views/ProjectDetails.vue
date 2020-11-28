<template>
    <div class="project-details-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="project-details-content">

            <v-tabs @change="changeContent($event)" grow color="rgba(235, 182, 193)">
                <v-tab>GENERAL</v-tab>
                <v-tab>BOARD</v-tab>
                <v-tab>TEAM</v-tab>
                <v-tab style="margin-left: 10%" @click="closeProjectDetails">
                    <font-awesome-icon icon="window-close" class="icon-nav"/>
                </v-tab>
            </v-tabs>

            <div v-show="generalContentVisible">
                Genral contnet
            </div>

            <div v-show="boardContentVisible">
                <TaskBoard
                        :tasks="tasks"
                        style="height: 95vh;"></TaskBoard>
            </div>

            <div v-show="teamContentVisible">

                <div style="display: flex;  justify-content: center;height: 92vh;">
                    <div style="display: flex; flex-wrap: wrap; ">
                        <div v-for="user in projectUsers" :key="user.id" style="flex: 1 0 21%;">
                            <UserCard
                                    :nickname="user.firstName+ ' ' + user.lastName"
                                    :position="'Developer'"
                                    :userid="user.id"
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

    export default {
        name: "ProjectDetails",
        components: {SideNavigationBar, UserCard, TaskBoard},
        props: {
            projectID: {
                type: Number,
            }
        },
        data() {
            return {
                teamContentVisible: false,
                boardContentVisible: false,
                generalContentVisible: true,
                tasks: [],
                tabNumber: 0,
                projectUsers: [],
            }
        },
        methods: {
            changeContent(event) {
                this.tabNumber = event;
                this.teamContentVisible = false;
                this.boardContentVisible = false;
                this.generalContentVisible = false;
                switch (this.tabNumber) {
                    case 0:
                        this.generalContentVisible = true;
                        break;
                    case 1:
                        this.boardContentVisible = true;
                        break;
                    case 2:
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

            axios.get('http://localhost:8080/projects/list-all-project-users/' + this.projectID).then(response => {
                this.projectUsers = response.data;
            })
        }
    }
</script>

<style scoped>
    *{
        box-sizing: border-box;
    }
    .project-details-container{
        height: 100%;
    }

    .project-details-content{
        margin-left: 10%;
    }
    .icon-nav {
        font-size: 40px;

    }

    .icon-nav:hover {
        color: lightpink;
    }
</style>
