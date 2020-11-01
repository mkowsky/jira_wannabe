<template>
    <div id="cont">
        <div class="content-black" @click="taskItemClicked">
            <div id="title">{{title}}</div>
            <div id="description">{{description}}</div>
            <div id="wrapper">
                <div class="circle">
                    <font-awesome-icon icon="user" class="task-icon"/>
                </div>
                <div id="projectManager">{{projectManager}}</div>

            </div>

        </div>

    </div>
</template>

<script>
    export default {
        name: "Task",
        props: {
            title: {
                type: String,
                required: true,
            },
            description: {
                type: String,
                required: true,
            },
            projectManager: {
                type: String,
                required: true,
            },

        },
        methods:{
            taskItemClicked(){
                this.$emit('task-item-clicked')
            }
        }
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
