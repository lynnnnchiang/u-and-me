package tw.idv.cha102.g7.attraction.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import tw.idv.cha102.g7.attraction.entity.Attraction;

import java.util.List;

public interface AttrService {

    Attraction getById(Integer attrId);

    String createAttr(Attraction attraction);

    /**
     * 查詢全部的Attraction
     *
     * @return List<Attraction>一組Attraction
     */
    ResponseEntity<List<Attraction>> getAll();

    void setSta(Integer attrId, Short attrSta);

    List<Attraction> getAttrsByName(String attrName);

    public List<Attraction> getAllPaged(int page, int size);


    //TRY PAGEABLE
    public Page<Attraction> getAllPagedByName(String attrName, int page, int size);

    public ResponseEntity<Attraction> insertNewAttraction(Attraction attraction);

    public ResponseEntity<Attraction> getAttrByName(String attrName);
    public ResponseEntity<Attraction> getAttrByNameFilter(String attrName);

    public ResponseEntity<Attraction> updateAttrByAttrId(Integer attrId, Attraction attraction);

    public ResponseEntity<String> deleteAttrByAttrId(Integer attrId);

    public ResponseEntity<List<Attraction>> getAttrsByType(String attrType);

    public ResponseEntity<List<Attraction>> getAttrsFilter(Integer attrId);

}
