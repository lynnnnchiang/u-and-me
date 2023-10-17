package tw.idv.cha102.g7.member.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "hosts")

public class Host {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name = "host_id")
        private Integer hostId;

        @Column(name = "host_phone")
        private String hostPhone;

        @Column(name = "host_email")
        private String hostEmail;

        @Column(name = "host_password")
        private String hostPassword;

        @Column(name = "host_name")
        private String hostName;

        @Column(name = "host_sta")
        private Integer hostSta;



}
