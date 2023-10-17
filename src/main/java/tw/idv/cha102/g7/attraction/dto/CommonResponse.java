package tw.idv.cha102.g7.attraction.dto;

import lombok.Data;

@Data
public class CommonResponse <T>{
    private T message;
}
