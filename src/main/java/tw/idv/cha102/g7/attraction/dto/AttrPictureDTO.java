package tw.idv.cha102.g7.attraction.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class AttrPictureDTO {

    private Integer attrPicId;
    private Integer attrId;
    @Lob
    private String attrPicData;
}
