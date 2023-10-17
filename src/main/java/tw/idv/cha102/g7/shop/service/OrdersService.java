package tw.idv.cha102.g7.shop.service;

import tw.idv.cha102.g7.shop.dto.MaxOrdIdDTO;
import tw.idv.cha102.g7.shop.entity.Orders;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrdersService {
    List<Orders> findByMemId(HttpServletRequest request, Integer page);

    Orders findByOrdId(Integer ordId);

    Orders insert(Orders orders, HttpServletRequest request);

    void delete(Integer ordId);

    void update(Integer ordId, Orders orders);

    void updateOrdSta(Integer ordId, Orders orders);

    MaxOrdIdDTO findMaxId();
}
