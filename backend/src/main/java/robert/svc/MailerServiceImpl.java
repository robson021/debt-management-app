package robert.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileSystemUtils;
import robert.svc.api.MailerService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Profile("mailer")
public class MailerServiceImpl implements MailerService {

    private static final Logger log = LoggerFactory.getLogger(MailerServiceImpl.class);

    private final JavaMailSender mailSender;

    private final AsyncTaskExecutorService asyncTaskExecutorService;

    public MailerServiceImpl(JavaMailSender mailSender, AsyncTaskExecutorService asyncTaskExecutorService) {
        this.mailSender = mailSender;
        this.asyncTaskExecutorService = asyncTaskExecutorService;
    }

    @Override
    public void sendEmail(String receiverEmail, String topic, String body, File file, boolean deleteFileAfterIsSent) {
        Assert.hasText(receiverEmail, "Receiver cannot be null");
        asyncTaskExecutorService.submit(() -> {
            try {
                mailSender.send(createMessage(receiverEmail, topic, body, file));
                log.info("Mail has been sent to {}", receiverEmail);
            } catch (Exception ex) {
                log.error("Could not send email to {}\n{}", receiverEmail, ex.getMessage());
            } finally {
                if (deleteFileAfterIsSent && file != null)
                    FileSystemUtils.deleteRecursively(file);
            }
        });
    }

    private MimeMessage createMessage(String receiverEmail, String topic, String body, File file) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(receiverEmail);
        helper.setSubject(topic);
        helper.setText(body);
        if (file != null) {
            FileSystemResource attachment = new FileSystemResource(file);
            helper.addAttachment(attachment.getFilename(), attachment);
        }
        return mimeMessage;
    }

}
