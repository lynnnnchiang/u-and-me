package tw.idv.cha102.g7.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.MemberRepository;
import tw.idv.cha102.g7.shop.dto.MaxOrdIdDTO;
import tw.idv.cha102.g7.shop.entity.Orders;
import tw.idv.cha102.g7.shop.repo.OrdersRepository;
import tw.idv.cha102.g7.shop.service.OrdersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Orders> findByMemId(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());

        Page<Orders> pageResult = ordersRepository.findByMemId(memId, PageRequest.of(page,
                10, //每筆訂單數量
                Sort.by("ordTime").ascending())); //依造ord_time欄位從小到大排序
        List<Orders> orders = pageResult.getContent();
        return orders;
    }

    @Override
    public Orders findByOrdId(Integer ordId) {
        return ordersRepository.findById(ordId).get();
    }

    @Override
    public Orders insert(Orders orders, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        orders.setMemId(memId);
        Member member = memberRepository.findById(orders.getMemId()).orElse(null);
        String recieverEmail = member.getMemEmail();
        Orders orders1 = ordersRepository.save(orders);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("xiaoina914@gmail.com");//寄出信件的信箱
        message.setTo(recieverEmail);//接收信件的信箱
        message.setSubject("U-and-Me 客服郵件");//信件主旨
        message.setText("訂購人姓名：" + member.getMemName()+"\n\n"+
                "訂購人信箱：" + member.getMemEmail()+"\n\n"+
                "訂單總金額：" + orders.getChecktotal()+"\n\n"+
                "訂單編號：" + orders.getOrdId()+"\n\n"+
                "＊－。－。－。－。－ 收件人留言 －。－。－。－。－＊\n\n"+
                "您的訂單已確認，感謝您的購買!"+"\n\n\n\n\n\n"+
                "From U-and-Me"+
                "＊－。－。－。－。－。－。－。－－。－。－＊");//信件內容 長String 可以加\n換行

        mailSender.send(message);
        return orders1;
    }

    @Override
    public void delete(Integer ordId) {
        ordersRepository.deleteById(ordId);
    }

    @Override
    public void update(Integer ordId, Orders orders) {
        if(ordersRepository.findById(ordId).isPresent()) {
            ordersRepository.save(orders);
        }
    }

    @Override
    public void updateOrdSta(Integer ordId, Orders orders) {
        if(ordersRepository.findById(ordId).isPresent()) {
            Orders updOrders = ordersRepository.findById(ordId).get();
            updOrders.setOrdSta(orders.getOrdSta());
            ordersRepository.save(updOrders);
        }
    }

    @Override
    public MaxOrdIdDTO findMaxId() {
        return ordersRepository.findMaxId();
    }


}
