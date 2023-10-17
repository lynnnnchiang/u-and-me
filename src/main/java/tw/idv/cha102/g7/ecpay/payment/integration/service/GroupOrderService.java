package tw.idv.cha102.g7.ecpay.payment.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idv.cha102.g7.ecpay.payment.integration.AllInOne;
import tw.idv.cha102.g7.ecpay.payment.integration.domain.AioCheckOutALL;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.repo.GroupRepository;
import tw.idv.cha102.g7.group.repo.MemberDetailRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class GroupOrderService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MemberDetailRepository memberDetailRepository;

    public String groupCheckout(Integer groupId, Integer total) {

        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

        Group group = groupRepository.findById(groupId).orElse(null);

        AllInOne all = new AllInOne("");
//        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId); //設定訂單編號
        obj.setMerchantTradeDate("2017/01/01 08:05:23"); //設定交易日期
        obj.setTotalAmount(total.toString()); //設定金額
        obj.setTradeDesc(group.getGroupDesc()); //設定交易描述
        obj.setItemName(group.getTheme()); //設定交易物品名稱
        obj.setReturnURL("http://localhost:8080/u-and-me/getResponse");//設定返回的CONTROLLER
        obj.setNeedExtraPaidInfo("N"); //是否需要額外付費資訊
        obj.setClientBackURL("http://localhost:8080/u-and-me/tmp/Front/group/groupFav.html");//設定返回網址

        String form = all.aioCheckOut(obj, null);

        return form;
    }
}
