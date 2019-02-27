package robert.svc;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import robert.svc.api.LogGrabber;

@Component
@RequiredArgsConstructor
public class TaskScheduler {

    private final LogGrabber logGrabber;

    @Scheduled(fixedDelay = 24 * 3600 * 1000)
    public void sendArchivedLogs() {
        logGrabber.findAndSendArchivedLogs();
    }
}
