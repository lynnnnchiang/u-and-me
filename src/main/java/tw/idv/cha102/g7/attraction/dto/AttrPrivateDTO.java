package tw.idv.cha102.g7.attraction.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "private_attraction")
public class AttrPrivateDTO {
    @EmbeddedId
    private AttrPrivateId attrPrivateId;

    public AttrPrivateDTO() {
    }
}
