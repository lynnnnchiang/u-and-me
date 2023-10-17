package tw.idv.cha102.g7.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.shop.entity.ProductPicture;

import java.util.List;

@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPicture, Integer> {
    void deleteByProdId(Integer prodId);

    List<ProductPicture> findByProdId(Integer prodId);

}
