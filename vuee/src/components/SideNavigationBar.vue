<template>
    <v-card

            class="sidenav"
            dark
    >
        <v-navigation-drawer permanent>
            <div style="display: flex; width: 100%; height: auto; flex-direction: column">
                <Logo class="logo" style="align-self: center; margin-bottom: 20px;"></Logo>
                <div style="margin-bottom: 30px; align-self: center; letter-spacing: 2px;">JIRA-WANNABE</div>
            </div>


            <v-divider></v-divider>

            <v-list nav>

                <v-list-item link @click="navigate('/board')">
                    <v-list-item-icon>
                        <v-icon>mdi-view-dashboard</v-icon>
                    </v-list-item-icon>

                    <v-list-item-content >
                        <v-list-item-title>Board</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>

                <v-list-item link @click="navigate('/profile')">
                    <v-list-item-icon>
                        <v-icon>mdi-account</v-icon>
                    </v-list-item-icon>

                    <v-list-item-content >
                        <v-list-item-title>Profile</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>

                <v-list-item link v-show="showModeratorBoard" @click="navigate('/task-management')">
                    <v-list-item-icon>
                        <v-icon>mdi-pencil</v-icon>
                    </v-list-item-icon>

                    <v-list-item-content>
                        <v-list-item-title>Task-Creation</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>

                <v-list-item link @click="logout">
                    <v-list-item-icon>
                        <v-icon>mdi-logout</v-icon>
                    </v-list-item-icon>

                    <v-list-item-content>
                        <v-list-item-title>Logout</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>


            </v-list>


        </v-navigation-drawer>
    </v-card>

</template>

<script>


    import Logo from "@/components/Logo";

    export default {
        name: "SideNavigationBar",
        components: {Logo},
        data() {
            return {}
        },
        methods: {
            logout() {
                this.$emit('logout');
            },
            navigate(value) {
                this.$router.push(value);
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
        width: 10%;
        position: fixed;
        top: 0;
        left: 0;
        background-color: #111;


    }


    .logo {
        height: 140px;
        width: 140px;
        position: relative;


    }

</style>
