package tw.idv.cha102.g7.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagList;
import tw.idv.cha102.g7.schedule.dto.TagsInScheduleDTO;
import tw.idv.cha102.g7.schedule.dto.TagToSchedulesDTO;
import tw.idv.cha102.g7.schedule.entity.Schedule;
import tw.idv.cha102.g7.schedule.entity.ScheduleTag;
import tw.idv.cha102.g7.schedule.entity.ScheduleTagListId;
import tw.idv.cha102.g7.schedule.repo.ScheduleRepository;
import tw.idv.cha102.g7.schedule.repo.ScheduleTagListRepository;
import tw.idv.cha102.g7.schedule.repo.ScheduleTagRepository;
import tw.idv.cha102.g7.schedule.service.ScheduleTagService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleTagServiceImpl implements ScheduleTagService {

    @Autowired
    private ScheduleTagRepository tagRepository;

    @Autowired
    private ScheduleTagListRepository listRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleTag> findAll() {
        return tagRepository.findAll().stream()
                .sorted(Comparator.comparing(ScheduleTag::getSchTagId))
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleTag findById(Integer schTagId) {
        ScheduleTag scheduleTag = tagRepository.findById(schTagId).orElse(null);
        return scheduleTag;
    }

    @Override
    public List<ScheduleTag> findByName(String schTagName) {
        return tagRepository.findBySchTagNameContaining(schTagName);
    }


    @Override
    public List<Schedule> findSchedulesBySchTagId(Integer schTagId) {
        List<ScheduleTagList> schTagList = listRepository.findAll();
        List<Schedule> filtSchedulesPub = new ArrayList<>();
        List<ScheduleTagList> filtTagId = schTagList
                .stream()
                .filter(listId -> listId.getScheduleTagListId().getSchTagId() == schTagId)
                .collect(Collectors.toList());
        System.out.println(filtTagId);
        if (filtTagId != null) {
            for (ScheduleTagList tagList : filtTagId) {
                Schedule schContainsTag = new Schedule();
                Integer schId = tagList.getScheduleTagListId().getSchId();
                schContainsTag = scheduleRepository.findById(schId).orElse(null);
                if (schContainsTag != null && !filtSchedulesPub.contains(schContainsTag)) {
                    filtSchedulesPub.add(schContainsTag);
                }
            }
            if (filtSchedulesPub != null) {
                List<Schedule> returnSchedules = filtSchedulesPub
                        .stream()
                        .filter(sch -> sch.getSchPub() == 2)
                        .sorted(Comparator.comparing(Schedule::getSchStart).reversed())
                        .collect(Collectors.toList());
                return returnSchedules;
            }
        }
        return null;
    }


    @Override
    public List<TagToSchedulesDTO> findSchedulesBySchTagName(String schTagName) {
        List<ScheduleTag> tags = tagRepository.findBySchTagNameContaining(schTagName);

        List<Object[]> list = tagRepository.findSchedulesBySchTagName(schTagName);
        List<TagToSchedulesDTO> collect = list.stream().map(TagToSchedulesDTO::new).collect(Collectors.toList());
        return collect;
    }


    @Override
    public List<ScheduleTag> findTagsBySchId(Integer schId) {
        List<ScheduleTagList> stList = listRepository.findAll();
        List<ScheduleTagList> myList = new ArrayList<>();
        List<ScheduleTag> tagList = new ArrayList<>();
        ScheduleTag tag = null;
        for (ScheduleTagList stl : stList) {
            if (stl.getScheduleTagListId().getSchId() == schId) {
                // 將不符合schId的元素從stList中移除
                myList.add(stl);
                for (ScheduleTagList stl2 : myList) {
                    // 將stList的每個元素一一取出，用tagId查詢到對應的ScheduleTag物件加入tagList中
                    tag = tagRepository.findById(stl2.getScheduleTagListId().getSchTagId()).orElse(null);
                    // 若此ScheduleTag物件不存在於tagList才可加入
                    if (!tagList.contains(tag))
                        tagList.add(tag);
                }
            }
        }
        return tagList;
    }

    @Override
    public ScheduleTag createTag(ScheduleTag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Integer schTagId) {
        tagRepository.deleteById(schTagId);
    }

    @Transactional
    @Override
    public int deleteScheduleTagListBySchId(Integer schId) {
        return listRepository.deleteBySchId(schId);
    }

    @Transactional
    @Override
    public List<ScheduleTagList> addTagsInSchedule(List<ScheduleTagListId> scheduleTagListIds) {
        if (scheduleTagListIds != null) {
            List<ScheduleTagList> returnSchTagList = new ArrayList<>();
            for (ScheduleTagListId id : scheduleTagListIds) {
                ScheduleTagList list = new ScheduleTagList();
                ScheduleTagListId listId = new ScheduleTagListId();
                Integer schId = id.getSchId();
                Integer schTagId = id.getSchTagId();
                listId.setSchId(schId);
                listId.setSchTagId(schTagId);
                list.setScheduleTagListId(listId);

                ScheduleTagList returnList = listRepository.save(list);
                returnSchTagList.add(returnList);
            }
            return returnSchTagList;
        }
        return null;
    }
}
