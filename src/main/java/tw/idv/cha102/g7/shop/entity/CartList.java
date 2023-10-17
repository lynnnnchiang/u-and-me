package tw.idv.cha102.g7.shop.entity;

import lombok.*;

import javax.persistence.*;

//表示這個類別是一個 JPA 實體，將被映射到資料庫中的表格。
//Lombok 註解，自動生成 getter、setter、toString 等方法
//Lombok 註解，自動生成無參數的建構函式
//Lombok 註解，自動生成包含所有成員變數的建構函式
//JPA 註解，指定實體對應的資料庫表格名稱為 "cart_list"


@Entity
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_list")
public class CartList {

//JPA 註解，表示這個實體使用了複合主鍵，並指定了複合主鍵的類別
    @EmbeddedId
    private CartListId cartListId;

    @Column(name = "cart_pri")
    private Integer cartPri;
    @Column(name = "cart_qty")
    private Integer cartQty;
}
