package tw.idv.cha102.g7.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.shop.dto.ProductDTO;
import tw.idv.cha102.g7.shop.dto.ProductPictureDTO;
import tw.idv.cha102.g7.shop.entity.Product;
import tw.idv.cha102.g7.shop.entity.ProductPicture;
import tw.idv.cha102.g7.shop.service.ProductPictureService;
import tw.idv.cha102.g7.shop.service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//介面層:負責接收前端請求，請業務邏輯層(Service)處理，再回傳資料給前端;也可以是View層，渲染使用者畫面
//表示這個類別是一個 REST 風格的控制器，會處理 HTTP 請求並返回 JSON 格式的數據
@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    // 注入Service，取得service層的物件
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPictureService productPictureService;

//    因上面已定義有參數的建構子，Spring 就不會尋找一个無參的預設建構函式來實例化控制器類別
//    新增這個無參建構函式後，Spring 就能夠順利實例化 ProductController 類別。同時仍可保留你的自定義有參建構函式，以便在進行依賴注入時使用

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) throws IOException {
        productService.insert(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body("新增成功！");
    }

    @RequestMapping("/insertNewProduct")
    public ResponseEntity<Product> insertNewProduct(@RequestBody Product product){
        return productService.insertNewProduct(product);
    }
    @RequestMapping("/insertProdPictures/{prodId}")
    public ResponseEntity<ProductPictureDTO> insertProdPictures(@PathVariable Integer prodId,
                                                                    @RequestBody ProductPicture pictures){
        System.out.println("IN controller");
        ProductPicture productPicture = new ProductPicture();
//
        System.out.println(pictures);
        productPicture.setProdId(prodId);
        productPicture.setProdPic(pictures.getProdPic());
        productPictureService.insertPictures(productPicture);
        return new ResponseEntity(productPictureService.insertPictures(productPicture), HttpStatus.OK);
    }
    @DeleteMapping("/deleteProduct/{prodId}")
    @Transactional  //確保在事務範圍內(方法內)運行
    public void deleteProduct(@PathVariable Integer prodId) {
        productPictureService.deleteProductPictureByProdId(prodId);
        productService.deleteProductById(prodId);
    }

    //ResponseEntity<> Spring Framework 提供的一个類別，控制控制器方法的回應內容，包括了回應的狀態碼、回應標頭以及回應主體內容
    //    (productDTO)接住商品修改後的資訊(前端傳回的JSON參數)
    @PutMapping("/updateProduct/{prodId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer prodId,
                                           @RequestBody ProductDTO productDTO) {
        productService.updateProduct(prodId, productDTO);

        //修改商品後就可用prodId去把更新的商品數據取回
        ProductDTO updatedProduct = productService.getProductById(prodId);

        // 建構返回的JSON資料
//        Map<String, String> responseJson = new HashMap<>();
//        responseJson.put("message", "更新成功");

        //更新後的數據放在body裡傳回給前端
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

    }

    @GetMapping("/listAllProduct")
    public List<ProductDTO> listAllProduct() {
        List<ProductDTO> productDTOList = productService.listAll();
        System.out.println("Test Success");
        return productDTOList;
    }

    @RequestMapping("/getProductByName/{prodName}")
    public Product getProductByName(@PathVariable String prodName){
        return productService.getProductByName(prodName);

    }

    @GetMapping("/listAllShop")
    public ResponseEntity<List<ProductDTO>> listAllShop(){
        List<ProductDTO> productDTOList = productService.listShop();
        System.out.println("Test Success");
        return ResponseEntity.ok(productDTOList);
    }

    @GetMapping("/listProductDetail/{prodId}")
    public ResponseEntity<ProductDTO> listProductDetail(@PathVariable Integer prodId){
        ProductDTO productDTODetail = productService.listDetail(prodId);
        System.out.println("Test Success");
        System.out.println(productDTODetail.getProdPic().toString());
        return ResponseEntity.ok(productDTODetail);
    }

    @RequestMapping("/findProdByName/{prodName}")
    public ResponseEntity<List<ProductDTO>> findProdByName(@PathVariable String prodName){
        List<ProductDTO> productDTOS = productService.findAllByProdName(prodName);
        return ResponseEntity.ok(productDTOS);
    }

    @RequestMapping("/findProdByCatId/{prodCatId}")
    public  ResponseEntity<List<ProductDTO>> findProdByCatId(@PathVariable Integer prodCatId){
        List<ProductDTO> productDTOS = productService.findAllByProdCatId(prodCatId);
        return ResponseEntity.ok(productDTOS);
    }


    @RequestMapping("/listAllStaOn")
   public ResponseEntity<List<ProductDTO>> listAllStaOn(){
        return ResponseEntity.ok(productService.listAllStaOn());
    }
}





