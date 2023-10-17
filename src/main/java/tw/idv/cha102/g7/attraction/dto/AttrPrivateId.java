package tw.idv.cha102.g7.attraction.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class AttrPrivateId implements Serializable {

    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "attr_id")
    private Integer attrId;

}
