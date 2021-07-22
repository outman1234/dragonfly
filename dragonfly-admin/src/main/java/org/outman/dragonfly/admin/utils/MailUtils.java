package org.outman.dragonfly.admin.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @ClassName MailUtils
 * @Description TODO
 * @Author OutMan
 * @create 2021-06-15 15:58
 */
@Component
public class MailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public void setEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("toviion@sz.com");
        message.setTo(toEmail);
        message.setSubject("主题：找回密码验证码");
        message.setText("验证码为：" + code);
        mailSender.send(message);
    }
}
