public class DeleteCommand extends Command {
    private int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        String task = tasks.deleteTask(taskNumber);
        ui.botReply("Noted. I've removed this task:\n " + task + "\nNow you have " +
                tasks.length() + " tasks in the list");
        storage.saveTasks(tasks);
    }
}