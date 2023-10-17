package tw.idv.cha102.g7.ecpay.payment.integration.service;

import org.springframework.stereotype.Service;
import tw.idv.cha102.g7.ecpay.payment.integration.AllInOne;
import tw.idv.cha102.g7.ecpay.payment.integration.domain.AioCheckOutALL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class OrderService {

    public String ecpayCheckout() {

        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

        AllInOne all = new AllInOne("");
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId); //設定訂單編號
        obj.setMerchantTradeDate("2017/01/01 08:05:23"); //設定交易日期
        obj.setTotalAmount("50"); //設定金額
        obj.setTradeDesc("test"); //設定交易描述
        obj.setItemName("Test"); //設定交易物品名稱


        obj.setReturnURL("http://localhost:8080/u-and-me/");//設定返回網址
        obj.setNeedExtraPaidInfo("N"); //是否需要額外付費資訊
        obj.setClientBackURL("http://localhost:8080/u-and-me/tmp/Front/group/myGroupList.html");//設定返回網址不行時的替代網址

        String form = all.aioCheckOut(obj, null);

        return form;
    }
}
