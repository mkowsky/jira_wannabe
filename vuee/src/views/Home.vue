<template>
    <div id="container">
        <div>WELCOME HOME</div>
        <button @click="logout">LOGOUT</button>
    </div>
</template>

<script>
    export default {
        name: "Home",
        methods: {
            logout(){
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        },
    }
</script>

<style scoped>

#container{
    top: 0;
    left: 0;
    position: absolute;
    height: 100%;
    width: 100%;
    background: #BDBBB6;
}

</style>
