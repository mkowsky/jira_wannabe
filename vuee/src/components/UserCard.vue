<template>
    <body>


    <v-card
            width="240"

            style="display: flex; flex-direction: column; padding: 10px 0 0 0; box-shadow: 0 0 5px black;"
    >


            <v-avatar style="align-self: center; width: 140px; height: 140px;">
                <img :src="profilePicture" style="border-radius: 50%; object-fit: cover; border: 2px solid black;">
            </v-avatar>


        <div style="color: black; font-weight: 600; align-self: center; padding: 10px;" >{{nickname}} </div>




        <v-divider class="mx-4"></v-divider>


        <v-card-actions style="display: flex; justify-content: center; padding: 10px;">
            <v-btn @click="navigateToUserProfile">VIEW PROFILE</v-btn>
        </v-card-actions>

    </v-card>
    </body>
</template>

<script>
    export default {
        name: "UserCard",
        props: {
            userid: {
                type: Number,
                require: true,
            },
            nickname: {
                type: String,
                required: true,
                default: "Username",
            },
            position: {
                type: String,
                required: true,
                default: "Developer"
            },
            profilePicture:{
                type: String,
            }
        },
        methods: {
            navigateToUserProfile() {
                this.$emit('navigate-to-profile', this.userid);
            }
        },
    }
</script>

<style scoped>



</style>
