package tw.idv.cha102.g7.shop.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.idv.cha102.g7.shop.dto.MaxOrdIdDTO;
import tw.idv.cha102.g7.shop.entity.OrderDetail;
import tw.idv.cha102.g7.shop.entity.Orders;
import tw.idv.cha102.g7.shop.entity.Product;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Page<Orders> findByMemId(Integer memId, Pageable pageable);

    @Query(value = "select Max(ord_id) as maxOrdId from orders", nativeQuery = true)
    MaxOrdIdDTO findMaxId();

    @Query(value = "SELECT p.prod_id FROM orders AS o LEFT JOIN order_details AS od ON o.ord_id = od.ord_id LEFT JOIN product AS p ON p.prod_id = od.prod_id WHERE o.ord_id = ?1", nativeQuery = true)
    List<Product> findProdIdByOrdId(Integer ordId);

}
