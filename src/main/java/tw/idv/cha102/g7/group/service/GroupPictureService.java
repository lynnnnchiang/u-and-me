package tw.idv.cha102.g7.group.service;

import tw.idv.cha102.g7.group.entity.GroupPicture;

import java.util.List;

public interface GroupPictureService {

    void insert(GroupPicture groupPicture);

    void update(Integer groupPicId, GroupPicture groupPicture);

    void delete(Integer groupPicId);

    GroupPicture getGroupPictureByGroupPicId(Integer groupPicId);

    List<GroupPicture> getAll();

    List<GroupPicture> findByGroupIdOrderByGroupPicId(Integer groupId);
}
