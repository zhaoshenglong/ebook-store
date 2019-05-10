package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "address", schema = "bookstore")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

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
