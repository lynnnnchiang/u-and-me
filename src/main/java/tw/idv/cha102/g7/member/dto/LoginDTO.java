package tw.idv.cha102.g7.member.dto;

import lombok.Data;

@Data
public class LoginDTO {

    String memEmail;
    String memPassword;

    public String getMemEmail(){
        return this.memEmail;
    }

}
