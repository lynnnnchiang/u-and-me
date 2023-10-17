package tw.idv.cha102.g7.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.activity.entity.DTO.ActivitySchRecommend;
import tw.idv.cha102.g7.activity.entity.DTO.ActivitySchRecommendId;


@Repository
public interface ActivitySchRecommendRepository extends JpaRepository<ActivitySchRecommend, ActivitySchRecommendId> {

}



