<template>
    <div id="wrp">
        <div class="page-title">PROFILE</div>
        <div class="circle"></div>

        <div class="profile-box">

          <div class="data-wrapper">
          <div class="data">{{user.firstName}}</div>
          </div>

            <div class="data-wrapper">
                <div class="data">{{user.lastName}}</div>
            </div>

        </div>



        <side-navigation-bar/>

    </div>

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
            console.log(this.$route.params.userID);
            if (this.user.id === this.$route.params.userID) console.log('to jest twoj profil')
            else console.log('to jest kogos profil');
            axios.get('http://localhost:8080/users/get-user-personal-data/' + this.$route.params.userID).then(response => {
                this.user = response.data;
            })

        }
    }
</script>

<style scoped>
    html, body {
        background-color: #1A1A1D;

        height: 100%;
    }
    #wrp{
        padding: 0;
        left: 0;
        top: 0;
        position: absolute;
        width: 100%;
        height: 100%;
        background-color: #1A1A1D;



    }
    .page-title {
        position: absolute;
        left: 10%;
        top: 2%;
        font-size: 30px;
        letter-spacing: 2px;

    }

    .circle {
        position: absolute;
        top: 30%;
        left: 50%;
        transform: translate3d(-50%, -50%, 0);
        box-shadow: 0 0 10px 15px black;
        width: 350px;
        height: 350px;
        border-radius: 50%;
        background: lightpink;
        z-index: 998;
    }
    .profile-box{
        position: absolute;
        width: 500px;
        height: 600px;
        top: 65%;
        left: 50%;
        transform: translate3d(-50%, -50%, 0);
        background-color: #A29C9B;
    }
    .data-wrapper{
        position: relative;
        top: 50%;
        margin-bottom: 20px;
        height: 40px;
    }

    .data{
        background: white;
        font-size: 36px;
        color: black;
    }


</style>
