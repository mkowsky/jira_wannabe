<template>

    <v-card class="card">


        <div class="img-wrapper" style=" align-self: center; width: 80%; height: 130px; ">
            <img src="../assets/creation.svg" style="height: 100%; width: 100%;">
        </div>

        <v-card-subtitle style="align-self: center; font-size: 15px;">{{projectTitle}}</v-card-subtitle>

        <v-card-actions style="background: rgba(225, 182, 193, 1); display: flex; flex-direction: row; justify-content: space-evenly; padding: 2px; height: 80px;">
            <div class="box"><span style="display: flex; flex-direction: column; align-self: center; font-size: 22px;">98</span><span style="font-size: 13px;">tasks</span></div>
            <div class="box"><span style="display: flex; flex-direction: column; align-self: center; font-size: 22px;">12</span><span style="font-size: 13px;">members</span></div>
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
        height: 320px;
        width: 240px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        transition: 0.4s;
        padding: 10px 0 0 0;
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
