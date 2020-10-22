<template>
    <div id="content">
        <div id="denied">ACCES DENIED</div>
        <button id="powrot" v-on:action-button-clicked="backHome($event)">BACK</button>
    </div>
</template>

<script>

    export default {
        components: {},
        data() {
            return {
                info: null,
                name: '',


            }
        },
        methods: {
            backHome(){
                this.$router.push('/home');
            }
        }
    }
</script>

<style>
    #content {
        position: absolute;
        background: #1A1A1D;
        z-index: 300;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }
    #powrot{
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translateX(-50%);
    }

    #denied {
        position: absolute;
        top: 30%;
        left: 50%;
        transform: translateX(-50%);
        font-family: 'Courier New', Courier, monospace;
        font-size: 100px;
        color: crimson;
    }
</style>
