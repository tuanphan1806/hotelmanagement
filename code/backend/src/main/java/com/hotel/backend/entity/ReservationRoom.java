package com.hotel.backend.entity;

import com.hotel.backend.constant.AssignStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;


@Entity
@Table(name = "reservation_rooms")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReservationRoom extends AbstractEntity<Long> implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_room_type_id", nullable = false)
    private ReservationRoomType reservationRoomType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room; // 1 phòng cụ thể: 101, 102, 301...

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private User assignedBy; // nullable — có thể chưa được assign

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignStatus status = AssignStatus.PENDING_ASSIGN;

    @Builder.Default
    @OneToMany(mappedBy = "reservationRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Guest> guests = new HashSet<>();
}
