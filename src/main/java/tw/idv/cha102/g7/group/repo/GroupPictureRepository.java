package tw.idv.cha102.g7.group.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.cha102.g7.group.entity.GroupPicture;

import java.util.List;

public interface GroupPictureRepository extends JpaRepository<GroupPicture, Integer> {

    List<GroupPicture> findByGroupIdOrderByGroupPicId(Integer groupId);
}
