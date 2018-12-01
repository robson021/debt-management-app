package robert.svc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import robert.svc.api.LogGrabber;
import robert.svc.api.MailerService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

@Service
@Slf4j
public class LogGrabberImpl implements LogGrabber {

    private final FilenameFilter fileFilter = new WildcardFileFilter("*.log.*.gz");

    private final MailerService mailSender;

    private final String mailReceiver;

    public LogGrabberImpl(@Value("${robert.defaultMailReceiver}") String mailReceiver, MailerService mailSender) {
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
    }

    @PostConstruct
    void run() {
        findAndSendArchivedLogs();
    }

    @Override
    public void findAndSendArchivedLogs() {
        File[] files = new File("./logs").listFiles(fileFilter);
        if (ArrayUtils.isNotEmpty(files)) {
            log.info("Current archived logs: {}", Arrays.toString(files));
            mailSender.sendEmail(mailReceiver, "Archived logs", "Gathered logs", true, files);
        } else {
            log.info("No archived logs have been found");
        }
    }
}
