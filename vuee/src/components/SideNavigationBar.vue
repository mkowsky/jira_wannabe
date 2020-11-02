<template>
    <div class="wrapper">

        <div class="sidenav">


            <Logo class="logo" v-bind:font-size="230"></Logo>

            <!--<div class="logo">-->
            <!--<img src="../assets/logo.png"  style="width: 65%; height: 65%; position: relative; top: 10%">-->
            <!--<div id="circle-title" style="position: relative; bottom: -12px; font-size: 11px; font-weight: bolder">JIRA-WANNABE</div>-->
            <!--<div style="color: white; font-size: 16px; margin-top: 40px">JIRA-WANNABE</div>-->
            <!--</div>-->

            <ul style="list-style: none;">
                <li>
                    <router-link to="/board" class="router-nav">
                        <font-awesome-icon icon="user" class="icon-nav"/>
                        Board
                    </router-link>
                </li>
                <li>
                    <router-link to="/profile" class="router-nav">
                        <font-awesome-icon icon="user" class="icon-nav"/>
                        Profile
                    </router-link>
                </li>
                <li>
                    <router-link to="/board" class="router-nav">
                        <font-awesome-icon icon="user" class="icon-nav"/>
                        Board
                    </router-link>
                </li>
                <li v-if="showModeratorBoard">
                    <router-link to="/task-management" class="router-nav">
                        <font-awesome-icon icon="user" class="icon-nav"/>
                        PM
                    </router-link>
                </li>
            </ul>


        </div>
    </div>
</template>

<script>
    import Logo from "@/components/Logo";

    export default {
        name: "SideNavigationBar",
        components: {Logo},
        methods: {
            logout: function () {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        },
        computed: {
            showModeratorBoard() {
                var user = JSON.parse(localStorage.getItem('user'));
                return user.roles.includes('ROLE_PROJECT_MANAGER');
            }
        }
    }
</script>

<style scoped>

    * {
        padding: 0;
        margin: 0;
    }


    .sidenav {

        height: 100%;
        width: 160px;
        position: fixed;
        top: 0;
        left: 0;
        background-color: #111;


    }

    .sidenav ul li {
        font-size: 20px;
        padding-bottom: 20px;
        padding-top: 20px;
        border-bottom: 2px solid #fff;
        border-bottom: 2px solid rgba(0, 0, 0, 0.5);
        border-top: 2px solid rgba(255, 255, 255, 0.5);
    }

    .sidenav ul li .router-nav {
        text-decoration: none;
        display: block;
    }

    .sidenav ul li:hover {
        background-color: #A29C9B;
        cursor: pointer;
    }

    .sidenav ul li:hover .router-nav {

        color: #fff
    }


    .sidenav .router-nav {

        color: lightpink;
    }


    .icon-nav {
        margin-right: 5px;
        color: white;
    }

    .logo {
        height: 120px;
        width: 120px;
        position: relative;
        left: 10px;
        margin-bottom: 60px;

    }

</style>
