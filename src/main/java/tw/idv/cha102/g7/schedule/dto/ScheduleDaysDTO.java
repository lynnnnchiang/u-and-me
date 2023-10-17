package tw.idv.cha102.g7.schedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
public class ScheduleDaysDTO {
    private Integer schId;
    private String schName;
    private Integer memId;
    private Date schStart;
    private Date schEnd;
    private Byte schPub;
    private Boolean schCopy;
    private Integer schCost;
    private Integer days;

//    private Integer sch_id;
//    private String sch_name;
//    private Integer mem_id;
//    private Date sch_start;
//    private Date sch_end;
//    private Byte sch_pub;
//    private Boolean sch_copy;
//    private Integer sch_cost;
//    private Integer days;

//    Integer sch_id, String sch_name, Integer mem_id, Date sch_start, Date sch_end,
//    Boolean sch_copy, Integer sch_cost,  Byte sch_pub,Integer days

        public ScheduleDaysDTO(Object[] objects) {
        this.schId = (Integer) objects[0];
        this.schName = (String) objects[1];
        this.memId = (Integer) objects[2];
        this.schStart = (Date) objects[3];
        this.schEnd = (Date) objects[4];
        this.schPub = (Byte) objects[5];
        this.schCopy = (Boolean) objects[6];
        this.schCost = (Integer) objects[7];
        this.days = (Integer) objects[8];
    }

//    public ScheduleDaysDTO(Object[] objects) {
//        this.sch_id = (Integer) objects[0];
//        this.sch_name = (String) objects[1];
//        this.mem_id = (Integer) objects[2];
//        this.sch_start = (Date) objects[3];
//        this.sch_end = (Date) objects[4];
//        this.sch_pub = (Byte) objects[5];
//        this.sch_copy = (Boolean) objects[6];
//        this.sch_cost = (Integer) objects[7];
//        this.days = (Integer) objects[8];
//    }

//
//    public Integer getSch_id() {
//        return sch_id;
//    }
//
//    public String getSch_name() {
//        return sch_name;
//    }
//
//    public Integer getMem_id() {
//        return mem_id;
//    }
//
//    public Date getSch_start() {
//        return sch_start;
//    }
//
//    public Date getSch_end() {
//        return sch_end;
//    }
//
//    public Byte getSch_pub() {
//        return sch_pub;
//    }
//
//    public Boolean getSch_copy() {
//        return sch_copy;
//    }
//
//    public Integer getSch_cost() {
//        return sch_cost;
//    }
//
//    public Integer getDays() {
//        return days;
//    }
}
