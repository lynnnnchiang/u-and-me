package tw.idv.cha102.g7.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberNoticeDTO {

    private Integer memId;
    private String content;
    private boolean read;
    private LocalDateTime timestamp;
}
