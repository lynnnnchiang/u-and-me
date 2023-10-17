package tw.idv.cha102.g7.attraction.service;

import org.springframework.http.ResponseEntity;
import tw.idv.cha102.g7.attraction.entity.GroupRegisterCard;

public interface GroupOwnerIdCardService {

    public ResponseEntity<GroupRegisterCard> insertPic(GroupRegisterCard groupRegisterCard);
}
