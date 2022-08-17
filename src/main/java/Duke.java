import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private Scanner sc;
    private static String botName = "DIGITAL DADDY";
    private static String emoji = "\uD83E\uDD16";
    private List<Task> taskList = new ArrayList<>();

    Duke(Scanner sc) {
        this.sc = sc;
    }

    private static void botReply(String input) {
        String lineSeparator = "____________________________________________________________";
        String reply = String.format("%s\n%s %s %s \n%s \n%s", lineSeparator, emoji, botName, emoji, input, lineSeparator);
        System.out.println(reply);
    }

    private String taskListToString(List<Task> taskList) {
        if (taskList.isEmpty()) {
            return "You haven't added anything to your list!";
        }

        String taskListString = "";

        for (int index = 1; index <= taskList.size(); index++) {
            Task listItem = taskList.get(index - 1);
            String listItemString = index + ". " + listItem.toString();
            if (index != taskList.size()) {
                listItemString += "\n";
            }
            taskListString += listItemString;
        }

        return taskListString;
    }

    public void start() {
        botReply("Hello! I'm " + botName + "\nWhat can I do for you?");
        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                botReply("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")) {
                botReply(this.taskListToString(this.taskList));
                continue;
            }

            if (input.startsWith("mark ") || input.startsWith("unmark ")) {
                String[] parts = input.split(" ");

                // Input validation
                if (parts.length != 2) {
                    botReply("Wrong input format! mark/unmark <item number>\ne.g. 'mark 3'");
                    continue;
                }

                int taskIndex;
                Task pickedTask;
                try {
                    taskIndex = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException e) {
                    botReply("Please enter a valid task number! mark/unmark <item number>\ne.g. 'mark 3'");
                    continue;
                }
                try {
                    pickedTask = this.taskList.get(taskIndex);
                } catch (IndexOutOfBoundsException e) {
                    botReply("Task number doesn't exist!");
                    continue;
                }

                String markOperation = parts[0];
                String reply = "";

                if (markOperation.equals("mark")) {
                    pickedTask.markTask(true);
                    reply += "Nice! I've marked this task as done:\n";
                } else if (markOperation.equals("unmark")) {
                    pickedTask.markTask(false);
                    reply += "OK, I've marked this task as not done yet:\n";
                }

                reply += "\t" + pickedTask.toString();
                botReply(reply);

                continue;
            }

            this.addToList(input);
        }
    }

    private void addToList(String item) {
        Task newTask = new Task(item);
        this.taskList.add(newTask);
        botReply("added: " + item);
    }

    public static void main(String[] args) {
        // Create a scanner to read from standard input.
        Scanner sc = new Scanner(System.in);

        Duke duke = new Duke(sc);
        duke.start();

        sc.close();
    }


}
