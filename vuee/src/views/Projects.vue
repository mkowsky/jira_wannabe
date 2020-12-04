<template>
    <div class="projects-container">
        <side-navigation-bar></side-navigation-bar>


        <div  style="height: 100%; display: flex; flex-direction: column;">
            <div  style="width: 100%; background: #424242; height: 55%;  position: relative;">

                <div style="left: 10%; top: 5%; display: flex;  position: relative; z-index: 25;">

                    <div>
                        <div class="project-title">Spacex project future exploration</div>
                        <div class="project-subtitle">Lorem Ipsum is simply dummy text of the printing and typesetting
                            industry.
                            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an
                            unknown
                            printer took a galley of type and scrambled it to make a type specimen book.
                        </div>
                    </div>

                    <v-btn style="align-self: center; margin-left: 1%;" rounded large @click="navigateToProjectDetails()">VIEW DETAILS</v-btn>
                </div>



                <svg style="position: absolute; bottom: 0px;" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
                    <path fill="white" fill-opacity="1"
                          d="M0,256L48,229.3C96,203,192,149,288,154.7C384,160,480,224,576,218.7C672,213,768,139,864,128C960,117,1056,171,1152,197.3C1248,224,1344,224,1392,224L1440,224L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path>
                </svg>
            </div>


            <div style="background: white; height: 45%; display: flex; justify-content: center">

                <div style="display: flex; align-self: center" ref="cards">

                    <div v-for="(project,index) in projects" :key="project.id" @click="cardClicked(index, project.id)" style="cursor: pointer">

                        <ProjectCard :id="index" class="gap" :project-i-d="project.id" active-class="active"
                                     :project-title="project.projectName"></ProjectCard>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
                currentPage: 0,
                currentCard: 2,
                przesunO: 0,
                disableNext: false,
                cards: [],
                active: '',
                previousCard: 0,
                currentProject: [],


            }
        },
        methods: {
            cardClicked(index, projectID) {



                document.getElementById(this.previousCard).style.opacity = '0.7';
                document.getElementById(this.previousCard).style.transform = 'scale(0.8)';

                document.getElementById(index).style.opacity = '1.0';
                document.getElementById(index).style.transform = 'scale(1.2)';
                this.currentProject = this.projects.find(project => project.id === projectID);
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

            listAllProjects() {
                axios.get('http://localhost:8080/projects/list-all').then(response => {
                    this.projects = response.data;

                })
            }
        },
        created() {
            axios.get('http://localhost:8080/projects/list-all').then(response => {
                this.projects = response.data;
                this.currentProject = this.projects[0];
            })

        },
        mounted() {

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


    .active {
        opacity: 1.0;

    }

    .inactvie {
        opacity: 0.7;

    }

    .gap {
        margin-right: 20px;
        transition: 0.4s;
    }

    * {
        box-sizing: border-box;

    }

    .projects-container {
        margin-left: 10%;
        height: 100%;
    }



    .project-title {
        position: relative;
        color: white;
        font-size: 50px;
        letter-spacing: 3px;
        max-width: 500px;
        transition: 1s;


    }

    .moveTitle{
        transition: 0.6s;
        transform: translateX(-1000%);
    }

    .project-subtitle {
        position: relative;
        margin-top: 20px;
        color: #e0e0e0;
        max-width: 500px;
        font-weight: 200;
        opacity: 0.8;
        font-size: 17px;
        word-spacing: 7px;

    }

    .card-active {

    }


</style>
