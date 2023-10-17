package tw.idv.cha102.g7.attraction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.attraction.entity.GroupRegisterCard;

@Repository
public interface GroupIdCardRepository extends JpaRepository<GroupRegisterCard,Integer> {

}
