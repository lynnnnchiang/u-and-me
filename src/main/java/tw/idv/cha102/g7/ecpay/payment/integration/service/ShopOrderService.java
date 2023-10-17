package tw.idv.cha102.g7.ecpay.payment.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idv.cha102.g7.ecpay.payment.integration.AllInOne;
import tw.idv.cha102.g7.ecpay.payment.integration.domain.AioCheckOutALL;
import tw.idv.cha102.g7.shop.entity.*;
import tw.idv.cha102.g7.shop.repo.CartListRepository;
import tw.idv.cha102.g7.shop.repo.OrderDetailRepository;
import tw.idv.cha102.g7.shop.repo.OrdersRepository;
import tw.idv.cha102.g7.shop.repo.ProductRepository;
import tw.idv.cha102.g7.shop.service.impl.CartServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.parseInt;

@Service
public class ShopOrderService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CartServiceImpl cartService;

    public String shopCheckout(Integer ordId){
        Orders orders = ordersRepository.findById(ordId).orElse(null);
        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

        AllInOne all = new AllInOne("");
        //String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId); //設定訂單編號
        obj.setMerchantTradeDate("2017/01/01 08:05:23"); //設定交易日期
        obj.setTotalAmount(orders.getChecktotal().toString()); //用於儲存訂單總金額以便在創建訂單時設定這個值
        obj.setTradeDesc(orders.getOrdId().toString());//設定交易描述
        obj.setItemName("UandMe Product"); //設定交易物品名稱
        obj.setReturnURL("http://localhost:8080/u-and-me/shopOrder/updateOrdPaySta/" + ordId);//設定返回的CONTROLLER
        obj.setNeedExtraPaidInfo("N"); //是否需要額外付費資訊
        obj.setClientBackURL("http://localhost:8080/u-and-me/tmp/Front/shop/myOrderList.html?ordId=" + ordId);//設定返回網址不行時的替代網址

        String form = all.aioCheckOut(obj, null);

        return form;
    }


    public void updateOrdPaySta(Integer ordId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Orders orders = ordersRepository.findById(ordId).orElse(null);
        orders.setOrdPaySta((byte) 1);
        ordersRepository.save(orders);

        List<OrderDetail> orderDetailList = orderDetailRepository.findByIdOrdId(ordId);
        if(orderDetailList != null){
            for(OrderDetail orderDetail : orderDetailList){
                OrderDetailId orderDetailId = orderDetail.getId();
                cartService.deleteById(memId, orderDetailId.getProdId());
            }
        }
    }
    public void updateOrdSta(Integer ordId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        Orders orders = ordersRepository.findById(ordId).orElse(null);
        orders.setOrdSta((byte)0);
        ordersRepository.save(orders);
    }
}
