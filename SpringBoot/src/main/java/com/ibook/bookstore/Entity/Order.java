package com.ibook.bookstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "orders", schema = "bookstore")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Order {
    @Id
    @Column(name = "order_id")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private User user;

    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    @Basic
    @Column(name = "paid")
    private Double paid;

    @Basic
    @Column(name = "state")
    private int state;

    @OneToMany(targetEntity = OrderItem.class, mappedBy = "orderId", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItemList;
}
