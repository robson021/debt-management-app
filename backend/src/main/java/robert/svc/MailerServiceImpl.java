package robert.svc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import robert.svc.api.MailerService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
@Profile("mailer")
@Slf4j
@RequiredArgsConstructor
public class MailerServiceImpl implements MailerService {

    private final JavaMailSender mailSender;

    private final AsyncTaskExecutorService asyncTaskExecutorService;

    @Override
    public void sendEmail(String receiverEmail, String topic, String body, boolean deleteFilesAfterIsSent, File... files) {
        asyncTaskExecutorService.submit(() -> {
            try {
                mailSender.send(createMessage(receiverEmail, topic, body, files));
                log.info("Mail has been sent to {}", receiverEmail);
                if (deleteFilesAfterIsSent) {
                    deleteFilesIfRequired(files);
                }
            } catch (Exception ex) {
                log.error("Error while sending email to {} - {}", receiverEmail, ex.getMessage());
            }
        });
    }

    private MimeMessage createMessage(String receiverEmail, String topic, String body, File... files) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(receiverEmail);
        helper.setSubject(topic);
        helper.setText(body);
        if (files != null && files.length > 0) {
            for (File file : files) {
                FileSystemResource attachment = new FileSystemResource(file);
                helper.addAttachment(attachment.getFilename(), attachment);
            }
        }
        return mimeMessage;
    }

    private void deleteFilesIfRequired(File[] files) throws IOException {
        if (files != null && files.length > 0) {
            for (File file : files)
                FileUtils.forceDelete(file);
        }
    }

}
