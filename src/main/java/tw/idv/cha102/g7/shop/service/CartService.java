package tw.idv.cha102.g7.shop.service;

import tw.idv.cha102.g7.shop.entity.CartList;

import java.util.List;

public interface CartService {
    //加入購物車(新增一筆購物車清單)
    public CartList addToCart(CartList cartList);

    //查看購物車內容
    public List<CartList> getAllCartList(Integer memId);

    // 刪除一筆購物車清單
    public void deleteById(Integer memId,Integer prodId);

    // 更新一筆購物車清單數量&小計
    public CartList updateCartListQty(Integer memId, Integer prodId, Integer cartQty,Integer newCartPri);

    //貨到付款
//    public Order checkoutWithCash(Integer memId);
    }

