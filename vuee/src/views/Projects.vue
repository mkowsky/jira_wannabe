<template>
    <body>
    <side-navigation-bar></side-navigation-bar>
    <div class="projects-container">

        <div class="upper-part">
            <div class="upper-part-flexbox">

                <div class="project-info-wrapper">
                    <div class="projects-title"></div>
                    <div class="project-subtitle">Lorem Ipsum is simply dummy text of the printing and typesetting
                        industry.
                        Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an
                        unknown
                        printer took a galley of type and scrambled it to make a type specimen book.
                    </div>
                    <v-btn rounded large
                           @click="navigateToProjectDetails()" style="width: 200px;">VIEW DETAILS
                    </v-btn>

                </div>

                <img src="../assets/undraw/20.svg" style="width: 400px; height: 400px;">

            </div>


            <svg class="waves" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
                <path fill="white" fill-opacity="1"
                      d="M0,256L48,229.3C96,203,192,149,288,154.7C384,160,480,224,576,218.7C672,213,768,139,864,128C960,117,1056,171,1152,197.3C1248,224,1344,224,1392,224L1440,224L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path>
            </svg>
        </div>


        <div class="lower-part">

            <div class="lower-part-flexbox">

                <div v-for="(project,index) in projects"
                     :key="project.id"
                     @click="cardClicked(index, project.id)"
                     style="cursor: pointer">

                    <ProjectCard :id="index"
                                 class="gap"
                                 active-class="active"
                                 :project-i-d="project.id"
                                 :project-title="project.projectName"
                                 :project-icon-i-d="project.projectIconID"></ProjectCard>
                </div>
            </div>
        </div>
    </div>

    </body>
</template>

<script>

    import SideNavigationBar from "@/components/SideNavigationBar";
    import axios from 'axios';
    import ProjectCard from "@/components/ProjectCard";



    export default {
        name: "Projects",
        components: {ProjectCard, SideNavigationBar},
        data() {
            return {
                projects: [],
                disableNext: false,
                previousCard: 0,
                currentProject: [],
                isPM: false,

            }
        },
        methods: {
            cardClicked(index, projectID) {
                document.getElementById(this.previousCard).style.opacity = '0.7';
                document.getElementById(this.previousCard).style.transform = 'scale(0.8)';
                document.getElementById(index).style.opacity = '1.0';
                document.getElementById(index).style.transform = 'scale(1.2)';
                this.currentProject = this.projects.find(project => project.id === projectID);
                document.querySelector('.projects-title').innerHTML = this.currentProject.projectName;
                this.previousCard = index;

            },
            next() {
                document.getElementById(this.currentCard).classList.remove('active');
                document.getElementById(this.currentCard).classList.add('inactive');
                this.currentCard++;
                if (this.currentCard >= 3) this.disableNext = true;
            },

            navigateToProjectDetails() {
                this.$router.push({name: 'projectDetails', params: {projectID: this.currentProject.id}})
            },
        },
        created() {
            let user = JSON.parse(localStorage.getItem('user'));
            axios.get('http://localhost:8080/projects/get-all-user-projects/' + user.id).then(response => {
                this.projects = response.data;
                this.currentProject = this.projects[0];
                document.querySelector('.projects-title').innerHTML = this.currentProject.projectName;
            })

        },
        updated() {
            document.getElementById('1').style.opacity = '0.7';
            document.getElementById('2').style.opacity = '0.7';
            document.getElementById('1').style.transform = 'scale(0.8)';
            document.getElementById('2').style.transform = 'scale(0.8)';

            document.getElementById('0').style.opacity = '1.0';
            document.getElementById('0').style.transform = 'scale(1.2)';


        }
    }
</script>

<style lang="scss">
    @import "../assets/css/main";



    body {
        height: 100vh;
        font-family: 'Montserrat', sans-serif;
        box-sizing: border-box;
    }


    .projects-container {
        margin-left: 12%;
        height: 100%;
        display: flex;
        flex-direction: column;
    }

    .upper-part {
        width: 100%;
        background: $color-primary-dark;
        height: 55%;
        position: relative;
    }

    .upper-part-flexbox {
        margin-left: 200px;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }


    .lower-part {
        background: $color-primary-white;
        height: 45%;
        display: flex;
        justify-content: center
    }

    .lower-part-flexbox {
        display: flex;
        align-self: center;
    }

    .project-info-wrapper {
        display: flex;
        flex-direction: column;
        z-index: 25;
    }

    .waves {
        position: absolute;
        bottom: 0px;
    }



    .gap {
        margin-right: 20px;
    }


    .projects-title {
        color: $color-primary-white;
        font-size: 48px;
        font-weight: 600;
        max-width: 500px;
    }


    .project-subtitle {
        color: $color-primary-white;
        max-width: 600px;
        font-weight: 400;
        opacity: 0.8;
        font-size: 18px;
        word-spacing: 7px;
        margin-bottom: 10px;
    }



</style>
