package tw.idv.cha102.g7.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.shop.entity.ProductCategory;
import tw.idv.cha102.g7.shop.repo.ProductCategoryRepository;

@RestController
public class ProductCategoryController {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    /**
     * 依照XX編號，新增商品類別
     * */
    @PostMapping("/productCategory")
    public String insert(@RequestBody ProductCategory productCategory){

        productCategoryRepository.save(productCategory);

        return "執行資料庫的 create 操作";
    }

    /**
     * 依照XX編號，刪除商品類別
     * */
    @DeleteMapping("/productCategory/{productCategoryId}")
    public String delete(@PathVariable ProductCategory productCategory){

        productCategoryRepository.delete(productCategory);

        return "執行資料庫的 create 操作";

    }



}
