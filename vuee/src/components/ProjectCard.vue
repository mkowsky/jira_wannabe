<template>
    <v-card class="card">
        <div class="circle">
            <v-card-title class="project-card-title">{{projectTitle}}</v-card-title>
        </div>

        <v-card-subtitle>SUBTITLE</v-card-subtitle>
        <v-card-text>TEXT</v-card-text>
        <v-card-actions>ACTIONS</v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "ProjectCard",
        props: ['projectTitle'],
    }
</script>

<style scoped lang="scss">
    .card{
        border-radius: 30px;
        height: 400px;
        width: 300px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        transition: 0.4s;
        &:hover{
            cursor: pointer;

            transform: scale(1.1);
        }
    }
    .circle {
        display: flex;
        justify-content: center;
        background: rgba(225, 182, 193, 1);
        height: 150px;
        border-radius: 50%;
    }

    .project-card-title {
        font-weight: 300;
        letter-spacing: 2px;
    }
</style>
