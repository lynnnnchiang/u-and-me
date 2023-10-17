package tw.idv.cha102.g7.customer.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage {

    private String type;

    private String sender;

    private String receiver;

    private String message;

    private String time;

    private String status;

}
