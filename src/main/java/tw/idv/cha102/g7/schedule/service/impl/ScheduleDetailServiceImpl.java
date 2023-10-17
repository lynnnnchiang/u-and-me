package tw.idv.cha102.g7.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.schedule.dto.ScheduleDetailsDTO;
import tw.idv.cha102.g7.schedule.entity.ScheduleDetail;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;
import tw.idv.cha102.g7.schedule.repo.ScheduleDetailRepository;
import tw.idv.cha102.g7.schedule.repo.ScheduleRepository;
import tw.idv.cha102.g7.schedule.repo.ScheduleTagRepository;
import tw.idv.cha102.g7.schedule.service.ScheduleDetailService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleDetailServiceImpl implements ScheduleDetailService {

    @Autowired
    private ScheduleDetailRepository detailRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleTagRepository tagRepository;


    @Override
    public ScheduleDetail findOneDetail(Integer schdeId) {
        return detailRepository.findById(schdeId).orElse(null);
    }

    @Override
    public List<ScheduleDetail> findBySchId(Integer schId) {
        return detailRepository.findBySchId(schId);
    }

    @Override
    public ScheduleDetail updateDetails(ScheduleDetail scheduleDetail) {
        if (scheduleDetail != null) {
            return detailRepository.save(scheduleDetail);
        }
        return null;
    }

    @Override
    public ScheduleDetail addDetail(ScheduleDetail scheduleDetail) {
        return detailRepository.save(scheduleDetail);
    }

    @Override
    public List<ScheduleDetail> addDetailList(List<ScheduleDetail> scheduleDetailList) {
        List<ScheduleDetail> returnDetailList = new ArrayList<>();
        for (ScheduleDetail detail : scheduleDetailList) {
            ScheduleDetail returnDetail = detailRepository.save(detail);
            returnDetailList.add(returnDetail);
        }
        return returnDetailList;
    }

    @Override
    public void deleteDetail(Integer schdeId) {
        detailRepository.deleteById(schdeId);
    }

    @Transactional
    @Override
    public int deleteDetailsInSch(Integer schId) {
        return detailRepository.deleteBySchId(schId);
    }
}
