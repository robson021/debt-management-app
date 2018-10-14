package robert.svc;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import robert.svc.api.LogGrabber;
import robert.svc.api.MailerService;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

@Service
@Lazy
public class LogGrabberImpl implements LogGrabber {

    private static final Logger log = LoggerFactory.getLogger(LogGrabber.class);

    private final FilenameFilter fileFilter;

    private final MailerService mailSender;

    private final String mailReceiver;

    public LogGrabberImpl(@Value("${robert.defaultMailReceiver}") String mailReceiver, MailerService mailSender) {
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
        String logFilePattern = "*.log.*.gz";
        this.fileFilter = new WildcardFileFilter(logFilePattern);
        log.info("Log file name pattern to search: {}", logFilePattern);
    }

    @Override
    public void findAndSendArchivedLogs() {
        File[] files = new File("./logs").listFiles(fileFilter);
        if (files != null && files.length > 0) {
            log.info("Current archived logs: {}", Arrays.toString(files));
            mailSender.sendEmail(mailReceiver, "Archived logs", "Gathered logs", true, files);
        } else {
            log.info("No archived logs have been found");
        }
    }
}
