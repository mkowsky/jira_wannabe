<template>
    <body>
    <div class="timeline-container">

        <div class="flexbox">
            <div class="inner-flexbox">
                <div class="claim">
                    Track task changes!
                </div>
                <div class="subclaim">
                    <span style="font-weight: 400">Task timeline</span> is a palce where you can check <span
                        style="font-weight: 400">all previous</span> task <span
                        style="font-weight: 400">changes</span>
                    with their exact dates and time. This helps you to <span
                        style="font-weight: 400">track all the proces</span>
                    from
                    task creation to its end!
                </div>
            </div>
            <img src="../assets/images/timeline.svg" class="img">
        </div>

        <div style="display: flex; flex-direction: column; justify-content: flex-end; padding: 20px;">
            <div class="task-timeline-item">
                <v-timeline>
                    <status-change-item v-for="change in taskChanges"
                                        :key="change.id"
                                        :change-date="change.changeDate"
                                        :change-description="change.changeDescription"
                                        :change-type="change.changeType">
                    </status-change-item>
                </v-timeline>
            </div>
        </div>


    </div>
    </body>
</template>

<script>

    import StatusChangeItem from "./StatusChangeItem";

    export default {
        name: "TaskTimeline",
        components: {StatusChangeItem},
        props: ['taskChanges']
    }
</script>


<style scoped>
    .timeline-container {
        width: 100%;
        height: 100%;
        padding: 20px;
    }

    .flexbox {
        display: flex;
        align-items: center;
        height: 100vh;
    }

    .inner-flexbox {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 800px;
        justify-content: space-evenly;
    }

    .claim {
        font-size: 54px;
        font-weight: 800;
        align-self: center;
    }

    .subclaim {
        font-size: 36px;
        font-weight: 200;
        width: 700px;
        text-align: center;

    }

    .img {
        width: 700px;
        height: 700px;
    }
</style>
