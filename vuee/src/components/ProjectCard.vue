<template>

    <v-card class="-project-card">


        <img :src="require(`../assets/undraw/` + projectIconID+ `.svg`)" class="-project-image">


        <v-card-subtitle class="-project-title">{{projectTitle}}</v-card-subtitle>

        <div class="-project-details-wrapper">
            <div class="box">
                <span class="box-upper">98</span>
                <span class="box-bottom">tasks</span>
            </div>
            <div class="box"><span class="box-upper">12</span><span
                    class="box-bottom">members</span></div>
            <div class="box"><span class="box-upper">32%</span><span
                    class="box-bottom">progress</span></div>
        </div>
    </v-card>

</template>

<script>

    export default {
        name: "ProjectCard",
        props: ['projectTitle', 'projectID', 'projectIconID'],
        methods: {
            cardClicked() {
                this.$emit('card-clicked', {id: this.projectID});
            }
        }
    }
</script>

<style scoped lang="scss">


    @import "../assets/css/main";

    .-project-card {
        border-radius: 30px;
        height: 320px;
        width: 260px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        transition: 0.4s;
        padding: 10px 0 0 0;
        font-family: 'Montserrat', sans-serif;
    }

    .-project-image {
        align-self: center;
        width: 80%;
        height: 130px;
    }

    .-project-title {
        color: black;
        align-self: center;
        font-size: 15px;
        font-weight: 700;

    }

    .-project-details-wrapper {
        background: $color-secondary-accent;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
        padding: 2px;
        height: 80px;
    }

    .box {
        height: 100%;
        max-width: 33.3%;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .box-bottom {
        font-size: 13px;
    }

    .box-upper {
        display: flex;
        flex-direction: column;
        align-self: center;
        font-size: 22px;
    }


    .project-card-title {
        font-weight: 300;
        letter-spacing: 2px;
    }
</style>
