package tw.idv.cha102.g7.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member implements Comparable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "mem_id")
    private Integer memId;

    @Column(name = "mem_email")
    private String memEmail;

    @Column(name = "mem_password")
    private String memPassword;

    @Column(name = "mem_name")
    private String memName;

    @Column(name = "mem_gender")
    private Integer memGender;

    @Column(name = "mem_city")
    private String memCity;

    @Column(name = "mem_dist")
    private String memDist;

    @Column(name = "mem_addr")
    private String memAddr;

    @Column(name = "mem_grade")
    private String memGrade;

    @Column(name = "mem_idcard")
    private String memIdcard;

    @Lob
    @Column(name ="mem_idpic")
    private String memIdPic;

    @Column(name = "mem_phone")
    private String memPhone;

    @Column(name = "mem_point")
    private Integer memPoint;

    @Column(name = "mem_sta")
    private Integer memSta;

    @Column(name = "mem_group")
    private Integer memGroup;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

