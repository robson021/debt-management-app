package robert.svc.api;

import java.io.File;

public interface MailerService {

    void sendEmail(String receiverEmail, String topic, String body, boolean deleteFilesAfterIsSent, File... files);

    default void sendEmail(String receiverEmail, String topic, String body) {
        sendEmail(receiverEmail, topic, body, false, null);
    }

}
