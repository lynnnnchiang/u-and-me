package tw.idv.cha102.g7.attraction.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.attraction.entity.Attraction;

import java.util.List;

@Repository
public interface AttrRepository extends JpaRepository<Attraction, Integer> {

    public List<Attraction> findAllByAttrNameContaining(String attrName);

    //TRY PAGEABLE
    public Page<Attraction> findAllByAttrNameContaining(String attrName, Pageable pageable);

    public Attraction findByAttrName(String attrName);

    public List<Attraction> findAllByAttrType(String attrType);

}
