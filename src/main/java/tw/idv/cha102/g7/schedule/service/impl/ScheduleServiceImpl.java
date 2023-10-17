package tw.idv.cha102.g7.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.schedule.dto.ScheduleDayDTO;
import tw.idv.cha102.g7.schedule.dto.TagToSchedulesDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleDetail;
import tw.idv.cha102.g7.schedule.repo.ScheduleRepository;
import tw.idv.cha102.g7.schedule.service.ScheduleDetailService;
import tw.idv.cha102.g7.schedule.service.ScheduleService;
import tw.idv.cha102.g7.schedule.service.ScheduleTagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository repository;
    @Autowired
    private ScheduleTagService tagService;
    @Autowired
    private ScheduleDetailService detailService;

    @Override
    public List<Schedule> findAllPublicPaged(int page, int size) {
        List<Schedule> publicSche = repository.findAllPublic();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, publicSche.size());

        List<Schedule> pagedPublicSchedules = publicSche.subList(startIndex, endIndex);
        return pagedPublicSchedules;
//
//        Page<Schedule> pageResult = repository.findAll(
//                PageRequest.of(page, //查詢的頁數 從0開始
//                        size,//查詢的每頁筆數
//                        Sort.by("schStart").descending())); //依造sch_start欄位降冪排序
//            List<Schedule> scheduleList = pageResult.getContent();
//            List<Schedule> filtScheList = scheduleList.stream().filter(schedule -> schedule.getSchPub() == 2).collect(Collectors.toList());
//        可能會運用到的方法
//        pageResult.getNumberOfElements(); //本頁筆數
//        pageResult.getSize(); //每頁筆數
//        pageResult.getTotalElements(); //全部筆數
//        pageResult.getTotalPages(); //全部頁數

    }

    @Override
    public List<Schedule> findAllPublicPagedASC(int page, int size) {
        List<Schedule> publicSche = repository.findAllPublicASC();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, publicSche.size());

        List<Schedule> pagedPublicSchedules = publicSche.subList(startIndex, endIndex);
        return pagedPublicSchedules;
    }

    @Override
    public List<Schedule> findAllPublic() {
        return repository.findAllPublic();
    }

    @Override
    public Stream<Schedule> findBySchNamePaged(String schName, int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return repository.findBySchName(schName, pageable).get();
    }

    @Override
    public List<Schedule> findBetweenDate(Date schStart, Date schEnd) {
        return repository.findBetweenDate(schStart, schEnd);
    }

    @Override
    public Stream<ScheduleDayDTO> findOrderByDays(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return repository.findOrderByDays(pageable).get();
    }

    @Override
    public Stream<ScheduleDayDTO> findOrderByDaysDESC(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return repository.findOrderByDaysDESC(pageable).get();
    }

    @Override
    public Schedule getById(Integer schId) {
        Schedule schedule = repository.findById(schId).orElse(null);
        return schedule;
    }

    @Override
    public List<Schedule> getAll() {
        return repository.findAll();
    }


    @Transactional
    @Override
    public Schedule copyOneSchedule(Integer schId, Integer memId) {
        // 先複製行程大綱內容，創造新的行程大綱及細節資料容器
        Schedule copySchedule = new Schedule();
        List<ScheduleDetail> copyDetailsList = new ArrayList<>();

        // 透過行程id找到對應行程大綱
        Schedule existSch = repository.findById(schId).orElse(null);
        // 透過行程id找到對應行程細節
        List<ScheduleDetail> existSchDetails = detailService.findBySchId(schId);

        if (existSch != null) {
            // 行程大綱內容
            copySchedule.setSchName(existSch.getSchName());
            copySchedule.setMemId(memId);
            copySchedule.setSchStart(existSch.getSchStart());
            copySchedule.setSchEnd(existSch.getSchEnd());
            copySchedule.setSchPub(existSch.getSchPub());
            copySchedule.setSchCopy(existSch.getSchCopy());
            copySchedule.setSchCost(existSch.getSchCost());
            Schedule returnCopySch = repository.save(copySchedule);
            // 拿取新建立的行程大綱內容id
            Integer copySchId = returnCopySch.getSchId();

            // 行程細節內容存入
            if (existSchDetails != null) {
                for (ScheduleDetail details : existSchDetails) {
                    ScheduleDetail copySchDetail = new ScheduleDetail();

                    copySchDetail.setSchId(copySchId);
                    copySchDetail.setAttrId(details.getAttrId());
                    copySchDetail.setSchdeStarttime(details.getSchdeStarttime());
                    copySchDetail.setSchdeStaytime(details.getSchdeStaytime());
                    copySchDetail.setSchdeTranstime(details.getSchdeTranstime());
                    copySchDetail.setSchdeTrans(details.getSchdeTrans());
                    copySchDetail.setSchdeCostname(details.getSchdeCostname());
                    copySchDetail.setSchdeCost(details.getSchdeCost());
                    copySchDetail.setSchdeRemark(details.getSchdeRemark());
                    // 將每一個行程細節存入行程list中
                    copyDetailsList.add(copySchDetail);
                    // 存入整個行程細節list中
                    detailService.addDetailList(copyDetailsList);
                }
            }
            return returnCopySch;
        }
        return null;
    }

    @Override
    public List<TagToSchedulesDTO> findTagsInOneSchdule(Integer schId) {
        List<Object[]> list = repository.findTagsByOneSchedule(schId);
        List<TagToSchedulesDTO> collect = list.stream().map(TagToSchedulesDTO::new).collect(Collectors.toList());
        return collect;
    }


    /***
     * 以上為搜瀏覽公開行程相關功能
     * 以下為管理行程功能
     */

    @Override
    public Stream<Schedule> getAllByMemId(Integer memId, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "sch_start");
        Pageable pageable = PageRequest.of(page, 6, sort);
        return repository.findByMemIdPaged(memId, pageable).get();
    }

    @Override
    public Stream<Schedule> getAllByMemIdASC(Integer memId, int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "sch_start");
        Pageable pageable = PageRequest.of(page, 6, sort);
        return repository.findByMemIdPaged(memId, pageable).get();
    }

    @Override
    public Stream<Schedule> findByMemIdAndSchNameDESC(Integer memId, String schName, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "sch_start");
        Pageable pageable = PageRequest.of(page, 6, sort);
        return repository.findByMemIdAndSchName(memId, schName, pageable).get();
    }

    @Override
    public Stream<ScheduleDayDTO> findByMemIdOrderByDays(Integer memId, int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return repository.findByMemIdOrderByDays(memId, pageable).get();
    }

    @Override
    public Stream<ScheduleDayDTO> findByMemIdOrderByDaysDESC(Integer memId, int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return repository.findByMemIdOrderByDaysDESC(memId, pageable).get();
    }

    @Override
    public Schedule create(Schedule schedule) {
        return repository.save(schedule);
    }

    @Transactional
    @Override
    public String deleteOneSchedule(Integer schId) {
        // 刪除行程前，需先刪除與其關聯的相關欄位
        // 從行程標籤清單中刪除行程與標籤關聯
        int deleteTags = tagService.deleteScheduleTagListBySchId(schId);
        // 此行程的所有行程細節均需刪除
        int deleteDetails = detailService.deleteDetailsInSch(schId);
        // 與此行程相關的行程檢舉要刪除
        // 活動推薦行程與揪團關聯行程清單記得要刪掉
        // 刪除行程
        repository.deleteById(schId);
        return "成功刪除" + deleteTags + "個標籤，" + deleteDetails + "個行程細節";
    }

    @Override
    public String edit(Integer schId, Schedule schedule) {
        // sche 為資料庫中已存在的行程，sche存在才能更新，否則回傳查詢不到資料
        Schedule sche = repository.findById(schId).orElse(null);
        if (sche != null) {  // schedule為欲修改的行程資料
            sche.setSchName(schedule.getSchName());
            sche.setSchStart(schedule.getSchStart());
            sche.setSchEnd(schedule.getSchEnd());
            sche.setSchCost(schedule.getSchCost());
            sche.setSchPub(schedule.getSchPub());
            sche.setSchCopy(schedule.getSchCopy());
            repository.save(sche);
            return "更新成功！";
        } else {
            return "更新失敗，查詢的行程不存在";
        }
    }

    @Override
    public void hide(Integer schId) {
        // 先查詢此id的行程是否存在，再進行行程公開設定
        var schedule = repository.findById(schId);
        if (schedule.isPresent()) {
            schedule.get().setSchPub((byte) 0);
            // 修改行程公開權限為0:私人檢視
            repository.save(schedule.get());
        }
    }

    @Override
    public Schedule privateSelect(Integer schId, Byte schPub) {
        var schedule = repository.findById(schId);
        if (schedule.isPresent()) {
            schedule.get().setSchPub(schPub);
            return repository.save(schedule.get());
        }
        return null;
    }

    @Override
    public Schedule copyrightSelect(Integer schId, Boolean schCopy) {
        var schedule = repository.findById(schId);
        if (schedule.isPresent()) {
            schedule.get().setSchCopy(schCopy);
            return repository.save(schedule.get());
        }
        return null;
    }

    //宇航 > 查詢會員公開行程
    @Override
    public Stream<Schedule> findPublicSchByMemIdPaged(HttpServletRequest request, Integer page) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());

        Sort sort = Sort.by(Sort.Direction.DESC, "sch_start");
        Pageable pageable = PageRequest.of(page, 6, sort);
        return repository.findPublicSchByMemIdPaged(memId, pageable).get();
    }
}


