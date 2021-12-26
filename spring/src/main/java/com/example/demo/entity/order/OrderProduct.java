package com.example.demo.entity.order;

import com.example.demo.entity.product.Product;
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
    private Order order;

    @OneToOne(optional = false)
    private Product product;
}
