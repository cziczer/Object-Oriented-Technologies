package command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandRegistry {

	private ObservableList<Command> commandStack = FXCollections
			.observableArrayList();

	private Stack<Command> redoStack = new Stack<>();

	public void executeCommand(Command command) {
		command.execute();
		commandStack.add(0, command);
		redoStack.removeAllElements();
	}

	public void redo() {
		if(!redoStack.isEmpty()){
			Command command = redoStack.pop();
			command.redo();
			commandStack.add(0, command);
		}

	}

	public void undo() {
		if(!commandStack.isEmpty()){
			Command command = commandStack.remove(0);
			command.undo();
			redoStack.push(command);
		}
	}

	public ObservableList<Command> getCommandStack() {
		return commandStack;
	}
}