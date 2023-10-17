package tw.idv.cha102.g7.group.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.sql.Timestamp;

public interface GroupMemo {

    String getMem_Name();

    Integer getSch_Id();

    Integer getMembers();

    BigInteger getDays();

    Integer getMin_Member();

    Integer getMax_Member();

    Integer getAmount();

    String getTheme();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp getDep_Date();

    String getGroup_Desc();

    String getNotice();

    Integer getGroup_Sta();

    byte[] getCover();
}
