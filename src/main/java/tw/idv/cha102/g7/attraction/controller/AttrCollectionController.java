package tw.idv.cha102.g7.attraction.controller;


import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.attraction.dto.AttrCollectionDTO;
import tw.idv.cha102.g7.attraction.dto.AttrCollectionId;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.attraction.service.AttrCollectionService;
import tw.idv.cha102.g7.member.entity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.TreeSet;

@RestController
@RequestMapping("/attrCol")
public class AttrCollectionController {

    @Autowired
    private AttrCollectionService attrCollectionService;

    /**
     * 加入景點收藏
     * @param attrCollectionDTO
     * @return String "success" or "failed"
     */
    @RequestMapping("/addAttrToCollection")
    public ResponseEntity<String> addAttrToCollection(@RequestBody AttrCollectionId attrCollectionId) {
        System.out.println("adddd");
    return new ResponseEntity(attrCollectionService.addAttrToCollection(attrCollectionId),HttpStatus.OK);
    }
    /*{
        "collectionId": {
  		    "memId":3,
  		    "attrId": 2
	            },

	    "attrColIlla": "第三個會員對第二個景點"
    }*/


    /**
     * 移除景點收藏
     * @param collectionId
     * @return String "success" or "failed"
     */
    @RequestMapping("/removeAttrFromCollection")
    public ResponseEntity<AttrCollectionId> removeAttrFromCollection(@RequestBody AttrCollectionId collectionId){

            return new ResponseEntity(attrCollectionService.removeAttrFromCollection(collectionId),HttpStatus.OK);
    }
    /*
    * {
  		    "memId":3,
  		    "attrId": 2
	   }
    * */

    /**
     * 顯示一個會員所有的景點收藏
     * @param memId
     * @return
     */
    @RequestMapping("/getAttrsFromCollectionByMemId/{memId}")
    public ResponseEntity<List<Attraction>> getAttrsFromCollectionByMemId(@PathVariable Integer memId){
        return new ResponseEntity<>(attrCollectionService.findAttrsByMemId(memId), HttpStatus.OK);
    }

    /**
     * 顯示一個會員所有的景點收藏 過濾已下架景點
     * @param memId
     * @return
     */
    @RequestMapping("/getAttrsFromCollectionByMemIdFilter/{memId}")
    public ResponseEntity<List<Attraction>> getAttrsFromCollectionByMemIdFilter(HttpServletRequest request,
                                                                                HttpServletResponse response,
                                                                                @PathVariable Integer memId){
        return new ResponseEntity(attrCollectionService.findAttrsByMemIdFilter(request,response,memId), HttpStatus.OK);
    }

    /**
     * 顯示一個會員所有的景點收藏 過濾已下架景點 會員本人用
     * @param memId
     * @return
     */
        @RequestMapping("/getAttrsFromCollectionByMem")
    public ResponseEntity<List<Attraction>> getAttrsFromCollectionByMem(HttpServletRequest request,
                                                                                HttpServletResponse response){
        return new ResponseEntity(attrCollectionService.findAttrsByMem(request,response), HttpStatus.OK);
    }


    /**
     * 一個會員透過名稱的模糊筆對 查詢他自己所有景點收藏中符合條件的景點收藏
     * @param memId
     * @param attrName
     * @return
     */
    @RequestMapping("/getAttrsFromCollectionByAttrName/{memId}/{attrName}")
    public ResponseEntity<List<AttrCollectionDTO>> getAttrsFromCollectionByMemName(
            @PathVariable Integer memId,
            @PathVariable String attrName){
        System.out.println("IN");
        return new ResponseEntity<>(attrCollectionService.findAttrCollectionsByAttrName(memId,attrName),HttpStatus.OK);
    }

    @RequestMapping("/getMemsByAttrId/{attrId}")
    public TreeSet<Member> getMemsByAttrId(@PathVariable Integer attrId){
        return attrCollectionService.returnMemsByAttrId(attrId);
    }

    @RequestMapping("/ifMemContainAttrInCol/{attrId}")
    public ResponseEntity<AttrCollectionDTO> ifMemContainAttrInCol(@PathVariable Integer attrId,
                                         HttpServletRequest request){
        return new ResponseEntity(attrCollectionService.ifMemGotTheAttr(request,attrId),HttpStatus.OK);

    }


}
