# Luxury Hotel - Hotel Management Frontend ⚜

Frontend của hệ thống Quản lý Khách sạn Cao cấp **Luxury Hotel**. Dự án được xây dựng trên nền tảng **Next.js 15 (App Router)** kết hợp với **TypeScript**, **TailwindCSS** và hệ thống thiết kế premium (glassmorphic UI, custom animations). Hướng dẫn dưới đây giúp bạn khởi chạy ứng dụng một cách nhanh nhất.

## 🛠️ Yêu cầu hệ thống

Trước khi bắt đầu, hãy đảm bảo máy tính của bạn đã cài đặt:
- **Node.js** (Khuyến nghị phiên bản LTS từ 18 trở lên)
- **pnpm** (Package Manager chính của dự án)
  - *Nếu chưa cài đặt pnpm, bạn có thể chạy lệnh:* `npm install -g pnpm`

---

## 🚀 Hướng dẫn khởi chạy dự án

Làm theo các bước sau sau khi pull code về:

### Bước 1: Di chuyển vào thư mục Frontend
```bash
cd code/frontend
```

### Bước 2: Cài đặt các thư viện (Dependencies)
```bash
pnpm install
```

### Bước 3: Khởi chạy môi trường phát triển (Development Server)
```bash
pnpm run dev
```

Sau khi chạy lệnh trên thành công, ứng dụng Frontend sẽ được chạy tại địa chỉ:
👉 **[http://localhost:3000](http://localhost:3000)**

---

## 💡 Lưu ý về cấu hình API (Environment Variables)

Mặc định, ứng dụng Frontend được cấu hình sẵn để kết nối tới cổng Backend tại:
* API URL: `http://localhost:8080`

Nếu bạn muốn thay đổi cấu hình kết nối API này, bạn có thể:
1. Tạo một file tên là `.env.local` ở thư mục `code/frontend`.
2. Định cấu hình biến môi trường tùy chỉnh như sau:
   ```env
   NEXT_PUBLIC_API_URL=http://localhost:8080
   ```