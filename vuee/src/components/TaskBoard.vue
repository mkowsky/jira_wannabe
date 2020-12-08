<template>

    <div class="task-board-wrapper">

        <div class="taskboard-filters">

            <div style="align-self: center">
                Filters
            </div>

            <div class="filters-wrapper">
                <div>
                    <v-text-field solo label="Search by task name" v-model="queryCopy"></v-text-field>
                </div>
                <div>
                    <v-select solo
                              :items="priorities"
                              item-text="value"
                              item-value="value"
                              label="Task priority"
                              v-model="queryPriorityCopy"></v-select>
                </div>
                <div>
                    <v-select solo label="Only from project" :items="projects" item-text="value" item-value="value"
                              v-model="queryProjectCopy"></v-select>
                </div>
                <div>
                    <v-btn solo @click="clearFilters()" small>CLEAR FILTERS</v-btn>
                    <v-btn solo @click="applyFilters()" small>APPLY FILTERS</v-btn>
                </div>

            </div>

        </div>

        <div class="taskboard-container">
            <v-card class="column" color="#424242" style="display: flex; flex-direction: column; min-height: 700px;">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    TO
                    DO
                </v-card-title>
                <Task v-for="task in toDo"
                      :key="task.taskID"
                      v-bind:task-name="task.taskName"
                      v-bind:project-name="task.projectName"
                      v-bind:task-user="task.userFullName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.userPictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.taskID)"/>
            </v-card>

            <v-card class="column" style="display: flex; flex-direction: column; min-height: 700px;" color="#424242">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    IN
                    PROGRESS
                </v-card-title>
                <Task v-for="task in inProgress"
                      :key="task.taskID"
                      v-bind:task-name="task.taskName"
                      v-bind:project-name="task.projectName"
                      v-bind:task-user="task.userFullName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.userPictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.taskID)"/>
            </v-card>


            <v-card class="column" style="display: flex; flex-direction: column; min-height: 750px;" color="#424242">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    CODE REVIEW
                </v-card-title>
                <Task v-for="task in codeReview"
                      :key="task.taskID"
                      v-bind:task-name="task.taskName"
                      v-bind:project-name="task.projectName"
                      v-bind:task-user="task.userFullName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.userPictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.taskID)"/>
            </v-card>

            <v-card class="column" style="display: flex; flex-direction: column; min-height: 750px;" color="#424242">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    DONE
                </v-card-title>
                <Task v-for="task in done"
                      :key="task.taskID"
                      v-bind:task-name="task.taskName"
                      v-bind:project-name="task.projectName"
                      v-bind:task-user="task.userFullName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.userPictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.taskID)"/>
            </v-card>

        </div>


        <div>

            <v-pagination
                    v-model="page"
                    :length="6"
                    @input="pageChanged($event)"
                    color="rgba(235, 182, 193, 1)"
                    circle

            ></v-pagination>
        </div>

    </div>
</template>

<script>
    import Task from '@/components/Task'


    export default {
        name: "TaskBoard",
        components: {Task},
        props: {
            tasks: {}
        },
        data() {
            return {
                page: 1,
                query: '',
                queryCopy: '',
                queryPriority: '',
                queryPriorityCopy: '',
                queryProject: '',
                queryProjectCopy: '',
                priorities: [
                    {value: 1},
                    {value: 2},
                    {value: 3},
                    {value: 4},
                ],
                projects: [
                    {value: 'Spacex future exploration'},
                    {value: 'The Smooth Prophecy'},
                    {value: 'The Ice of the Guardian'},
                ]
            }
        },
        methods: {
            paginate(array, pageSize, pageNumber) {
                return array.slice((pageNumber - 1) * pageSize, (pageNumber) * pageSize);
            },
            pageChanged(value) {
                this.page = value;
            },
            navigateToTask(value) {
                this.$router.push({name: 'taskDetails', params: {taskID: value}})
            },
            applyFilters() {
                this.query = this.queryCopy;
                this.queryPriority = this.queryPriorityCopy;
                this.queryProject = this.queryProjectCopy;
            },
            clearFilters() {
                this.queryCopy = '';
                this.queryPriorityCopy = '';
                this.queryProjectCopy = '';

                this.query = '';
                this.queryPriority = '';
                this.queryProject = '';
            }
        },
        computed: {
            toDo() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.taskState.includes("TO_DO");
                })
                if ((this.queryPriority) && (this.query) && (this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.taskPriority === (this.queryPriority))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if ((this.queryPriority) && (this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.queryPriority) && (this.queryProject) && !(this.query)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.query) && (this.queryProject) && !(this.queryPriority)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if (this.query) {
                    return this.paginate(filteredTasks.filter((task) => task.taskName.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                } else if (this.queryPriority) {
                    return this.paginate(filteredTasks.filter((task) => task.taskPriority === (this.queryPriority)), 3, this.page);
                } else if (this.queryProject) {
                    return this.paginate(filteredTasks.filter((task) => task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())), 3, this.page);
                } else if (!(this.queryPriority) && !(this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks, 3, this.page);
                }
                return null;

            },
            codeReview() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.taskState.includes("CODE_REVIEW");
                })
                if ((this.queryPriority) && (this.query) && (this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.taskPriority === (this.queryPriority))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if ((this.queryPriority) && (this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.queryPriority) && (this.queryProject) && !(this.query)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.query) && (this.queryProject) && !(this.queryPriority)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if (this.query) {
                    return this.paginate(filteredTasks.filter((task) => task.taskName.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                } else if (this.queryPriority) {
                    return this.paginate(filteredTasks.filter((task) => task.taskPriority === (this.queryPriority)), 3, this.page);
                } else if (this.queryProject) {
                    return this.paginate(filteredTasks.filter((task) => task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())), 3, this.page);
                } else if (!(this.queryPriority) && !(this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks, 3, this.page);
                }
                return null;

            },
            inProgress() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.taskState.includes("IN_PROGRESS");
                })
                if ((this.queryPriority) && (this.query) && (this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.taskPriority === (this.queryPriority))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if ((this.queryPriority) && (this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.queryPriority) && (this.queryProject) && !(this.query)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.query) && (this.queryProject) && !(this.queryPriority)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if (this.query) {
                    return this.paginate(filteredTasks.filter((task) => task.taskName.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                } else if (this.queryPriority) {
                    return this.paginate(filteredTasks.filter((task) => task.taskPriority === (this.queryPriority)), 3, this.page);
                } else if (this.queryProject) {
                    return this.paginate(filteredTasks.filter((task) => task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())), 3, this.page);
                } else if (!(this.queryPriority) && !(this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks, 3, this.page);
                }
                return null;
            },
            done() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.taskState.includes("DONE");
                })
                if ((this.queryPriority) && (this.query) && (this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.taskPriority === (this.queryPriority))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if ((this.queryPriority) && (this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.queryPriority) && (this.queryProject) && !(this.query)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.query) && (this.queryProject) && !(this.queryPriority)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if (this.query) {
                    return this.paginate(filteredTasks.filter((task) => task.taskName.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                } else if (this.queryPriority) {
                    return this.paginate(filteredTasks.filter((task) => task.taskPriority === (this.queryPriority)), 3, this.page);
                } else if (this.queryProject) {
                    return this.paginate(filteredTasks.filter((task) => task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())), 3, this.page);
                } else if (!(this.queryPriority) && !(this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks, 3, this.page);
                }
                return null;
            }
        },
        watch: {
            query: function () {
                this.checkWhichQuery();
            },
            queryPriority: function () {
                this.checkWhichQuery();
            }

        }

    }
</script>

<style scoped>
    .task-board-wrapper {
        display: flex;
        flex-direction: column;

    }

    .taskboard-container {
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
    }

    .taskboard-filters {
        display: flex;
        flex-direction: column;
        justify-content: space-around;

        width: 100%;
        height: 15%;

    }

    .filters-wrapper {
        align-self: center;
        display: flex;
        width: 60%;
        justify-content: space-between;
        flex-direction: row;
    }

    .column {

        width: 20%;
        padding: 15px;

    }

    .gap {
        margin-bottom: 20px;
        cursor: pointer;
    }
</style>
