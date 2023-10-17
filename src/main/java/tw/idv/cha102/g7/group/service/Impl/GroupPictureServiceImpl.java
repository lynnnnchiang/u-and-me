package tw.idv.cha102.g7.group.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.group.entity.GroupPicture;
import tw.idv.cha102.g7.group.repo.GroupPictureRepository;
import tw.idv.cha102.g7.group.service.GroupPictureService;

import java.util.List;

@Component
public class GroupPictureServiceImpl implements GroupPictureService {

    @Autowired
    GroupPictureRepository groupPictureRepository;

    public void insert(GroupPicture groupPicture) {
        groupPictureRepository.save(groupPicture);
    }

    public void update(Integer groupPicId, GroupPicture groupPicture) {
        if (getGroupPictureByGroupPicId(groupPicId) != null) {
            groupPicture.setGroupPicId(groupPicId);
            groupPictureRepository.save(groupPicture);
        }
    }

    public void delete(Integer groupPicId) {
        groupPictureRepository.deleteById(groupPicId);
    }

    public GroupPicture getGroupPictureByGroupPicId(Integer groupPicId) {
        return groupPictureRepository.findById(groupPicId).orElse(null);
    }

    public List<GroupPicture> getAll() {
        return groupPictureRepository.findAll();
    }

    @Override
    public List<GroupPicture> findByGroupIdOrderByGroupPicId(Integer groupId) {
        return groupPictureRepository.findByGroupIdOrderByGroupPicId(groupId);
    }
}
