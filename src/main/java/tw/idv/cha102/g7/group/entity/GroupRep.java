package tw.idv.cha102.g7.group.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "group_rep")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRep implements Serializable {
    private static final long serialVersionUID = 7577470020250959553L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_rep_id")
    private Integer groupRepId;
    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "hosts_id")
    private Integer hostId;
    @Column(name = "reason")
    private String reason;
    @Column(name = "group_rep_sta")
    private Integer groupRepSta;


}
