package tw.idv.cha102.g7.shop.entity;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable  // JPA 的註解，表示這個類別是可嵌入的，用於表示複合主鍵中的組件部分
public class CartListId implements Serializable {

    private Integer memId;
    private Integer prodId;

    public CartListId() {}

    public CartListId(Integer memId, Integer prodId) {
        this.memId = memId;
        this.prodId = prodId;
    }

    public CartListId(Integer prodId) {
        this.prodId = prodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartListId that = (CartListId) o;
        return Objects.equals(memId, that.memId) && Objects.equals(prodId, that.prodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memId, prodId);
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }
}

//先建一個方法透過memId作為參數查出複合主鍵類別
//再藉由複合主鍵類別物件.getXXId()