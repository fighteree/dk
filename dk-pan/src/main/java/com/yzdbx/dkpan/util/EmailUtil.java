package com.yzdbx.dkpan.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/**
 * springboot整合mail工具类
 */
@Data
@Slf4j
@Component
@SuppressWarnings("all")
@ConfigurationProperties(prefix = "spring.mail")
public class EmailUtil {
    @Autowired
    JavaMailSender mailSender;
    private String from;
    private final static String CODE_CONSTANS = "qwert9yu0iopQW876ERTYUIO5Pasdfghj1klASDFGH2JK4LZX3CVBNM";
    private final Random random = new Random();

    /**
     * 收件人
     *
     * @param to
     * @return 验证码
     */
    public String sendMail(String to) {
        //获取4位数随机字母验证码
        String code = createCode();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //发件人
            helper.setFrom(from);
            //设置发送邮件内容
            String href = "<a href=\"http://localhost:8080/reg/active?code=" + code + "&email=" + to + "\">点击激活</a>";
            helper.setText("<h2>【DK云盘】尊敬的用户，你好<h2><h3>你的激活码为:" + code + ",一分钟内有效！" + href + "</h3>", true);
            //收件人
            helper.setTo(to);
            mailSender.send(message);
            log.info("code:{}", code);
            return code;
        } catch (MessagingException e) {
            log.error("邮件发送失败:{}" + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private String createCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(CODE_CONSTANS.charAt(random.nextInt(36)));
        }
        return sb.toString();
    }

}
