<template>
    <body>
    <side-navigation-bar></side-navigation-bar>
    <div class="dashboard-content">

    </div>
    </body>
</template>

<script>
    import SideNavigationBar from "@/components/SideNavigationBar";




    export default {
        name: "AdminDashboard",
        components: {SideNavigationBar}
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";;



    body {
        height: 100%;
        font-family: 'Montserrat', sans-serif;
    }
    .dashboard-content{
        margin-left: 12%;
        width: 100%;
        height: 100vh;

    }
</style>
