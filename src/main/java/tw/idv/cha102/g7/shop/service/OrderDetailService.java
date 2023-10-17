package tw.idv.cha102.g7.shop.service;

import tw.idv.cha102.g7.shop.entity.OrderDetail;
import tw.idv.cha102.g7.shop.entity.OrderDetailId;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    List<OrderDetail> findAll(Integer page);

    List<OrderDetail> findByIdOrdId(Integer ordId);

    Optional<OrderDetail> findById(Integer ordId, Integer proId, OrderDetailId orderDetailId);

    void insert(OrderDetail orderDetail);

    void update(Integer ordId, Integer proId, OrderDetailId orderDetailId, OrderDetail orderDetail);

    void delete(Integer ordId, Integer proId, OrderDetailId orderDetailId);

}
