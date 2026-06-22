package com.hotel.backend.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;


@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Review extends AbstractEntity<Long> implements Serializable{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

}