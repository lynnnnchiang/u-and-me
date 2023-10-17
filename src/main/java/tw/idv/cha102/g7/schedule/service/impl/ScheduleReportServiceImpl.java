package tw.idv.cha102.g7.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.repo.GroupRepository;
import tw.idv.cha102.g7.group.service.GroupService;
import tw.idv.cha102.g7.group.service.Impl.GroupServiceImpl;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleReport;
import tw.idv.cha102.g7.schedule.repo.ScheduleReportRepository;
import tw.idv.cha102.g7.schedule.repo.ScheduleRepository;
import tw.idv.cha102.g7.schedule.service.ScheduleReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class ScheduleReportServiceImpl implements ScheduleReportService {

    @Autowired
    private ScheduleReportRepository reportRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private GroupServiceImpl groupService;

    @Override
    public void insert(ScheduleReport scheduleRep, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer memId = parseInt(session.getAttribute("memberId").toString());
        scheduleRep.setMemId(memId);
        reportRepository.save(scheduleRep);
    }

    @Override
    public void update(Integer schRepId, ScheduleReport scheduleRep) {
        var existReport = reportRepository.findById(schRepId);
        if (existReport.isPresent()) {
            reportRepository.save(scheduleRep);
        }
    }

    @Override
    public List<ScheduleReport> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<ScheduleReport> findByStatus(Short schRepSta) {
        return reportRepository.findByStatus(schRepSta);
    }

    @Override
    public ScheduleReport findBySchRepId(Integer schRepId) {
        ScheduleReport report = reportRepository.findById(schRepId).orElse(null);
        return report;
    }

    @Override
    public void hideById(Integer schId) {
        // 先查詢此id的行程是否存在，再進行行程公開權限設定
        var schedule = scheduleRepository.findById(schId);
        if (schedule.isPresent()) {
            // 修改行程公開權限為0:私人檢視
            schedule.get().setSchPub((byte) 0);
            scheduleRepository.save(schedule.get());
        }
    }

    @Override
    public void modifyStatus(HttpServletRequest request, Integer schRepId, Byte schPub, ScheduleReport schReport) {
        HttpSession session = request.getSession();
        Integer hostId = parseInt(session.getAttribute("hostId").toString());
        var existReport = reportRepository.findById(schRepId);
        if (existReport.isPresent()) {
            // 存入更改的檢舉處理狀態
            existReport.get().setSchRepSta(schReport.getSchRepSta());
            // 存入修改檢舉處理狀態的管理員id
            existReport.get().setHostId(hostId);
            reportRepository.save(existReport.get());
        }

        // 存入對被檢舉的行程公開權限處理
        Schedule reportSch = scheduleRepository.findById(schReport.getSchId()).orElse(null);

        if (reportSch != null) {
            reportSch.setSchPub(schPub);
            System.out.println("!=null");
            if(schPub == 0){
                System.out.println("" +reportSch.getSchId());
                groupService.updGroupStaBySchRep(reportSch.getSchId());
            }
            scheduleRepository.save(reportSch);
        }
    }

    @Override
    @Transactional
    public void deleteReportedSchedule(Integer schId) {
        var existSchedule = scheduleRepository.findById(schId);
        if (existSchedule.isPresent()) {
            scheduleRepository.deleteById(schId);
        }
    }
}
