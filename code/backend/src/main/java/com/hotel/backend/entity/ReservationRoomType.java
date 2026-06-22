package com.hotel.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;


@Entity
@Table(name = "reservation_room_types")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReservationRoomType extends AbstractEntity<Long> implements Serializable{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id",nullable = false)
    private RoomType roomType;

    


    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @Column(name = "room_price", precision = 12, scale = 2)
    private BigDecimal roomPrice;

    @Column(name = "total",precision = 12, scale = 2)
    private BigDecimal subtotal;

    // @OneToMany(mappedBy = "reservationRoomType", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<ReservationRoom> rooms = new HashSet<>();
}
