package tw.idv.cha102.g7.schedule.dto;

import java.sql.Date;

public interface ScheduleDayDTO {
    public Integer getSchId();

    public String getSchName();

    public Integer getMemId();

    public Date getSchStart();

    public Date getSchEnd();

    public Byte getSchPub();

    public Boolean getSchCopy();

    public Integer getSchCost();

    public Integer getDays();

}
