package tw.idv.cha102.g7.attraction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.attraction.dto.AttrCollectionDTO;
import tw.idv.cha102.g7.attraction.dto.AttrPictureDTO;
import tw.idv.cha102.g7.attraction.entity.AttrPicture;
import tw.idv.cha102.g7.attraction.entity.Attraction;
import tw.idv.cha102.g7.attraction.entity.GroupRegisterCard;
import tw.idv.cha102.g7.attraction.service.AttrCollectionService;
import tw.idv.cha102.g7.attraction.service.AttrPictureService;
import tw.idv.cha102.g7.attraction.service.AttrService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import tw.idv.cha102.g7.attraction.service.GroupOwnerIdCardService;
import tw.idv.cha102.g7.member.service.MemberService;

@RestController
@CrossOrigin
//@RequestMapping("/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AttrPictureService attrPictureService;
    @Autowired
    private AttrCollectionService attrCollectionService;

    @Autowired
    private GroupOwnerIdCardService groupOwnerIdCardService;

    @RequestMapping("/memberGroupRegister/{memId}")
    public ResponseEntity<GroupRegisterCard> memberGroupRegister(@PathVariable Integer memId,
                                                             @RequestBody GroupRegisterCard groupRegisterCard){
        GroupRegisterCard groupRegisterCard1 = new GroupRegisterCard();

        groupRegisterCard1.setMemId(memId);
        groupRegisterCard1.setGroupIdCard(groupRegisterCard.getGroupIdCard());
        groupRegisterCard1.setGroupIdCardPic(groupRegisterCard.getGroupIdCardPic());
        System.out.println(groupRegisterCard1);

        return new ResponseEntity(groupOwnerIdCardService.insertPic(groupRegisterCard1), HttpStatus.OK);
    }


    /**
     * 導向首頁
     * @param model
     * @return String 首頁HTML名稱 不含附檔名
     */
    //Spring MVC html轉向寫法
    @GetMapping("/index")
    public String thymeleafExample(Model model) {
        model.addAttribute("pageTitle", "Thymeleaf Example");
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "index";
    }

    /**
     * 導向地圖頁面
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/AttractionPage")
    public void AttractionPage(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
        String redirectUrl = "AttractionPage.html"; // 設定要重定向的目標 URL
        res.sendRedirect(redirectUrl);
    }

    /**
     * 導向景點查詢頁面
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/listAllAttraction")
    public void listAllAttraction(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
        String redirectUrl = "listAllAttraction.html"; // 設定要重定向的目標 URL
        System.out.println(res);
        res.sendRedirect(redirectUrl);
    }

    @RequestMapping("/getAttr")
    public Attraction getAttr(@RequestParam Integer attrId) {
        return attrService.getById(attrId);
    }

    //Spring MVC html轉向寫法
    @RequestMapping("/getAllAttr")
    public ResponseEntity<List<Attraction>> getAllAttr(/*Model model*/) {
        return new ResponseEntity(attrService.getAll(),HttpStatus.OK);
    }

    @RequestMapping("/getAttrByName/{attrName}")
    public ResponseEntity<Attraction> getAttrByName(@PathVariable String attrName){
        return new ResponseEntity(attrService.getAttrByName(attrName),HttpStatus.OK);
    }

    @RequestMapping("/getAttrsByName/{attrName}")
    public ResponseEntity<List<Attraction>> getAttrsByName(@PathVariable String attrName){
        return new ResponseEntity(attrService.getAttrsByName(attrName),HttpStatus.OK);
    }

    @RequestMapping("/getAttrsByNameFilter/{attrName}")
    public ResponseEntity<List<Attraction>> getAttrsByNameFilter(@PathVariable String attrName){
        return new ResponseEntity(attrService.getAttrByNameFilter(attrName),HttpStatus.OK);
    }
    @RequestMapping("/getAttrsFilter/{attrId}")
    public ResponseEntity<List<Attraction>> getAttrsFilter(@PathVariable Integer attrId){
        return new ResponseEntity(attrService.getAttrsFilter(attrId),HttpStatus.OK);
    }


    @GetMapping("/attr/all/{pageSize}/{page}")
    public List<Attraction> getAllPaged(@PathVariable Integer pageSize,@PathVariable Integer page){
        return attrService.getAllPaged(page,pageSize);
    }

    @GetMapping("/attr/{attrName}/{pageSize}/{page}")
    public List<Attraction> getAllPaged(@PathVariable String attrName,
                                        @PathVariable Integer pageSize,
                                        @PathVariable Integer page){

        Page<Attraction> pageResult = attrService.getAllPagedByName(attrName,page,pageSize);
        System.out.println(pageResult.getContent());
        return pageResult.getContent();
    }

    @RequestMapping("/insertNewAttraction")
    public ResponseEntity<Attraction> insertNewAttraction(@RequestBody Attraction attraction){
        return attrService.insertNewAttraction(attraction);
    }

    @RequestMapping("/deleteAttraction/{attrId}")
    public ResponseEntity<String> deleteNewAttraction(@PathVariable Integer attrId){
        attrService.deleteAttrByAttrId(attrId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/insertAttrPictures/{attrId}")
    public ResponseEntity<AttrPictureDTO> insertAttrPictures(@PathVariable Integer attrId,
                                                             @RequestBody AttrPicture pictures){
        System.out.println("IN controller");
        AttrPicture attrPicture = new AttrPicture();
//        for(String pic:pictures){
//            attrPictureDTO.setAttrId(attrId);
//            attrPictureDTO.setAttrPicData(pic);
//            attrPictureService.insertPictures(attrPictureDTO);
//        }
        System.out.println(pictures);
        attrPicture.setAttrId(attrId);
        attrPicture.setAttrPicData(pictures.getAttrPicData());
        attrPictureService.insertPictures(attrPicture);
        return new ResponseEntity(attrPictureService.insertPictures(attrPicture), HttpStatus.OK);
    }

    @RequestMapping("/deleteAttrPictures/{attrId}")
    public ResponseEntity<AttrPictureDTO> deleteAttrPictures(@PathVariable Integer attrId){
        attrPictureService.delPicByAttrId(attrId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/delAttrPic/{attrPicId}", method = {RequestMethod.POST, RequestMethod.GET})
    public String delAttrPic(@PathVariable Integer attrPicId){
        try{
            attrPictureService.delPicByAttrPicId(attrPicId);
        }catch(Exception e){
            return "Failed to delete the picture. ";
        }
        return "Picture deleted";
    }

    @RequestMapping(value = "/getAttrPics/{attrId}",
                    method = {RequestMethod.POST, RequestMethod.GET})
    public String getAttrPics(@PathVariable Integer attrId){
        return attrPictureService.getPicsByAttrId(attrId);
    }

    @RequestMapping(value = "/updateAttr/{attrId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Attraction> updateAttr(@PathVariable Integer attrId,
                                                 @RequestBody Attraction attraction){

        return new ResponseEntity(attrService.updateAttrByAttrId(
                                    attrId,attraction),
                                    HttpStatus.OK);
    }

    @RequestMapping("/getAttrsByType/{attrType}")
    public ResponseEntity<List<Attraction>> getAttrsByType(@PathVariable String attrType){
        return attrService.getAttrsByType(attrType);
    }



    //SERVLET 轉向寫法
//    @RequestMapping("/getAllAttr")
//    public void getAllAttr(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//
//        String url = "/jsp/getAllAttr.jsp";
//
//        List<Attraction> attraction = attractionService.getAll();
//        req.setAttribute("attractions", attraction);
//
//        RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
//        successView.forward(req, res);
//    }

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/json-data")
    public Attraction postJsonData(HttpServletRequest request) throws JsonProcessingException {
        // 在这里处理接收到的 JSON 数据
        Attraction attraction = attrService.getById(1);

        System.out.println("HIIIIIIII");

        final HttpSession session = request.getSession();
        session.setAttribute("loggedin", true);
        session.setAttribute("attraction", attraction);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(attraction);

        return attraction;
    }

    @GetMapping("/getCollectionAttrsByMemId/{memId}")
    public List<Attraction> getCollectionAttrsByMemId(@PathVariable Integer memId) {
        return attrCollectionService.findAttrsByMemId(memId);
    }

    @RequestMapping("/getCollectionAttrsByMemName/{memName}")
    public List<AttrCollectionDTO> findAttrCollectionsByMemName(@PathVariable String memName) {
        List<AttrCollectionDTO> attrCollectionDTOS =
                attrCollectionService.findAttrCollectionsByMemName(memName);
        if (attrCollectionDTOS == null)
            return null;
        else
            return attrCollectionDTOS;
    }


}
