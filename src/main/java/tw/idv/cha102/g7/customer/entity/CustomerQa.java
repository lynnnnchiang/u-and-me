package tw.idv.cha102.g7.customer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "qa")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerQa implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_id")
    private Integer qaId;

    @Column(name = "qa_title")
    private String qaTitle;

    @Column(name = "qa_con")
    private String qaCon;

    @Column(name = "qa_state", insertable = false)
    private Integer qaState;

    @CreatedDate
    @Column(name = "qa_created_time")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp qaCreatedTime;

    @LastModifiedDate
    @Column(name = "qa_last_updated_time")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp qaLastUpdatedTime;

}
