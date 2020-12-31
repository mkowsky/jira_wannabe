<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div style="display: flex; flex-direction: column">


        <v-stepper
                v-model="e6"
                vertical
        >
            <v-stepper-step
                    :complete="e6 > 1"
                    step="1"
                    editable
                    :color="colorAccent">
                Chose project

            </v-stepper-step>

            <v-stepper-content step="1">
                <v-select solo
                          dense
                          label="Chose project"
                          :items="projects"
                          item-text="projectName"
                          item-value="id"
                          class="select"
                          v-model="task.taskProject"
                          :color="colorAccent"
                          @change="getProjectTeamMembers()"
                          @input="liveRenderChange('taskProject')"
                ></v-select>

                <v-btn

                        @click="e6 = 2;"
                        :color="colorAccent"
                >CONFIRM
                </v-btn>
            </v-stepper-content>


            <v-stepper-step
                    :complete="e6 > 2"
                    step="2"
                    editable
                    :color="colorAccent"
            >
                Set title
            </v-stepper-step>

            <v-stepper-content step="2">
                <v-text-field solo
                              label="Title"
                              class="select"
                              v-model="task.taskTitle"
                              @input="liveRenderChange('taskTitle')"></v-text-field>

                <v-btn
                        :color="colorAccent"
                        @click="e6 = 3;"
                >CONFIRM
                </v-btn>
            </v-stepper-content>


            <v-stepper-step
                    :complete="e6 > 3"
                    step="3"
                    editable
                    :color="colorAccent">
                Pick priority
            </v-stepper-step>

            <v-stepper-content step="3">
                <v-select solo
                          dense
                          label="Chose Priority"
                          :items="priorities"
                          item-text="name"
                          item-value="value"
                          class="select"
                          :color="colorAccent"
                          v-model="task.taskPriority"
                          @input="liveRenderChange('taskPriority')"

                ></v-select>

                <v-btn
                        :color="colorAccent"
                        @click="e6 = 4; "
                >CONFIRM
                </v-btn>
            </v-stepper-content>


            <v-stepper-step
                    :complete="e6 > 4"
                    step="4"
                    editable
                    :color="colorAccent">
                Task description

            </v-stepper-step>

            <v-stepper-content step="4">
                <v-textarea solo
                            label="Task description"
                            v-model="task.taskDescription"
                            @input="liveRenderChange('taskDescription')"></v-textarea>
                <v-btn
                        :color="colorAccent"
                        @click="e6 = 5; "
                >CONFIRM
                </v-btn>
            </v-stepper-content>


            <v-stepper-step
                    :complete="e6 > 5"
                    step="5"
                    editable
                    :color="colorAccent">
                Pick deadline
            </v-stepper-step>

            <v-stepper-content step="5">
                <v-date-picker v-model="task.taskDeadline"
                               :color="colorAccent"
                               :min="currentDate"></v-date-picker>
                <v-btn :color="colorAccent" @click="e6 = 6;">CONFIRM</v-btn>
            </v-stepper-content>


            <v-stepper-step
                    :complete="e6 > 6"
                    step="6"
                    editable
                    :color="colorAccent">
                Select users
            </v-stepper-step>

            <v-stepper-content step="6">
                <template v-if="!task.taskProject">
                    To select user, first select one of the projects from first step
                </template>
                <template v-if="task.taskProject">
                    <v-select
                            solo
                            dense
                            label="Chose user"
                            :items="users"
                            item-text="fullName"
                            item-value="id"
                            class="select"
                            :color="colorAccent"
                            v-model="chosenUser"
                            @input="liveRenderChange('taskPriority')"

                    ></v-select>
                </template>

                <v-btn
                        :color="colorAccent"
                        @click="e6 = 7"
                >CONFIRM
                </v-btn>

            </v-stepper-content>


        </v-stepper>
        <div style="width: 100%; height: auto; display: flex; justify-content: center;margin-top: 20px;">
            <v-btn :disabled="isConfirmButtonDisabled" @click="createTask()" style="width: 50%;">CREATE TASK</v-btn>
            <!--<v-btn  @click="clearData" style="width: 50%;">CANCEL</v-btn>-->
        </div>

    </div>
</template>

<script>
    import Task from "../model/task";
    import colors from "@/assets/css/colors";

    export default {
        name: "TaskCreation",
        components: {},
        props: {
            users: {
                type: Array,
            },
            projects: {
                type: Array,
            }

        },
        data() {
            return {
                colorAccent: colors.ACCENT,
                e6: 1,
                task: new Task(null, null, 'BACKEND', null, null, "TO_DO", null, null),
                currentDate: this.$moment().format("YYYY-MM-DD"),
                priorities: [
                    {value: "ABLE_TO_WAIT", name: "ABLE TO WAIT"},
                    {value: "IMPORTANT", name: "IMPORTANT"},
                    {value: "URGENT", name: "URGENT"},
                    {value: "IMMEDIATE", name: 'IMMEDIATE'},
                ],


                chosenPeople: [],
                chosenUser: '',
            }
        },
        methods: {
            liveRenderChange(value) {
                this.$emit('task-render', {task: this.task, changeValue: value})
            },
            createTask() {
                this.$emit('create-task', {task: this.task, user: this.chosenUser});
                this.clearData();
            },
            getProjectTeamMembers() {
                this.$emit('get-team-members', {taskID: this.task.taskProject});
            },
            clearData() {
                this.e6 = 1;
                this.task = new Task(null, null, null, null, null, "TO_DO", null, null)
                this.chosenUser = '';
            },
        },
        computed: {
            isConfirmButtonDisabled() {

                if ((this.task.taskPriority) && (this.task.department) && (this.task.taskTitle) && (this.task.taskDescription) && (this.task.taskDeadline) && (this.chosenUser)) return false
                else return true
            },
        },
    }
</script>

<style scoped>
    .select {
        width: 50%;
    }
</style>
