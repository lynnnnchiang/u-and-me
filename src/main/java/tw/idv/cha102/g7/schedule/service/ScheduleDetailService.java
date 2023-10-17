package tw.idv.cha102.g7.schedule.service;

import tw.idv.cha102.g7.schedule.dto.ScheduleDetailsDTO;
import tw.idv.cha102.g7.schedule.entity.ScheduleDetail;

import java.util.List;

public interface ScheduleDetailService {

    /**
     * 查詢單一個行程細節
     */
    public ScheduleDetail findOneDetail(Integer schdeId);

    /**
     * 查詢單一行程的所有行程細節
     */
    public List<ScheduleDetail> findBySchId(Integer schId);

    /**
     * 行程細節內容進行修改(更新)
     */
    public ScheduleDetail updateDetails(ScheduleDetail scheduleDetail);

    /**
     * 新增行程細節(單個)
     */
    public ScheduleDetail addDetail(ScheduleDetail scheduleDetail);

    /**
     * 新增行程細節(多個)
     */
    public List<ScheduleDetail> addDetailList(List<ScheduleDetail> scheduleDetailList);

    /**
     * 依照行程細節id刪除行程細節(刪除一筆行程細節)
     */
    public void deleteDetail(Integer schdeId);

    /**
     * 依照行程id刪除所有行程細節(刪除多筆行程細節)
     */
    public int deleteDetailsInSch(Integer schId);


    /**
     * 見ScheduleManageController.java
     * 查詢單一行程詳細資訊(包含行程、 行程標籤、行程細節)後，對行程內容進行修改(增、刪、改、查)
     */
//    public ScheduleDetailsDTO findAllDetailsBySchId(Integer schId);

    // 修改行程公開權限(私密、透過連結分享、公開)
    // 行程細節中起始時間、停留時間及交通方式變動時，時間要能夠重新調整、排序
    // 要能搜尋、新增自訂私人景點、看到景點收藏
    // 複製行程細節時，向下插入複製的行程細節後，調整行程細節排序
    // 列印行程詳細資訊(轉換成pdf檔)
    // 行程共同編輯成員及權限設定(可檢視、可編輯、新增及移除共編成員，透過連結分享邀請成員共編)
}
