package be.jorambarrez.experimental;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


public class RemoveCommentCommandTest {
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Test
	public void testRemoveComment() {
		TaskService taskService = activitiRule.getTaskService();
		
		// Create a simple test task
		Task testTask = taskService.newTask();
		testTask.setName("test task");
		taskService.saveTask(testTask);
		
		// Add some test comments
		taskService.addComment(testTask.getId(), null, "comment 1");
		taskService.addComment(testTask.getId(), null, "comment 2");
		taskService.addComment(testTask.getId(), null, "comment 3");
		
		// Verify
		assertEquals(3, taskService.getTaskComments(testTask.getId()).size());
		
		// Remove all comments
		CommandExecutor commandExecutor = ((ProcessEngineImpl) activitiRule.getProcessEngine()).getProcessEngineConfiguration().getCommandExecutorTxRequired();
		RemoveCommentsCommand removeCommentsCommand = new RemoveCommentsCommand(testTask.getId());
		commandExecutor.execute(removeCommentsCommand);
		
		// Verify that comments are deleted (and basically, that our custom comment works)
		assertEquals(0, taskService.getTaskComments(testTask.getId()).size());
		
		// Cleanup after ourseleves
		taskService.deleteTask(testTask.getId());
	}
	
}
