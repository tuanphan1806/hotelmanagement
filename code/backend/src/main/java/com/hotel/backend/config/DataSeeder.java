package com.hotel.backend.config;


import com.hotel.backend.constant.UserStatus;
import com.hotel.backend.constant.UserType;
import com.hotel.backend.entity.Facility;
import com.hotel.backend.entity.RoomType;
import com.hotel.backend.entity.User;
import com.hotel.backend.repository.FacilityRepository;
import com.hotel.backend.repository.RoomTypeRepository;
import com.hotel.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final FacilityRepository facilityRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed Facilities
        Facility gym = seedFacility("THE GYM", "GYM", "State of the art gym with premium equipment", "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=1200&h=600&fit=crop");
        Facility poolBar = seedFacility("POOLSIDE BAR", "BAR", "Cocktails and snacks by the poolside", "https://images.unsplash.com/photo-1576091160550-112173e7d87d?w=1200&h=600&fit=crop");
        Facility spa = seedFacility("THE SPA", "SPA", "Premium massage and wellness treatments", "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=1200&h=600&fit=crop");
        Facility pool = seedFacility("SWIMMING POOL", "POOL", "Outdoor infinity swimming pool", "https://images.unsplash.com/photo-1576086213369-97a306d36557?w=1200&h=600&fit=crop");
        Facility restaurant = seedFacility("RESTAURANT", "RESTAURANT", "Fine dining 5-star restaurant", "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=1200&h=600&fit=crop");
        Facility laundry = seedFacility("LAUNDRY", "SERVICES", "24/7 dry cleaning and laundry services", "https://images.unsplash.com/photo-1517677129300-07b130802f46?w=1200&h=600&fit=crop");

        // Seed Room Types
        seedRoomType("SINGLE ROOM", "Single room with all comfort and luxury setup", new BigDecimal("147.00"), "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=1000&h=600&fit=crop", Set.of(laundry, wifiFacility()));
        seedRoomType("DOUBLE ROOM", "Double room with premium queen size bed and top view", new BigDecimal("155.00"), "https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=1000&h=600&fit=crop", Set.of(pool, poolBar, gym, laundry));
        seedRoomType("TWIN ROOM", "Twin room equipped with two premium single beds", new BigDecimal("155.00"), "https://images.unsplash.com/photo-1595526014635-a5dc1dd4b08f?w=1000&h=600&fit=crop", Set.of(pool, spa, laundry));

        // Seed Default Users
        seedUser("Admin Luxury", "admin", "admin@luxuryhotels.com", "admin123", "0987654321", UserType.ADMIN);
        seedUser("Demo Customer", "customer", "customer@luxuryhotels.com", "customer123", "0123456789", UserType.CUSTOMER);
    }

    private Facility seedFacility(String name, String type, String desc, String Url) {
        if (!facilityRepository.existsByFacilityNameIgnoreCase(name)) {
            Facility facility = Facility.builder()
                    .facilityName(name)
                    .type(type)
                    .description(desc)
                    .imageUrl(Url)
                    .build();
            return facilityRepository.save(facility);
        }
        return facilityRepository.findByFacilityNameContainingIgnoreCaseOrderByFacilityNameAsc(name).get(0);
    }

    private Facility wifiFacility() {
        return seedFacility("FREE WIFI", "SERVICES", "Complimentary high-speed internet", "wifi-icon");
    }

    private void seedRoomType(String name, String desc, BigDecimal price, String imageUrl, Set<Facility> facilities) {
        if (!roomTypeRepository.existsByTypeNameIgnoreCase(name)) {
            RoomType rt = RoomType.builder()
                    .typeName(name)
                    .description(desc)
                    .price(price)
                    .imageUrl(imageUrl)
                    .facilities(new HashSet<>(facilities))
                    .build();
            roomTypeRepository.save(rt);
        }
    }

    private void seedUser(String fullName, String username, String email, String password, String phone, UserType role) {
        // Let's check if user with same username or email exists. We can fetch all and check or use simple query.
        boolean exists = userRepository.findAll().stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username) || u.getEmail().equalsIgnoreCase(email));
        if (!exists) {
            User user = User.builder()
                    .fullName(fullName)
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .phone(phone)
                    .type(role)
                    .status(UserStatus.ACTIVE)
                    .build();
            userRepository.save(user);
        }
    }
}
