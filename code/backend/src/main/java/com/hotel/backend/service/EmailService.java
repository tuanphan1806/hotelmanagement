package com.hotel.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hotel.backend.entity.User;
import com.hotel.backend.repository.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j(topic = "EMAIL-SERVIVE")
public class EmailService {
    @Value("${spring.sendgrid.from-email}")
    private String from;
    private final SendGrid sendGrid;
    @Value("${spring.sendgrid.templateId}")
    private String templateId;
    @Value("${spring.sendgrid.verificationLink}")
    private String verificationLink;
    private final UserRepository userRepository;
    public void send(String to, String subject,String text){
        Email fromEmail=new Email(from);
        Email toemail=new Email(to);
        Content content= new Content("text/plain",text);
        Mail mail=new Mail(fromEmail,subject,toemail,content);

        Request request=new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response= sendGrid.api(request);
            log.info("SendGrid status: {}", response.getStatusCode());
            log.info("SendGrid body: {}", response.getBody());
            log.info("SendGrid headers: {}", response.getHeaders());
            if (response.getStatusCode() == 202) {
                log.info("Email sent successfully");
            }else{
                log.error("Email sent failed with status: {}", response.getStatusCode());
                log.error("Body: {}", response.getBody());
            }

        } catch (IOException e) {
            log.error("Error occurred while sending email ,error: {}",e.getMessage());
        }
    }

    // email Verificate by sendgrid
    public void emailVerification(String to, String name) throws IOException{
        log.info("Email verification started");
        Email fromEmail=new Email(from,"LuxStay Hotel");
        Email toemail=new Email(to);
        String subject="Xác thực tài khoản";

        //TODO genegrate secretCode and save to database
        String code = UUID.randomUUID().toString();
        String secretCode = String.format("?secretCode=%s", code);

        // Lưu vào DB

        User user = userRepository.findByEmail(to)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setVerificationCode(code);
        userRepository.save(user);

        // dinh nghia template
        Map<String,String> map= new HashMap<>();
        map.put("name",name);
        map.put("verification_link", verificationLink+secretCode);
        // map.put("verification_link", verificationLink);
        map.put("emailUser", to);


        Mail mail =new Mail();
        mail.setFrom(fromEmail);
        mail.setSubject(subject);

        Personalization personalization= new Personalization();
        personalization.addTo(toemail);

        //add to dynamic data
        map.forEach(personalization::addDynamicTemplateData);
        mail.addPersonalization(personalization);
        mail.setTemplateId(templateId);

        Request request= new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        // log.info("Request body: {}", mail.build());
        Response response= sendGrid.api(request);
        if (response.getStatusCode() == 202) {
            log.info("Verification sent successfully");
        }else{
            log.error("Verification sent failed with status: {}", response.getStatusCode());
            log.error("Body: {}", response.getBody());
        }

    }
    
} 