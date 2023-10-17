package tw.idv.cha102.g7.attraction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tw.idv.cha102.g7.attraction.entity.GroupRegisterCard;
import tw.idv.cha102.g7.attraction.repo.GroupIdCardRepository;
import tw.idv.cha102.g7.attraction.service.GroupOwnerIdCardService;

@Service
public class GroupOwnerIdCardServiceImpl implements GroupOwnerIdCardService {

    @Autowired
    private GroupIdCardRepository groupIdCardRepository;

    @Override
    public ResponseEntity<GroupRegisterCard> insertPic(GroupRegisterCard groupRegisterCard){
        return new ResponseEntity<>(groupIdCardRepository.save(groupRegisterCard), HttpStatus.OK);
    }
}
