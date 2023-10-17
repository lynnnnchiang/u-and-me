package tw.idv.cha102.g7.group.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "reg_form")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegForm implements Serializable {
    private static final long serialVersionUID = 7577470020250959553L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Integer formId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "join_member")
    private Integer joinMember;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "reg_time", insertable = false)
    private Timestamp regTime;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "regForm")
//    @JsonManagedReference
//    private List<MemberDetail> memberDetailList;
}
