package robert.svc;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import robert.svc.api.AsyncTaskService;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

	private final TaskExecutor taskExecutor;

	public AsyncTaskServiceImpl() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setQueueCapacity(3);
		executor.setMaxPoolSize(4);
		executor.setKeepAliveSeconds(30);
		this.taskExecutor = executor;
	}

	@Override
	public void submit(Runnable task) {
		taskExecutor.execute(task);
	}
}
