package com.hotel.backend.entity;

import com.hotel.backend.constant.IdCardType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "guests")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_room_id")
    private ReservationRoom reservationRoom;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(length = 20)
    private String phone;

    private String email;

    @Column(name = "id_card_number", length = 50)
    private String idCardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_card_type")
    private IdCardType idCardType;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String nationality;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
