package tw.idv.cha102.g7.shop.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.cha102.g7.shop.entity.OrderDetail;
import tw.idv.cha102.g7.shop.entity.OrderDetailId;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    Page<OrderDetail> findAll(Pageable pageable);

    List<OrderDetail> findByIdOrdId(Integer ordId);


}
