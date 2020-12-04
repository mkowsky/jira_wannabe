<template>

    <div class="task-board-wrapper">

        <div class="taskboard-filters">

            <div>
                <v-text-field solo label="Search" v-model="query"></v-text-field>
            </div>
        </div>

        <div class="taskboard-container">
            <v-card class="column" style="display: flex; flex-direction: column; min-height: 750px;" color="#424242">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    TO
                    DO
                </v-card-title>
                <Task v-for="task in toDo"
                      :key="task.id"
                      v-bind:title="task.name"
                      v-bind:description="task.description"
                      v-bind:project-manager="task.user.firstName + ' ' + task.user.lastName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.user.pictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.id)"/>
            </v-card>

            <v-card class="column" style="display: flex; flex-direction: column; min-height: 750px;" color="#424242">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    IN
                    PROGRESS
                </v-card-title>
                <Task v-for="task in inProgress"
                      :key="task.id"
                      v-bind:title="task.name"
                      v-bind:description="task.description"
                      v-bind:project-manager="task.user.firstName + ' ' + task.user.lastName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.user.pictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.id)"/>
            </v-card>


            <v-card class="column" style="display: flex; flex-direction: column; min-height: 750px;" color="#424242">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    CODE REVIEW
                </v-card-title>
                <Task v-for="task in codeReview"
                      :key="task.id"
                      v-bind:title="task.name"
                      v-bind:description="task.description"
                      v-bind:project-manager="task.user.firstName + ' ' + task.user.lastName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.user.pictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.id)"/>
            </v-card>

            <v-card class="column" style="display: flex; flex-direction: column; min-height: 750px;" color="#424242">
                <v-card-title
                        style="color: white; font-weight: 100; letter-spacing: 3px; font-size: 26px; align-self: center">
                    DONE
                </v-card-title>
                <Task v-for="task in done"
                      :key="task.id"
                      v-bind:title="task.name"
                      v-bind:description="task.description"
                      v-bind:project-manager="task.user.firstName + ' ' + task.user.lastName"
                      v-bind:priority="task.taskPriority"
                      v-bind:profile-picture="task.user.pictureURL"
                      class="gap"
                      @task-item-clicked="navigateToTask(task.id)"/>
            </v-card>

        </div>


        <div>

            <v-pagination
                    v-model="page"
                    :length="6"
                    @next="next"
                    @previous="previous"
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
            }
        },
        methods: {
            paginate(array, pageSize, pageNumber) {

                return array.slice((pageNumber - 1) * pageSize, (pageNumber) * pageSize);
            },
            next() {
                console.log('nex');
            },
            previous() {
                console.log('prev');
            },
            pageChanged(value) {
                this.page = value;
            },
            navigateToTask(value) {
            this.$router.push({name: 'taskDetails', params: {taskID: value}})
            }
        },
        computed: {
            toDo() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("TO_DO");
                    })
                    return this.paginate(filteredTasks, 3, this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("TO_DO");
                    })
                    //return filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase()))
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
            },
            codeReview() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("CODE_REVIEW");
                    })
                    return this.paginate(filteredTasks, 3, this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("CODE_REVIEW");
                    })
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
            },
            inProgress() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("IN_PROGRESS");
                    })
                    return this.paginate(filteredTasks, 3, this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("IN_PROGRESS");
                    })
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
            },
            done() {
                if (this.query === '') {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("DONE");
                    })
                    return this.paginate(filteredTasks, 3, this.page);
                } else {
                    let filteredTasks = this.tasks.filter((task) => {
                        return task.state.includes("DONE");
                    })
                    return this.paginate(filteredTasks.filter((task) => task.name.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                }
            }
        },

    }
</script>

<style scoped>
    .task-board-wrapper {
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
    }

    .taskboard-container {
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
    }

    .taskboard-filters {
        display: flex;
        justify-content: center;
        align-self: center;
        width: 30%;
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
