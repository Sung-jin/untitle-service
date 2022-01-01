package com.example.demo.entity.order;

import com.example.demo.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Order order;

    @OneToOne(optional = false)
    private Product product;

    public void setOrder(Order order) {
        this.order = order;
    }
}
