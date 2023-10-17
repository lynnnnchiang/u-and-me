package tw.idv.cha102.g7.attraction.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "attraction_collections")
public class AttrCollectionDTO {

    @EmbeddedId
    private AttrCollectionId collectionId;

    public AttrCollectionDTO() {
    }

}
