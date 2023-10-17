package tw.idv.cha102.g7.activity.entity.DTO;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// 複合主鍵
@Entity
@Data
@Table(name = "activity_schedule_recommendation")
public class ActivitySchRecommend {

    @EmbeddedId
    private ActivitySchRecommendId activrecommendId;

    public ActivitySchRecommend() {

    }
}