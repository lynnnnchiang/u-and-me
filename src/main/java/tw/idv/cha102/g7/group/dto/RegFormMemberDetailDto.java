package tw.idv.cha102.g7.group.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Timestamp;

public interface RegFormMemberDetailDto {
    Integer getGroup_Id();
    Integer getMem_Id();
    Integer getDetail_Id();

    Integer getForm_Id();

    String getName();

    String getEmail();

    String getPhone();

    Integer getJoin_Member();

    String getIdnumber();

    Date getBirthday();

    String getGender();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp getReg_Time();

    Integer getRefund_Sta();
}
