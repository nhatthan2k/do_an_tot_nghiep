package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", length = 100, unique = true)
    private String name;

    private String description;

    @Column(name = "unit_price")
    private Double price;

    @Column(name = "stock_quantity")
    @Min(0)
    private int quantity;

    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<ShopingCart> shopingCarts;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<WishList> wishLists;
}
