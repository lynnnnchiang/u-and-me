package tw.idv.cha102.g7.group.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public interface GroupRegFormDto {

    byte[] getCover();
    Integer getGroup_Id();

    Integer getForm_Id();
    Integer getMem_Id();
    String getTheme();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Timestamp getDep_Date();

}
