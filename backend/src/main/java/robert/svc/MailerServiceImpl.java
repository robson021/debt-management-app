package robert.svc;

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
		Assert.hasText(receiverEmail, "Receiver cannot be null");
		asyncTaskService.submit(() -> {
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setTo(receiverEmail);
				helper.setSubject("Server logs");
				helper.setText("Debts management app - " + new Date());
				FileSystemResource file = new FileSystemResource(applicationLogFile);
				helper.addAttachment(file.getFilename(), file);
			} catch (Exception ignored) {
				log.error("Could not send server logs to {}", receiverEmail);
			}
		});
	}

}
