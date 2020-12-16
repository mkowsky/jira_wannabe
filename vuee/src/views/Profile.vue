<template>
    <body>
    <side-navigation-bar></side-navigation-bar>
    <div class="profile-content-wrapper">

        <v-card class="profile-info-box">

            <div style="width: 100%; height:35%; display: flex; justify-content: center; padding: 10px;">
                <div style="width: 40%; height: 100%;"><img :src="user.pictureURL"
                                                            style="border-radius: 50%; width: 100%; height: 100%; object-fit: cover; box-shadow: 0 0 0 5px black;">
                </div>

            </div>

            <div style="height: 50%; display: flex; flex-direction: column;">
                <div class="fullname">{{user.firstName}} {{user.lastName}}</div>
                <div class="country">Los Angeles, CA</div>
                <div class="position">Webdeveloper</div>
                <div class="description">David can develop any kind of software, he started learning to code at age of
                    10!
                </div>


            </div>

            <v-card-actions class="actions">
                <div class="action action--one">ACTION1</div>
                <div class="action action--two">ACTION2</div>
                <div class="action action--three">ACITON3</div>
            </v-card-actions>
        </v-card>


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

    @import url("https://fonts.googleapis.com/css?family=Roboto:400,400i,700");


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
        width: 20%;
        height: 55%;
        display: flex;
        flex-direction: column;
        align-items: center;
        font-family: Calibri;

    }

    .fullname {
        font-size: 32px;
        font-weight: 300;
        align-self: center
    }

    .country {
        font-size: 20px;
        font-weight: 200;
        align-self: center;
        margin-bottom: 15px;
    }

    .position {
        letter-spacing: 2px;
        border-radius: 25px;
        background: rgba(225, 182, 193, 1);
        text-align: center;
        max-width: 80%;
        align-self: center;
        padding: 10px;
        border: 1px solid black;
        margin-bottom: 15px;
        transition: 400ms;

        &:hover {
            cursor: pointer;
            transform: scale(1.1);
            box-shadow: 0 0 20px lightpink;
        }
    }

    .description {
        font-weight: 300;
        font-size: 20px;
        max-width: 75%;
        align-self: center;
        text-align: center;
        font-style: italic
    }

    .actions {
        background: rgba(225, 182, 193, 1);
        width: 100%;
        align-self: flex-end;
        height: 15%;
        display: flex;
        justify-content: space-around;
    }

    .action {
        border-radius: 25px;
        padding: 10px;
        transition: 400ms;
        letter-spacing: 1px;

        &:hover {
            cursor: pointer;
            transform: scale(1.1);

        }

        &--one {


            &:hover {
                background: white;
                box-shadow: 0 0 20px white;
            }
        }

        &--two {

            &:hover {
                background: #bababa;

                box-shadow: 0 0 20px #bababa;
            }
        }

        &--three {


            &:hover {
                background: #424242;
                box-shadow: 0 0 20px #424242;
            }
        }

    }


</style>
