<template>
    <div>


        <v-card
                class="sidenav"
                dark
                tile

        >
            <v-navigation-drawer permanent>
                <div class="logo-container">
                    <div class="logo-wrapper">
                        <img src="../assets/images/logo.png" class="logo-img">
                    </div>
                    <div class="logo-title">JIRA-WANNABE</div>
                </div>


                <v-divider></v-divider>

                <v-list-item link @click="navigate('/board')">
                    <v-list-item-icon>
                        <v-avatar><img :src="showUserPicture" style="object-fit: cover"></v-avatar>
                    </v-list-item-icon>

                    <v-list-item-content>
                        <v-list-item-title
                                style="font-size: 16px; display: flex; flex-direction: column; font-weight: 500"><span>{{showUserFirstName}}</span><span>{{showUserLastName}}</span>
                        </v-list-item-title>
                        <v-list-item-title>
                            {{showUserRole}}
                        </v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-divider></v-divider>
                <v-list nav>

                    <v-list-item link @click="navigate('/board')">
                        <v-list-item-icon>
                            <v-icon>mdi-view-dashboard</v-icon>
                        </v-list-item-icon>

                        <v-list-item-content>
                            <v-list-item-title>Board</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>


                    <v-list-item link @click="navigate('/projects')">
                        <v-list-item-icon>
                            <v-icon>mdi-form-select</v-icon>
                        </v-list-item-icon>

                        <v-list-item-content>
                            <v-list-item-title>Projects</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-list-item link @click="navigate('/profile')">
                        <v-list-item-icon>
                            <v-icon>mdi-account</v-icon>
                        </v-list-item-icon>

                        <v-list-item-content>
                            <v-list-item-title>Profile</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>


                    <v-list-item link v-show="showModeratorBoard" @click="navigate('/task-creation')">
                        <v-list-item-icon>
                            <v-icon>mdi-pencil</v-icon>
                        </v-list-item-icon>

                        <v-list-item-content>
                            <v-list-item-title>Create task</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-list-item link v-show="showModeratorBoard" @click="navigate('/project-creation')">
                        <v-list-item-icon>
                            <v-icon>mdi-note-plus-outline</v-icon>
                        </v-list-item-icon>

                        <v-list-item-content>
                            <v-list-item-title>Add project</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-list-item link v-show="showAdminBoard" @click="navigate('/admin-dashboard')">
                        <v-list-item-icon>
                            <v-icon>mdi-lock</v-icon>
                        </v-list-item-icon>

                        <v-list-item-content>
                            <v-list-item-title>Moderation</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-list-item link @click="modalVisible = true;">
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

        <Dialog :dialog="modalVisible"
               :dialog-content="'Are you sure you want to log out?'"
               :dialog-title="'Log out'"
               @modal-agree="logout"
               @modal-cancel="modalVisible=false"></Dialog>
    </div>
</template>

<script>




    import Dialog from "@/components/Dialog";

    export default {
        name: "SideNavigationBar",
        components: {Dialog},
        data() {
            return {
                modalVisible: false,
                user: this.user,
            }
        },
        methods: {
            logout() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            },
            navigate(value) {
                this.$router.push(value);
            }
        },
        computed: {
            showModeratorBoard() {
                return this.user.roles.includes('ROLE_PROJECT_MANAGER');
            },
            showAdminBoard(){
                return this.user.roles.includes('ROLE_ADMIN');
            },
            showUserFirstName() {
                return this.user.firstName;
            },
            showUserLastName() {
                return this.user.lastName;
            },
            showUserPicture() {
                console.log(this.user);
                return this.user.pictureURL;
            },
            showUserRole(){
                if ((this.user.roles.includes('ROLE_PROJECT_MANAGER')) && (this.user.roles.includes('ROLE_ADMIN'))) return 'ADMIN';
                if (this.user.roles.includes('ROLE_PROJECT_MANAGER')) return 'PM';
                else return 'USER'
            }
        },
        created() {
            this.user = JSON.parse(localStorage.getItem('user'));
            console.log(this.user);
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
        width: 12%;
        position: fixed;
        top: 0;
        left: 0;

    }

    .logo-container {
        display: flex;
        width: 100%;
        margin-top: 20px;
        flex-direction: column;
    }

    .logo-wrapper {
        border: 3px solid black;
        align-self: center;
        height: 160px;
        width: 160px;
        background: linear-gradient(90deg, rgba(108,99,255,1) 0%, rgba(125,120,221,1) 60%, rgba(92,184,236,1) 100%);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 20px;

    }
    .logo-img{
        width: 70%;
        height: 70%;
    }

    .logo-title {
        margin-bottom: 30px;
        align-self: center;
        font-weight: 300;
        letter-spacing: 2px;
    }

</style>
