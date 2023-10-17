package tw.idv.cha102.g7.customer.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatState {

    private  String type;

    private  String user;

    private List<Map<String, String>> userList;

}
