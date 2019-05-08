package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "orders", schema = "bookstore")
@Data
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Order {
    @Id
    @Column(name = "order_id")
    @GenericGenerator(name = "h-uuid", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "h-uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private User user;

    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @Basic
    @Column(name = "state")
    private String state;

    @OneToMany(targetEntity = OrderItem.class, mappedBy = "order_id")
    private Set<OrderItem> orderItemList;
}
