<template>
    <v-card @click="taskItemClicked" style="width: 300px;">
        <v-card-title>{{taskName}}</v-card-title>
        <v-card-subtitle>{{projectName}}</v-card-subtitle>
        <v-card-text></v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
            <v-avatar style="margin-right: 10px;"><img :src="profilePicture" style="object-fit: cover"></v-avatar>
            {{taskUser}}
        </v-card-actions>
        <v-card-actions style="padding: 0;">
            <div v-bind:style="{width: 100+ '%', height: 10 + 'px', background: color}"></div>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "Task",
        props: {
            taskName: {
                default: "Task Title",
            },
            projectName: {
                default: "Project Name",
            },
            taskUser: {},
            profilePicture:{},
            priority: {},
        },
        data() {
            return {
                color: 'crimson'
            }
        },
        methods: {
            checkPriority() {
                switch (this.priority) {
                    case 'ABLE_TO_WAIT':
                        this.color = 'green';
                        break;
                    case 'IMPORTANT':
                        this.color = 'orange'
                        break;
                    case 'URGENT':
                        this.color = 'gold';
                        break;
                    case 'IMMEDIATE':
                        this.color = '#AD1457';
                        break;
                }
            },
            taskItemClicked() {
                this.$emit('task-item-clicked')
            }
        },
        watch: {
            priority: function () {
                this.checkPriority();
            }
        },

        created() {
            this.checkPriority();

        },
    }
</script>

<style scoped>


</style>
