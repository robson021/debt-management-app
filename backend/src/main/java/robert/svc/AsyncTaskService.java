package robert.svc;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

	private final TaskExecutor taskExecutor;

	public AsyncTaskService() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setQueueCapacity(3);
		executor.setMaxPoolSize(4);
		executor.setKeepAliveSeconds(30);
		this.taskExecutor = executor;
	}

	void submit(Runnable task) {
		taskExecutor.execute(task);
	}
}
