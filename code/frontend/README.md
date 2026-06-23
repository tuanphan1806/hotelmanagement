# Lumière Palace - Hotel Management Frontend ⚜

Frontend của hệ thống Quản lý Khách sạn Cao cấp **Lumière Palace**. Dự án được xây dựng trên nền tảng **Next.js 15 (App Router)** kết hợp với **TypeScript**, **TailwindCSS** và hệ thống thiết kế premium (glassmorphic UI, custom animations).

---

## ✨ Tính năng chính (Frontend)

*   **🔒 Hệ thống Authentication Premium**:
    *   Trang Đăng nhập (Login) & Đăng ký (Signup) được thiết kế hiện đại, responsive hoàn toàn.
    *   Tích hợp Đăng nhập qua mạng xã hội (Google, Facebook).
    *   Quy trình Đăng ký tài khoản khách hàng (Signup Flow) tối giản 2 bước (Multi-step form) để tối ưu hóa trải nghiệm khách đặt phòng (Guest/Customer).
*   **🏢 Khách hàng & Thành viên (Guest-focused)**:
    *   Giao diện giới thiệu phòng (Rooms), Dịch vụ & Tiện ích (Facilities).
    *   Thông tin liên hệ & Đặt lịch (Contact & Booking).
*   **📊 Trang quản trị (Dashboard)**:
    *   Bảng điều khiển trực quan cho nhân viên/quản trị viên khách sạn.

---

## 🛠 Công nghệ sử dụng

*   **Core**: Next.js 15 (App Router), React 19
*   **Language**: TypeScript
*   **Styling**: Tailwind CSS
*   **Validation**: Zod (Form validation)
*   **Icons**: Lucide React
*   **Linting & Quality**: ESLint, Prettier, Husky (Pre-commit hooks)

---

## 🚀 Khởi chạy dự án (Local)

### 📋 Yêu cầu hệ thống
*   **Node.js**: Phiên bản LTS mới nhất (Khuyên dùng v20+)
*   **Package Manager**: `pnpm` (Khuyên dùng) hoặc `npm` / `yarn`

### ⚙ Các bước cài đặt

1.  Cài đặt các gói phụ thuộc (Dependencies):
    ```bash
    pnpm install
    ```

2.  Khởi động máy chủ phát triển (Development Server):
    ```bash
    pnpm run dev
    ```
    Mở [http://localhost:3000](http://localhost:3000) trên trình duyệt của bạn để xem kết quả.

3.  Build dự án phục vụ Production:
    ```bash
    pnpm run build
    pnpm start
    ```

---

## 📁 Cấu trúc thư mục Frontend

```
code/frontend/
├── public/              # Static assets (hình ảnh, logo, v.v.)
└── src/
    ├── app/             # Next.js App Router Pages & Layouts
    │   ├── (auth)/      # Route Group cho Authentication (Login/Signup)
    │   └── (main)/      # Route Group cho Main pages (Rooms, Dashboard...)
    ├── components/      # Các React Components dùng chung
    │   ├── AuthForm/    # Các thành phần form Auth (InputField)
    │   └── UI/          # Các UI Component cơ bản (Button)
    ├── constants/       # Định nghĩa hằng số, text content tĩnh
    └── lib/             # Các hàm Helper, Cookies utilities
```

---

## 🛡 Quy chuẩn Commit & Code

*   **Husky**: Dự án đã tích hợp Husky để tự động chạy kiểm tra định dạng và cấu trúc code trước mỗi lần commit (`pre-commit hook`).
*   **Quy tắc đặt tên commit**: Khuyên dùng chuẩn [Conventional Commits](https://www.conventionalcommits.org/) (ví dụ: `feat: ...`, `fix: ...`, `chore: ...`).
