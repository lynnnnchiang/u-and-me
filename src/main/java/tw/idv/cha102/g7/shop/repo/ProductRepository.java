package tw.idv.cha102.g7.shop.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.shop.dto.ProductDTO;
import tw.idv.cha102.g7.shop.entity.Product;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//資料訪問層:對資料庫做增刪改查等操作<自定義的類別,PK的型別>
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findAllByProdNameContaining(String prodName);

    Product findByProdName(String prodName);

    public List<Product> findByProdCatId(Integer prodCatId);

    public Product findByProdId(Integer prodId);

    @Query(value = "select p.prod_id from product AS p left join order_details AS od on p.prod_id = od.prod_id left join orders As o on o.ord_id = od.ord_id left join product_category AS pc on p.prodcat_id = pc.prodcat_id where o.ord_id = ?1 ", nativeQuery = true)
    List<Product> findProdIdByOrdId(Integer ordId);
}
