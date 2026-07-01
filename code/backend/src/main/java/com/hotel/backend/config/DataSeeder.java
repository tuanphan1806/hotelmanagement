package com.hotel.backend.config;

import com.hotel.backend.constant.CleaningStatus;
import com.hotel.backend.constant.RoomStatus;
import com.hotel.backend.constant.UserStatus;
import com.hotel.backend.constant.UserType;
import com.hotel.backend.entity.Facility;
import com.hotel.backend.entity.Gallery;
import com.hotel.backend.entity.Room;
import com.hotel.backend.entity.RoomType;
import com.hotel.backend.entity.User;
import com.hotel.backend.repository.FacilityRepository;
import com.hotel.backend.repository.GalleryRepository;
import com.hotel.backend.repository.RoomRepository;
import com.hotel.backend.repository.RoomTypeRepository;
import com.hotel.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Seed dữ liệu master: Facility -> RoomType (gán facility) -> Room -> Gallery -> User.
 * Không seed dữ liệu giao dịch (reservations, payments, guests, reviews, room_holds,
 * chat_messages...) vì đó là dữ liệu runtime/demo, nên tạo qua API hoặc test data riêng.
 * Không dùng Role/Permission/Group — phân quyền dựa trực tiếp vào UserType (xem User#getAuthorities).
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final FacilityRepository facilityRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final GalleryRepository galleryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Map<String, Facility> facilities = seedFacilities();
        Map<String, RoomType> roomTypes = seedRoomTypes(facilities);
        seedRooms(roomTypes);
        seedGalleries();
        seedUsers();
    }

    // ==================== FACILITIES ====================

    private Map<String, Facility> seedFacilities() {
        Map<String, Facility> result = new HashMap<>();

        result.put("aircon", seedFacility("Điều hòa", "ROOM",
                "Điều hòa nhiệt độ 2 chiều inverter",
                "https://cdn.luxstay.vn/facilities/aircon.jpg"));
        result.put("tv", seedFacility("Smart TV 55\"", "ROOM",
                "TV màn hình phẳng 55 inch 4K",
                "https://cdn.luxstay.vn/facilities/tv.jpg"));
        result.put("minibar", seedFacility("Minibar", "ROOM",
                "Minibar với đồ uống và snack",
                "https://cdn.luxstay.vn/facilities/minibar.jpg"));
        result.put("bathtub", seedFacility("Bồn tắm", "ROOM",
                "Bồn tắm riêng",
                "https://cdn.luxstay.vn/facilities/bathtub.jpg"));
        result.put("wifi", seedFacility("WiFi tốc độ cao", "ROOM",
                "WiFi tốc độ cao 1Gbps",
                "https://cdn.luxstay.vn/facilities/wifi.jpg"));
        result.put("safe", seedFacility("Két an toàn", "ROOM",
                "Két an toàn trong phòng",
                "https://cdn.luxstay.vn/facilities/safe.jpg"));
        result.put("pool", seedFacility("Hồ bơi", "HOTEL",
                "Hồ bơi ngoài trời tầng thượng",
                "https://cdn.luxstay.vn/facilities/pool.jpg"));
        result.put("gym", seedFacility("Phòng Gym", "HOTEL",
                "Phòng gym hiện đại 24/7",
                "https://cdn.luxstay.vn/facilities/gym.jpg"));
        result.put("spa", seedFacility("Spa & Massage", "HOTEL",
                "Spa và massage cao cấp",
                "https://cdn.luxstay.vn/facilities/spa.jpg"));
        result.put("restaurant", seedFacility("Nhà hàng", "HOTEL",
                "Nhà hàng phục vụ 3 bữa",
                "https://cdn.luxstay.vn/facilities/restaurant.jpg"));

        return result;
    }

    private Facility seedFacility(String name, String type, String description, String imageUrl) {
        if (!facilityRepository.existsByFacilityNameIgnoreCase(name)) {
            Facility facility = Facility.builder()
                    .facilityName(name)
                    .type(type)
                    .description(description)
                    .imageUrl(imageUrl)
                    .build();
            return facilityRepository.save(facility);
        }
        return facilityRepository.findByFacilityNameContainingIgnoreCaseOrderByFacilityNameAsc(name).get(0);
    }

    // ==================== ROOM TYPES ====================
    // Facility gán cho từng room type theo bảng room_type_facilities trong SQL

    private Map<String, RoomType> seedRoomTypes(Map<String, Facility> facilities) {
        Map<String, RoomType> result = new HashMap<>();

        result.put("Standard", seedRoomType(
                "Standard",
                "Phòng Standard tiện nghi đầy đủ, phù hợp cho cặp đôi hoặc du khách đơn lẻ.",
                new BigDecimal("800000.00"),
                "https://cdn.luxstay.vn/room-types/standard.jpg",
                Set.of(facilities.get("aircon"), facilities.get("tv"),
                        facilities.get("wifi"), facilities.get("safe"))
        ));

        result.put("Deluxe", seedRoomType(
                "Deluxe",
                "Phòng Deluxe rộng rãi với ban công view thành phố, nội thất sang trọng.",
                new BigDecimal("1500000.00"),
                "https://cdn.luxstay.vn/room-types/deluxe.jpg",
                Set.of(facilities.get("aircon"), facilities.get("tv"), facilities.get("minibar"),
                        facilities.get("wifi"), facilities.get("safe"))
        ));

        result.put("Suite", seedRoomType(
                "Suite",
                "Phòng Suite cao cấp với phòng khách riêng, bồn tắm jacuzzi và dịch vụ butler.",
                new BigDecimal("3000000.00"),
                "https://cdn.luxstay.vn/room-types/suite.jpg",
                Set.of(facilities.get("aircon"), facilities.get("tv"), facilities.get("minibar"),
                        facilities.get("bathtub"), facilities.get("wifi"), facilities.get("safe"))
        ));

        result.put("Family", seedRoomType(
                "Family",
                "Phòng Family rộng lớn thiết kế cho gia đình, có 2 phòng ngủ và bếp nhỏ.",
                new BigDecimal("2200000.00"),
                "https://cdn.luxstay.vn/room-types/family.jpg",
                Set.of(facilities.get("aircon"), facilities.get("tv"), facilities.get("minibar"),
                        facilities.get("wifi"), facilities.get("safe"))
        ));

        result.put("Presidential Suite", seedRoomType(
                "Presidential Suite",
                "Presidential Suite sang trọng bậc nhất với tầm nhìn panoramic 360 độ.",
                new BigDecimal("8000000.00"),
                "https://cdn.luxstay.vn/room-types/presidential.jpg",
                Set.of(facilities.get("aircon"), facilities.get("tv"), facilities.get("minibar"),
                        facilities.get("bathtub"), facilities.get("wifi"), facilities.get("safe"))
        ));

        return result;
    }

    private RoomType seedRoomType(String name, String desc, BigDecimal price, String imageUrl, Set<Facility> facilities) {
        if (!roomTypeRepository.existsByTypeNameIgnoreCase(name)) {
            RoomType rt = RoomType.builder()
                    .typeName(name)
                    .description(desc)
                    .price(price)
                    .imageUrl(imageUrl)
                    .facilities(new HashSet<>(facilities))
                    .build();
            return roomTypeRepository.save(rt);
        }
        return roomTypeRepository.findByTypeName(name).orElseThrow();
    }

    // ==================== ROOMS ====================

    private void seedRooms(Map<String, RoomType> roomTypes) {
        seedRoom("101", 1, "Phòng Standard hướng sân vườn", roomTypes.get("Standard"));
        seedRoom("102", 1, "Phòng Standard hướng sân vườn", roomTypes.get("Standard"));
        seedRoom("103", 1, "Phòng Standard hướng đường", roomTypes.get("Standard"));
        seedRoom("104", 1, "Phòng Standard hướng đường", roomTypes.get("Standard"));
        seedRoom("201", 2, "Phòng Standard tầng 2", roomTypes.get("Standard"));
        seedRoom("202", 2, "Phòng Deluxe view thành phố", roomTypes.get("Deluxe"));
        seedRoom("203", 2, "Phòng Deluxe view thành phố", roomTypes.get("Deluxe"));
        seedRoom("204", 2, "Phòng Deluxe góc ban công đôi", roomTypes.get("Deluxe"));
        seedRoom("301", 3, "Phòng Deluxe tầng 3", roomTypes.get("Deluxe"));
        seedRoom("302", 3, "Phòng Family 2 phòng ngủ", roomTypes.get("Family"));
        seedRoom("303", 3, "Phòng Family view sân vườn", roomTypes.get("Family"));
        seedRoom("304", 3, "Phòng Deluxe tầng 3", roomTypes.get("Deluxe"));
        seedRoom("401", 4, "Suite với bồn tắm jacuzzi", roomTypes.get("Suite"));
        seedRoom("402", 4, "Suite view thành phố", roomTypes.get("Suite"));
        seedRoom("403", 4, "Suite góc panoramic", roomTypes.get("Suite"));
        seedRoom("501", 5, "Presidential Suite tầng thượng", roomTypes.get("Presidential Suite"));
    }

    private void seedRoom(String roomName, int floor, String description, RoomType roomType) {
        if (!roomRepository.existsByRoomName(roomName)) {
            Room room = Room.builder()
                    .roomName(roomName)
                    .floor(floor)
                    .description(description)
                    .roomType(roomType)
                    .status(RoomStatus.AVAILABLE)
                    .cleaningStatus(CleaningStatus.CLEAN)
                    .build();
            roomRepository.save(room);
        }
    }

    // ==================== GALLERIES ====================

    private void seedGalleries() {
        seedGallery("Sảnh Khách Sạn", "HOTEL", "https://cdn.luxstay.vn/gallery/lobby.jpg");
        seedGallery("Hồ Bơi Tầng Thượng", "HOTEL", "https://cdn.luxstay.vn/gallery/pool.jpg");
        seedGallery("Phòng Standard", "ROOM", "https://cdn.luxstay.vn/gallery/standard.jpg");
        seedGallery("Phòng Deluxe", "ROOM", "https://cdn.luxstay.vn/gallery/deluxe.jpg");
        seedGallery("Phòng Suite", "ROOM", "https://cdn.luxstay.vn/gallery/suite.jpg");
        seedGallery("Nhà Hàng", "HOTEL", "https://cdn.luxstay.vn/gallery/restaurant.jpg");
        seedGallery("Khu Spa", "HOTEL", "https://cdn.luxstay.vn/gallery/spa.jpg");
        seedGallery("Phòng Presidential Suite", "ROOM", "https://cdn.luxstay.vn/gallery/presidential.jpg");
    }

    private void seedGallery(String title, String type, String imageUrl) {
        if (!galleryRepository.existsByImageUrl(imageUrl)) {
            Gallery gallery = Gallery.builder()
                    .title(title)
                    .type(type)
                    .imageUrl(imageUrl)
                    .build();
            galleryRepository.save(gallery);
        }
    }

    // ==================== USERS ====================
    // Mật khẩu demo được đặt mới (SQL dump chỉ có hash, không phục hồi được plaintext).
    // Phân quyền dựa trực tiếp vào UserType (ADMIN/STAFF/CUSTOMER), không dùng Role/Group.

    private void seedUsers() {
        seedUser("Nguyễn Văn Admin", "admin", "admin@luxstay.vn", "admin123",
                "0901000001", "12 Lý Thường Kiệt, Hà Nội", UserType.ADMIN);

        seedUser("Trần Thị Lan", "staff1", "staff1@luxstay.vn", "staff123",
                "0901000002", "45 Trần Hưng Đạo, Hà Nội", UserType.STAFF);

        seedUser("Lê Văn Minh", "staff2", "staff2@luxstay.vn", "staff123",
                "0901000003", "78 Nguyễn Huệ, TP.HCM", UserType.STAFF);

        seedUser("Hoàng Thị Thu", "staff_thu", "staff3@luxstay.vn", "staff123",
                "0901000008", "33 Hai Bà Trưng, TP.HCM", UserType.STAFF);

        seedUser("Phạm Thị Hoa", "customer1", "customer1@gmail.com", "customer123",
                "0901000004", "23 Hoàng Diệu, Đà Nẵng", UserType.CUSTOMER);

        seedUser("Nguyễn Quốc Hùng", "customer2", "customer2@gmail.com", "customer123",
                "0901000005", "56 Lê Lợi, Hà Nội", UserType.CUSTOMER);

        seedUser("Vũ Thị Mai", "vtmai", "customer3@gmail.com", "customer123",
                "0901000006", "90 Pasteur, TP.HCM", UserType.CUSTOMER);

        seedUser("Bùi Thị Ngọc", "btngocc", "customer5@gmail.com", "customer123",
                "0901000009", "67 Phan Chu Trinh, Đà Nẵng", UserType.CUSTOMER);

        seedUser("Trương Văn Khoa", "tvkhoa", "customer6@gmail.com", "customer123",
                "0901000010", "44 Võ Văn Tần, TP.HCM", UserType.CUSTOMER);

        // Tài khoản chưa xác minh email
        seedUnverifiedUser("Đặng Văn Tùng", "dvtung", "customer4@gmail.com", "customer123",
                "0901000007", "11 Đinh Tiên Hoàng, Hà Nội", "VERIFY_ABC123");
    }

    private void seedUser(String fullName, String username, String email, String password,
                           String phone, String address, UserType type) {
        if (userExists(username, email)) {
            return;
        }

        User user = User.builder()
                .fullName(fullName)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .address(address)
                .type(type)
                .status(UserStatus.ACTIVE)
                .emailVerified(true)
                .build();
        userRepository.save(user);
    }

    private void seedUnverifiedUser(String fullName, String username, String email, String password,
                                     String phone, String address, String verificationCode) {
        if (userExists(username, email)) {
            return;
        }

        User user = User.builder()
                .fullName(fullName)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .address(address)
                .type(UserType.CUSTOMER)
                .status(UserStatus.PENDING_VERIFICATION)
                .emailVerified(false)
                .verificationCode(verificationCode)
                .build();
        userRepository.save(user);
    }

    private boolean userExists(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }
}