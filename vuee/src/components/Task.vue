<template>
    <v-card @click="taskItemClicked">
        <v-card-title>{{title}}</v-card-title>
        <v-card-subtitle>Subtitle</v-card-subtitle>
        <v-card-text>{{description}}</v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
            <v-avatar color="grey" size="36" style="margin-right: 10px;"></v-avatar>
            {{projectManager}}
        </v-card-actions>
        <v-card-actions style="padding: 0;">
            <div v-bind:style="{width: 100+ '%', height: 10 + 'px', background: color}"></div>
        </v-card-actions>
    </v-card>

    <!---->
    <!--<div id="cont">-->
    <!--<div class="content-black" @click="navigateToTask">-->
    <!--<div id="title">{{title}}</div>-->
    <!--<div id="description">{{description}}</div>-->
    <!--<div id="wrapper">-->
    <!--<div class="circle">-->
    <!--<font-awesome-icon icon="user" class="task-icon"/>-->
    <!--</div>-->
    <!--<div id="projectManager">{{projectManager}}</div>-->

    <!--</div>-->

    <!--</div>-->

    <!--</div>-->
</template>

<script>
    export default {
        name: "Task",
        props: {
            title: {
                type: String,
                default: "Title",

            },
            description: {
                type: String,
                default: "Description",

            },
            projectManager: {
                type: String,

            },
            priority: {
                type: Number,
            }

        },
        data() {
            return {
                color: 'crimson'
            }
        },
        methods: {
            checkPriority() {
                switch (this.priority) {
                    case 1:
                        this.color = 'green';
                        break;
                    case 2:
                        this.color = 'orange'
                        break;
                    case 3:
                        this.color = 'gold';
                        break;
                    case 4:
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

    .content-black {
        width: 300px;
        height: 180px;
        box-shadow: -10px 0 10px 0 black;
        background: white;

    }

    .content-pink {
        width: 300px;
        height: 180px;
        box-shadow: -10px 0 10px 0 lightpink;
        background: white;
    }

    .content-green {
        width: 300px;
        height: 180px;
        box-shadow: -10px 0 10px 0 darkgreen;
        background: white;
    }

    .content-silver {
        width: 300px;
        height: 180px;
        box-shadow: -10px 0 10px 0 lightyellow;
        background: white;
    }

    #title {
        position: relative;
        left: 40%;
        transform: translateX(-50%);
        font-size: 20px;
        font-weight: lighter;
        font-family: 'Courier New', Courier, monospace;
    }

    #description {
        position: relative;
        top: 10%;
        font-size: 18px;
        font-weight: lighter;
        font-family: 'Courier New', Courier, monospace;

    }

    .circle {
        text-align: center;
        background: gray;
        height: 25px;
        width: 25px;
        border-radius: 50%;
        margin-right: 10px;

    }

    #wrapper {
        position: relative;
        top: 55%;
        display: inline-flex;
        left: -30%;

    }

    #projectManager {
        position: relative;
        top: 5px;

    }

    .task-icon {
        top: 3px;
        position: relative;
        color: black;
        text-align: center;
    }
</style>
