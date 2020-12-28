<template>
    <body>


    <v-card
            width="240"

            style="display: flex; flex-direction: column; padding: 10px 0 0 0; box-shadow: 0 0 5px black;"
    >


            <v-avatar style="align-self: center; width: 140px; height: 140px;">
                <img :src="profilePicture" style="border-radius: 50%; object-fit: cover; border: 2px solid black;">
            </v-avatar>


        <div style="color: black; font-weight: 400; align-self: center; padding: 10px;" @click="navigateToUserProfile">{{nickname}} </div>
        <div style="font-size: 14px; align-self: center">username@jira-wannabe.com</div>
        <div style="align-self: center; padding: 10px;"><v-chip>POSITION</v-chip></div>



        <v-divider class="mx-4"></v-divider>


        <v-card-actions style="display: flex; justify-content: space-between; padding: 10px;">
            <span>action1</span>
            <span>action1</span>
            <span>action1</span>
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
