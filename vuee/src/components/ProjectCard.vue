<template>

    <v-card class="card">

        <div class="circle">
            <v-card-title class="project-card-title">PROJECT TITLE</v-card-title>
        </div>

        <v-card-subtitle>SUBTITLE</v-card-subtitle>
        <v-card-text>{{projectTitle}}</v-card-text>
        <v-card-actions style="background: rgba(225, 182, 193, 1); display: flex; flex-direction: row; justify-content: space-evenly; padding: 2px; height: 80px;">
            <div class="box"><span style="display: flex; flex-direction: column; align-self: center; font-size: 22px;">98</span><span style="font-size: 13px;">tasks</span></div>
            <div class="box"><span style="display: flex; flex-direction: column; align-self: center; font-size: 22px;">12</span><span style="font-size: 13px;">team members</span></div>
            <div class="box"><span style="display: flex; flex-direction: column; align-self: center; font-size: 22px;">32%</span><span style="font-size: 13px;">progress</span></div>
        </v-card-actions>
    </v-card>

</template>

<script>

    export default {
        name: "ProjectCard",
        props: ['projectTitle', 'projectID'],
        methods:{
            cardClicked(){
                this.$emit('card-clicked', {id: this.projectID});
            }
        }
    }
</script>

<style scoped lang="scss">

    .box{

        height: 100%;
        max-width: 33.3%;

        display: flex;
        flex-direction: column;
        justify-content: center;
    }


    .card {
        border-radius: 30px;
        height: 300px;
        width: 300px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        transition: 0.4s;
    }

    .circle {
        display: flex;
        justify-content: center;
        background: rgba(225, 182, 193, 1);
        height: 120px;
        border-radius: 50%;
    }

    .project-card-title {
        font-weight: 300;
        letter-spacing: 2px;
    }
</style>
