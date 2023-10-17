package tw.idv.cha102.g7.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.shop.dto.ProductDTO;
import tw.idv.cha102.g7.shop.entity.Product;
import tw.idv.cha102.g7.shop.entity.ProductPicture;
import tw.idv.cha102.g7.shop.repo.ProductPictureRepository;
import tw.idv.cha102.g7.shop.repo.ProductRepository;
import tw.idv.cha102.g7.shop.service.ProductPictureService;
import tw.idv.cha102.g7.shop.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//業務邏輯層，被呼叫，根據請求做資料處理&處理從DAO/Repo回來的資料
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;  //取得ProductRepository物件，productRepository變數名稱也是自定義

    @Autowired
    private ProductPictureRepository productPictureRepository;

    //從Spring容器找出物件注入給該屬性:當容器初始化時會自動將ProductRepository注入於該建構子中
    //類別的建構子，用於初始化 ProductServiceImpl物件，並設定其依賴的ProductRepository物件

    //把insert進來的物件用productRepository的save方法存在product中
    @Transactional
    @Override
    public Integer insert(ProductDTO productDTO) {
        Product product = new Product();
        product.setProdCatId(productDTO.getProdCatId());
        product.setProdName(productDTO.getProdName());
        product.setProdCon(productDTO.getProdCon());
        product.setProdPri(productDTO.getProdPri());
        product.setProdSta(productDTO.getProdSta());
        product = productRepository.save(product);
        Integer prodId = product.getProdId();

        ProductPicture productPicture = new ProductPicture();
        productPicture.setProdId(prodId);
        productPicture.setProdPic(productDTO.getProdPic());
        productPictureRepository.save(productPicture);

        return prodId;

    }

    // 刪除單筆商品
    @Override
    public void deleteProductById(Integer prodId) {
        productRepository.deleteById(prodId);
    }

    @Override
    public void updateProduct(Integer prodId, ProductDTO productDTO) {

        Product product = new Product();
        product.setProdId(prodId);
        product.setProdCatId(productDTO.getProdCatId());
        product.setProdName(productDTO.getProdName());
        product.setProdCon(productDTO.getProdCon());
        product.setProdPri(productDTO.getProdPri());
        product.setProdSta(productDTO.getProdSta());

        List<ProductPicture> productPictures = new ArrayList<>();
        productPictures = productPictureRepository.findByProdId(prodId);
        for(ProductPicture productPicture : productPictures){
            productPicture.setProdPic(productDTO.getProdPic());
        }

        productRepository.save(product);
        productPictureRepository.save(productPictures.get(0));
        System.out.println("STA: "+product.getProdSta());


//        List<ProductPicture> pplist = productDTO.getProductPictures();
//            if(pplist != null) {
//                for (ProductPicture pp : pplist) {
//                    productPictureRepository.save(pp);
//                }
//            }

    }

    @Override
    public ProductDTO getProductById(Integer prodId) {
        Product product = productRepository.findById(prodId).orElse(null);
        List<ProductPicture> pplist = productPictureRepository.findByProdId(prodId);

        ProductDTO dto = new ProductDTO();
        dto.setProdId(product.getProdId());
        dto.setProdCatId(product.getProdCatId());
        dto.setProdName(product.getProdName());
        dto.setProdCon(product.getProdCon());
        dto.setProdPri(product.getProdPri());
        dto.setProdSta(product.getProdSta());
        dto.setProductPictures(pplist);

        return dto;
    }

    @Override
    public List<ProductDTO> listAll() {
        //找出全部商品
        List<Product> productList = productRepository.findAll();

        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product p : productList){
            Integer prodId = p.getProdId();
            List<ProductPicture> productPictures = productPictureRepository.findByProdId(prodId);
            Object[] objects = {p.getProdId(),
                                p.getProdCatId(),
                                p.getProdName(),
                                p.getProdCon(),
                                p.getProdPri(),
                                p.getProdSta(),
                                productPictures};
            ProductDTO dto = new ProductDTO(objects);
            productDTOS.add(dto);
        }
        return productDTOS;

        //        List<Product> prodList = productRepository.findAll();
//        List<ProductDTO> productDTOS = new ArrayList<>();

//        for (Product product : prodList) {
//            Integer prodId = product.getProdId();
//            List<ProductPicture> pp = product.getProductPictures();
//
//            ProductDTO productDTO = new ProductDTO(
//                    prodId,
//                    product.getProdCatId(),
//                    product.getProdName(),
//                    product.getProdCon(),
//                    product.getProdPri(),
//                    product.getProdSta(),
//                    pp
//            );
//
//            productDTOS.add(productDTO);
//            }
//
//            return productDTOS;


        //找出全部照片並對應prodId
//        List<ProductPicture> pp = new ArrayList<>();
//        //從prodList裡面一個一個拿出放到product，再取得prodId，再從PPR裡找照片對應的prodId
//        for (Product product : prodList) {
//            Integer prodId = product.getProdId();
//            pp = productPictureRepository.findByProdId(prodId);
//            Integer prodCatId = product.getProdCatId();
//            String prodName = product.getProdName();
//            String prodCon = product.getProdCon();
//            Integer prodPri = product.getProdPri();
//            Short prodSta = product.getProdSta();
//
//            //Object[]是ProductDTO的型態
//            Object[] objects = {prodId, prodCatId, prodName, prodCon, prodPri, prodSta, pp};
//            //productDTO代表每一列商品清單
//            ProductDTO productDTO = new ProductDTO(objects);
//            //把每一列商品清單放進productDTOS
//            productDTOS.add(productDTO);
//        }
//        return productDTOS;
    }

    @Override
    public Product getProductByName(String prodName) {
        return productRepository.findByProdName(prodName);
    }

    @Override
    public ResponseEntity<Product> insertNewProduct(Product product) {
        return new ResponseEntity(productRepository.save(product), HttpStatus.OK);
    }

    @Override
    public List<ProductDTO> listShop() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for( Product p : productList){
            Integer prodId = p.getProdId();
            List<ProductPicture> productPictures = productPictureRepository.findByProdId(prodId);
            Object[] objects = {
                                p.getProdId(),
                                p.getProdCatId(),
                                p.getProdName(),
                                "",
                                p.getProdPri(),
                                p.getProdSta(),
                                productPictures};
            ProductDTO productDTO = new ProductDTO(objects);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public ProductDTO listDetail(Integer prodId) {
        System.out.println("prodId: "+prodId);
        Product product = productRepository.findById(prodId).orElse(null);
//        System.out.println(product.toString());
        ProductDTO productDetail = new ProductDTO();
        List<ProductPicture> productPictures = productPictureRepository.findByProdId(prodId);
        Object[] objects = {
                prodId,//product.getProdId(),
                product.getProdCatId(),
                product.getProdName(),
                product.getProdCon(),
                product.getProdPri(),
                product.getProdSta(),
                productPictures};
        ProductDTO productDTO = new ProductDTO(objects);
        productDTO.setProdPic(productPictures.get(0).getProdPic());

        return productDTO;
    }

    @Override
    public List<ProductDTO> findAllByProdName(String prodName) {
        List<Product> productList = productRepository.findAllByProdNameContaining(prodName);
        List<ProductDTO> productListWithName = new ArrayList<>();
        for(Product product : productList){
            Object[] objects = {
                    product.getProdId(),
                    product.getProdCatId(),
                    product.getProdName(),
                    product.getProdCon(),
                    product.getProdPri(),
                    product.getProdSta(),
                    productPictureRepository.findByProdId(product.getProdId())
            };
            ProductDTO productDTO = new ProductDTO(objects);
            productListWithName.add(productDTO);
        }
        return productListWithName;
    }

    @Override
    public List<ProductDTO> findAllByProdCatId(Integer prodCatId) {
        List<Product> productList = productRepository.findByProdCatId(prodCatId);
        List<ProductDTO> productListWithCatId = new ArrayList<>();
        for(Product product : productList){
            Object[] objects = {
                    product.getProdId(),
                    product.getProdCatId(),
                    product.getProdName(),
                    product.getProdCon(),
                    product.getProdPri(),
                    product.getProdSta(),
                    productPictureRepository.findByProdId(product.getProdId())
            };
            ProductDTO productDTO = new ProductDTO((objects));
            productListWithCatId.add(productDTO);
        }
        return productListWithCatId;
    }


//    //////////////////////////////////////////////////////////////

    @Override
    public List<ProductDTO> listAllStaOn() {
        //找出全部商品
        List<ProductDTO> productDTOS = listAll().stream()
                .filter(productDTO -> productDTO.getProdSta() == 1)
                .collect(Collectors.toList());
        return productDTOS;
    }





}







//是否return是看自己定義，
//不return的話回傳值要寫void空值，要return 回傳值就是一個物件(Product...等)


