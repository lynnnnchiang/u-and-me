package tw.idv.cha102.g7.activity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activ_id")
    private Integer activId;

    @Column(name = "activ_pic", columnDefinition = "MEDIUMBLOB")
    @Lob
    private byte[] activPic;

    @Column(name = "activ_name")
    private String activName;

    @Column(name = "activ_con")
    private String activCon;

    @Column(name = "activ_starttime")
    private Timestamp activStarttime;

    @Column(name = "activ_endtime")
    private Timestamp activEndtime;

    @Column(name = "activ_sta")
    private Byte activSta;


    public String gettActivCon() {
        return activCon;
    }
}
