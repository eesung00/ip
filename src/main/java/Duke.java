import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Duke {

    BotUI ui;
    private final TaskRecords taskList;

    Duke() {
        this.ui = new BotUI();
        TaskRecords temp;
        try {
            temp = FileManager.read();
        } catch (FileNotFoundException ex) {
            temp = new TaskRecords();
        }
        this.taskList = temp;
    }

    void runBot() {
        System.out.print(ui.sayHello());
        boolean exitDuke = false;
        while (!exitDuke) {
            try {
                String rawCommand = ui.readCommand();
                Command c = Parser.parse(rawCommand);
                c.execute(taskList, ui);
                exitDuke = c.isExit();
            } catch (DukeException de) {
                System.out.print(de.getMessage());
            }
        }

        try {
            FileManager.write(this.taskList);
        } catch (IOException ex) {
            System.out.println("Error while Saving File!");
        }
        System.out.print(ui.botDivider());
        System.out.println(ui.sayBye());
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.runBot();
    }
}
