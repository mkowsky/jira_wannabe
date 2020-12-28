<template>
    <body>
    <side-navigation-bar></side-navigation-bar>
    <div class="profile-content-wrapper">
            <div class="grid-display">
                <div class="grid-img">
                    <img :src="user.pictureURL" style="border-radius: 50%; height: 100%; width: 100%;">
                </div>
                <div class="grid-name">
                    <span style="font-size: 30px; font-weight: 500;">
                        {{user.firstName}}
                    </span>
                    <span style="font-size: 40px; font-weight: 800;">
                        {{user.lastName}}
                    </span>
                </div>
                <div class="grid-position">
                   <span style="background: purple; border-radius: 25px; padding: 10px 40px 10px 40px; font-size: 22px; opacity: 0.9;">WEBDEVELOPER</span>
                </div>
                <div class="grid-tmp4">
                    <span style="font-size: 36px; font-weight: 200">
                        We
                    </span>
                    <span style="font-size: 24px; font-weight: 300">
                        may encounter
                    </span>
                    <span  style="font-size: 24px;">
                        many defeats
                    </span>
                    <span  style="font-size: 24px; font-weight: 600">
                         but
                    </span>
                    <span  style="font-size: 24px; font-weight: 800">
                            we must not be defeated.
                    </span>

                </div>
                <div class="grid-tmp5">
                   <div style="text-transform: uppercase; font-size: 30px; font-weight: 800; background: purple; padding: 10px; border-radius: 25px;cursor: pointer">Message Me</div>
                </div>
                <div class="grid-description">
                    <span style="font-weight: 600; font-size: 26px; text-align: center">
                        David can develop any kind of software, he started learning to code at age of
                    10!
                    </span>

                </div>
                <div class="grid-stats">
                        <span style="font-size: 54px; ">10</span>
                        <span style="font-size: 31px; align-self: flex-end; font-weight: 800">ACTIVE TASKS</span>
                        <div style="font-size: 30px; font-weight: 300;">2 PROJECTS </div>
                </div>
                <div class="grid-tmp8"></div>
                <div class="grid-tmp9"></div>



            </div>
    </div>

    </body>


</template>

<script>
    import SideNavigationBar from "@/components/SideNavigationBar";
    import axios from "axios";

    export default {
        name: "Profile",
        components: {SideNavigationBar},
        data() {
            return {

                user: "",
            }
        },
        methods: {},
        created() {
            this.user = JSON.parse(localStorage.getItem('user'));
            if (this.$route.params.userID) {
                if (this.$route.params.userID === this.user.id) console.log('swoj')
                else {
                    console.log('czyis');
                    axios.get('http://localhost:8080/users/get-user-personal-data/' + this.$route.params.userID).then(response => {
                        this.user = response.data;
                        console.log(response.data);
                    })
                }
            } else {
                console.log('swoj');
                axios.get('http://localhost:8080/users/get-user-personal-data/' + this.user.id).then(response => {
                    this.user = response.data;
                    console.log(response.data);
                })
            }


        }
    }
</script>

<style scoped lang="scss">

    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800&display=swap');

    body {
        height: 100%;
        font-family: 'Montserrat', sans-serif;
    }

    .profile-content-wrapper {
        margin-left: 10%;
        height: 100%;
        width: 100%;

        display: flex;
        justify-content: center;




    }
    .grid-display{
        align-self: center;
        width: 60%;
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;
        grid-template-rows: auto;
        grid-gap: 20px;
        grid-template-areas: "tmp1 name img"
                            "tmp4 tmp5 description"
                            "stats stats description";
    }
    .grid-img{
        grid-area: img;
        background: white;
        box-shadow: 0 0 10px black;
        max-height: 300px;


    }
    .grid-name{
        grid-area: name;
        background: white;
        box-shadow: 0 0 10px black;
        height: 100px;
        align-self: end;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
    }
    .grid-position{
        grid-area: tmp1;
        background: white;
        box-shadow: 0 0 10px black;
        height: 100px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .grid-tmp4{
        grid-area: tmp4;
        background: white;
        box-shadow: 0 0 10px black;
        height: 200px;
        display: flex;
        flex-direction: column;
        padding: 5px;
    }
    .grid-tmp5{
        grid-area: tmp5;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        justify-content: center;
        align-items: center;

    }
    .grid-description{
        grid-area: description;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px;


    }
    .grid-stats{
        grid-area: stats;
        background: white;
        box-shadow: 0 0 10px black;
        height: 100px;
        display: flex;
        padding: 5px;
    }
    .grid-tmp8{
        grid-area: tmp8;
        background: white;
        box-shadow: 0 0 10px black;
        height: 100px;
    }
    .grid-tmp9{
        grid-area: tmp9;
        background: white;
        box-shadow: 0 0 10px black;
        height: 100px;
    }


</style>
