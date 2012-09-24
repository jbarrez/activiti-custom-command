package be.jorambarrez.experimental;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;


public class RemoveCommentsCommand implements Command<Object> {
	
	private String taskId;
	
	public RemoveCommentsCommand(String taskId) {
		this.taskId = taskId;
	}

	public Void execute(CommandContext commandContext) {
		commandContext.getCommentManager().deleteCommentsByTaskId(taskId);
		return null;
	}

}
