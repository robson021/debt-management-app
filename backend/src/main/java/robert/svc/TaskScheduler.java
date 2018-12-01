package robert.svc;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import robert.svc.api.LogGrabber;

@Component
public class TaskScheduler {

    private final LogGrabber logGrabber;

    TaskScheduler(LogGrabber logGrabber) {
        this.logGrabber = logGrabber;
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void sendArchivedLogs() {
        logGrabber.findAndSendArchivedLogs();
    }
}
