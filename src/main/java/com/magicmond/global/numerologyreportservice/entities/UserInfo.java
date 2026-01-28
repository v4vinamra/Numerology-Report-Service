package com.magicmond.global.numerologyreportservice.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.boot.model.internal.GeneratorStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@Entity
@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String fullName;

    public String emailId;

    public Date dob;

}
