package tw.idv.cha102.g7.shop.service;

import org.springframework.http.ResponseEntity;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.shop.dto.ProductDTO;
import tw.idv.cha102.g7.shop.entity.Product;

import java.util.List;


public interface ProductService {

    //新增商品
    public Integer insert(ProductDTO productDTO);

    //刪除單筆商品
    public void deleteProductById(Integer prodId);

    //更新商品資訊
    //參數product 表示要更新的物件，返回Product物件，代表更新後的商品
    public void updateProduct(Integer prodId, ProductDTO productDTO);

    //查詢單個商品
    //根據Id查詢單個商品，參數prodId表示要查詢的商品Id，返回值是一個Product物件，代表查詢到的單個產品
    public ProductDTO getProductById(Integer prodId);

//    顯示商品列表，返回值是一個包含多個Product物件的列表
    public List<ProductDTO> listAll();

    public Product getProductByName(String prodName);

    public ResponseEntity<Product> insertNewProduct(Product product);


    //使用者業務
    /*
     * 顯示商品列表
     * */
    List<ProductDTO> listShop();
    /*
     * 顯示單一商品詳情
     * */
    public ProductDTO listDetail(Integer prodId);


    /*
     * 搜尋商品byNAME 模糊比對%name% (1-多筆)
     * */
    public List<ProductDTO> findAllByProdName(String prodName);


    /*
     * 搜尋商品by類別 (1-多筆)
     * */
    public List<ProductDTO> findAllByProdCatId(Integer prodCatId);

    /**
     * 顯示所有上架商品
     * @return 上架商品清單
     */
    public List<ProductDTO> listAllStaOn();

}

