export default class Task {
    constructor(taskManagerID, taskPriority, department, taskTitle, taskDescription, state, taskDeadline, taskProject) {
        this.taskManagerID = taskManagerID;
        this.taskPriority = taskPriority;
        this.department = department;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.lifecycle = state;
        this.taskDeadline = taskDeadline;
        this.taskProject = taskProject;
    }

    clearData(){
        this.taskTitle = "";
    }
}
