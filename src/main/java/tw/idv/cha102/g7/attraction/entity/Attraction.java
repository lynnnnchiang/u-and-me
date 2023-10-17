package tw.idv.cha102.g7.attraction.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="attractions")
public class Attraction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attr_id")
    private Integer attrId;
    @Column(name = "attr_veri_sta")
    private Short attrVeriSta;
    @Column(name = "attr_sta")
    private Short attrSta;
    @Column(name = "attr_name")
    private String attrName;
    @Column(name = "attr_addr")
    private String attrAddr;
    @Column(name = "attr_lon")
    private Float attrLon;//google api可透過地址定位經緯度
    @Column(name = "attr_lat")
    private Float attrLat;
    @Column(name = "attr_illa")
    private String attrIlla;
    @Column(name = "attr_type")
    private String attrType;
    @Column(name = "attr_buss_time")
    private String attrBussTime;
    @Column(name = "attr_cost_range")
    private Short attrCostRange;
    @Column(name = "attr_rep")
    private String attrRep;

//    public Integer getId() {
//        return id;
//    }
//    public void setId(Integer id) {
//        this.id = id;
//    }
//    public Short getVeri_sta() {
//        return veri_sta;
//    }
//    public void setVeri_sta(Short veri_sta) {
//        this.veri_sta = veri_sta;
//    }
//    public Short getSta() {
//        return sta;
//    }
//    public void setSta(Short sta) {
//        this.sta = sta;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getAddr() {
//        return addr;
//    }
//    public void setAddr(String addr) {
//        this.addr = addr;
//    }
//    public Float getLon() {
//        return lon;
//    }
//    public void setLon(Float lon) {
//        this.lon = lon;
//    }
//    public Float getLat() {
//        return lat;
//    }
//    public void setLat(Float lat) {
//        this.lat = lat;
//    }
//    public String getIlla() {
//        return illa;
//    }
//    public void setIlla(String illa) {
//        this.illa = illa;
//    }
//    public Integer getType_id() {
//        return type_id;
//    }
//    public void setType_id(Integer type_id) {
//        this.type_id = type_id;
//    }
//    public String getBuss_time() {
//        return buss_time;
//    }
//    public void setBuss_time(String buss_time) {
//        this.buss_time = buss_time;
//    }
//    public Short getCost_range() {
//        return cost_range;
//    }
//    public void setCost_range(Short cost_range) {
//        this.cost_range = cost_range;
//    }
//    public String getRep() {
//        return rep;
//    }
//    public void setRep(String rep) {
//        this.rep = rep;
//    }
}
