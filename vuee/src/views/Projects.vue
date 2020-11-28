<template>
    <div class="projects-container">
        <side-navigation-bar></side-navigation-bar>
        <div class="projects-content">

            <div class="flex-wrapper">
                <div style="align-self: center">TYTUL</div>
                <div class="flex-box">
                    <div v-for="project in projects" :key="project.id" @click="projectClicked(project.id)"
                         style="align-self: center">
                        <project-card
                                :project-title="project.projectName"
                        ></project-card>
                    </div>

                </div>

            </div>

        </div>
    </div>
</template>

<script>

    import SideNavigationBar from "@/components/SideNavigationBar";
    import ProjectCard from "@/components/ProjectCard";
    import axios from 'axios';


    export default {
        name: "Projects",
        components: {ProjectCard, SideNavigationBar},
        data() {
            return {
                projects: [],


            }
        },
        methods: {
            projectClicked(value) {
                this.$router.push({name: 'projectDetails', params: {projectID: value}})

            },
        },
        created() {
            axios.get('http://localhost:8080/projects/list-all').then(response => {
                this.projects = response.data;
                console.log(this.projects);
            })
        }
    }
</script>

<style>

    * {
        box-sizing: border-box;
    }

    .projects-container {
        height: 100%;
    }

    .projects-content {
        margin-left: 10%;
        height: 100%;


    }

    .flex-wrapper {
        display: flex;
        flex-direction: column;
        justify-content: center;
        height: 100%;
    }

    .flex-box {

        align-self: center;
        display: flex;
        justify-content: space-evenly;
        height: 70%;
        width: 80%;

    }


</style>
