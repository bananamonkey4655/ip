import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> storedTasks) {
        this.list = storedTasks;
    }

    public int length() {
        return this.list.size();
    }

    public void addTask(Task task) {
        this.list.add(task);
    }

    public String deleteTask(int taskIndex) throws DukeException {
        Task task;
        try {
            task = this.list.get(taskIndex);
            this.list.remove(taskIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Task number doesn't exist!");
        }
        return task.toString();
    }

    public String markTask(int taskIndex, boolean done) throws DukeException {
        Task task;
        try {
            task = this.list.get(taskIndex);
            task.markTask(done);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Task number doesn't exist!");
        }
        return task.toString();
    }

    public String getTasksString() {
        String tasksString = "";
        for (int i = 0; i < this.list.size(); i++) {
            Task task = this.list.get(i);
            String taskItem = task.getTask() + "\n";
            tasksString += taskItem;
        }
        return tasksString;
    }

    @Override
    public String toString() {
        //todo: use string builder
        if (list.isEmpty()) {
            return "You haven't added anything to your list!";
        }

        String taskListString = "";

        for (int index = 1; index <= list.size(); index++) {
            Task listItem = list.get(index - 1);
            String listItemString = index + ". " + listItem.toString();
            if (index != list.size()) {
                listItemString += "\n";
            }
            taskListString += listItemString;
        }

        return taskListString;
    }
}
