package robert.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class AsyncTaskExecutorService {

    private static final Logger log = LoggerFactory.getLogger(AsyncTaskExecutorService.class);

    private final ThreadPoolTaskExecutor taskExecutor;

    public AsyncTaskExecutorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setKeepAliveSeconds(30);
        executor.initialize();
        this.taskExecutor = executor;
    }

    void submit(Runnable task) {
        taskExecutor.execute(task);
        log.info("Submitted new task. Active now: {}", taskExecutor.getActiveCount());
    }
}
