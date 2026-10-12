package vn.edu.hcmuaf.fit.pkcn.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

//Tạo 1 lớp EmailUtils và fill các thông tin FROM_EMAIL/APP_PASSWORD
public class EmailUtils_template {
    // Thông tin cấu hình SMTP của Gmail
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";
    private static final String FROM_EMAIL = "w@gmail.com";
    private static final String APP_PASSWORD = "wwwwww";

    public static void sendEmail(String toEmail, String subject, String body) {
        // thiết lập các thuộc tính kết nối
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Tạo Authenticator để đăng nhập vào Gmail
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        };

        // tạo phiên làm việc (Session)
        Session session = Session.getInstance(props, auth);

        try {
            // tạo tin nhắn MimeMessage
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(FROM_EMAIL, "PhuKienCongNghe"));
            msg.setReplyTo(InternetAddress.parse(FROM_EMAIL, false));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(body, "text/html; charset=UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Gửi thư
            Transport.send(msg);
            System.out.println(">>> Email đã được gửi thành công tới: " + toEmail);

        } catch (MessagingException e) {
            System.err.println(">>> Lỗi khi gửi Email: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        EmailUtils.sendEmail("23130390@st.hcmuaf.edu.vn", "Test thử", "Chào bạn, đây là mail test từ Java!");
    }
}