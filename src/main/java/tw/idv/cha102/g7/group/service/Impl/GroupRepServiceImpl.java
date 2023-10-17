package tw.idv.cha102.g7.group.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.group.entity.GroupRep;
import tw.idv.cha102.g7.group.repo.GroupRepRepository;
import tw.idv.cha102.g7.group.service.GroupRepService;

import java.util.List;

@Component
public class GroupRepServiceImpl implements GroupRepService {

    @Autowired
    private GroupRepRepository groupRepRepository;

    public void insert(GroupRep groupRep) {
        groupRepRepository.save(groupRep);
    }

    public void update(Integer groupRepId, GroupRep groupRep) {
        if (getGroupRepByGroupRepId(groupRepId) != null) {
            groupRep.setGroupRepId(groupRepId);
            groupRepRepository.save(groupRep);
        } else {
            throw new RuntimeException();
        }
    }

    public void delete(Integer groupRepId) {
        groupRepRepository.deleteById(groupRepId);
    }


    public GroupRep getGroupRepByGroupRepId(Integer groupRepId) {
        return groupRepRepository.findById(groupRepId).orElse(null);
    }

    public List<GroupRep> getAll() {
        return (List<GroupRep>) groupRepRepository.findAll();
    }

    @Override
    public List<GroupRep> findByGroupRepSta(Integer GroupRepSta) {
        return groupRepRepository.findByGroupRepSta(GroupRepSta);
    }

    @Override
    public List<GroupRep> findByMemIdAndGroupRepSta(Integer MemId, Integer GroupRepSta) {
        return groupRepRepository.findByMemIdAndGroupRepSta(MemId, GroupRepSta);
    }

}
