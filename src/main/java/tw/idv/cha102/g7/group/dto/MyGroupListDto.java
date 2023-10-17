package tw.idv.cha102.g7.group.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public interface MyGroupListDto {
    Integer getGroup_Id();

    String getTheme();

    Integer getSch_Id();

    Integer getGroup_Sta();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp getDeadline();

    Integer getPayment_Sta();
}
