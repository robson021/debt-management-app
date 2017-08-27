package robert.svc.api;

import java.io.File;

public interface MailerService {

    void sendServerLogs(String receiverEmail);

    void sendEmail(String receiverEmail, String topic, String body, File file, boolean deleteFileAfterIsSent);

}
