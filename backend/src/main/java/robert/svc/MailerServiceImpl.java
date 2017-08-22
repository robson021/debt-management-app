package robert.svc;

import java.io.File;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileSystemUtils;

import robert.svc.api.MailerService;

@Service
@Profile("prod")
public class MailerServiceImpl implements MailerService {

	private static final Logger log = LoggerFactory.getLogger(MailerServiceImpl.class);

	private final String applicationLogFile;

	private final JavaMailSender mailSender;

	private final AsyncTaskService asyncTaskService;

	public MailerServiceImpl(@Value("${logging.file}") String applicationLogFile, JavaMailSender mailSender, AsyncTaskService asyncTaskService) {
		this.applicationLogFile = applicationLogFile;
		this.mailSender = mailSender;
		this.asyncTaskService = asyncTaskService;
		log.info("Application log file for mail service: '{}'", this.applicationLogFile);
	}

	@Override
	public void sendServerLogs(String receiverEmail) {
		log.info("Server logs request to {}", receiverEmail);
		sendEmail(receiverEmail, "Server log", "Debts management app - " + new Date(), new File(applicationLogFile), false);
	}

	@Override
	public void sendEmail(String receiverEmail, String topic, String body, File file, boolean deleteFileAfterIsSend) {
		Assert.hasText(receiverEmail, "Receiver cannot be null");
		asyncTaskService.submit(() -> {
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setTo(receiverEmail);
				helper.setSubject(topic);
				helper.setText(body);
				if ( file != null ) {
					FileSystemResource attachment = new FileSystemResource(file);
					helper.addAttachment(attachment.getFilename(), attachment);
				}
			} catch (Exception ignored) {
				log.error("Could not send server logs to {}", receiverEmail);
			} finally {
				if ( deleteFileAfterIsSend && file != null ) {
					FileSystemUtils.deleteRecursively(file);
				}
			}
		});
	}

}
