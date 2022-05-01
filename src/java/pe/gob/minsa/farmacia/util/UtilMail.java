package pe.gob.minsa.farmacia.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class UtilMail {

    private MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public static boolean isMailValid(String email) {
        System.out.println(email);
        Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        
        return matcher.matches();        
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {        
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }
    
    public void sendMail(String usuario, String clave) {
        JavaMailSenderImpl mailSenderImp = (JavaMailSenderImpl) mailSender;
        simpleMailMessage.setFrom(mailSenderImp.getUsername());        
        SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);

        message.setText(String.format(
                simpleMailMessage.getText(), usuario, clave));

        mailSender.send(message);

    }
}
