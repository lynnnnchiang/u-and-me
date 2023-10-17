package tw.idv.cha102.g7.activity.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "psytest_result")
public class PsytestResult {

    @Id
    @Column(name = "psytresult_id")
    private Integer psytresultId;

    @Column(name = "psytresult_name")
    private String psytresultName;

    @Column(name = "psytresult_con")
    private String psytresultCon;

}
