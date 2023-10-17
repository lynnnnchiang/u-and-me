package tw.idv.cha102.g7.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.shop.dto.MaxOrdIdDTO;
import tw.idv.cha102.g7.shop.entity.Orders;
import tw.idv.cha102.g7.shop.service.OrdersService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    /**
     * ?
     * 查詢該會員的訂單
     * @param page  頁數(一頁10筆)
     * @return 查詢結果
     */
    @GetMapping("/member/myOrders/{page}")
    public List<Orders> findByMemId(HttpServletRequest request, @PathVariable Integer page) {
        return ordersService.findByMemId(request, page);
    }

    /**
     * ?
     * 以訂單編號查找訂單
     * @param ordId 訂單編號
     * @return 查詢結果
     */
    @GetMapping("/Orders/ordId{ordId}")
    public Orders findByOrdId(@PathVariable Integer ordId){
        return ordersService.findByOrdId(ordId);
    }

    /**
     * ?
     * 新增資料
     * @param orders 欲新增的資料
     * @return 新增成功 / 新增失敗
     */
    @PostMapping("/member/orders")
    public void insert(@RequestBody Orders orders, HttpServletRequest request) {

//        try {
            ordersService.insert(orders, request);
//            return ResponseEntity.ok("新增成功");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("新增失敗");
//        }
    }

    /**
     * ?
     * 刪除資料
     * @param ordId 訂單ID
     * @return 刪除成功 / 刪除失敗
     */
    @DeleteMapping("/orders")
    public ResponseEntity<?> delete(@PathVariable Integer ordId) {
        try {
            ordersService.delete(ordId);
            return ResponseEntity.ok("刪除成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("刪除失敗");
        }
    }

    /**
     * ?
     * 修改訂單
     * @param ordId 訂單ID
     * @param orders 修改後的訂單資料
     */
    @PutMapping("/orders/{ordId}")
    public void update(@PathVariable Integer ordId,
                                    @RequestBody Orders orders) {
        ordersService.update(ordId, orders);
    }

    @PutMapping("/orders/updSta/{ordId}")
    public void updateOrdSta(@PathVariable Integer ordId,
                             @RequestBody Orders orders){
        ordersService.updateOrdSta(ordId, orders);
    }


    @GetMapping("/findMaxId")
    public ResponseEntity<MaxOrdIdDTO> findMaxId(){
        return new ResponseEntity(ordersService.findMaxId(), HttpStatus.OK);
    }

}
