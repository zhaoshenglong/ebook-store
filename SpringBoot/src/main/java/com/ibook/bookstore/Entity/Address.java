package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "address", schema = "bookstore")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Address {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Basic
    @Column(name = "province")
    private String province;

    @Basic
    @Column(name = "city")
    private String city;

    @Basic
    @Column(name = "district")
    private String district;

    @JsonIgnore
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @JsonIgnore
    @Basic
    @Column(name = "modify_date")
    private Timestamp modifyDate;

    @Basic
    @Column(name = "detail")
    private String detail;


    @Basic
    @Column(name = "user_name")
    private String userName;
}
