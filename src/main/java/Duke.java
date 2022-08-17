import java.util.Scanner;

public class Duke {

    BotUI ui;
    private final TaskRecords taskList;

    Duke() {
        this.ui = new BotUI();
        this.taskList = new TaskRecords();
    }

    void runBot() {
        System.out.print(ui.botSpeak(ui.sayHello()));
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();

        while (!input.equals("bye")) {
            try {
                InputChecker.checkInput(input);
                if (input.equals("list")) {
                    System.out.print(ui.botSpeak(ui.showList(taskList)));
                }
                else if (input.startsWith("todo")) {
                    Task task = new ToDos(input.substring(5));
                    taskList.addProcess(task);
                    System.out.print(ui.botSpeak(ui.addStatus(taskList, task)));
                }
                else if (input.startsWith("deadline")) {
                    Task task = new Deadlines(input);
                    taskList.addProcess(task);
                    System.out.print(ui.botSpeak(ui.addStatus(taskList, task)));
                }
                else if (input.startsWith("event")) {
                    Task task = new Events(input);
                    taskList.addProcess(task);
                    System.out.print(ui.botSpeak(ui.addStatus(taskList, task)));
                }
                else if (input.startsWith("mark") || input.startsWith("unmark") || input.startsWith("delete")) {
                    int taskIdx = Integer.parseInt(input.substring(input.length() - 1)) - 1;
                    if (input.startsWith("delete")) {
                        System.out.print(ui.botSpeak(ui.successRemoved(taskList, taskList.delete(taskIdx))));
                        input = scn.nextLine();
                        continue;
                    }
                    Task currTask = taskList.get(taskIdx);
                    currTask = (input.startsWith("mark")) ? currTask.markDone() : currTask.unmarkDone();
                    taskList.addProcess(currTask);
                    System.out.print(ui.botSpeak(ui.informMarkStatus(taskList.get(taskIdx))));
                }
                else {
                    throw new DukeException(ui.invalidCommand());
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.print(ui.botSpeak(ui.taskNotExist(taskList)));
            } catch (NumberFormatException ex) {
                System.out.print(ui.botSpeak(ui.invalidCheckFormat()));
            } catch (DukeException de) {
                System.out.print(ui.botSpeak(de.getMessage()));
            }
            input = scn.nextLine();
        }
        System.out.print(ui.botDivider());
        System.out.println(ui.sayBye());
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.runBot();
    }
}
