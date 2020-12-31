<template>
    <body>
    <div style="height: 90vh; width: 100%;  display: flex; flex-direction: column; justify-content: center; padding: 30px;">

        <div style="display: flex; justify-content: center; width: 100%;">
            <div v-if="!(taskIssues.length > 0)" style="display: flex; flex-direction: row;  width: 100%;">
                <div style="width: 50%; display: flex; flex-direction: column">
                    <div class="-section-title">
                        TASK ISSUES
                    </div>
                    <div style="font-size: 44px; font-weight: 800; text-align: center; margin-bottom: 30px;">THERE IS NO
                        ISSUES ASSIGNED TO THIS TASK
                    </div>
                    <div style="font-size: 36px; text-align: center; font-style: italic; opacity: 0.8; margin-bottom: 80px;">
                        If anyone will report issue with this task, it will be displayed in this place.
                    </div>
                    <v-btn rounded x-large style="align-self: center" :color="colorAccent" @click="reportIssue">REPORT
                        ISSUE
                    </v-btn>
                </div>

                <div style="width: 50%;">
                    <img src="../assets/images/undraw_fixing_bugs_w7gi.svg" style="width: 100%; height: 100%;">
                </div>
            </div>

            <div v-if="taskIssues.length > 0" style="display: flex; flex-direction: row; width: 100%; ">

                <div style="display: flex; flex-direction: column; width: 60%; ">
                    <div>
                        <div class="section-title">TASK ISSUES</div>
                        <div class="subclaim" @click="issuePicked = false;">CLAIM</div>
                    </div>
                    <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
                        <div v-for="issue in taskIssues" class="issue-card" :key="issue.issueKEY">
                            <div style="font-size: 30px;">{{issue.issueKEY}}</div>
                            <div>{{issue.issueStatus}}</div>
                            <span>{{issue.issueType}}</span>
                            <font-awesome-icon icon="times-circle" color="#424242" style="opacity: 0.7"
                                               class="fa-4x"></font-awesome-icon>
                            <v-btn @click="viewIssueDetails(issue.issueID)">View Details</v-btn>
                        </div>
                    </div>
                </div>


                <div v-if="issuePicked"
                     style="background: white; box-shadow: 0 0 10px black; align-self: center; transition: 0.7s; height: 100%; width: 900px; display: flex;">
                    <div style="padding: 20px; width: 60%; display: flex; flex-direction: column;">
                        <div style="font-size: 32px; font-weight: 500; opacity: 0.8; margin-bottom: 10px;">DETAILS</div>
                        <div style="display: flex; justify-content: space-between; margin-bottom: 10px; flex-wrap: wrap;">
                            <div style="margin-right: 15px; display: flex; flex-direction: column; align-items: center">
                                <span style="font-weight: 600;">Type</span>
                                <span>{{currentIssue.issueType}}</span>
                            </div>
                            <div style="margin-right: 15px; display: flex; flex-direction: column; align-items: center">
                                <span style="font-weight: 600;">Status</span>
                                <span>{{currentIssue.issueStatus}}</span>
                            </div>

                            <div style="margin-right: 15px; display: flex; flex-direction: column; align-items: center">
                                <span style="font-weight: 600;">Priority</span>
                                <span>{{currentIssue.issuePriority}}</span>
                            </div>
                        </div>
                        <div style="font-weight: 600; margin-bottom: 5px;">REPORTED BY</div>
                        <div style="font-size: 18px; display: flex; align-items: center; margin-bottom: 10px;">
                            <v-avatar style="height: 50px; width: 50px; margin-right: 10px;">
                                <img :src="currentIssue.pictureURL" style="object-fit: cover">
                            </v-avatar>
                            {{currentIssue.userFullName}}

                        </div>
                        <div style="font-size: 32px; font-weight: 500; opacity: 0.8; margin-bottom: 10px;">DESCRIPTION
                        </div>
                        <div style="margin-bottom: 10px; font-size: 18px; font-style: italic">
                            {{currentIssue.issueDescription}}
                        </div>
                        <div style="font-size: 32px; font-weight: 500; opacity: 0.8; margin-bottom: 10px;">ACITONS</div>
                        <div>
                            <v-btn :color="colorAccent" large>CLOSE ISSUE</v-btn>
                        </div>
                    </div>
                    <div style="display: flex; flex-direction: column; width: 40%; justify-content: flex-end; align-items: center; padding: 10px; ">
                        <div style="font-weight: 800; color: black; opacity: 0.7; font-size: 42px;">Issue</div>
                        <div class="issue-key">{{currentIssue.issueKEY}}</div>
                        <img src="../assets/images/undraw_collecting_fjjl.svg" style="width: 100%; height: 80%;">
                    </div>
                </div>

                <div v-if="!issuePicked" style="display: flex; flex-direction: column; width: 50%; ">

                    <img src="../assets/images/undraw_Questions_re_1fy7.svg"
                         style="width: 80%; height: 80%; opacity: 0.6">

                    <div style="font-size: 40px; opacity: 0.8; align-self: center; margin-bottom: 10px;">No
                        data available
                    </div>
                    <div style="font-style: italic; font-size: 32px; color: black; opacity: 0.6; align-self: center">
                        Pick issue to see its details
                    </div>
                </div>


            </div>
        </div>
    </div>
    </body>
</template>

<script>
    import colors from "@/assets/css/colors";

    export default {
        name: "TaskIssues",
        props: ['taskIssues'],
        data() {
            return {
                issuePicked: false,
                currentIssue: [],
                colorAccent: colors.ACCENT,
            }
        },
        methods: {
            viewIssueDetails(id) {
                this.issuePicked = true;
                this.taskIssues.filter((issue) => {
                    if (issue.issueID === id) this.currentIssue = issue;
                })
            },
            reportIssue() {
                this.$emit('report-issue');
            }
        }
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main.scss";

    .claim {
        font-weight: 600;
        font-size: 40px;
        letter-spacing: 3px;
    }

    .issue-card {
        height: 300px;
        width: 250px;
        background: white;
        box-shadow: 0 0 10px black;
        margin-right: 20px;
        padding: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;

    }

    .section-title {
        background: $color-secondary-accent;
        padding: 10px;
        font-size: 50px;
        margin-bottom: 20px;
        text-align: center;
        width: 800px;

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
        width: 800px;
    }

    .issue-key {
        color: $color-secondary-accent;
        font-weight: 700;
        font-size: 32px;
    }

    .-section-title {
        background: $color-secondary-accent;
        padding: 10px;
        font-size: 50px;
        margin-bottom: 20px;
        text-align: center
    }
</style>
