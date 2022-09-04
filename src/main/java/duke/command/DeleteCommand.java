package duke.command;

import duke.DukeException;
import duke.storage.TaskList;
import duke.ui.BotUI;

/**
 * Represents a deleting command of task. A <code>DeleteCommand</code> object stores
 * the details of the task as a String type Integer. eg. "1"
 *
 */
public class DeleteCommand extends Command {
    private final String detail;

    /**
     * Constructs DeleteCommand object
     *
     * @param command command of the user input
     * @param detail detail of the user input as String type Integer
     */
    public DeleteCommand(String command, String detail) {
        super(command);
        this.detail = detail;
    }

    /**
     * Deletes Task from the TaskList
     *
     * @param taskList stores the list of tasks
     * @param ui Object that responsible in returning necessary formatted String
     *           to print on the user interface
     * @return String of suitable response according to the user input through BotUI object.
     * @throws DukeException - thrown when NumberFormatException or IndexOutOfBoundsException is
     *     catch cause by invalid user input. e.g. delete1 or delete someNonIntegerText.
     */
    @Override
    public String execute(TaskList taskList, BotUI ui) throws DukeException {
        try {
            int taskIdx = Integer.parseInt(detail) - 1;
            return ui.successRemoveTask(taskList, taskList.delete(taskIdx));
        } catch (NumberFormatException ex) {
            throw new DukeException(ui.invalidCheckFormat());
        } catch (IndexOutOfBoundsException ex) {
            throw new DukeException(ui.taskNotExist(taskList));
        }

    }

    /**
     * Returns the true/false of the command exit status that
     * will cause duke stop running
     *
     * @return the true/false of the command exit status
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
