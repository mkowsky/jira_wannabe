<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div class="project-creation-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="project-creation-content">

            <div class="project-creation-box">
                <img src="../assets/creation.svg"
                     style="position:absolute; width: 50%; height: 50%; top: 50%; transform: translateY(-50%); left: -5%;">
                <p class="project-title">PROJECT CREATOR</p>
                <p class="project-info">In this project-creator
                    you can only choose your team members and set
                    title for your new project. To add tasks you should head to
                    task creation view.
                </p>

                <div style="background: white; width: 40%; height: 60%; position: absolute; left: 55%; top: 30%; border-radius: 35px; display: flex; flex-direction: column; padding: 20px;">
                    <div style="display: flex; align-items: center; justify-content: center; margin-bottom: 20px;">
                        <v-btn style="margin-right: 20px;" @click="modalVisible = true">Choose icon</v-btn>

                        <div style="border: 2px solid black; width: 150px; height: 150px; padding: 5px;">
                            <v-img
                                    style="object-fit: cover; "
                                    :src="currentSRC"
                                    class="image"
                            ></v-img>
                        </div>
                    </div>
                    <v-text-field solo label="Enter project title" v-model="projectTitle" style=""></v-text-field>

                    <v-autocomplete
                            solo
                            label="Search for user"
                            :items="users"
                            item-text="name"
                            item-value="id"
                            hide-selected
                            multiple
                            chips
                            small-chips
                            v-model="chosenPeople"
                            color="rgba(225, 182, 193)"
                            style=""
                    >
                        <template v-slot:selection="data">
                            <v-chip
                                    close
                                    @click:close="remove(data.item.id)"
                                    color="#bababa"
                            >
                                {{ data.item.name }}
                            </v-chip>
                        </template>
                    </v-autocomplete>

                    <v-btn @click="createNewProject()" style="">CREATE
                        PROJECT
                    </v-btn>
                </div>

            </div>


        </div>
        <Modal :dialog="modalVisible"
               :dialog-content="'Pick icon for you new project.'"
               :dialog-title="'Pick icon'"
               @modal-cancel="modalVisible=false"
               @modal-agree="pickIcon()">
            <template slot="body">
                <div style="display: flex; flex-direction: column; padding: 10px;">
                    <div style="display: flex; flex-direction: row; margin-bottom: 20px;"
                         v-for="(icon, index) in iconImages" :key="index">
                        <div style="width: 150px; height: 150px; margin-right: 20px; padding: 5px;"><img :id="index+'0'"
                                                                                                         :src="icon.src1"
                                                                                                         style="height: 100%; width: 100%; "
                                                                                                         @click="makeHover($event, icon.src1)"
                        >
                        </div>
                        <div style="width: 150px; height: 150px; margin-right: 20px; padding: 5px;"><img :id="index+'1'"
                                                                                                         :src="icon.src2"
                                                                                                         style="height: 100%; width: 100%; "
                                                                                                         @click="makeHover($event,icon.src2 )">
                        </div>
                        <div style="width: 150px; height: 150px; margin-right: 20px; padding: 5px;"><img :id="index+'2'"
                                                                                                         :src="icon.src3"
                                                                                                         style="height: 100%; width: 100%; "
                                                                                                         @click="makeHover($event, icon.src3)"
                        >
                        </div>
                    </div>


                </div>

            </template>
        </Modal>
    </div>
</template>

<script>
    import SideNavigationBar from "@/components/SideNavigationBar";
    import axios from "axios";
    import Modal from "@/components/Modal";

    export default {
        name: "ProjectCreation",
        components: {Modal, SideNavigationBar},
        data() {
            return {
                users: [],
                chosenPeople: [],
                projectTitle: '',
                modalVisible: false,
                currentHoverID: [],
                currentSRC: 'https://assets.stickpng.com/thumbs/5a4613ddd099a2ad03f9c994.png',
                iconImages: [
                    {
                        "src1": require(`../assets/undraw/1.svg`),
                        "src2": require(`../assets/undraw/2.svg`),
                        "src3": require(`../assets/undraw/3.svg`)
                    },
                    {
                        "src1": require(`../assets/undraw/4.svg`),
                        "src2": require(`../assets/undraw/5.svg`),
                        "src3": require(`../assets/undraw/6.svg`)
                    },
                    {
                        "src1": require(`../assets/undraw/7.svg`),
                        "src2": require(`../assets/undraw/8.svg`),
                        "src3": require(`../assets/undraw/9.svg`)
                    },
                    {
                        "src1": require(`../assets/undraw/10.svg`),
                        "src2": require(`../assets/undraw/11.svg`),
                        "src3": require(`../assets/undraw/12.svg`)
                    },
                    {
                        "src1": require(`../assets/undraw/13.svg`),
                        "src2": require(`../assets/undraw/14.svg`),
                        "src3": require(`../assets/undraw/15.svg`)
                    },
                    {
                        "src1": require(`../assets/undraw/16.svg`),
                        "src2": require(`../assets/undraw/17.svg`),
                        "src3": require(`../assets/undraw/18.svg`)
                    },
                    {
                        "src1": require(`../assets/undraw/19.svg`),
                        "src2": require(`../assets/undraw/20.svg`),
                        "src3": require(`../assets/undraw/21.svg`)
                    },
                    {
                        "src1": require(`../assets/undraw/22.svg`),
                        "src2": require(`../assets/undraw/23.svg`),
                        "src3": require(`../assets/undraw/24.svg`)
                    },
                ]
            }
        },
        methods: {

            pickIcon() {
                this.modalVisible = false;
                this.currentSRC = require(`../assets/undraw/` + this.currentHoverID[0].src + `.svg`);


            },
            makeHover(event, value) {
                let splited = value.split(".", 2);
                let sliced = splited[0].slice(-2);
                if (sliced[0] === "/") sliced = sliced.slice(-1);
                console.log(sliced);

                if (this.currentHoverID.length === 0) {
                    document.getElementById(event.target.id).parentElement.style.border = '2px solid black';
                    document.getElementById(event.target.id).parentElement.style.opacity = '0.5';
                    this.currentHoverID.push({ID: event.target.id, src: sliced});
                } else if (this.currentHoverID.length >= 1) {
                    if (event.target.id === this.currentHoverID[0].ID) {
                        this.currentHoverID.splice(0, 1)
                        document.getElementById(event.target.id).parentElement.style.border = 'none';
                        document.getElementById(event.target.id).parentElement.style.opacity = '1';
                    } else {
                        console.log('you have already picekd');
                    }
                }
            },
            createNewProject() {
                let user = JSON.parse(localStorage.getItem('user'));
                console.log(this.chosenPeople);
                console.log(this.projectTitle);
                axios.post('http://localhost:8080/projects/create-new-project', {
                    projectName: this.projectTitle,
                    usersID: this.chosenPeople,
                    projectManagerID: user.id,
                    projectIconID: this.currentHoverID[0].src,
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
                this.currentHoverID.splice(0, 1);
                this.currentSRC = 'https://assets.stickpng.com/thumbs/5a4613ddd099a2ad03f9c994.png';
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

    .project-info {
        color: black;
        position: absolute;
        max-width: 30%;
        word-spacing: 7px;
        left: 30px;
        bottom: 20px;
        font-weight: 300;

    }


</style>
