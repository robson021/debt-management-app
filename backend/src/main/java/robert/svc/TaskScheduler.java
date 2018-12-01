package robert.svc;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import robert.svc.api.LogGrabber;

@Component
@RequiredArgsConstructor
public class TaskScheduler {

    private final LogGrabber logGrabber;

    @Scheduled(cron = "0 0 4 * * *")
    public void sendArchivedLogs() {
        logGrabber.findAndSendArchivedLogs();
    }
}
