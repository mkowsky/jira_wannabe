<template>
    <body>
    <side-navigation-bar></side-navigation-bar>
    <div class="profile-content-wrapper">
        <div class="profile-info-box">


            <v-card style="display: flex; flex-direction: column; align-items: center;      background: #bababa">



                <v-img  src="https://tvline.com/wp-content/uploads/2019/04/lucifer-4x10.jpg?w=620" class="circle" >
                </v-img>

             <v-card-title style="font-size: 30px; font-weight: 100;">{{user.firstName}} {{user.lastName}}</v-card-title>
                <v-card-subtitle style="font-size: 24px; font-weight: 100;">email@jira-wannabe.com</v-card-subtitle>
                <v-card-subtitle>POZYCJA</v-card-subtitle>
                <v-card-subtitle>team</v-card-subtitle>

            <v-card-actions>CONTACT ME</v-card-actions>
            </v-card>
            <!--<div class="profile-background"-->
                 <!--style="width: 100%; height: 30%;display: flex; flex-direction: column; justify-content: flex-end; align-items: center">-->
                <!--<div style="border-radius: 50%; width: 200px; height: 200px; background: white; position: relative; top: 50%;"></div>-->
            <!--</div>-->
            <!--<div class="profile-content"-->
                 <!--style="width: 100%; height: 70%; display: flex; flex-direction: column; justify-content: center; ">-->
                <!--<div style="align-self: center; width: 100%; height: 80%; margin-top: 20%; display: flex; flex-direction: column; align-items: center">-->
                    <!--<p class="lol">{{user.firstName}} {{user.lastName}}</p>-->
                    <!--<p class="lol">adres@email.com</p>-->
                    <!--<p class="lol">POZYCJA</p>-->
                    <!--<p class="lol">TEAM</p>-->

                    <!--<div>-->
                        <!--<p class="lol">Wyslij waidomosc</p>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->


        </div>
    </div>
    </body>

    <!--<div class="page-title">PROFILE</div>-->
    <!--<div class="circle"></div>-->

    <!--<div class="profile-box">-->

    <!--<div class="data-wrapper">-->
    <!--<div class="data">{{user.firstName}}</div>-->
    <!--</div>-->

    <!--<div class="data-wrapper">-->
    <!--<div class="data">{{user.lastName}}</div>-->
    <!--</div>-->

    <!--</div>-->


    <!--<side-navigation-bar/>-->

    <!--</div>-->

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
                    })
                }
            } else console.log('swoj');


        }
    }
</script>

<style scoped>
    body {
        height: 100%;

    }

    .profile-content-wrapper {

        margin-left: 10%;
        height: 100%;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .profile-info-box {


        width: 30%;
        height: 60%;
        display: flex;
        flex-direction: column;

    }

    .lol {
        color: white;

    }


</style>
