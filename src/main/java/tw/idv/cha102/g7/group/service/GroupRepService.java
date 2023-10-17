package tw.idv.cha102.g7.group.service;

import tw.idv.cha102.g7.group.entity.GroupRep;

import java.util.List;

public interface GroupRepService {
    void insert(GroupRep groupRep);

    void update(Integer groupRepId, GroupRep groupRep);

    void delete(Integer groupRepId);

    GroupRep getGroupRepByGroupRepId(Integer groupRepId);

    List<GroupRep> getAll();

    List<GroupRep> findByGroupRepSta(Integer GroupRepSta);

    List<GroupRep> findByMemIdAndGroupRepSta(Integer MemId ,Integer GroupRepSta);
}
