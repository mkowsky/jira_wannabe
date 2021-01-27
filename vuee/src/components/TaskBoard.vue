<template>
    <div class="task-board-container">

        <div class="taskboard-filters">

            <div  style=" display: flex; align-items: center">

                <p  class="ma-2 filters-text" >Active filters: <span v-if="!filtrsActive"> none</span></p>
                <template v-if="filtrsActive" style="display: flex; ">


                <v-chip
                        v-if="query"
                        close
                        :color="colorAccent"
                        text-color="white"
                        @click:close="clearFilters('query')"
                        style="margin-right: 15px;"
                >
                    Task name: {{query}}
                </v-chip>
                <v-chip
                        v-if="queryPriority"
                        close
                        :color="colorAccent"
                        text-color="white"
                        @click:close="clearFilters('priority')"
                        style="margin-right: 15px;"
                >
                    Task priority: {{queryPriorityName}}
                </v-chip>
                <v-chip
                        v-if="queryProject"
                        close
                        :color="colorAccent"
                        text-color="white"
                        @click:close="clearFilters('project')"
                        style="margin-right: 15px;"
                >
                    Project: {{queryProject}}
                </v-chip>
                </template>
            </div>

            <div class="filters-wrapper">
                <div  class="filter">
                    <v-text-field solo label="Search by task name" v-model="queryCopy"></v-text-field>
                </div>
                <div  class="filter">
                    <v-select solo
                              :items="priorities"
                              item-text="name"
                              item-value="value"
                              label="Task priority"
                              v-model="queryPriorityCopy"></v-select>
                </div>
                <div v-if="projects" class="filter">
                    <v-select solo label="Only from project" :items="projects" item-text="projectName"
                              item-value="projectName"
                              v-model="queryProjectCopy"></v-select>
                </div>


                <v-btn solo @click="applyFilters()" :disabled="applyFiltersButtonEnabled" style="height: 45px;">APPLY
                    FILTERS
                </v-btn>

            </div>


        </div>


        <div class="task-board-wrapper">
            <v-card class="column">
                <v-card-title
                        class="column-title">
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

            <v-card class="column">
                <v-card-title
                        class="column-title">
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

            <v-card class="column">
                <v-card-title
                        class="column-title">
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

            <v-card class="column">
                <v-card-title
                        class="column-title">
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


        <div v-if="pages > 1">
            <v-pagination

                    v-model="page"
                    :length="pages"
                    @input="pageChanged($event)"
                    :color="colorAccent"
                    class="pagination-style"
            ></v-pagination>
        </div>

    </div>
</template>

<script>
    import Task from './Task'
    import col from "@/assets/css/colors";


    export default {
        name: "TaskBoard",
        components: {Task},
        props: {
            tasks: {},
            projects: {},
            pages: {},

        },
        data() {
            return {
                colorAccent: col.ACCENT,
                colorDark: col.DARK,
                filtrsActive: false,
                page: 1,
                query: '',
                queryCopy: '',
                queryPriority: '',
                queryPriorityCopy: '',
                queryProject: '',
                queryProjectCopy: '',

                queryPriorityName: '',
                priorities: [
                    {value: "ABLE_TO_WAIT", name: "ABLE TO WAIT"},
                    {value: "IMPORTANT", name: "IMPORTANT"},
                    {value: "URGENT", name: "URGENT"},
                    {value: "IMMEDIATE", name: 'IMMEDIATE'},
                ],
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
                for (let i = 0; i < this.priorities.length; i++) {
                    if (this.priorities[i].value === this.queryPriorityCopy) this.queryPriorityName = this.priorities[i].name;
                }
                this.filtrsActive = true;
                this.page = 1;
            },
            clearFilters(value) {
                switch (value) {
                    case 'query':
                        this.queryCopy = '';
                        this.query = '';
                        break;
                    case 'project':
                        this.queryProjectCopy = '';
                        this.queryProject = '';
                        break;
                    case 'priority':
                        this.queryPriorityCopy = '';
                        this.queryPriority = '';
                        break;
                }

                if ((this.query) || (this.queryPriority) || (this.queryProject)) this.filtrsActive = true;
                else this.filtrsActive = false;
            }
        },
        computed: {
            toDo() {
                let filteredTasks = this.tasks.filter((task) => {
                    return task.taskState.includes("TO_DO");
                })
                if ((this.queryPriority) && (this.query) && (this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.taskPriority.includes(this.queryPriority))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if ((this.queryPriority) && (this.query) && !(this.queryProject)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase())) && (task.taskPriority.includes(this.queryPriority)))), 3, this.page);
                } else if ((this.queryPriority) && (this.queryProject) && !(this.query)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())) && (task.taskPriority === (this.queryPriority)))), 3, this.page);
                } else if ((this.query) && (this.queryProject) && !(this.queryPriority)) {
                    return this.paginate(filteredTasks.filter((task) => ((task.taskName.toLowerCase().includes(this.query.toLowerCase()))
                        && (task.projectName.toLowerCase().includes(this.queryProject.toLowerCase())))), 3, this.page);
                } else if (this.query) {
                    return this.paginate(filteredTasks.filter((task) => task.taskName.toLowerCase().includes(this.query.toLowerCase())), 3, this.page);
                } else if (this.queryPriority) {
                    return this.paginate(filteredTasks.filter((task) => task.taskPriority.includes(this.queryPriority)), 3, this.page);
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
            },
            applyFiltersButtonEnabled() {
                if ((this.queryCopy) || (this.queryPriorityCopy) || (this.queryProjectCopy)) return false;
                else return true;
            }
        },
    }
</script>

<style scoped lang="scss">
    @import "../assets/css/main";


        * {
            box-sizing: border-box;
        }


        .task-board-container {
            display: flex;
            flex-direction: column;
            padding: 30px;

        }


        .task-board-wrapper {
            width: 100%;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            margin-bottom: 15px;
        }

        .taskboard-filters {
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
            height: 100px;
        }

        .filters-wrapper {
            display: flex;
            flex-direction: row;
        }
        .filters-text{
            text-transform: uppercase;
            font-weight: 600;
            font-size: 26px;
            opacity: 0.87;
        }

        .column {
            background: $color-primary-dark;
            width: 20%;
            align-items: center;
            display: flex;
            flex-direction: column;
            height: 680px;
            padding: 0 0 0 20px;

        }

        .column-title {
            color: $color-primary-white;
            font-weight: 600;
            font-size: 30px;
            align-self: center
        }

        .gap {
            margin-bottom: 20px;
            cursor: pointer;
        }

        .filter {
            width: 300px;
            margin-right: 20px;
        }
    




</style>
