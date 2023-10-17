package tw.idv.cha102.g7.ecpay.payment.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.cha102.g7.ecpay.payment.integration.service.OrderService;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/ecpayTest")
    public String ecpayCheckout(){
        String aioCheckOutAllForm = orderService.ecpayCheckout();

        return aioCheckOutAllForm;
    }


}
