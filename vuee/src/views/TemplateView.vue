<template>
    <div id="containter">
        <h3 style="color: whitesmoke">HELLO FROM TEMPLATE VIEW</h3>
        <button @click="buttonClicked">LIST ALL USERS</button>
        <table class="users">
            <tr>
                <th v-for="cell in cellUsers" v-bind:key="cell">{{cell}}</th>
            </tr>
            <tr v-for="appUser in appUsers" v-bind:key="appUser.id">
                <td>{{appUser.id}}</td>
                <td>{{appUser.username}}</td>
                <td>{{appUser.email}}</td>
            </tr>


        </table>

        <button @click="logout">LOGOUT</button>


    </div>
    
</template>

<script>
    import axios from 'axios';

    export default {
        name: "TemplateView",
        data() {
            return {
                appUsers: [],
                cellUsers: ['User ID', 'Username', 'Email']
            }
        },
        methods: {
            buttonClicked: function(){
                console.log('button clicked');

            },
            logout(){
                this.$store.dispatch('auth/logout');
                this.$router.push('/');
            }
        },
        mounted(){

        },
        created() {
            axios.get('http://localhost:8080/users/test').then(response => {
                this.appUsers = response.data;
            })
        }
    }
</script>

<style scoped>





    #containter {

    position: absolute;
    top: 0;
    left: 0;
    z-index: 100;
    width: 100%;
    height: 100%;
    background-size: 100% 100%;
    background: #1A1A1D;
}

.users{
    width: 60%;
    background: transparent;
    position: absolute;
    left: 20%;
    top: 20%;

}

.users tr:nth-child(even) {
    border-left: 1px solid white;
    border-bottom: 1px solid white;
    border-right: 1px solid white;
    background: rgba(255, 255, 255, 0.2);
}

.users tr:nth-child(odd) {
    border-left: 1px solid white;
    border-bottom: 1px solid white;
    border-right: 1px solid white;
    background: rgba(255, 255, 255, 0.4);
}


.users th {
    font-family: 'Courier New', Courier, monospace;
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: center;
    border-bottom: 3px solid lightpink;
    border-top: 3px solid lightpink;
    color: white;


}

.users th:first-child {
    border-left: 3px solid lightpink;
}

.users th:last-child {
    border-right: 3px solid lightpink;
}

.users td, tr {
    font-family: 'Courier New', Courier, monospace;
    padding: 10px;
    text-align: center;
    color: lightpink;
}


</style>
