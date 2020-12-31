<template>
    <body>
    <div class="mailsent-wrapper">
        <div class="mailsent-claim">JIRA-WANNABE</div>
        <div class="mailsent-subclaim">Dear Mateusz, check your email! </div>
        <div class="mailsent-img">
            <img src="../assets/images/undraw_Mail_sent_re_0ofv.svg" style="width: 100%; height: 100%">
        </div>
        <div class="mailsent-description">We just send you a verification link. That link will expire shortly, so be sure to click on it soon to get started! </div>
        <v-btn x-large :color="colorAccent" style="width: 200px;">LOG IN</v-btn>
    </div>

    </body>
</template>

<script>
    import colors from '@/assets/css/colors'
    export default {

        name: "MailSent",
        data(){
            return{
                colorAccent: colors.ACCENT,
            }
        }
    }
</script>

<style scoped>
    .mailsent-wrapper{
        height: 100vh;
        background: white;
        box-shadow: 0 0 10px black;
        display: flex;
        flex-direction: column;
        padding: 40px;
        align-items: center;
        justify-content: center;
    }
    .mailsent-claim{
        font-size: 50px;
        font-weight: 800;
        color: #6C63FF;
    }
    .mailsent-subclaim{
        font-size: 40px;
        opacity: 0.8;

    }

    .mailsent-img{
        width: 400px;
        height: 400px;
    }

    .mailsent-description{
        font-size: 34px;
        width: 600px;
        text-align: center;
        margin-bottom: 20px;
    }
</style>
