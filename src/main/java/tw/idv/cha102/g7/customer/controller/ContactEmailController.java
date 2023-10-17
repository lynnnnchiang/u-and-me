package tw.idv.cha102.g7.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.cha102.g7.customer.entity.ContentEmail;
import tw.idv.cha102.g7.customer.service.ContactMailService;

@RestController
public class ContactEmailController {

    @Autowired
    private ContactMailService mailService;


    @PostMapping("/contact")
    public void sendSimpleMail(@RequestBody ContentEmail contentEmail) {

        try {
            mailService.sendContactMail(contentEmail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
