package com.personal.redditclone.service;

import com.personal.redditclone.exceptions.SpringRedditException;
import com.personal.redditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    private final MailContentBuilder mailContentBuilder;

    public void sendMail(NotificationEmail notificationEmail)  {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try{
            mailSender.send(messagePreparator);
            log.info("Activation email sent");
        }catch (MailException e){
            throw new SpringRedditException("Exception occured while sending mail to "+notificationEmail.getRecipient());
        }
    }
}
