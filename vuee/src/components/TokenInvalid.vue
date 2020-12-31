<template>
    <body>
        <div class="invalid-wrapper">
            <div class="flx-wrp-clm">
                <div class="-inv-claim">JIRA-WANNABE</div>
                <div class="-inv-subclaim">Whoooops! Something went wrong! </div>
                <div class="-inv-dsc">It seems that your token expired.</div>
                <v-btn x-large style="width: 200px;" :color="colorAccent" @click="$router.push('/')">GO BACK</v-btn>
            </div>

            <div class="-inv-img">
                <img src="../assets/images/minon5.svg" style="width: 100%; height: 100%">
            </div>

        </div>
    </body>
</template>

<script>
    import colors from '@/assets/css/colors'
    export default {

        name: "TokenInvalid",
        data(){
            return{
                colorAccent: colors.ACCENT,
            }
        }
    }
</script>

<style scoped>
    .invalid-wrapper{
        height: 100vh;
        width: 100%;
        background: white;
        padding: 20px;
        justify-content: center;
        align-items: center;
        display: flex;

    }
    .flx-wrp-clm{
        display: flex;
        flex-direction: column;
        width: 700px;
        align-items: center;

    }
    .-inv-claim{
        font-size: 60px;
        font-weight: 800;
        color: #6C63FF;
    }
    .-inv-subclaim{
        font-size: 44px;
        opacity: 0.8;
        font-weight: 600;
    }
    .-inv-img{
        width: 700px;
        height: 700px;
    }
    .-inv-dsc{
        font-size: 38px;
        width: 600px;
        text-align: center;
        margin-bottom: 20px;
        opacity: 0.8;
    }

</style>
