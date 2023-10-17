package tw.idv.cha102.g7.attraction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.attraction.dto.AttrPictureDTO;
import tw.idv.cha102.g7.attraction.entity.AttrPicture;

import java.util.List;

@Repository
public interface AttrPictureRepository extends JpaRepository<AttrPicture, Integer> {

    public List<AttrPicture> findAllByAttrId(Integer attrId);

    public void removeByAttrPicId(Integer attrPicId);
    public void deleteAllByAttrId(Integer attrId);
}
