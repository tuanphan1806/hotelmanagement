-- ============================================================
-- SAMPLE DATA - hotelmanagement
-- Thứ tự insert theo FK dependency
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 1. USERS
-- ============================================================
INSERT INTO `users` (`id`, `created_at`, `updated_at`, `address`, `email`, `email_verified`, `full_name`, `image_url`, `password`, `phone`, `status`, `type`, `username`, `verification_code`) VALUES
(1,  NOW(), NOW(), '12 Lý Thường Kiệt, Hà Nội',      'admin@luxstay.vn',      b'1', 'Nguyễn Văn Admin',   NULL, '$2a$10$hashed_password_1', '0901000001', 'ACTIVE', 'ADMIN',    'admin',       NULL),
(2,  NOW(), NOW(), '45 Trần Hưng Đạo, Hà Nội',        'staff1@luxstay.vn',     b'1', 'Trần Thị Lan',        NULL, '$2a$10$hashed_password_2', '0901000002', 'ACTIVE', 'STAFF',    'staff_lan',   NULL),
(3,  NOW(), NOW(), '78 Nguyễn Huệ, TP.HCM',           'staff2@luxstay.vn',     b'1', 'Lê Văn Minh',         NULL, '$2a$10$hashed_password_3', '0901000003', 'ACTIVE', 'STAFF',    'staff_minh',  NULL),
(4,  NOW(), NOW(), '23 Hoàng Diệu, Đà Nẵng',          'customer1@gmail.com',   b'1', 'Phạm Thị Hoa',        NULL, '$2a$10$hashed_password_4', '0901000004', 'ACTIVE', 'CUSTOMER', 'phamhoa',     NULL),
(5,  NOW(), NOW(), '56 Lê Lợi, Hà Nội',               'customer2@gmail.com',   b'1', 'Nguyễn Quốc Hùng',   NULL, '$2a$10$hashed_password_5', '0901000005', 'ACTIVE', 'CUSTOMER', 'nqhung',      NULL),
(6,  NOW(), NOW(), '90 Pasteur, TP.HCM',               'customer3@gmail.com',   b'1', 'Vũ Thị Mai',          NULL, '$2a$10$hashed_password_6', '0901000006', 'ACTIVE', 'CUSTOMER', 'vtmai',       NULL),
(7,  NOW(), NOW(), '11 Đinh Tiên Hoàng, Hà Nội',      'customer4@gmail.com',   b'0', 'Đặng Văn Tùng',       NULL, '$2a$10$hashed_password_7', '0901000007', 'PENDING_VERIFICATION', 'CUSTOMER', 'dvtung', 'VERIFY_ABC123'),
(8,  NOW(), NOW(), '33 Hai Bà Trưng, TP.HCM',         'staff3@luxstay.vn',     b'1', 'Hoàng Thị Thu',       NULL, '$2a$10$hashed_password_8', '0901000008', 'ACTIVE', 'STAFF',    'staff_thu',   NULL),
(9,  NOW(), NOW(), '67 Phan Chu Trinh, Đà Nẵng',      'customer5@gmail.com',   b'1', 'Bùi Thị Ngọc',        NULL, '$2a$10$hashed_password_9', '0901000009', 'ACTIVE', 'CUSTOMER', 'btngocc',     NULL),
(10, NOW(), NOW(), '44 Võ Văn Tần, TP.HCM',           'customer6@gmail.com',   b'1', 'Trương Văn Khoa',     NULL, '$2a$10$hashed_password_10','0901000010', 'INACTIVE','CUSTOMER', 'tvkhoa',     NULL);

-- ============================================================
-- 2. TBL_GROUPS
-- ============================================================
INSERT INTO `tbl_groups` (`id`, `created_at`, `updated_at`, `name`) VALUES
(1, NOW(), NOW(), 'ADMIN_GROUP'),
(2, NOW(), NOW(), 'RECEPTIONIST_GROUP'),
(3, NOW(), NOW(), 'HOUSEKEEPING_GROUP'),
(4, NOW(), NOW(), 'CUSTOMER_GROUP');

-- ============================================================
-- 3. PERMISSIONS
-- ============================================================
INSERT INTO `permissions` (`id`, `created_at`, `updated_at`, `name`) VALUES
(1,  NOW(), NOW(), 'user:create'),
(2,  NOW(), NOW(), 'user:read'),
(3,  NOW(), NOW(), 'user:update'),
(4,  NOW(), NOW(), 'user:delete'),
(5,  NOW(), NOW(), 'room:create'),
(6,  NOW(), NOW(), 'room:read'),
(7,  NOW(), NOW(), 'room:update'),
(8,  NOW(), NOW(), 'room:delete'),
(9,  NOW(), NOW(), 'reservation:create'),
(10, NOW(), NOW(), 'reservation:read'),
(11, NOW(), NOW(), 'reservation:update'),
(12, NOW(), NOW(), 'reservation:delete'),
(13, NOW(), NOW(), 'payment:read'),
(14, NOW(), NOW(), 'payment:update'),
(15, NOW(), NOW(), 'review:create'),
(16, NOW(), NOW(), 'review:read'),
(17, NOW(), NOW(), 'review:delete');

-- ============================================================
-- 4. ROLES
-- ============================================================
INSERT INTO `roles` (`id`, `created_at`, `updated_at`, `name`) VALUES
(1, NOW(), NOW(), 'SUPER_ADMIN'),
(2, NOW(), NOW(), 'RECEPTIONIST'),
(3, NOW(), NOW(), 'HOUSEKEEPING'),
(4, NOW(), NOW(), 'CUSTOMER');

-- ============================================================
-- 5. ROLE_HAS_PERMISSION
-- ============================================================
-- SUPER_ADMIN: tất cả quyền
INSERT INTO `role_has_permission` (`role_id`, `permission_id`) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),
(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17);

-- RECEPTIONIST: quản lý reservation, room read, payment
INSERT INTO `role_has_permission` (`role_id`, `permission_id`) VALUES
(2,6),(2,9),(2,10),(2,11),(2,12),(2,13),(2,14),(2,16);

-- HOUSEKEEPING: chỉ read & update room (cleaning)
INSERT INTO `role_has_permission` (`role_id`, `permission_id`) VALUES
(3,6),(3,7);

-- CUSTOMER: tạo reservation, review
INSERT INTO `role_has_permission` (`role_id`, `permission_id`) VALUES
(4,6),(4,9),(4,10),(4,15),(4,16);

-- ============================================================
-- 6. GROUP_HAS_ROLE
-- ============================================================
INSERT INTO `group_has_role` (`group_id`, `role_id`) VALUES
(1, 1), -- ADMIN_GROUP -> SUPER_ADMIN
(2, 2), -- RECEPTIONIST_GROUP -> RECEPTIONIST
(3, 3), -- HOUSEKEEPING_GROUP -> HOUSEKEEPING
(4, 4); -- CUSTOMER_GROUP -> CUSTOMER

-- ============================================================
-- 7. GROUP_HAS_USER
-- ============================================================
INSERT INTO `group_has_user` (`user_id`, `group_id`) VALUES
(1, 1),  -- admin -> ADMIN_GROUP
(2, 2),  -- staff_lan -> RECEPTIONIST
(3, 2),  -- staff_minh -> RECEPTIONIST
(8, 3),  -- staff_thu -> HOUSEKEEPING
(4, 4),  -- customers
(5, 4),
(6, 4),
(9, 4),
(10, 4);

-- ============================================================
-- 8. FACILITIES
-- ============================================================
INSERT INTO `facilities` (`id`, `created_at`, `updated_at`, `description`, `facility_name`, `image_url`, `type`) VALUES
(1,  NOW(), NOW(), 'Điều hòa nhiệt độ 2 chiều inverter', 'Điều hòa',         'https://cdn.luxstay.vn/facilities/aircon.jpg',    'ROOM'),
(2,  NOW(), NOW(), 'TV màn hình phẳng 55 inch 4K',        'Smart TV 55"',     'https://cdn.luxstay.vn/facilities/tv.jpg',        'ROOM'),
(3,  NOW(), NOW(), 'Minibar với đồ uống và snack',        'Minibar',          'https://cdn.luxstay.vn/facilities/minibar.jpg',   'ROOM'),
(4,  NOW(), NOW(), 'Bồn tắm riêng',                       'Bồn tắm',         'https://cdn.luxstay.vn/facilities/bathtub.jpg',   'ROOM'),
(5,  NOW(), NOW(), 'WiFi tốc độ cao 1Gbps',               'WiFi tốc độ cao', 'https://cdn.luxstay.vn/facilities/wifi.jpg',      'ROOM'),
(6,  NOW(), NOW(), 'Két an toàn trong phòng',             'Két an toàn',      'https://cdn.luxstay.vn/facilities/safe.jpg',      'ROOM'),
(7,  NOW(), NOW(), 'Hồ bơi ngoài trời tầng thượng',       'Hồ bơi',          'https://cdn.luxstay.vn/facilities/pool.jpg',      'HOTEL'),
(8,  NOW(), NOW(), 'Phòng gym hiện đại 24/7',             'Phòng Gym',        'https://cdn.luxstay.vn/facilities/gym.jpg',       'HOTEL'),
(9,  NOW(), NOW(), 'Spa và massage cao cấp',               'Spa & Massage',   'https://cdn.luxstay.vn/facilities/spa.jpg',       'HOTEL'),
(10, NOW(), NOW(), 'Nhà hàng phục vụ 3 bữa',             'Nhà hàng',         'https://cdn.luxstay.vn/facilities/restaurant.jpg','HOTEL');

-- ============================================================
-- 9. GALLERIES
-- ============================================================
INSERT INTO `galleries` (`id`, `created_at`, `updated_at`, `image_url`, `title`, `type`) VALUES
(1, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/lobby.jpg',       'Sảnh Khách Sạn',         'HOTEL'),
(2, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/pool.jpg',        'Hồ Bơi Tầng Thượng',     'HOTEL'),
(3, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/standard.jpg',    'Phòng Standard',          'ROOM'),
(4, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/deluxe.jpg',      'Phòng Deluxe',            'ROOM'),
(5, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/suite.jpg',       'Phòng Suite',             'ROOM'),
(6, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/restaurant.jpg',  'Nhà Hàng',                'HOTEL'),
(7, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/spa.jpg',         'Khu Spa',                 'HOTEL'),
(8, NOW(), NOW(), 'https://cdn.luxstay.vn/gallery/presidential.jpg','Phòng Presidential Suite','ROOM');

-- ============================================================
-- 10. ROOM_TYPES
-- ============================================================
INSERT INTO `room_types` (`id`, `created_at`, `updated_at`, `description`, `image_url`, `price`, `type_name`) VALUES
(1, NOW(), NOW(), 'Phòng Standard tiện nghi đầy đủ, phù hợp cho cặp đôi hoặc du khách đơn lẻ.',
 'https://cdn.luxstay.vn/room-types/standard.jpg', 800000.00, 'Standard'),
(2, NOW(), NOW(), 'Phòng Deluxe rộng rãi với ban công view thành phố, nội thất sang trọng.',
 'https://cdn.luxstay.vn/room-types/deluxe.jpg', 1500000.00, 'Deluxe'),
(3, NOW(), NOW(), 'Phòng Suite cao cấp với phòng khách riêng, bồn tắm jacuzzi và dịch vụ butler.',
 'https://cdn.luxstay.vn/room-types/suite.jpg', 3000000.00, 'Suite'),
(4, NOW(), NOW(), 'Phòng Family rộng lớn thiết kế cho gia đình, có 2 phòng ngủ và bếp nhỏ.',
 'https://cdn.luxstay.vn/room-types/family.jpg', 2200000.00, 'Family'),
(5, NOW(), NOW(), 'Presidential Suite sang trọng bậc nhất với tầm nhìn panoramic 360 độ.',
 'https://cdn.luxstay.vn/room-types/presidential.jpg', 8000000.00, 'Presidential Suite');

-- ============================================================
-- 11. ROOM_TYPE_FACILITIES
-- ============================================================
-- Standard: điều hòa, TV, WiFi, két an toàn
INSERT INTO `room_type_facilities` (`room_type_id`, `facility_id`) VALUES
(1,1),(1,2),(1,5),(1,6);

-- Deluxe: Standard + minibar
INSERT INTO `room_type_facilities` (`room_type_id`, `facility_id`) VALUES
(2,1),(2,2),(2,3),(2,5),(2,6);

-- Suite: tất cả ROOM facilities
INSERT INTO `room_type_facilities` (`room_type_id`, `facility_id`) VALUES
(3,1),(3,2),(3,3),(3,4),(3,5),(3,6);

-- Family: điều hòa, TV, WiFi, két, minibar
INSERT INTO `room_type_facilities` (`room_type_id`, `facility_id`) VALUES
(4,1),(4,2),(4,3),(4,5),(4,6);

-- Presidential: tất cả
INSERT INTO `room_type_facilities` (`room_type_id`, `facility_id`) VALUES
(5,1),(5,2),(5,3),(5,4),(5,5),(5,6);

-- ============================================================
-- 12. ROOMS
-- ============================================================
INSERT INTO `rooms` (`id`, `created_at`, `updated_at`, `cleaning_status`, `description`, `floor`, `room_name`, `status`, `room_type_id`) VALUES
-- Tầng 1: Standard
(1,  NOW(), NOW(), 'CLEAN',       'Phòng Standard hướng sân vườn', 1, '101', 'AVAILABLE',   1),
(2,  NOW(), NOW(), 'CLEAN',       'Phòng Standard hướng sân vườn', 1, '102', 'AVAILABLE',   1),
(3,  NOW(), NOW(), 'DIRTY',       'Phòng Standard hướng đường',    1, '103', 'AVAILABLE',   1),
(4,  NOW(), NOW(), 'IN_PROGRESS', 'Phòng Standard hướng đường',    1, '104', 'MAINTENANCE', 1),
-- Tầng 2: Standard + Deluxe
(5,  NOW(), NOW(), 'CLEAN',       'Phòng Standard tầng 2',         2, '201', 'AVAILABLE',   1),
(6,  NOW(), NOW(), 'CLEAN',       'Phòng Deluxe view thành phố',   2, '202', 'BOOKED',      2),
(7,  NOW(), NOW(), 'DIRTY',       'Phòng Deluxe view thành phố',   2, '203', 'CHECKED_IN',  2),
(8,  NOW(), NOW(), 'CLEAN',       'Phòng Deluxe góc ban công đôi', 2, '204', 'AVAILABLE',   2),
-- Tầng 3: Deluxe + Family
(9,  NOW(), NOW(), 'CLEAN',       'Phòng Deluxe tầng 3',           3, '301', 'AVAILABLE',   2),
(10, NOW(), NOW(), 'CLEAN',       'Phòng Family 2 phòng ngủ',      3, '302', 'AVAILABLE',   4),
(11, NOW(), NOW(), 'DIRTY',       'Phòng Family view sân vườn',    3, '303', 'CHECKED_IN',  4),
(12, NOW(), NOW(), 'CLEAN',       'Phòng Deluxe tầng 3',           3, '304', 'AVAILABLE',   2),
-- Tầng 4: Suite
(13, NOW(), NOW(), 'CLEAN',       'Suite với bồn tắm jacuzzi',     4, '401', 'AVAILABLE',   3),
(14, NOW(), NOW(), 'CLEAN',       'Suite view thành phố',          4, '402', 'BOOKED',      3),
(15, NOW(), NOW(), 'IN_PROGRESS', 'Suite góc panoramic',           4, '403', 'AVAILABLE',   3),
-- Tầng 5: Presidential
(16, NOW(), NOW(), 'CLEAN',       'Presidential Suite tầng thượng',5, '501', 'AVAILABLE',   5);

-- ============================================================
-- 13. USER_TOKENS (access/refresh token mẫu - đã rút gọn)
-- ============================================================
INSERT INTO `user_tokens` (`user_id`, `access_token`, `created_at`, `refresh_token`) VALUES
(1, 'eyJhbGciOiJIUzI1NiJ9.admin_access_sample',  NOW(), 'eyJhbGciOiJIUzI1NiJ9.admin_refresh_sample'),
(2, 'eyJhbGciOiJIUzI1NiJ9.staff1_access_sample', NOW(), 'eyJhbGciOiJIUzI1NiJ9.staff1_refresh_sample'),
(4, 'eyJhbGciOiJIUzI1NiJ9.cust1_access_sample',  NOW(), 'eyJhbGciOiJIUzI1NiJ9.cust1_refresh_sample'),
(5, 'eyJhbGciOiJIUzI1NiJ9.cust2_access_sample',  NOW(), 'eyJhbGciOiJIUzI1NiJ9.cust2_refresh_sample');

-- ============================================================
-- 14. INVALIDATED_TOKENS (token đã logout / bị thu hồi)
-- ============================================================
INSERT INTO `invalidated_tokens` (`token`, `expiry_time`, `reason`) VALUES
('eyJhbGciOiJIUzI1NiJ9.old_token_1', DATE_ADD(NOW(), INTERVAL 1 DAY),  'LOGOUT'),
('eyJhbGciOiJIUzI1NiJ9.old_token_2', DATE_ADD(NOW(), INTERVAL 2 DAY),  'SESSION_REPLACED'),
('eyJhbGciOiJIUzI1NiJ9.old_token_3', DATE_SUB(NOW(), INTERVAL 1 DAY),  'LOGOUT');

-- ============================================================
-- 15. RESERVATIONS
-- ============================================================
INSERT INTO `reservations` (`id`, `created_at`, `updated_at`, `cancellation_reason`, `check_in`, `check_out`, `discount_amount`, `guest_count`, `note`, `reservation_code`, `status`, `tax_amount`, `total_amount`, `customer_id`) VALUES
(1, NOW(), NOW(), NULL,
 '2026-07-10 14:00:00', '2026-07-13 12:00:00',
 0.00, 2, 'Khách yêu cầu tầng cao', 'RES-2026-0001', 'CONFIRMED',
 240000.00, 4740000.00, 4),

(2, NOW(), NOW(), NULL,
 '2026-07-15 14:00:00', '2026-07-18 12:00:00',
 150000.00, 4, 'Thêm giường phụ nếu có', 'RES-2026-0002', 'CONFIRMED',
 315000.00, 6465000.00, 5),

(3, NOW(), NOW(), NULL,
 '2026-06-28 14:00:00', '2026-07-02 12:00:00',
 0.00, 2, NULL, 'RES-2026-0003', 'CHECKED_IN',
 480000.00, 9480000.00, 6),

(4, NOW(), NOW(), 'Thay đổi kế hoạch du lịch',
 '2026-07-05 14:00:00', '2026-07-07 12:00:00',
 0.00, 1, NULL, 'RES-2026-0004', 'CANCELLED',
 0.00, 0.00, 9),

(5, NOW(), NOW(), NULL,
 '2026-07-20 14:00:00', '2026-07-25 12:00:00',
 500000.00, 2, 'Phòng trăng mật, trang trí hoa', 'RES-2026-0005', 'CONFIRMED',
 1120000.00, 21620000.00, 4),

(6, NOW(), NOW(), NULL,
 '2026-06-25 14:00:00', '2026-06-29 12:00:00',
 0.00, 3, NULL, 'RES-2026-0006', 'CHECKED_OUT',
 352000.00, 6952000.00, 5);

-- ============================================================
-- 16. RESERVATION_ROOM_TYPES
-- ============================================================
INSERT INTO `reservation_room_types` (`id`, `created_at`, `updated_at`, `quantity`, `room_price`, `subtotal`, `reservation_id`, `room_type_id`) VALUES
-- RES-0001: 2 phòng Standard x 3 đêm
(1, NOW(), NOW(), 2, 800000.00, 4800000.00, 1, 1),
-- RES-0002: 1 phòng Deluxe x 3 đêm + 1 phòng Standard x 3 đêm
(2, NOW(), NOW(), 1, 1500000.00, 4500000.00, 2, 2),
(3, NOW(), NOW(), 1, 800000.00,  2400000.00, 2, 1),
-- RES-0003: 1 phòng Suite x 4 đêm
(4, NOW(), NOW(), 1, 3000000.00, 12000000.00, 3, 3),
-- RES-0004 (CANCELLED): 1 phòng Standard x 2 đêm
(5, NOW(), NOW(), 1, 800000.00,  1600000.00, 4, 1),
-- RES-0005: 1 phòng Presidential Suite x 5 đêm
(6, NOW(), NOW(), 1, 8000000.00, 40000000.00, 5, 5),
-- RES-0006 (CHECKED_OUT): 1 phòng Deluxe x 4 đêm
(7, NOW(), NOW(), 1, 1500000.00, 6000000.00, 6, 2);

-- ============================================================
-- 17. RESERVATION_ROOMS
-- ============================================================
INSERT INTO `reservation_rooms` (`id`, `created_at`, `updated_at`, `status`, `assigned_by`, `reservation_room_type_id`, `room_id`) VALUES
-- RES-0001 Standard x2
(1, NOW(), NOW(), 'ASSIGNED', 2, 1, 1),
(2, NOW(), NOW(), 'ASSIGNED', 2, 1, 2),
-- RES-0002 Deluxe + Standard
(3, NOW(), NOW(), 'ASSIGNED', 2, 2, 6),
(4, NOW(), NOW(), 'ASSIGNED', 2, 3, 5),
-- RES-0003 Suite (CHECKED_IN)
(5, NOW(), NOW(), 'CHECKED_IN', 3, 4, 13),
-- RES-0004 (CANCELLED - chưa assign)
(6, NOW(), NOW(), 'CANCELLED',  NULL, 5, NULL),
-- RES-0005 Presidential Suite (chưa check-in)
(7, NOW(), NOW(), 'ASSIGNED',   2, 6, 16),
-- RES-0006 Deluxe (CHECKED_OUT)
(8, NOW(), NOW(), 'CHECKED_OUT', 3, 7, 8);

-- ============================================================
-- 18. GUESTS
-- ============================================================
INSERT INTO `guests` (`id`, `created_at`, `updated_at`, `date_of_birth`, `email`, `full_name`, `id_card_number`, `id_card_type`, `is_primary`, `nationality`, `phone`, `reservation_room_id`) VALUES
-- Phòng 1 (reservation_room 1)
(1, NOW(), NOW(), '1990-05-15', 'customer1@gmail.com', 'Phạm Thị Hoa',    '079090001234', 'CCCD', b'1', 'Việt Nam', '0901000004', 1),
(2, NOW(), NOW(), '1992-08-20', NULL,                   'Phạm Văn Nam',    '079092005678', 'CCCD', b'0', 'Việt Nam', '0901000011', 1),
-- Phòng 2 (reservation_room 2)
(3, NOW(), NOW(), '1988-03-10', NULL,                   'Trần Thị Bình',   '079088009012', 'CCCD', b'1', 'Việt Nam', '0901000012', 2),
-- Phòng 3 Deluxe (reservation_room 3)
(4, NOW(), NOW(), '1985-11-25', 'customer2@gmail.com', 'Nguyễn Quốc Hùng','079085003456', 'CCCD', b'1', 'Việt Nam', '0901000005', 3),
(5, NOW(), NOW(), '1987-07-14', NULL,                   'Nguyễn Thị Lan',  '079087007890', 'CCCD', b'0', 'Việt Nam', '0901000013', 3),
-- Phòng 5 Suite CHECKED_IN (reservation_room 5)
(6, NOW(), NOW(), '1995-01-30', 'customer3@gmail.com', 'Vũ Thị Mai',      '079095001122', 'CCCD', b'1', 'Việt Nam', '0901000006', 5),
(7, NOW(), NOW(), '1993-09-08', NULL,                   'Vũ Văn Đức',      '079093003344', 'CCCD', b'0', 'Việt Nam', '0901000014', 5),
(8, NOW(), NOW(), '2000-12-05', NULL,                   'Vũ Ngọc An',      NULL,           NULL,   b'0', 'Việt Nam', NULL,         5),
-- Phòng 8 CHECKED_OUT (reservation_room 8)
(9, NOW(), NOW(), '1980-06-22', 'customer2@gmail.com', 'Nguyễn Quốc Hùng','079085003456', 'CCCD', b'1', 'Việt Nam', '0901000005', 8),
(10,NOW(), NOW(), '1982-04-18', NULL,                   'Nguyễn Thị Cúc',  '079082005566', 'CCCD', b'0', 'Việt Nam', '0901000015', 8),
(11,NOW(), NOW(), '2010-09-01', NULL,                   'Nguyễn Minh Khôi',NULL,           NULL,   b'0', 'Việt Nam', NULL,         8);

-- ============================================================
-- 19. ROOM_HOLDS
-- ============================================================
INSERT INTO `room_holds` (`id`, `created_at`, `updated_at`, `expires_at`, `status`, `reservation_room_type_id`) VALUES
-- RES-0001 hold đã RELEASED (đã assign phòng)
(1, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 15 MINUTE), 'RELEASED', 1),
-- RES-0002 hold đã RELEASED
(2, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 15 MINUTE), 'RELEASED', 2),
(3, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 15 MINUTE), 'RELEASED', 3),
-- RES-0005 đang ACTIVE (khách sắp check-in)
(4, NOW(), NOW(), DATE_ADD(NOW(), INTERVAL 20 DAY),    'ACTIVE',   6);

-- ============================================================
-- 20. PAYMENTS
-- ============================================================
INSERT INTO `payments` (`id`, `created_at`, `updated_at`, `amount`, `paid_at`, `payment_method`, `payment_status`, `transaction_code`, `reservation_id`) VALUES
(1, NOW(), NOW(), 4740000.00, NOW(),  'BANKING', 'PAID',    'TXN-VCB-20260710-001', 1),
(2, NOW(), NOW(), 6465000.00, NOW(),  'VNPAY',   'PAID',    'TXN-VNPAY-20260715-002', 2),
(3, NOW(), NOW(), 9480000.00, NOW(),  'CASH',    'PAID',    'TXN-CASH-20260628-003', 3),
(4, NOW(), NOW(), 0.00,       NULL,   'BANKING', 'REFUNDED','TXN-VCB-20260705-004', 4),
(5, NOW(), NOW(), 21620000.00,NULL,   'BANKING', 'PENDING', NULL,                    5),
(6, NOW(), NOW(), 6952000.00, NOW(),  'MOMO',    'PAID',    'TXN-MOMO-20260625-006', 6);

-- ============================================================
-- 21. REVIEWS (chỉ cho reservation đã CHECKED_OUT)
-- ============================================================
INSERT INTO `reviews` (`id`, `created_at`, `updated_at`, `comment`, `rating`, `reservation_id`, `room_type_id`, `user_id`) VALUES
(1, NOW(), NOW(), 'Phòng sạch sẽ, nhân viên thân thiện. Sẽ quay lại lần sau!', 5, 6, 2, 5),
(2, NOW(), NOW(), 'Suite rất rộng, bồn tắm jacuzzi tuyệt vời. Dịch vụ 5 sao!',  5, 3, 3, 6);

-- ============================================================
-- 22. CHAT_MESSAGES
-- ============================================================
INSERT INTO `chat_messages` (`id`, `sender`, `session_id`, `text`, `timestamp`) VALUES
(1, 'customer', 'SESSION-001', 'Xin chào, tôi muốn hỏi về phòng còn trống?',                      NOW()),
(2, 'bot',      'SESSION-001', 'Xin chào! Hiện tại chúng tôi còn nhiều phòng trống. Bạn muốn đặt từ ngày nào?', NOW()),
(3, 'customer', 'SESSION-001', 'Tôi muốn đặt từ 10/07 đến 13/07 cho 2 người.',                    NOW()),
(4, 'bot',      'SESSION-001', 'Chúng tôi có phòng Standard (800k/đêm) và Deluxe (1.5tr/đêm) phù hợp cho 2 người.', NOW()),
(5, 'customer', 'SESSION-002', 'Khách sạn có bãi đỗ xe không?',                                   NOW()),
(6, 'bot',      'SESSION-002', 'Dạ có! Chúng tôi có bãi đỗ xe miễn phí cho khách lưu trú.',      NOW());

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- DONE - Tổng quan dữ liệu mẫu:
--   users: 10 (1 admin, 3 staff, 6 customer)
--   groups: 4 | roles: 4 | permissions: 17
--   room_types: 5 | rooms: 16 | facilities: 10
--   reservations: 6 (đủ các trạng thái)
--   reservation_room_types: 7 | reservation_rooms: 8
--   guests: 11 | room_holds: 4 | payments: 6 | reviews: 2
-- ============================================================
