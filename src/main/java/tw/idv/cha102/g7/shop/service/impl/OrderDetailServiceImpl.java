package tw.idv.cha102.g7.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.shop.entity.OrderDetail;
import tw.idv.cha102.g7.shop.entity.OrderDetailId;
import tw.idv.cha102.g7.shop.repo.OrderDetailRepository;
import tw.idv.cha102.g7.shop.service.OrderDetailService;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findAll(Integer page) {
        Page<OrderDetail> pageResult = orderDetailRepository.findAll(
                PageRequest.of(page, //查詢的頁數 從0開始
                        10));//查詢的每頁筆數

        List<OrderDetail> orderDetailList = pageResult.getContent();
        return orderDetailList;
    }

    @Override
    public List<OrderDetail> findByIdOrdId(Integer ordId) {
        return orderDetailRepository.findByIdOrdId(ordId);
    }

    @Override
    public Optional<OrderDetail> findById(Integer ordId, Integer prodId, OrderDetailId orderDetailId) {
        orderDetailId.setOrdId(ordId);
        orderDetailId.setProdId(prodId);
        return orderDetailRepository.findById(orderDetailId);
    }

    @Override
    public void insert(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void update(Integer ordId, Integer proId, OrderDetailId orderDetailId, OrderDetail orderDetail) {
        var existOrderDetail = findById(ordId, proId, orderDetailId);
        if(existOrderDetail.isPresent()){

            //可以再依實際使用set個別值

            orderDetailRepository.save(existOrderDetail.get());
        }
    }

    @Override
    public void delete(Integer ordId, Integer proId, OrderDetailId orderDetailId) {
        orderDetailId.setOrdId(ordId);
        orderDetailId.setProdId(proId);
        orderDetailRepository.deleteById(orderDetailId);
    }
}
