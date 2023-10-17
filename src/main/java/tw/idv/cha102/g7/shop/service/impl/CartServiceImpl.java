package tw.idv.cha102.g7.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.shop.entity.CartList;
import tw.idv.cha102.g7.shop.entity.CartListId;
import tw.idv.cha102.g7.shop.repo.CartListRepository;
import tw.idv.cha102.g7.shop.repo.OrdersRepository;
import tw.idv.cha102.g7.shop.repo.ProductRepository;
import tw.idv.cha102.g7.shop.service.CartService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartListRepository cartListRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ProductRepository productRepository;

        @Override
        //加入購物車
        public CartList addToCart(CartList cartList) {
            System.out.println("addToCart");
            CartListId cartListId = cartList.getCartListId();
            CartList originCartList = cartListRepository.
                    findByCartListId_MemIdAndCartListId_ProdId(cartListId.getMemId(), cartListId.getProdId());

            if(originCartList == null){
//            cartList = new CartList();  //創建一筆新的購物車清單
//            cartList.setCartListId(new CartListId(memId, prodId)); //因為複合主鍵，要用cartListId new新物件才有memId&prodId)同時set這兩項內容
//            cartList.setCartQty(cartQty);
            cartList.setCartPri(productRepository.findByProdId(cartListId.getProdId()).getProdPri()*cartList.getCartQty());
                return cartListRepository.save(cartList);
        }else {
                //增加購物車清單數量
                originCartList.setCartQty(cartList.getCartQty() + originCartList.getCartQty());
                originCartList.setCartPri(productRepository.
                                                findByProdId(originCartList.getCartListId().
                                                                getProdId()).getProdPri() * originCartList.getCartQty());
                return cartListRepository.save(originCartList);
            }
            //save是新增與修改，如果同一個會員選同一個商品加入購物車，要讓他變成修改(增加)數量
//            return cartListRepository.save(cartList);
        }


        //查看購物車項目: 先把cartList全部列出，如果對應到的memId=裡面的memId就被取出來
        @Override
        public List<CartList> getAllCartList(Integer memId){
            List<CartList> allList = cartListRepository.findAll();
            List<CartList> returnList = new ArrayList<>();
            for(CartList cartlist: allList){
                if(cartlist.getCartListId().getMemId() == memId){
                    returnList.add(cartlist);
                }
            }
            return returnList;
        }
        //刪除一筆購物車清單
        @Transactional //可確保該方法中的所有數據庫操作都在一個事務內進行，並在方法執行完成時，根據操作的結果進行事務的提交或回滾
        @Override
        public void deleteById(Integer memId,Integer prodId) {
            CartListId cartListId = new CartListId(memId,prodId);
            cartListRepository.deleteByCartListId(cartListId);

        }

//        //更新一筆購物車數量
        public CartList updateCartListQty(Integer memId, Integer prodId, Integer cartQty, Integer newCartPri) {
            CartListId cartListId = new CartListId(memId, prodId);
            CartList cartList = cartListRepository.findByCartListId_MemIdAndCartListId_ProdId(memId, prodId);
            if (cartList != null) {
                cartList.setCartQty(cartQty);
                cartList.setCartPri(newCartPri);
            }
                return cartListRepository.save(cartList);
        }


//    @Override
//    public Order checkoutWithCash(Integer memId) {
//        // 根據購物車內容創建訂單
//        Order order = new Order();
//        order.setMemId(memId);
//        // 設置其他訂單相關信息
//        // ...
//
//        // 設置支付方式為 "貨到付款"
//        order.setPaymentMethod("Cash On Delivery");
//
//        // 保存訂單到數據庫
//        return orderRepository.save(order);
    }



