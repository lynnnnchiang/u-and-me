package tw.idv.cha102.g7.group.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.cha102.g7.group.entity.GroupRep;

import java.util.List;

public interface GroupRepRepository extends JpaRepository<GroupRep, Integer> {
    List<GroupRep> findByGroupRepSta(Integer GroupRepSta);

    List<GroupRep> findByMemIdAndGroupRepSta(Integer MemId ,Integer GroupRepSta);
}
