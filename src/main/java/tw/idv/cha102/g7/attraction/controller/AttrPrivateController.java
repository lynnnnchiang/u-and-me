package tw.idv.cha102.g7.attraction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.attraction.dto.AttrPrivateDTO;
import tw.idv.cha102.g7.attraction.dto.AttrPrivateId;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.attraction.service.AttrPrivateService;
import tw.idv.cha102.g7.attraction.service.AttrService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/attrPriv")
public class AttrPrivateController {

    @Autowired
    private AttrPrivateService attrPrivateService;

    @Autowired
    private AttrService attrService;


    /**
     * 新增一個私人景點到景點資料庫 預設可見度是true
     * @param attraction
     * @return ResponseEntity<Attraction>
     */
    @PostMapping("/createPrivateAttr")
    public ResponseEntity<AttrPrivateDTO> createPrivateAttraction(@RequestBody Attraction attraction,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response){
        return new ResponseEntity(attrPrivateService.addAndUpdatePrivateAttraction(request,response,attraction), HttpStatus.OK);
    }

    /**
     * 刪除一個私人景點from景點資料庫
     * @param attraction
     * @return
     */
    @PostMapping("/deletePrivateAttr")
    public ResponseEntity<String> deletePrivateAttraction(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          Integer attrId){
        return new ResponseEntity(deletePrivateAttraction(request,response,attrId),HttpStatus.OK);
    }

    /**
     * Deprecated
     * 新增一個私人景點到私人景點資料庫
     * @param request
     * @param response
     * @param attraction
     * @return
     */
    @PostMapping("/createPrivateAttrDTO")
    public ResponseEntity<AttrPrivateDTO> createPrivateAttr(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            @RequestBody Attraction attraction){
        return attrPrivateService.addAndUpdatePrivateAttraction(request,response,attraction);
    }

    /**
     * 修改一個私人景點
     * @param request
     * @param response
     * @param attraction
     * @return
     */
    @RequestMapping("/updatePrivateAttrDTO")
    public ResponseEntity<AttrPrivateDTO> updatePrivateAttr(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            @RequestBody Attraction attraction){
        return attrPrivateService.addAndUpdatePrivateAttraction(request,response,attraction);
    }

    /**
     * 刪除一個私人景點
     * @param attrPrivateId
     * @return
     */
    @RequestMapping("/deletePrivateAttractionDTO")
    public ResponseEntity<String> deletePrivateAttractionDTO(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          @RequestBody Integer attrId){
        return new ResponseEntity(attrPrivateService.
                            deletePrivateAttraction(request,response,attrId),HttpStatus.OK);
    }

    /**
     * 查詢一個私人景點
     * @param attrPrivateId
     * @return AttrPrivateDTO
     */
    @RequestMapping("/getPrivateAttractionDTO")
    public AttrPrivateDTO getPrivateAttraction(@RequestBody AttrPrivateId attrPrivateId){
        return attrPrivateService.getPrivateAttraction(attrPrivateId);
    }

    /**
     * 查詢所有私人景點
     * @param memId
     * @return AttrPrivateDTO
     */
        @RequestMapping("/getPrivateAttractions/{memId}")
    public List<Attraction> getPrivateAttractions(@PathVariable Integer memId){
        return attrPrivateService.getPrivateAttractionByMemId(memId);
    }




}
