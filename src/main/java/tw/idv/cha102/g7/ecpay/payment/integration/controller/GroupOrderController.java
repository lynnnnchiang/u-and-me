package tw.idv.cha102.g7.ecpay.payment.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.cha102.g7.ecpay.payment.integration.service.GroupOrderService;

@RestController
@CrossOrigin
public class GroupOrderController {

    @Autowired
    GroupOrderService groupOrderService;

    @PostMapping("/groupPay/{groupId}/{total}")
    public String groupCheckout(@PathVariable Integer groupId,
                                @PathVariable Integer total){

        String aioCheckOutAllForm = groupOrderService.groupCheckout(groupId, total);

        return aioCheckOutAllForm;
    }

    @PostMapping("/getResponse")
    public void getResponse(){
    }
}
