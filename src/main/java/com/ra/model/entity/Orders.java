package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "serial_number", length = 100)
    private String orderNumber;

    private Double price;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @Column(length = 100)
    private String note;

    @Column(name = "receive_name",length = 100)
    private String receiveName;

    @Column(name = "receive_address")
    private String receiveAddress;

    @Column(name = "receive_phone", length = 15)
    private String receivePhone;

    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;

    @Column(name = "received_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date received;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users users;

    @OneToMany(mappedBy = "orders")
    @JsonIgnore
    List<OrderDetail> orderDetails;
}
