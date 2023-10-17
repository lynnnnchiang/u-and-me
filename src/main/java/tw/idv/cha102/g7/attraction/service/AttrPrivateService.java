package tw.idv.cha102.g7.attraction.service;

import org.springframework.http.ResponseEntity;
import tw.idv.cha102.g7.attraction.dto.AttrPrivateDTO;
import tw.idv.cha102.g7.attraction.dto.AttrPrivateId;
import tw.idv.cha102.g7.attraction.entity.Attraction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AttrPrivateService {

    public ResponseEntity<AttrPrivateDTO> addAndUpdatePrivateAttraction(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       Attraction Attraction);

    List<Attraction> findAttrsByMem(HttpServletRequest request,
                                    HttpServletResponse response);

    public ResponseEntity<String> deletePrivateAttraction(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          Integer attrId);

    public AttrPrivateDTO getPrivateAttraction(AttrPrivateId attrPrivateId);

    public List<Attraction> getPrivateAttractionByMemId(Integer memId);
}
