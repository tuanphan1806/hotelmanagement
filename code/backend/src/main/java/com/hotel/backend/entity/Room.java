package com.hotel.backend.entity;

import com.hotel.backend.constant.RoomStatus;
import com.hotel.backend.constant.CleaningStatus;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
@Entity
@Table(name = "rooms")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Room extends AbstractEntity<Long> implements Serializable{

    @Column(name = "room_name", unique = true, length = 20)
    private String roomName;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    private Integer floor;

    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "cleaning_status")
    private CleaningStatus cleaningStatus = CleaningStatus.CLEAN;

    @Column(columnDefinition = "TEXT")
    private String description;


}
