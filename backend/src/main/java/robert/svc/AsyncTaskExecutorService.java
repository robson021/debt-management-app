package robert.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskExecutorService {

    private static final Logger log = LoggerFactory.getLogger(AsyncTaskExecutorService.class);

    private final ThreadPoolTaskExecutor taskExecutor;

    public AsyncTaskExecutorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setQueueCapacity(3);
        executor.setMaxPoolSize(8);
        executor.setKeepAliveSeconds(30);
        this.taskExecutor = executor;
    }

    void submit(Runnable task) {
        taskExecutor.execute(task);
        log.info("Submitted new task. Active now: {}", taskExecutor.getActiveCount());
    }
}
