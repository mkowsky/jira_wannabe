<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div class="releases-content-wrapper">
        <div style="font-size: 32px; letter-spacing: 2px; font-weight: 300">VERSION 6.3.3</div>
        <div style="display: flex; flex-direction: row; font-size: 20px;">
            <p style="margin-right: 15px;">Start: 10 Aug 2015 </p>
            <p>Release: 9 Oct 2015</p>
        </div>
        <div>28 days left</div>
        <div style="display: flex; flex-direction: row; background: lightgray; justify-content: flex-start">
            <div style="width: 73%; background: green; height: 20px; opacity: 0.6"></div>
            <div style="width: 4%; background: orange; height: 20px; opacity: 0.8"></div>
            <div style="width: 23%; background: blue; height: 20px; opacity: 0.5"></div>
        </div>


        <v-tabs grow height="100" @change="tabChanged($event)">
            <v-tabs-slider :color="color"></v-tabs-slider>
            <v-tab style="display: flex; justify-content: space-evenly; color: crimson;">
                <div style="font-size: 38px;">12</div>
                <span style="display: flex; flex-direction: column; font-size: 18px;">warnings</span></v-tab>
            <v-tab style="display: flex; justify-content: space-evenly; color: black;">
                <div style="font-size: 38px;">106</div>
                <span style="display: flex; flex-direction: column; font-size: 18px;">ISSUES <span>IN VERSION</span></span>
            </v-tab>
            <v-tab style="display: flex; justify-content: space-evenly; color: green">
                <div style="font-size: 38px;">73</div>
                <span style="display: flex; flex-direction: column; font-size: 18px;">ISSUES <span>DONE</span></span>
            </v-tab>
            <v-tab style="display: flex; justify-content: space-evenly; color: orange;">
                <div style="font-size: 38px;">4</div>
                <span style="display: flex; flex-direction: column; font-size: 18px;">ISSUES <span>IN PROGRESS</span></span>
            </v-tab>
            <v-tab style="display: flex; justify-content: space-evenly; color: blue;">
                <div style="font-size: 38px;">29</div>
                <span style="display: flex; flex-direction: column; font-size: 18px;">ISSUES <span>TO-DO</span></span>
            </v-tab>
        </v-tabs>

        <div style="margin-top: 20px;">

            <div v-show="warningsVisible">

            </div>

            <div v-show="allIssuesVisible">
                all issues
            </div>

            <div v-show="doneVisible">
                done issues
            </div>

            <div v-show="inProgressVisible">
                in progress issues
            </div>

            <div v-show="toDoVisible">
                todo issues
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "ProjectReleasesContent",
        data() {
            return {

                warningsVisible: true,
                allIssuesVisible: false,
                doneVisible: false,
                inProgressVisible: false,
                toDoVisible: false,
            }
        },
        methods: {
            tabChanged(value) {
                this.warningsVisible = false;
                this.allIssuesVisible = false;
                this.doneVisible = false;
                this.inProgressVisible = false;
                this.toDoVisible = false;

                console.log(value);
                switch (value) {
                    case 0:
                        this.color = 'red';
                        this.warningsVisible = true;
                        break;
                    case 1:
                        this.color = 'black';
                        this.allIssuesVisible = true;
                        break;
                    case 2:
                        this.color = 'green';
                        this.doneVisible = true;
                        break;
                    case 3:
                        this.color = 'orange';
                        this.inProgressVisible = true;
                        break;
                    case 4:
                        this.color = 'blue';
                        this.toDoVisible = true;
                        break;
                }
            }
        }
    }
</script>

<style scoped>

</style>
