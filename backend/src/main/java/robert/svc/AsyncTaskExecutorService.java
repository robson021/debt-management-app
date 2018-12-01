package robert.svc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Lazy
@Slf4j
public class AsyncTaskExecutorService {

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
