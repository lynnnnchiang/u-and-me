package tw.idv.cha102.g7.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "prod_qty")
    private Integer prodQty;

    @Column(name = "prod_review")
    private String prodReview;

    @Column(name = "prod_price")
    private Integer prodPrice;

    @Column(name = "prod_com_score")
    private Double prodComScore;

}
