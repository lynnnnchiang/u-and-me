package tw.idv.cha102.g7.member.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    Integer memId;
    String oldMemPassword;
    String newMemPassword;
}
