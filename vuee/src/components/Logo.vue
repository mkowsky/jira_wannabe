<template>

    <div class="circle">
        <div class="logo-img-wrapper"><img src="../assets/logo.png" style="width: 100%; height: 100%"></div>
        <div class="circle-title">JIRA-WANNABE</div>
    </div>

</template>

<script>
    export default {
        name: "Logo",
        props: {
            fontSize: {
                type: Number,
            },
          },


    }
</script>

<style scoped>

    .circle {

        background-color: lightpink;
        height: 300px;
        width: 300px;
        border-radius: 50%;
        box-shadow: 0 0 15px 15px black;

    }

    .circle .circle-title {
        position: relative;
        top: 80%;
        color: white;
        font-size: 20px;
        font-weight: bolder;
        font-family: 'Courier New', Courier, monospace;
        letter-spacing: 1px;

    }

    .logo-img-wrapper {
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translate3d(-50%, -50%, 0);
        width: 70%;
        height: 70%;
    }


</style>
