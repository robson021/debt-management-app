package robert.svc;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import robert.svc.api.LogGrabber;

@Component
@Profile("prod")
public class TaskScheduler {

    private final LogGrabber logGrabber;

    TaskScheduler(LogGrabber logGrabber) {
        this.logGrabber = logGrabber;
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void reportCurrentTime() {
        logGrabber.findAndSendArchivedLogs();
    }
}
