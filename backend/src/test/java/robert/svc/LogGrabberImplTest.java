package robert.svc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import robert.svc.api.LogGrabber;
import robert.svc.api.MailerService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogGrabberImplTest {

    private static final String MAIL_RECEIVER = "example@mail.pl";

    @Mock
    private MailerService mailerService;

    private LogGrabber logGrabber;

    @Before
    public void setup() {
        logGrabber = new LogGrabberImpl(MAIL_RECEIVER, mailerService);
    }

    @Test
    public void given_noLogs_when_findAndSendArchivedLogs_then_noMailerServiceMethodIsInvoked() {
        logGrabber.findAndSendArchivedLogs();
        verify(mailerService, times(0)).sendEmail(eq(MAIL_RECEIVER), anyString(), anyString(), anyBoolean(), any(File[].class));
    }

    @Test
    public void given_existingLogs_when_findAndSendArchivedLogs_then_mailerServiceMethodIsInvoked() throws IOException {
        // given
        String logFilename1 = "1st.log.xyz.gz";
        String logFilename2 = "2nd.log.xyz.gz";
        createLogFile(logFilename1);
        createLogFile(logFilename2);

        // when
        logGrabber.findAndSendArchivedLogs();

        // then
        verify(mailerService, times(1)).sendEmail(eq(MAIL_RECEIVER), anyString(), anyString(), anyBoolean(), any());

        // todo - cleanup files
    }

    private void createLogFile(String logFilename) throws IOException {
        String logsDirName = "logs";
        Path path = Paths.get(logsDirName);
        if (Files.notExists(path))
            Files.createDirectories(path);

        Path file = Paths.get(logsDirName, logFilename);

        List<String> lines = Arrays.asList("The first line", "The second line");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }
}