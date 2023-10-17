package tw.idv.cha102.g7.group.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public interface UpdateMyGroupDto {
    Integer getMin_Member();

    Integer getMax_Member();

    String getTheme();

    Integer getAmount();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp getDep_Date();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp getDeadline();

    String getGroup_Desc();

    String getNotice();

    byte[] getCover();
}
