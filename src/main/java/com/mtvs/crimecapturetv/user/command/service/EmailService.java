package com.mtvs.crimecapturetv.user.command.service;

import com.mtvs.crimecapturetv.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final StringRedisTemplate redisTemplate;
    private final CommandUserService userService;

    private final String ePw = createkey();

    public String createkey() {

        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int)(random.nextInt(26)) + 97));
                    // a ~ z (ex. 1+97=98 -> (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int)(random.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((random.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        System.out.println("인증키: "
                + key.toString());
        System.out.println("인증키 객체 주소: " + System.identityHashCode(key));
        return key.toString();
    }

    // 회원 가입 인증 이메일
    private MimeMessage createMessage(String to) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[Crime Capture TV] 본인 인증 메일");

        String msgg = "";

        msgg += "<div style='margin:100px'>";
        msgg += "<h1>이메일 인증번호 안내</h1>";
        msgg += "<br>";
        msgg += "<p>본 메일은 Crime Capture TV 사이트의 회원가입을 위한 이메일 인증입니다.</p>";
        msgg += "<p>아래의 [이메일 인증번호]를 입력하여 본인확인을 해주시기 바랍니다.</p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.</p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br />";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("crimecapturetv@gmail.com", "Crime Capture TV"));

        return message;
    }

    // 아이디 찾기 이메일
    private MimeMessage foundIdMessage(String to, String id) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("Crime Capture TV 요청하신 아이디 정보 보내드립니다.");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 Crime Capture TV 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래의 아이디를 확인해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원님의 Crime Capture TV 아이디 입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "아이디 : <strong>";
        msgg += id + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("crimecapturetv@gmail.com", "Crime Capture TV"));//보내는 사람

        return message;
    }

    // 비밀번호 찾기 이메일
    private MimeMessage foundPasswordMessage(String to) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("Crime Capture TV 비밀번호 변경 안내");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 Crime Capture TV 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>새로운 비밀번호로 로그인 해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>새로운 비밀번호 입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "비밀번호 : <strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("crimecapturetv@gmail.com", "Crime Capture TV"));//보내는 사람

        return message;
    }

    // 회원 가입 인증 메시지 발송
    @Transactional
    public String sendLoginAuthMessage(String to) throws Exception{

        MimeMessage message = createMessage(to);

        try {
            javaMailSender.send(message);
        }catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        setDataExpire(ePw, to, 60 * 5L); // 유효 시간 5분

        return "인증 메일이 발송되었습니다.";
    }

    // 아이디 찾기 아이디 메시지 발송
    public String sendFoundIdMessage(String email) throws Exception {
        String result = "메일로 아이디를 전송했습니다.";
        String id = userService.findIdByEmail(email);
        MimeMessage message = foundIdMessage(email, id);
        try {    //예외처리
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        } catch (AppException e) {
            result = e.getMessage();
        } catch (Exception e) {
            result = "메일 전송에 실패하였습니다.";
        }
        return result;
    }

    // 비밀번호 찾기 새로운 비밀번호 메시지 발송
    public String sendFoundPasswordMessage(String email, String id) throws Exception {
        String result = "메일로 새로운 비밀번호를 전송했습니다";
        String foundUserId = userService.findIdByEmail(email);
        if (!id.equals(foundUserId)) {
            result = "아이디에 해당하는 이메일이 없습니다.";
        } else {
            String newPassword = ePw;
            userService.changePassword(foundUserId, newPassword);
            MimeMessage message = foundPasswordMessage(email);

            try {    //예외처리
                javaMailSender.send(message);
            } catch (MailException es) {
                es.printStackTrace();
                throw new IllegalArgumentException();
            } catch (AppException e) {
                result = e.getMessage();
            } catch (Exception e) {
                result = "메일 전송에 실패하였습니다.";
            }
            setDataExpire(ePw, email, 60 * 5L);
        }

        return result;
    }



    // Redis
    // 인증 번호 확인
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

}
