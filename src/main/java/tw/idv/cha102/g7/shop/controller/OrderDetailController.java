package tw.idv.cha102.g7.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.attraction.dto.AttrPrivateDTO;
import tw.idv.cha102.g7.member.controller.MemberController;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.service.MemberService;
import tw.idv.cha102.g7.shop.entity.OrderDetail;
import tw.idv.cha102.g7.shop.entity.OrderDetailId;
import tw.idv.cha102.g7.shop.service.OrderDetailService;
import tw.idv.cha102.g7.shop.service.OrdersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private MemberService memberService;

    /**
     * ??
     * 依分頁找到全部的訂單
     * @param page 分頁頁數從0開始
     * @return 查詢結果
     */
    @GetMapping("/OrderDetail/{page}")
    List<OrderDetail> findAll(@PathVariable Integer page) {
        return orderDetailService.findAll(page);
    }

    /**
     * ??
     * 依訂單編號找到該編號的訂單明細
     * @param ordId 訂單編號
     * @return 查詢結果
     */
    @GetMapping("/OrderDetailOrdId/{ordId}")
    ResponseEntity<List<OrderDetail>> findByIdOrdId(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable Integer ordId){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        if (obj == null) {
            return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
        }
        Integer memId = Integer.parseInt(obj.toString());

        Integer ordMemId = ordersService.findByOrdId(ordId).getMemId();
        if(memId!=ordMemId){
            return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
        }

        return new ResponseEntity(orderDetailService.findByIdOrdId(ordId),HttpStatus.OK);
    }

    /**
     * ???
     * 查詢指定訂單編號、商品編號的訂單明細
     * @param ordId 訂單標號
     * @param prodId 商品編號
     * @param orderDetailId 複合主鍵類
     * @return 查詢結果
     */
    @GetMapping("/OrderDetail/{ordId}/{prodId}")
    public Optional<OrderDetail> findById(@PathVariable Integer ordId,
                                          @PathVariable Integer prodId,
                                          OrderDetailId orderDetailId){
        return orderDetailService.findById(ordId, prodId, orderDetailId);
    }

    /**
     * ???
     * 新增訂單明細
     * @param orderDetail 訂單明細資料
     */
    @PostMapping("/OrderDetail")
    public void insert(@RequestBody OrderDetail orderDetail) {
        orderDetailService.insert(orderDetail);
    }

    /**
     * 更新訂單明細
     * @param ordId 待更新訂單明細的訂單編號
     * @param prodId 待更新訂單明細的商品編號
     * @param orderDetailId 複合主鍵類
     * @param orderDetail 更新資料
     */
    @PutMapping("/OrderDetail/{ordID}/{prodId}")
    public void update(@PathVariable Integer ordId,
                       @PathVariable Integer prodId,
                       OrderDetailId orderDetailId,
                       @RequestBody OrderDetail orderDetail){
        orderDetailService.update(ordId, prodId, orderDetailId, orderDetail);
    }

    /**
     * ???
     * 刪除訂單明細
     * @param ordId 欲刪除的訂單明細的訂單編號
     * @param prodId 欲刪除的訂單明細的商品編號
     * @param orderDetailId 複合主鍵類
     */
    @DeleteMapping("/OrderDetail/{ordId}/{prodId}")
    public void delete(Integer ordId, Integer prodId, OrderDetailId orderDetailId) {
        orderDetailService.delete(ordId, prodId ,orderDetailId);
    }
}
