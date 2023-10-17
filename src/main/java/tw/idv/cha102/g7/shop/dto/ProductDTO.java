package tw.idv.cha102.g7.shop.dto;

import lombok.*;
import tw.idv.cha102.g7.shop.entity.Product;
import tw.idv.cha102.g7.shop.entity.ProductPicture;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Integer prodId;
    private Integer prodCatId;
    private String prodName;
    private String prodCon;
    private Integer prodPri;
    private Short prodSta;
    private byte[] prodPic;
    private List<ProductPicture> productPictures;

    //    生成一個DTO時傳進來的資料讓他排序
    public ProductDTO(Object[] objects) {
        this.prodId = (Integer) objects[0];
        this.prodCatId = (Integer) objects[1];
        this.prodName = (String) objects[2];
        this.prodCon = (String) objects[3];
        this.prodPri = (Integer) objects[4];
        this.prodSta = (Short) objects[5];
        this.productPictures = (List<ProductPicture>) objects[6];
    }
}
