package tw.idv.cha102.g7.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.shop.entity.CartList;
import tw.idv.cha102.g7.shop.entity.CartListId;
import tw.idv.cha102.g7.shop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class CartListController {

    @Autowired
    private CartService cartService;

    @Autowired
    public CartListController(CartService cartService) {
        this.cartService = cartService;
    }

    //用cartList取得cartListId實體變數
    //.getCartListId() 方法獲取複合主鍵對象，就可.getMemId()和.getProdId() 方法來獲取複合主鍵的成員屬性值
    @PostMapping("/addCartList/{prodId}")
    public CartList addCartList(@PathVariable Integer prodId,
            @RequestBody CartList cartList, HttpServletRequest request){
        System.out.println("addCartList");
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        if(obj == null)
            return new CartList();
        Integer memId = Integer.parseInt(obj.toString());

//        session.setAttribute("cartInfo", cartList);

        CartListId cartListId = new CartListId();
        cartListId.setMemId(memId);
        cartListId.setProdId(prodId);
        cartList.setCartListId(cartListId);
//        Integer cartPri = cartList.getCartPri();
//        Integer cartQty = cartList.getCartQty();
        return cartService.addToCart(cartList);
    }

    @GetMapping("/showAllCartList")
    public List<CartList> showAllCartList(HttpServletRequest request) {
        System.out.println("showAllCartList");
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        String jsessionId;
        Integer memId ;
        if(obj == null) {
            List<CartList> cartLists = new ArrayList<>();
            return cartLists;
        }else {
            jsessionId = obj.toString();
            memId = Integer.parseInt(jsessionId);
        }
        return cartService.getAllCartList(memId);
    }

    @DeleteMapping("/deleteCartList/{prodId}")
    public void deleteCartList(HttpServletRequest request,
                               @PathVariable Integer prodId){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        String jsessionId;
        Integer memId= 0 ;
        if(obj == null) {
            CartList cartList = new CartList();
        }else {
            jsessionId = obj.toString();
            memId = Integer.parseInt(jsessionId);
            System.out.println("memId: "+memId);
        }
        System.out.println("prodId: "+prodId);
        cartService.deleteById(memId,prodId);
    }

    @RequestMapping("/updateCartList/{prodId}/{cartQty}/{cartPri}")
    public CartList updateCartList(HttpServletRequest request,
                                   @PathVariable Integer prodId,
                                   @PathVariable Integer cartQty,
                                   @PathVariable Integer cartPri){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        String jsessionId;
        Integer memId = 0 ;
        if(obj == null) {
            CartList cartList = new CartList();
        }else {
            jsessionId = obj.toString();
            memId = Integer.parseInt(jsessionId);
            System.out.println("memId: "+memId);
        }
       return cartService.updateCartListQty(memId, prodId, cartQty, cartPri);
    }

    @RequestMapping("/saveTotal/{total}")
    public Integer saveTotal(HttpServletRequest request,
                             @PathVariable Integer total){
        HttpSession session = request.getSession();
        session.setAttribute("total",total);
        return total;
    }
    @RequestMapping("/getTotal")
    public Integer getTotal(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object obj= session.getAttribute("total");
        if(obj==null){
            return null;
        }
        Integer total = Integer.parseInt(obj.toString());
        return total;
    }

    @GetMapping("/member/match")
    public void memberMatch (){

    }
}
