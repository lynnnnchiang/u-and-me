package tw.idv.cha102.g7.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tw.idv.cha102.g7.shop.entity.Product;
import tw.idv.cha102.g7.shop.entity.ProductPicture;
import tw.idv.cha102.g7.shop.repo.ProductPictureRepository;
import tw.idv.cha102.g7.shop.repo.ProductRepository;
import tw.idv.cha102.g7.shop.service.ProductPictureService;

import java.util.List;

@Service
public class ProductPictureServiceImpl implements ProductPictureService {

    @Autowired
    private ProductPictureRepository productPictureRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductPicture insert(ProductPicture productPicture) {
        return productPictureRepository.save(productPicture);
    }

    @Override
    public ResponseEntity<ProductPicture> insertPictures(ProductPicture productPicture) {
        return new ResponseEntity(productPictureRepository.save(productPicture), HttpStatus.OK);
    }
    @Override
    public void deleteProductPictureByProdId(Integer prodId) {
        Product product = productRepository.findById(prodId).orElse(null);
        if (product != null) {
            List<ProductPicture> productPictures = productPictureRepository.findByProdId(prodId);
            productPictureRepository.deleteAll(productPictures);
        }
    }


}
