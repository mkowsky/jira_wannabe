<template>
    <body>
            <div v-if="valid">
                <ConfirmationSuccesfull></ConfirmationSuccesfull>
            </div>

            <div v-if="!valid">
                <TokenInvalid></TokenInvalid>
            </div>
    </body>
</template>

<script>

    import TokenInvalid from "@/components/TokenInvalid";
    import ConfirmationSuccesfull from "@/components/ConfirmationSuccesfull";

    export default {
        name: "TokenValidation",
        components: {ConfirmationSuccesfull, TokenInvalid,},
        data() {
            return {
            }
        },
        computed:{
            valid(){
                if(this.$route.params.isValid === 'true') return true;
                else return false;
            }
        },

    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";


    body {
        height: 100vh;
        font-family: 'Montserrat', sans-serif;
        width: 100%;

    }

</style>
