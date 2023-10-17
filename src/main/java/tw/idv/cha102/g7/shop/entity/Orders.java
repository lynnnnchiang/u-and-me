package tw.idv.cha102.g7.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ord_id")
    private Integer ordId;

    @Column(name = "mem_id")
    private Integer memId;

    @Column(name = "points", insertable = false)
    private Integer points;

    @Column(name = "ord_fee", insertable = false)
    private Integer ordFee;

    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Column(name = "ord_pay_sta", insertable = false)
    private Byte ordPaySta;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "recipient_addr")
    private String recipientAddr;

    @Column(name = "ord_sta", insertable = false)
    private Byte ordSta;

    @Column(name = "total")//把所有訂單價格總合後傳到後端新增
    private Integer total;

    @Column(name = "checktotal")
    private Integer checktotal;

    @Column(name = "ord_time", insertable = false)
    private Timestamp ordTime;

}
