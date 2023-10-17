package tw.idv.cha102.g7.customer.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentEmail {

    String name;

    String usermail;

    String content;

}
