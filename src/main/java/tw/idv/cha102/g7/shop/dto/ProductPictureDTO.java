package tw.idv.cha102.g7.shop.dto;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class ProductPictureDTO {

    private Integer prodPicId;

    private Integer prodId;

    @Lob
    private String prodPic;
}
