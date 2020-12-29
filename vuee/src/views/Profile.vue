<template>
    <body>
    <side-navigation-bar></side-navigation-bar>
            <div class="profile-container">
                    <div class="profile-wrapper">
                        <div class="profile-photo">
                            <img :src="user.pictureURL" style="border-radius: 50%;  width: 250px; height: 250px; object-fit: cover; align-self: center; margin-bottom: 50px;">

                                <div style="display: flex; flex-direction: column">
                                    <div class="profile-stats"><span class="profile-span">32</span>ACTIVE TASKS</div>
                                    <div class="profile-stats"><span class="profile-span">11</span>REPORTED ISSUES</div>
                                    <div class="profile-stats"><span class="profile-span">2</span>PROJECTS</div>
                            </div>
                        </div>
                        <div class="profile-data">
                            <div class="position">DEVELOPER</div>
                            <div class="full-name">{{user.fullName}}</div>
                            <div class="about-claim">ABOUT</div>
                            <div class="about-desc">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,</div>
                            <div class="details-info">
                                <div style="display: flex; flex-direction: column">
                                    <span class="subclaim">EMAIL</span>
                                    <div class="contact">pietrzykowski@jira-wannabe.com</div>
                                </div>
                                <div style="display: flex; flex-direction: column">
                                    <span class="subclaim">TEAM</span>
                                    <div class="contact">BACKEND-I13</div>
                                </div>
                            </div>
                            <v-btn v-if="!ownProfile" class="profile-btn" x-large>MESSAGE ME</v-btn>
                            <v-btn v-if="ownProfile" class="profile-btn" x-large>EDIT PROFILE</v-btn>
                        </div>


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
                ownProfile: true,
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
                        this.ownProfile = false;
                    })
                }
            } else {
                console.log('swoj');
                axios.get('http://localhost:8080/users/get-user-personal-data/' + this.user.id).then(response => {
                    this.user = response.data;
                    console.log(response.data);
                    this.ownProfile = true;
                })
            }


        }
    }
</script>

<style scoped lang="scss">
    $color-primary: white;
    $color-main-accent: #424242;
    $color-secondary-accent: #6C63FF;

    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800&display=swap');

    body {
        height: 100%;
        font-family: 'Montserrat', sans-serif;
    }

    .profile-container{

        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }
    .profile-wrapper{
        width: 1000px;
        height: 600px;
        background: white;
        align-self: center;
        display: flex;
        box-shadow: 0 0 20px black;
    }

    .profile-photo{
        /*background: #424242;*/
        width: 30%;
        display: flex;
        flex-direction: column;
        padding: 20px 0 0 20px;


    }

    .profile-data{
        /*background: #676767;*/
        width: 70%;
        display: flex;
        flex-direction: column;
        padding: 50px;
        border-left: 15px solid white;

    }

    .position{
        background: $color-secondary-accent;
        font-size: 28px;
        width: 200px;
        text-align: center;
        align-self: center;
        border-radius: 25px;
        margin-bottom: 20px;
    }


    .full-name{
        font-size: 42px;
        font-weight: 600;
        align-self: center;
        margin-bottom: 20px;
    }


    .about-claim{
        font-size: 28px;
        font-weight: 500;
        text-transform: uppercase;
        opacity: 0.8;
        margin-bottom: 20px;
    }
    .about-desc{
        font-size: 20px;
        font-style: italic;
        color: black;
        margin-bottom: 20px;
    }
    .details-info{
        display: flex;
        justify-content: space-between;

    }

    .subclaim{
        text-align: center;
        font-weight: 600;
        font-size: 24px;
    }

    .contact{
        font-size: 18px;
        font-weight: 500;
    }
    .profile-stats{
        width: 100%;
        margin-bottom: 10px;
        font-size: 22px;
        padding-left: 10px;
        border-left: 12px solid $color-secondary-accent;
    }
    .profile-span{
        margin-right: 20px;
        font-size: 30px;
        font-weight: 600;
    }

    .profile-btn{
        margin-top: 50px;
        width: 200px;
        align-self: center;
    }

</style>
