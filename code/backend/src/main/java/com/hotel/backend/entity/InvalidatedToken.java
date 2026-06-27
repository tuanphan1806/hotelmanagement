package com.hotel.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;
@Entity
@Table(name = "invalidated_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvalidatedToken {

    @Id
    private String token; // lưu jti hoặc token hash

    private Date expiryTime; // để scheduled job dọn dẹp
}