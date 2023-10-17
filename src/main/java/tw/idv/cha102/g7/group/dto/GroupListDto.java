package tw.idv.cha102.g7.group.dto;

import java.math.BigInteger;

public interface GroupListDto {
    Integer getGroup_Id();

    byte[] getCover();

    String getTheme();

    Integer getAmount();

    //Dto getXXX() XXX必須跟@QUERY裡的SELECT欄位同名才有辦法讀出資料
    Integer getM();

    BigInteger getD();

//    Integer getGroup_Sta();
}
