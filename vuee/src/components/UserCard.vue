<template>
    <v-card
            :loading="loading"
            class="mx-auto my-12 flex-display"
            width="300"
            color="#424242"
    >

        <div style="width: 180px; height: 180px; border-radius: 50%; background: white; align-self: center; box-shadow: 0 0 10px 10px gray;"></div>


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
    .flex-display {
        padding: 10px;
        display: flex;
        flex-direction: column;
    }


    .nickname {
        cursor: pointer;
        letter-spacing: 3px;
        color: white;
        font-weight: 300;
        font-size: 22px;
    }

    .center {
        align-self: center;
    }

    .template {
        font-weight: 100;
        letter-spacing: 5px;
        color: white;

    }
</style>
