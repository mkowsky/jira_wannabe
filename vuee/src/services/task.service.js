import axios from 'axios';


const API_URL = 'http://localhost:8080/tasks/';

class TaskService {

    defaultFunction() {
        console.log('default funciton hello');
    }

    createNewTaks(task, chosenUser) {
        console.log(task.taskProject);
        return axios.post(API_URL + 'create-new', {
            taskManagerID: task.taskManagerID,
            taskPriority: task.taskPriority,
            department: task.department,
            taskTitle: task.taskTitle,
            taskDescription: task.taskDescription,
            state: task.lifecycle,
            taskDeadline: task.taskDeadline,
            projectID: task.taskProject,
            taskUser: chosenUser,

        })
    }

    delteTask(value) {
        return axios.post(API_URL + 'delete-task', null, {
            params: {
                taskID: value
            }
        })
    }

    getAllTasks() {
        return null
    }




}

export default new TaskService();
