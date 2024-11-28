function updateTaskOnFrontend(taskId, updatedTask) {
    const taskElements = document.querySelectorAll('.task');
    taskElements.forEach(taskElement => {
        if (taskElement.getAttribute('data-task-id') === taskId.toString()) {
            taskElement.innerHTML = `
                <h2>${updatedTask.title}</h2>
                <p>${updatedTask.description}</p>
                <p>Status: ${updatedTask.status}</p>
                <p>Due Date: ${updatedTask.dueDate}</p>
                <p>Assigned To: ${updatedTask.assignedTo}</p>
                <button onclick="toggleUpdateForm(${taskId})">Update Task</button>
            `;
        }
    });
}

async function populateUpdateForm(taskId) {
    try {
        const task = await sendRequest(`http://localhost:8189/tasks/${taskId}`, 'GET');
        document.getElementById('update-task-title').value = task.title;
        document.getElementById('update-task-description').value = task.description;
        document.getElementById('update-task-status').value = task.status;
        document.getElementById('update-task-dueDate').value = task.dueDate;
        document.getElementById('update-task-assignedTo').value = task.assignedTo;
    } catch (error) {
        console.error('Error:', error);
    }
}

function toggleUpdateForm(taskId) {
    const addTaskForm = document.getElementById('task-form');
    const updateTaskForm = document.getElementById('update-task-form');

    addTaskForm.style.display = 'none';
    updateTaskForm.style.display = 'block';

    populateUpdateForm(taskId);
}