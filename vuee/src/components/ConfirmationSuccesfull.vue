<template>
    <body>

                <div class="-claim">JIRA-WANNABE</div>
                <div class="-subclaim">Confirmation was successful</div>

                    <img src="../assets/images/undraw_happy_birthday_s72n.svg" class="-img">

                <div class="-description">Congratulations, your email was verified,  you can  now log in with yours credintials.</div>
                <v-btn x-large style="width: 400px;" :color="colorAccent" @click="navigateTo('login')">LOG IN</v-btn>

    </body>
</template>

<script>
    import colors from '@/assets/css/colors'
    export default {
        name: "ConfirmationSuccesfull",
        data(){
            return {
                colorAccent: colors.ACCENT,
            }
        },
        methods:{
            navigateTo(value){
                this.$router.push('/'+value);
            }
        }
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";



    body {

        font-family: 'Montserrat', sans-serif;
        background: white;
        height: 100vh;
        width: 100%;
        display: flex;
        flex-direction: column;
        padding: 40px;
        align-items: center;
        justify-content: center;
    }

    .-claim{
        font-size: 50px;
        font-weight: 800;
        color: $color-secondary-accent
    }
    .-subclaim{
        font-size: 40px;
        opacity: 0.8;
    }
    .-description{
        font-size: 34px;
        width: 600px;
        text-align: center;
        margin-bottom: 20px;
    }
    .-img{
        width: 500px;
        height: 500px;
    }
</style>
