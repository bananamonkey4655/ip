import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public void saveTasks(TaskList list) {
        FileWriter fr = null;
        try {
            fr = new FileWriter(this.file);
            fr.write(list.getTasksString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources, otherwise disk won't be saved? something about OS buffering
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        Scanner sc = null;

        try {
            if (!this.file.exists()) {
                System.out.println("No existing save file found. Created one for you.");
                try {
                    this.file.getParentFile().mkdirs();
                    this.file.createNewFile();
                } catch (IOException err) {
                    System.out.println("Problem trying to create directory!");
                }
            }
            sc = new Scanner(this.file);
        } catch (FileNotFoundException e) {
            return loadedTasks;
        }

        while (sc.hasNext()) {
            //store each task into an array
            String taskString = sc.nextLine();
            String[] taskData = taskString.split("\\|");

            //remove whitespaces
            for (int i = 0; i < taskData.length; i++) {
                taskData[i] = taskData[i].trim();
            }

            //organise task data into named variables
            String taskType = taskData[0];
            boolean isTaskDone = taskData[1].equals("1");
            String taskDescription = taskData[2];
            LocalDate taskDate = null;
            if (taskType.equals("D") || taskType.equals("E")) {
                taskDate = LocalDate.parse(taskData[3]);
            }

            //Create task and add to list
            Task newTask;
            switch (taskType) {
                case "T":
                    newTask = new Todo(isTaskDone, taskDescription);
                    break;
                case "D":
                    newTask = new Deadline(isTaskDone, taskDescription, taskDate);
                    break;
                case "E":
                    newTask = new Event(isTaskDone, taskDescription, taskDate);
                    break;
                default:
                    throw new DukeException("Task type not defined!");
            }

            loadedTasks.add(newTask);
        }

        sc.close();
        return loadedTasks;
    }
}
