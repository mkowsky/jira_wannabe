<template>
    <div id="wrp">
        <div class="page-title">PROFILE</div>
        <div>welcome form profile page</div>
        <side-navigation-bar/>

        <div class="profile-box"></div>
    </div>

</template>

<script>
    import SideNavigationBar from "@/components/SideNavigationBar";
    export default {
        name: "Profile",
        components: {SideNavigationBar}
    }
</script>

<style scoped>
    .page-title {
        position: absolute;
        left: 10%;
        top: 2%;
        font-size: 30px;
        letter-spacing: 2px;

    }
    .profile-box{
        position: absolute;
        top: 130px;
        left: 50%;
        transform: translateX(-50%);
        width: 800px;
        height: 800px;
        background: gray;
    }
</style>
