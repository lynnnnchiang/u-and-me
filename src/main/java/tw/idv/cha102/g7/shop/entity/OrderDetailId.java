package tw.idv.cha102.g7.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderDetailId implements Serializable {

    @Column(name = "ord_id")
    private Integer ordId;

    @Column(name = "prod_id")
    private Integer prodId;
}
