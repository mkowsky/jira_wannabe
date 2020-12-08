<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div class="project-creation-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="project-creation-content">
      <project-card style="position: absolute; left: 50%;"></project-card>
            <!--<div class="project-creation-box">-->
                <!--<img src="../assets/creation.svg"-->
                     <!--style="position:absolute; width: 50%; height: 50%; top: 50%; transform: translateY(-50%); left: -5%;">-->
                <!--<v-text class="project-title">PROJECT CREATOR</v-text>-->
                <!--<v-text class="project-info">In this project-creator-->
                    <!--you can only choose your team members and set-->
                    <!--title for your new project. To add tasks you should head to-->
                    <!--task creation view.-->
                <!--</v-text>-->

                <!--<div style="background: white; width: 40%; height: 50%; position: absolute; left: 55%; top: 30%; border-radius: 35px;">-->
                    <!--<v-text-field solo label="Enter project title" v-model="projectTitle"  style="position: relative; top:20%; width: 70%; left: 50%; transform: translateX(-50%);"></v-text-field>-->
                    <!--<v-autocomplete-->
                            <!--solo-->
                            <!--label="Search for user"-->
                            <!--:items="users"-->
                            <!--item-text="name"-->
                            <!--item-value="id"-->
                            <!--hide-selected-->
                            <!--multiple-->
                            <!--chips-->
                            <!--small-chips-->
                            <!--v-model="chosenPeople"-->
                            <!--color="rgba(225, 182, 193)"-->
                            <!--style="position: relative; top: 25%; width: 70%; left: 50%; transform: translateX(-50%);"-->
                    <!--&gt;-->
                        <!--<template v-slot:selection="data">-->
                            <!--<v-chip-->
                                    <!--close-->
                                    <!--@click:close="remove(data.item.id)"-->
                                    <!--color="#bababa"-->
                            <!--&gt;-->
                                <!--{{ data.item.name }}-->
                            <!--</v-chip>-->
                        <!--</template>-->
                    <!--</v-autocomplete>-->

                    <!--<v-btn @click="createNewProject()" style="position: absolute; bottom: 20px; left: 50%; transform: translateX(-50%); border-radius: 25px;">CREATE-->
                        <!--PROJECT-->
                    <!--</v-btn>-->
                <!--</div>-->

            <!--</div>-->


        </div>
    </div>
</template>

<script>
    import SideNavigationBar from "@/components/SideNavigationBar";
    import axios from "axios";
    import ProjectCard from "@/components/ProjectCard";

    export default {
        name: "ProjectCreation",
        components: {ProjectCard, SideNavigationBar},
        data() {
            return {
                users: [],
                chosenPeople: [],
                projectTitle: '',
            }
        },
        methods: {
            createNewProject() {
                let user = JSON.parse(localStorage.getItem('user'));
                console.log(this.chosenPeople);
                console.log(this.projectTitle);
                axios.post('http://localhost:8080/projects/create-new-project', {
                    projectName: this.projectTitle,
                    usersID: this.chosenPeople,
                    projectManagerID: user.id,
                }).then(response => {
                    console.log(response.status);
                    this.clearData();
                })
            },
            getAllUsers() {
                let user = JSON.parse(localStorage.getItem('user'));
                axios.get('http://localhost:8080/users/get-names').then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        if (response.data[i].id === user.id) console.log('znalazlem');
                        else {
                            this.users.push({
                                id: response.data[i].id,
                                name: response.data[i].firstName + ' ' + response.data[i].lastName
                            })
                        }
                    }
                })
            },
            remove(value) {
                console.log(value)
                for (let i = 0; i < this.chosenPeople.length; i++) {
                    console.log(i);
                    if (this.chosenPeople[i].id === value) console.log('yes');
                }
            },
            clearData() {
                this.chosenPeople = [];
                this.projectTitle = '';
            }
        },
        created() {
            this.getAllUsers();
        }
    }
</script>

<style scoped>
    .project-creation-container {
        height: 100%;

    }

    .project-creation-content {
        height: 100%;
        margin-left: 10%;

        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .project-creation-box {
        position: relative;
        height: 80vh;
        max-height: 90vh;
        width: 80%;
        align-self: center;
        background: #424242;
        box-shadow: 0 0 15px black;
        overflow: hidden;


    }

    .project-creation-box:before {
        content: '';
        position: absolute;
        background: white;
        border-radius: 50%;
        /*width: 1400px;*/
        /*height: 1400px;*/
        width: 100%;
        height: 180%;
        transform: translate3d(-50%, -36%, 0);

    }

    .project-title {
        color: black;
        position: absolute;;
        left: 25%;
        top: 10%;
        transform: translateX(-50%);

        letter-spacing: 3px;
        font-weight: 200;
        font-size: 36px;

    }
    .project-info{
        color: black;
        position: absolute;
        max-width: 30%;
        word-spacing: 7px;
        left: 30px;
        bottom: 20px;
        font-weight: 300;

    }


</style>
