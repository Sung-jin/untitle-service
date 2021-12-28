package com.example.demo.entity.order;

import com.example.demo.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private User orderUser;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<OrderProduct> orderList;

    public Order prepareSave() {
        this.orderList.forEach(orderProduct -> orderProduct.setOrder(this));
        return this;
    }
}
