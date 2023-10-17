package tw.idv.cha102.g7.group.dto;

import java.sql.Date;

public interface GroupRegFormMemberDetailDto {
    Integer getGroupId();

    Integer getDetailId();

    Integer getFormId();

    String getName();

    String getEmail();

    String getPhone();

    Integer getJoinMember();

    String getIdnumber();

    Date getBirthday();

    String getGender();

    Integer getRefundSta();

}
