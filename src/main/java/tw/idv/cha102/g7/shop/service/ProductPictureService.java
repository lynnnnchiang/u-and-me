package tw.idv.cha102.g7.shop.service;

import org.springframework.http.ResponseEntity;
import tw.idv.cha102.g7.attraction.entity.AttrPicture;
import tw.idv.cha102.g7.shop.entity.ProductPicture;

public interface ProductPictureService {


    public void deleteProductPictureByProdId(Integer prodId);

    public ProductPicture insert(ProductPicture productPicture);


    public ResponseEntity<ProductPicture> insertPictures(ProductPicture productPicture);


}
