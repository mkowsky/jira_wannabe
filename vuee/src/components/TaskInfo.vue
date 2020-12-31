<template>
    <body>
    <div style="display: flex; justify-content: flex-end;width: 100%; ">
        <div style="display: flex;  flex-direction: column; width: 40%;">
            <div class="section-title">TASK DETAILS</div>
        </div>
    </div>

    <div class="grid-wrapper">
        <div class="grid-title">{{currentTask.name}}</div>
        <div class="description-card">
            <div class="--claim">
                TASK DESCRIPTION
            </div>

            <div class="--content">
                Lorem Ipsum is simply dummy
                text of the printing and typesetting industry. Lorem Ipsum has been the industry's
                standard dummy
                text ever since the 1500s, when an unknown printer took a galley of type and scrambled
                it to make a
                type specimen book. It has survived not only five centuries, but also the leap into
                electronic
                typesetting, remaining essentially unchanged.
            </div>
        </div>
        <div class="deadline-card">
            <div class="small-box-title">DEADLINE</div>
            <div class="small-box-content"> {{currentTask.expireDate | moment("DD-MM-YYYY")}}</div>
        </div>
        <div class="priority-card">
            <div class="small-box-title">PRIORITY</div>
            <div class="small-box-content">
                {{currentTask.taskPriority}}
            </div>
        </div>
        <div class="status-card">
            <div class="small-box-title">STATUS</div>
            <div class="small-box-content"> {{currentTask.state}}</div>
        </div>
        <div class="template-grid">
            <div class="task-name">{{currentTask.name}}</div>
        </div>
        <div class="image-grid">

            <div style="display: flex; flex-direction: column;">
                <div style="color: black; text-transform: uppercase; font-weight: 600; font-size: 50px;">
                    task
                </div>
                <div style="color: black;  font-size: 60px; font-weight: 400; margin-top: -20px;">is
                </div>
                <div style="color: black; font-size: 60px; font-weight: 200; margin-top: -40px;">
                    assigned to
                </div>
            </div>

            <div style="display:flex; ">
                <v-avatar style="height: 150px; width: 150px;">
                    <img :src="currentTask.user.pictureURL" style="object-fit: cover">
                </v-avatar>
                <div style="display: flex; flex-direction: column; align-self: flex-end">
                    <div @click="navigateToUserProfile"
                         style="cursor: pointer; font-size: 46px; font-weight: 400">
                        {{currentTask.user.firstName}}
                    </div>
                    <div @click="navigateToUserProfile"
                         style="cursor: pointer; font-size: 56px; font-weight: 800; margin-top: -30px;">
                        {{currentTask.user.lastName}}
                    </div>
                </div>
            </div>


        </div>
    </div>


    </body>
</template>

<script>
    export default {
        name: "TaskInfo",
        props: ['currentTask'],
        data() {
            return {}
        },
        methods: {
            navigateToUserProfile() {

                this.$emit('navigate-to-user-profile', {userID: this.currentTask.user.id})
            }
        }
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";


    .grid-wrapper {
        display: grid;
        grid-template-columns: 200px 300px 700px 400px;
        grid-template-rows: auto;
        grid-column-gap: 20px;
        grid-row-gap: 10px;
        grid-template-areas: "title deadline dsc img" "title prio dsc img" "title status template img";

    }

    .grid-title {
        font-size: 36px;
        font-weight: 600;
        grid-area: title;
        text-transform: uppercase;
        display: flex;
        align-items: center;
        justify-content: center;
        background: white;
        box-shadow: 0 0 10px black;
        writing-mode: vertical-lr;
        text-orientation: mixed;
        transform: rotate(180deg);
    }

    .description-card {
        grid-area: dsc;
        box-shadow: 0 0 10px black;
        background: white;
        height: 300px;
        padding: 20px;

        .--claim {
            font-size: 26px;
            font-weight: 500;
            color: black;
            margin-bottom: 20px;
        }

        .--subclaim {
            font-size: 20px;
            font-weight: 400;

        }

        .--content {
            font-size: 18px;
            font-style: italic;
            width: 100%;
        }
    }

    .small-box-title {
        font-size: 28px;
        font-weight: 700;
        color: $color-secondary-accent;
    }

    .small-box-content {
        font-size: 32px;
        font-weight: 600;
    }

    .deadline-card {
        grid-area: deadline;
        box-shadow: 0 0 10px black;
        background: white;
        height: 150px;

        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px;
    }

    .priority-card {
        grid-area: prio;
        box-shadow: 0 0 10px black;
        background: white;
        height: 150px;

        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px;
    }

    .status-card {
        grid-area: status;
        box-shadow: 0 0 10px black;
        background: white;
        height: 150px;

        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 10px;
    }

    .image-grid {
        grid-area: img;
        height: 100%;
        background: white;
        box-shadow: 0 0 10px black;
        padding: 10px;
    }

    .template-grid {
        grid-area: template;
        height: 150px;
        background: white;
        box-shadow: 0 0 10px black;
        padding: 10px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .task-name {
        font-weight: 600;
        text-transform: uppercase;
        font-size: 40px;
    }

    .section-title {
        background: $color-secondary-accent;
        padding: 10px;
        font-size: 50px;
        margin-bottom: 20px;
        text-align: center;

    }

    .claim {
        font-size: 44px;
        font-weight: 800;
        text-align: center;
        margin-bottom: 30px;
    }

    .subclaim {
        font-size: 36px;
        text-align: center;
        font-style: italic;
        opacity: 0.8;
        margin-bottom: 80px;
    }
</style>
