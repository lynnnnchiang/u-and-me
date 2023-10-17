package tw.idv.cha102.g7.attraction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.cha102.g7.attraction.dto.AttrCollectionDTO;
import tw.idv.cha102.g7.attraction.dto.AttrCollectionId;

import java.util.List;

public interface AttrCollectionRepository extends JpaRepository<AttrCollectionDTO, AttrCollectionId> {
    public List<AttrCollectionDTO> findByCollectionId(AttrCollectionId collectionId);

    public void deleteByCollectionId(AttrCollectionId collectionId);

//    public List<AttrCollectionDTO> findByMemNameContaining(String memName);


//    public List<CollectionDTO> findByMemId(Integer memId);


}
