package tw.idv.cha102.g7.attraction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.attraction.repo.AttrRepository;
import tw.idv.cha102.g7.attraction.service.AttrService;
import tw.idv.cha102.g7.member.repo.MemberRepository;

import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AttrServiceImpl implements AttrService {

    @Autowired
    private AttrRepository attrRepository;

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 透過attrId去查詢一個景點
     *
     * @param attrId Atraction的ID
     * @return 查詢到的Attraction，若沒查到則返回null
     */
    @Override
    public Attraction getById(Integer attrId) {
        return attrRepository.findById(attrId).orElse(null);
    }


    @Override
    public ResponseEntity<List<Attraction>> getAll() {
        List<Attraction> attractions = attrRepository.findAll();
        List<Attraction> returnList = new ArrayList<>();
        if(attractions==null)
            return new ResponseEntity(new ArrayList<>(),HttpStatus.OK);
        for(Attraction attraction:attractions){
            if(attraction.getAttrSta()!=0){
                returnList.add(attraction);
            }
        }
        return new ResponseEntity<>(returnList,HttpStatus.OK);
    }

    @Override
    public void setSta(Integer attrId, Short attrSta) {
        Attraction attraction = attrRepository.getById(attrId);
        attraction.setAttrSta(attrSta);
        attrRepository.save(attraction);
    }

    @Override
    public List<Attraction> getAttrsByName(String attrName) {
        return attrRepository.findAllByAttrNameContaining(attrName);
    }

    @Override
    public ResponseEntity<Attraction> getAttrByNameFilter(String attrName){
        List<Attraction> attractions = attrRepository.findAllByAttrNameContaining(attrName);
        List<Attraction> returnList = new ArrayList<>();
        if(attractions==null)
            return new ResponseEntity(new ArrayList<>(),HttpStatus.OK);
        for(Attraction attraction:attractions){
            if(attraction.getAttrSta()!=0){
                returnList.add(attraction);
            }
        }
        return new ResponseEntity(returnList,HttpStatus.OK);
    }

    @Override
    public List<Attraction> getAllPaged(int page, int size) {
        Page<Attraction> pageResult = attrRepository.findAll(
                PageRequest.of(page, //查詢的頁數 從0開始
                        size,//查詢的每頁筆數
                        Sort.by("attrId").ascending())); //依造group_sta欄位升冪排序
//        可能會運用到的方法
//        pageResult.getNumberOfElements(); //本頁筆數
//        pageResult.getSize(); //每頁筆數
//        pageResult.getTotalElements(); //全部筆數
//        pageResult.getTotalPages(); //全部頁數
        List<Attraction> attrList = pageResult.getContent();
        return attrList;
    }

    @Override
    public Page<Attraction> getAllPagedByName(String attrName, int page, int size) {
        return attrRepository.findAllByAttrNameContaining(attrName, PageRequest.of(page, size));
    }

    @Override
    public ResponseEntity<Attraction> insertNewAttraction(Attraction attraction) {

        return new ResponseEntity(attrRepository.save(attraction), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Attraction> getAttrByName(String attrName) {
        Attraction attraction = new Attraction();
        try{
            attraction = attrRepository.findByAttrName(attrName);
        }
        catch(NonUniqueResultException | IncorrectResultSizeDataAccessException e){
            return new ResponseEntity(new Attraction(),HttpStatus.OK);
        }


            return new ResponseEntity(attraction,HttpStatus.OK);
    }

    @Override
    public String createAttr(Attraction attraction) {
        if (attrRepository.getById(attraction.getAttrId()) != null) {
            return "Existed Attraction";
        } else {
            attrRepository.save(attraction);
        }
        return "Create Attraction Success";
    }

    @Override
    public ResponseEntity<Attraction> updateAttrByAttrId(Integer attrId, Attraction attraction){
        Attraction originAttr = attrRepository.findById(attrId).orElse(null);
//        originAttr.setAttrId(attrId);
        originAttr.setAttrName(attraction.getAttrName());
        originAttr.setAttrAddr(attraction.getAttrAddr());
        originAttr.setAttrSta(attraction.getAttrSta());
        originAttr.setAttrType(attraction.getAttrType());
        originAttr.setAttrCostRange(attraction.getAttrCostRange());
        originAttr.setAttrBussTime(attraction.getAttrBussTime());
        originAttr.setAttrIlla(attraction.getAttrIlla());
        return new ResponseEntity(attrRepository.save(originAttr),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAttrByAttrId(Integer attrId){
        attrRepository.deleteById(attrId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Attraction>> getAttrsByType(String attrType){
        List<Attraction> attractions = attrRepository.findAllByAttrType(attrType);
        if(attractions==null){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }
        for(Attraction attraction:attractions){
            if(attraction.getAttrSta()!=1){
                attractions.remove(attraction);
            }
        }
        return new ResponseEntity<>(attractions,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Attraction>> getAttrsFilter(Integer attrId){
        List<Attraction> attractions = attrRepository.findAll();
        if(attractions==null){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }
        for(Attraction attraction:attractions){
            if(attraction.getAttrSta()!=1){
                attractions.remove(attraction);
            }
        }
        return new ResponseEntity<>(attractions,HttpStatus.OK);
    }
}
