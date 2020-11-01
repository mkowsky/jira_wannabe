export default class Task {
    constructor(taskManagerID, taskPriority, department, taskTitle, taskDescription, state, taskDeadline) {
        this.taskManagerID = taskManagerID;
        this.taskPriority = taskPriority;
        this.department = department;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.lifecycle = state;
        this.taskDeadline = taskDeadline;
    }

    clearData(){
        this.taskTitle = "";
    }
}
