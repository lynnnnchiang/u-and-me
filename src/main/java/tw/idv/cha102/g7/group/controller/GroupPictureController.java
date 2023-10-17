package tw.idv.cha102.g7.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.group.entity.GroupPicture;
import tw.idv.cha102.g7.group.service.GroupPictureService;

import java.util.List;

@RestController
@CrossOrigin
public class GroupPictureController {

    @Autowired
    private GroupPictureService groupPictureService;

    /**
     * 揪團團主
     * 插入揪團圖片
     * @param groupPicture 揪團圖片資訊
     */
    @PostMapping("/groupPicture")
    public void insert(@RequestBody GroupPicture groupPicture){
        groupPictureService.insert(groupPicture);
    }

    /**
     * 揪團團主
     * 修改揪團圖片
     * @param groupPicId 揪團ID
     * @param groupPicture 修改後的揪團圖片
     */
    @PutMapping("/groupPicture/{groupPicId}")
    public void update(@PathVariable Integer groupPicId,
                       @RequestBody GroupPicture groupPicture){
        groupPictureService.update(groupPicId ,groupPicture);
    }

    /**
     * 揪團團主/管理員
     * 刪除圖片
     * @param groupPicId 欲刪除的圖片ID
     */
    @DeleteMapping("/groupPicture/{groupPicId}")//刪除圖片
    public void delete(@PathVariable Integer groupPicId){
        groupPictureService.delete(groupPicId);
    }

    /**
     * 管理員/遊客/一般使用者/揪團團主
     * 以揪團ID查詢該揪團所有圖片
     * @param groupId
     * @return 查詢圖片結果
     */
    @GetMapping("groupPictures/{groupId}")
    public List<GroupPicture> findByGroupIdOrderByGroupPicId(@PathVariable Integer groupId) {
        return groupPictureService.findByGroupIdOrderByGroupPicId(groupId);
    }

    /**
     * 管理員
     * 查詢指定圖片
     * @param groupPicId 圖片ID
     * @return 查詢圖片結果
     */
    @GetMapping("/groupPicture/{groupPicId}")
    public GroupPicture getGroupPictureByGroupPicId(@PathVariable Integer groupPicId){
        return groupPictureService.getGroupPictureByGroupPicId(groupPicId);
    }

    /**
     * 尋找用處中...
     * @return 全部圖片結果
     */
    @GetMapping("/groupPictures/all")
    public List<GroupPicture> getAll(){
        return groupPictureService.getAll();
    }
}

