package tw.idv.cha102.g7.attraction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.attraction.dto.AttrCollectionDTO;
import tw.idv.cha102.g7.attraction.dto.AttrCollectionId;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.attraction.repo.AttrCollectionRepository;
import tw.idv.cha102.g7.attraction.repo.AttrRepository;
import tw.idv.cha102.g7.attraction.service.AttrCollectionService;
import tw.idv.cha102.g7.attraction.service.AttrService;
import tw.idv.cha102.g7.member.entity.Member;
import tw.idv.cha102.g7.member.repo.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Component
public class AttrCollectionServiceImpl implements AttrCollectionService {
    @Autowired
    private AttrCollectionRepository attrCollectionRepository;
    @Autowired
    private AttrRepository attrRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AttrService attrService;

    @Override
    public TreeSet<Member> returnMemsByAttrId(Integer attrId){
        List<AttrCollectionDTO> attrCollectionDTOS = attrCollectionRepository.findAll();
        TreeSet<Member> members = new TreeSet<>();

        for(AttrCollectionDTO attrCollectionDTO: attrCollectionDTOS){
            if(attrCollectionDTO.getCollectionId().getAttrId()==attrId){
                members.add(memberRepository.findById(attrCollectionDTO.getCollectionId().getMemId()).orElse(null));
            }
        }
        return members;
    }


    /**
     * 先查詢有無此會員，以及有無此景點，
     * 若皆有則新增或更新已有的收藏
     *
     * @param attrCollectionDTO
     * @return String
     */
    @Override
    public ResponseEntity<AttrCollectionDTO> addAttrToCollection(AttrCollectionId attrCollectionId) {
        AttrCollectionDTO attrCollectionDTO = new AttrCollectionDTO();
        if (attrRepository.findById(attrCollectionId.getAttrId()) == null
                || memberRepository.findById(attrCollectionId.getMemId()) == null) {
            return new ResponseEntity<>(new AttrCollectionDTO(),HttpStatus.OK);
        }
        System.out.println("good");
        attrCollectionDTO.setCollectionId(attrCollectionId);
        System.out.println(attrCollectionDTO);
//        attrCollectionRepository.save(attrCollectionDTO);
        return new ResponseEntity<>(attrCollectionRepository.save(attrCollectionDTO),HttpStatus.OK);
    }

    @Override
    public List<Attraction> findAttrsByMem(HttpServletRequest request,
                                           HttpServletResponse response){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        if(obj==null){
            return new ArrayList<>();
        }
        Integer memId = Integer.parseInt(obj.toString());
        List<AttrCollectionDTO> attrCollectionDTOS = attrCollectionRepository.findAll();
        List<Attraction> returnList = new ArrayList<>();
        for(AttrCollectionDTO  attrCollectionDTO:attrCollectionDTOS){
            Attraction attraction = attrRepository.findById(attrCollectionDTO.getCollectionId().getAttrId()).orElse(null);
            if(attraction==null){
                return returnList;
            }
            if(attrCollectionDTO.getCollectionId().getMemId()==memId &&
                    attraction.getAttrSta()!=0){
                returnList.add(attraction);
                System.out.println(attraction);
            }
        }
        return returnList;
    }


    /**
     * 先查詢有無此會員，以及有無此景點，
     * 若皆有則刪除已有的收藏
     * @param attrCollectionId
     * @return String "success" or "failed"
     */
    @Override
    @Transactional
    public ResponseEntity<AttrCollectionId> removeAttrFromCollection(AttrCollectionId attrCollectionId) {
        if (attrCollectionRepository.findByCollectionId(attrCollectionId) == null) {
            return new ResponseEntity<>(new AttrCollectionId(),HttpStatus.OK);
        }
        attrCollectionRepository.deleteByCollectionId(attrCollectionId);
        return new ResponseEntity<>(attrCollectionId,HttpStatus.OK);

    }

    /**
     * 透過會員編號，找出該會員的所有景點收藏
     * @param memId
     * @return List<AttrCollectionDTO>
     */
    @Override
    public List<Attraction> findAttrsByMemId(Integer memId) {
        List<AttrCollectionDTO> dtoList = attrCollectionRepository.findAll();
        List<Attraction> returnList = new ArrayList<>();
        System.out.println(memId);
        for(AttrCollectionDTO dto:dtoList){
            if(dto.getCollectionId().getMemId() == memId){
                ;
                returnList.add(attrRepository.findById(dto.getCollectionId().getAttrId()).orElse(null));
            }
        }
        return returnList;
//        return attrCollectionRepository.findByCollectionId((attrCollectionRepository.findByMemId(memId)));
    }

    /**
     * 透過會員名稱，找出該會員的所有景點收藏
     * @param memName
     * @return List<AttrCollectionDTO>
     */
    @Override
    public List<AttrCollectionDTO> findAttrCollectionsByMemName(String memName) {
        Integer memId = memberRepository.findByMemNameContaining(memName).getMemId();
        System.out.println(memId);
        List<AttrCollectionDTO> dtoList = attrCollectionRepository.findAll();
        List<AttrCollectionDTO> returnList = new ArrayList<>();
        System.out.println(memName);
        for(AttrCollectionDTO dto:dtoList){
            if(dto.getCollectionId().getMemId() == memId){
                returnList.add(dto);
            }
        }
        return returnList;
//        return attrCollectionRepository.findByCollectionId((attrCollectionRepository.findByMemId(memId)));
    }

    /**
     * 透過會員編號，找出該會員的所有景點收藏 過濾已下架景點
     *
     * @param memId
     * @return List<AttrCollectionDTO>
     */
    @Override
    public List<Attraction> findAttrsByMemIdFilter(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   Integer memId){
        List<Attraction> returnList = new ArrayList<>();
        List<AttrCollectionDTO> attrCollectionDTOList = attrCollectionRepository.findAll();
        System.out.println(attrCollectionDTOList);
        for(AttrCollectionDTO attrCollectionDTO:attrCollectionDTOList){
            Attraction attraction = attrRepository.findById(attrCollectionDTO.getCollectionId().getAttrId()).orElse(null);
            if(attraction==null){
                continue;
            }
            if(attrCollectionDTO.getCollectionId().getMemId()==memId &&
                        attraction.getAttrSta()!=0){
                returnList.add(attraction);
                System.out.println(attraction);
            }
        }
        return returnList;
    }

    @Override
    public List<AttrCollectionDTO> findAttrCollectionsByAttrName(
            Integer memId,
            String attrName
    ){
        List<Attraction> attractions = attrRepository.findAllByAttrNameContaining(attrName);
        List<Integer> attrIds = new ArrayList<>();
        for(Attraction attraction:attractions){
            attrIds.add(attraction.getAttrId());
        }

        List<AttrCollectionDTO> attrCollectionDTOS = attrCollectionRepository.findAll();
        List<AttrCollectionDTO> returnList = new ArrayList<>();
        for(AttrCollectionDTO attrCollectionDTO:attrCollectionDTOS){
            for(Integer attrId:attrIds){
                if(attrCollectionDTO.getCollectionId().getAttrId()==attrId
                        &&!returnList.contains(attrCollectionDTO)){
                    returnList.add(attrCollectionDTO);
                }
            }
        }
        return returnList;

    }

    @Override
    public ResponseEntity<AttrCollectionDTO> ifMemGotTheAttr(HttpServletRequest request,
                                   Integer attrId){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("memberId");
        if(obj==null){
            return new ResponseEntity<>(new AttrCollectionDTO(),HttpStatus.OK);
        }
        Integer memId = Integer.parseInt(obj.toString());
        List<AttrCollectionDTO> attrCollectionDTOS = attrCollectionRepository.findAll();
        for(AttrCollectionDTO  attrCollectionDTO:attrCollectionDTOS){
            Attraction attraction = attrRepository.findById(attrCollectionDTO.getCollectionId().getAttrId()).orElse(null);
            if(attrCollectionDTO.getCollectionId().getAttrId()==attrId &&
                    attrCollectionDTO.getCollectionId().getMemId()==memId &&
                    attraction.getAttrSta()!=0){
                System.out.println("FOUND");
                return new ResponseEntity<>(attrCollectionDTO,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new AttrCollectionDTO(),HttpStatus.OK);
    }
}
