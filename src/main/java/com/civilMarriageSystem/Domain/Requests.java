package com.civilMarriageSystem.Domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Requests {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private User citizen;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date requestDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_of_request=new Date();
    private String Status="Pending";
}
