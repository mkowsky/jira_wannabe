<template>
    <v-card
            :loading="loading"
            width="240"
            color="#424242"
            style="display: flex; flex-direction: column; padding: 10px 0 0 0"
    >



            <v-avatar style="align-self: center; box-shadow: 0 0 10px 10px gray; width: 140px; height: 140px;">
                <img :src="profilePicture" style="object-fit: cover">
            </v-avatar>


        <v-card-title class="center nickname" @click="navigateToUserProfile">{{nickname}}</v-card-title>


        <v-divider class="mx-4"></v-divider>

        <v-card-title class="center template">{{position}}</v-card-title>


    </v-card>
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



    .nickname {
        cursor: pointer;
        letter-spacing: 2px;
        color: white;
        font-weight: 300;
        font-size: 16px;
    }

    .center {
        align-self: center;
    }

    .template {
        font-weight: 100;
        letter-spacing: 3px;
        color: white;

    }
</style>
