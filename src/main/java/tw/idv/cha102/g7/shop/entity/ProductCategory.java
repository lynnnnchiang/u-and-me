package tw.idv.cha102.g7.shop.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
@Table(name = "product_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prodcat_id")
    private Integer prodCatId;
    @Column(name = "prodcat_name")
    private String prodCatName;

}

