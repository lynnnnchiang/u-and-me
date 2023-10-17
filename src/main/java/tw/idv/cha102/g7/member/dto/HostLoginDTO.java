package tw.idv.cha102.g7.member.dto;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class HostLoginDTO {

    String hostEmail;
    String hostPassword;

    public String getHostEmail(){
        return this.hostEmail;
    }

}
