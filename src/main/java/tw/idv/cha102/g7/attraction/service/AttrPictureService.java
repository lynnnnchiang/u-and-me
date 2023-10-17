package tw.idv.cha102.g7.attraction.service;

import org.springframework.http.ResponseEntity;
import tw.idv.cha102.g7.attraction.dto.AttrPictureDTO;
import tw.idv.cha102.g7.attraction.entity.AttrPicture;
import tw.idv.cha102.g7.attraction.entity.Attraction;

import java.util.List;

public interface AttrPictureService {

    public ResponseEntity<AttrPicture> insertPictures(AttrPicture attrPicture);

    public String getPicsByAttrId(Integer attrId);

    public void delPicByAttrPicId(Integer attrPicId);

    public void delPicByAttrId(Integer attrId);

}
