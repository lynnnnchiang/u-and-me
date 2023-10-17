package tw.idv.cha102.g7.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberNotice {
    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "mem_id")
    private Integer memId;
    private String content;
    private boolean read;
    private LocalDateTime timestamp;


}
