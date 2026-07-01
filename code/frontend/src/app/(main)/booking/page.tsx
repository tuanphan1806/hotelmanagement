"use client";

import React, { useState, useEffect, Suspense } from "react";
import { useSearchParams, useRouter } from "next/navigation";
import Link from "next/link";
import { apiClient } from "@/lib/api";

interface BookingData {
  roomName: string;
  size: string;
  image: string;
  pricePerNight: number;
  totalNights: number;
  checkInDate: string;
  checkOutDate: string;
  adultsCount: string;
  childrenCount: string;
}

// Fallback details for pricing calculations based on room keys
const ROOM_DATABASE: Record<string, { typeName: string; size: string; price: number; image: string }> = {
  "single-room": {
    typeName: "Single Room",
    size: "45 m²",
    price: 2100000,
    image: "https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=300&h=200&fit=crop"
  },
  "double-room": {
    typeName: "Double Room",
    size: "65 m²",
    price: 2900000,
    image: "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=300&h=200&fit=crop"
  },
  "twin-room": {
    typeName: "Twin Room",
    size: "40 m²",
    price: 1850000,
    image: "https://images.unsplash.com/photo-1739590269025-07766e4ab657?w=300&h=300&fit=crop"
  }
};

function BookingFormContent() {
  const searchParams = useSearchParams();
  const router = useRouter();

  const roomId = searchParams.get("roomId") || "premium-deluxe-suite";
  const checkIn = searchParams.get("checkIn") || "2026-06-13";
  const checkOut = searchParams.get("checkOut") || "2026-06-14";
  const adults = searchParams.get("adults") || "2";
  const childrenVal = searchParams.get("children") || "0";

  const [step, setStep] = useState(2); // Step 2 is active on this page
  const [bookingData, setBookingData] = useState<BookingData | null>(null);

  // Form states
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [country, setCountry] = useState("Việt Nam");
  const [specialRequest, setSpecialRequest] = useState("");

  // Payment states
  const [cardNumber, setCardNumber] = useState("");
  const [expiry, setExpiry] = useState("");
  const [cvv, setCvv] = useState("");
  const [agree, setAgree] = useState(false);

  useEffect(() => {
    // Calculate nights
    let nights = 1;
    try {
      const d1 = new Date(checkIn);
      const d2 = new Date(checkOut);
      const diffTime = Math.abs(d2.getTime() - d1.getTime());
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      if (diffDays > 0) nights = diffDays;
    } catch (e) {}

    // Find room info from static DB or use fallbacks
    const match = ROOM_DATABASE[roomId] || ROOM_DATABASE["premium-deluxe-suite"];
    setBookingData({
      roomName: match.typeName,
      size: match.size,
      image: match.image,
      pricePerNight: match.price,
      totalNights: nights,
      checkInDate: checkIn,
      checkOutDate: checkOut,
      adultsCount: adults,
      childrenCount: childrenVal
    });

    // Load user data if logged in
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      try {
        const parsed = JSON.parse(storedUser);
        setFullName(parsed.fullName || "");
        setEmail(parsed.email || "");
        setPhone(parsed.phone || "");
      } catch (e) {}
    }
  }, [roomId, checkIn, checkOut, adults, childrenVal]);

  if (!bookingData) return null;

  // Math calculations
  const subtotal = bookingData.pricePerNight * bookingData.totalNights;
  const tax = Math.round(subtotal * 0.1);
  const total = subtotal + tax;

  const formatVND = (num: number) => {
    return num.toLocaleString("vi-VN") + " đ";
  };

  const formatDateVietnamese = (dateStr: string) => {
    try {
      const d = new Date(dateStr);
      return `${d.getDate()} Tháng ${d.getMonth() + 1}, ${d.getFullYear()}`;
    } catch (e) {
      return dateStr;
    }
  };

  const handleConfirmBooking = (e: React.FormEvent) => {
    e.preventDefault();
    if (!fullName || !email || !phone) {
      alert("Vui lòng điền đầy đủ thông tin khách hàng!");
      return;
    }
    if (!cardNumber || !expiry || !cvv) {
      alert("Vui lòng điền đầy đủ thông tin thanh toán!");
      return;
    }
    if (!agree) {
      alert("Bạn phải đồng ý với Điều khoản & Điều kiện!");
      return;
    }
    // Proceed to Step 3 (Confirmation)
    setStep(3);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  if (step === 3) {
    return (
      <div className="bg-[#FAF9F6] py-10 min-h-screen">
        <div className="max-w-6xl mx-auto px-6 space-y-8">
          {/* Step Indicator Header */}
          <div className="flex items-center justify-center gap-6 text-xs sm:text-sm font-semibold text-text-light border-b border-gray-200/50 pb-6">
            <div className="flex items-center gap-2 text-[#8B6A3E] font-medium">
              <span className="w-6 h-6 rounded-full bg-[#8B6A3E] text-white flex items-center justify-center text-[10px] font-bold">1</span>
              <span className="uppercase tracking-wider">Chọn phòng</span>
            </div>
            <div className="h-px w-10 sm:w-16 bg-[#8B6A3E]" />
            <div className="flex items-center gap-2 text-[#8B6A3E] font-medium">
              <span className="w-6 h-6 rounded-full bg-[#8B6A3E] text-white flex items-center justify-center text-[10px] font-bold">2</span>
              <span className="uppercase tracking-wider">Dịch vụ</span>
            </div>
            <div className="h-px w-10 sm:w-16 bg-[#8B6A3E]" />
            <div className="flex items-center gap-2 text-primary-navy font-bold">
              <span className="w-6 h-6 rounded-full bg-primary-navy text-white flex items-center justify-center text-[10px] font-bold">3</span>
              <span className="uppercase tracking-wider">Xác nhận</span>
            </div>
          </div>

          {/* Success Checkmark & Titles */}
          <div className="text-center space-y-4 max-w-2xl mx-auto pb-6">
            <div className="w-16 h-12 bg-[#8B6A3E] rounded-md flex items-center justify-center mx-auto text-white text-xl font-bold shadow-sm">
              ✓
            </div>
            <h2 className="font-serif text-3xl md:text-4xl font-bold text-primary-navy tracking-wide">
              Đặt phòng thành công!
            </h2>
            <p className="text-sm md:text-base font-serif font-bold text-[#8B6A3E] tracking-widest uppercase">
              Mã số đặt phòng: #HN-2026-8888
            </p>
            <p className="text-xs text-text-light font-medium">
              Một email xác nhận đã được gửi đến địa chỉ email của bạn.
            </p>
          </div>

          {/* Two Columns Grid */}
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 items-start">
            
            {/* Left Column: Confirmation Details */}
            <div className="lg:col-span-2 space-y-6">
              <div className="bg-white border border-gray-200 rounded-sm shadow-sm overflow-hidden">
                <div className="p-6 border-b border-gray-100 bg-gray-50/50">
                  <h3 className="text-sm font-bold text-primary-navy tracking-wider uppercase">
                    Chi tiết xác nhận
                  </h3>
                </div>

                <div className="p-8 grid grid-cols-1 md:grid-cols-2 gap-8 text-sm">
                  {/* Customer Info */}
                  <div className="space-y-4">
                    <h4 className="text-[10px] font-bold text-[#8B6A3E] tracking-widest uppercase">
                      Thông tin khách hàng
                    </h4>
                    <div className="space-y-3">
                      <div>
                        <p className="text-[10px] text-text-light font-bold uppercase tracking-wider">Họ tên</p>
                        <p className="font-semibold text-text-dark text-base mt-0.5">{fullName || "Nguyễn Văn A"}</p>
                      </div>
                      <div>
                        <p className="text-[10px] text-text-light font-bold uppercase tracking-wider">Email</p>
                        <p className="font-medium text-text-dark mt-0.5">{email || "nguyenvana@example.com"}</p>
                      </div>
                      <div>
                        <p className="text-[10px] text-text-light font-bold uppercase tracking-wider">Số điện thoại</p>
                        <p className="font-medium text-text-dark mt-0.5">{phone || "+84 90 123 4567"}</p>
                      </div>
                    </div>
                  </div>

                  {/* Room Booking Info */}
                  <div className="space-y-4">
                    <h4 className="text-[10px] font-bold text-[#8B6A3E] tracking-widest uppercase">
                      Thông tin đặt phòng
                    </h4>
                    <div className="space-y-3">
                      <div>
                        <p className="text-[10px] text-text-light font-bold uppercase tracking-wider">Phòng</p>
                        <p className="font-semibold text-text-dark text-base mt-0.5">{bookingData.roomName}</p>
                      </div>
                      <div className="grid grid-cols-2 gap-4">
                        <div>
                          <p className="text-[10px] text-text-light font-bold uppercase tracking-wider">Nhận phòng</p>
                          <p className="font-medium text-text-dark mt-0.5">{bookingData.checkInDate}</p>
                        </div>
                        <div>
                          <p className="text-[10px] text-text-light font-bold uppercase tracking-wider">Trả phòng</p>
                          <p className="font-medium text-text-dark mt-0.5">{bookingData.checkOutDate}</p>
                        </div>
                      </div>
                      <div>
                        <p className="text-[10px] text-text-light font-bold uppercase tracking-wider">Số khách</p>
                        <p className="font-medium text-text-dark mt-0.5">{bookingData.adultsCount} người lớn, {bookingData.childrenCount} trẻ em</p>
                      </div>
                    </div>
                  </div>
                </div>

                {/* Navy Total Bar */}
                <div className="bg-primary-navy p-6 text-white flex flex-col sm:flex-row sm:items-center sm:justify-between gap-2">
                  <span className="text-sm font-bold tracking-wider uppercase">Tổng cộng</span>
                  <div className="text-right">
                    <span className="text-xl sm:text-2xl font-bold text-accent-gold">{formatVND(total)}</span>
                    <p className="text-[10px] text-white/50 uppercase tracking-widest font-semibold mt-0.5">
                      Đã thanh toán qua thẻ tín dụng
                    </p>
                  </div>
                </div>
              </div>

              {/* Action Buttons below card */}
              <div className="flex flex-col sm:flex-row gap-4 pt-2">
                <button 
                  onClick={() => window.print()}
                  className="flex-1 border border-[#8B6A3E] text-[#8B6A3E] hover:bg-[#8B6A3E]/5 px-8 py-3.5 font-bold text-xs tracking-widest flex items-center justify-center gap-2 uppercase rounded-sm transition-colors"
                >
                  <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                    <polyline points="6 9 6 2 18 2 18 9" />
                    <path d="M6 18H4a2 2 0 0 1-2-2v-5a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2h-2" />
                    <rect x="6" y="14" width="12" height="8" />
                  </svg>
                  In xác nhận
                </button>
                <Link 
                  href="/"
                  className="flex-1 bg-[#8B6A3E] hover:bg-[#735630] text-white px-8 py-3.5 font-bold text-xs tracking-widest flex items-center justify-center gap-2 uppercase rounded-sm transition-colors shadow-sm"
                >
                  <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                    <path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
                    <polyline points="9 22 9 12 15 12 15 22" />
                  </svg>
                  Về trang chủ
                </Link>
              </div>
            </div>

            {/* Right Column: Your Stay Booking Summary */}
            <div className="bg-white border border-gray-200 p-6 rounded-sm shadow-sm space-y-6">
              <div className="flex items-center gap-3 pb-3 border-b border-gray-100">
                <span className="text-xl">🏨</span>
                <div>
                  <h3 className="font-serif text-lg font-bold text-primary-navy">Your Stay</h3>
                  <p className="text-[10px] text-text-light font-medium tracking-wider uppercase">Booking Summary</p>
                </div>
              </div>

              <div className="space-y-3 text-xs font-semibold text-text-dark">
                <div className="flex justify-between">
                  <span className="text-text-light font-medium">Selected Rooms</span>
                  <span>01</span>
                </div>
                <div className="flex justify-between pb-3">
                  <span className="text-text-light font-medium">Add-ons</span>
                  <span>00</span>
                </div>
                <div className="flex justify-between border-t border-gray-150 pt-3 text-sm font-bold text-primary-navy">
                  <span>Total:</span>
                  <span className="text-base text-accent-gold">{formatVND(total)}</span>
                </div>
              </div>

              {/* Quote block */}
              <div className="bg-gray-50 border border-gray-100 p-4 rounded-sm text-xs italic font-light text-text-dark/80 leading-relaxed">
                "Chúng tôi rất hân hạnh được đón tiếp quý khách tại Luxury Hotels. Mọi yêu cầu đặc biệt xin vui lòng liên hệ bộ phận Concierge qua hotline +84 24 1234 5678."
              </div>

              {/* Building Facade Image */}
              <div className="h-[180px] rounded-sm overflow-hidden shadow-sm">
                <img 
                  src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=500&h=300&fit=crop" 
                  alt="hotel building night" 
                  className="w-full h-full object-cover hover:scale-105 transition-transform duration-500"
                />
              </div>
            </div>

          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="bg-[#FAF9F6] py-10 min-h-screen">
      <div className="max-w-6xl mx-auto px-6 space-y-8">
        
        {/* Step Indicator Header */}
        <div className="flex items-center justify-center gap-8 text-xs sm:text-sm font-semibold text-text-light border-b border-gray-200/50 pb-6">
          <div className="flex items-center gap-2">
            <span className="w-6 h-6 rounded-full bg-gray-200 text-text-light flex items-center justify-center text-[10px]">1</span>
            <span>Chọn phòng</span>
          </div>
          <div className="h-px w-10 sm:w-16 bg-gray-200" />
          <div className="flex items-center gap-2 text-primary-navy font-bold">
            <span className="w-6 h-6 rounded-full bg-primary-navy text-white flex items-center justify-center text-[10px]">2</span>
            <span>Thông tin & Thanh toán</span>
          </div>
          <div className="h-px w-10 sm:w-16 bg-gray-200" />
          <div className="flex items-center gap-2">
            <span className="w-6 h-6 rounded-full bg-gray-200 text-text-light flex items-center justify-center text-[10px]">3</span>
            <span>Xác nhận</span>
          </div>
        </div>

        {/* Two Columns Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 items-start">
          
          {/* Left Columns - Forms */}
          <div className="lg:col-span-2 space-y-8">
            
            {/* Customer Details Form */}
            <div className="bg-white border border-gray-200 p-8 rounded-sm shadow-sm space-y-6">
              <h3 className="font-serif text-2xl font-bold text-primary-navy pb-3 border-b border-gray-100">
                Thông tin khách hàng
              </h3>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">Họ và tên *</label>
                  <input 
                    type="text" 
                    placeholder="Nguyễn Văn A" 
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                    className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                    required
                  />
                </div>
                <div>
                  <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">Email *</label>
                  <input 
                    type="email" 
                    placeholder="email@example.com" 
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                    required
                  />
                </div>
                <div>
                  <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">Số điện thoại *</label>
                  <input 
                    type="tel" 
                    placeholder="+84 ..." 
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                    className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                    required
                  />
                </div>
                <div>
                  <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">Quốc gia</label>
                  <select 
                    value={country}
                    onChange={(e) => setCountry(e.target.value)}
                    className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium bg-transparent"
                  >
                    <option value="Việt Nam">Việt Nam</option>
                    <option value="Mỹ">Mỹ</option>
                    <option value="Nhật Bản">Nhật Bản</option>
                    <option value="Hàn Quốc">Hàn Quốc</option>
                  </select>
                </div>
              </div>

              <div>
                <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">Yêu cầu đặc biệt</label>
                <textarea 
                  rows={4}
                  placeholder="Ví dụ: Phòng tầng cao, check-in sớm..." 
                  value={specialRequest}
                  onChange={(e) => setSpecialRequest(e.target.value)}
                  className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                />
              </div>
            </div>

            {/* Payment Details Form */}
            <div className="bg-white border border-gray-200 p-8 rounded-sm shadow-sm space-y-6">
              <div className="flex items-center justify-between pb-3 border-b border-gray-100">
                <h3 className="font-serif text-2xl font-bold text-primary-navy">
                  Thông tin thanh toán
                </h3>
                <div className="flex gap-2">
                  <span className="px-2 py-0.5 border border-gray-300 text-xs font-bold rounded-sm text-[#1A1F71] bg-gray-50">VISA</span>
                  <span className="px-2 py-0.5 border border-gray-300 text-xs font-bold rounded-sm text-[#EB001B] bg-gray-50">MC</span>
                </div>
              </div>

              <div className="space-y-4">
                <div>
                  <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">Số thẻ tín dụng *</label>
                  <input 
                    type="text" 
                    placeholder="0000 0000 0000 0000" 
                    value={cardNumber}
                    onChange={(e) => setCardNumber(e.target.value)}
                    className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                    required
                  />
                </div>

                <div className="grid grid-cols-2 gap-6">
                  <div>
                    <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">Hạn sử dụng *</label>
                    <input 
                      type="text" 
                      placeholder="MM/YY" 
                      value={expiry}
                      onChange={(e) => setExpiry(e.target.value)}
                      className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                      required
                    />
                  </div>
                  <div>
                    <label className="block text-xs font-bold text-text-dark uppercase tracking-wider mb-2">CVV *</label>
                    <input 
                      type="password" 
                      placeholder="***" 
                      maxLength={3}
                      value={cvv}
                      onChange={(e) => setCvv(e.target.value)}
                      className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                      required
                    />
                  </div>
                </div>

                <label className="flex items-start gap-3 text-xs text-text-light font-medium cursor-pointer pt-2">
                  <input 
                    type="checkbox" 
                    checked={agree}
                    onChange={(e) => setAgree(e.target.checked)}
                    className="mt-0.5 rounded border-gray-300 text-accent-gold focus:ring-accent-gold"
                    required
                  />
                  <span>
                    Tôi đã đọc và đồng ý với các <Link href="#" className="text-accent-gold hover:underline">Điều khoản & Điều kiện</Link> và <Link href="#" className="text-accent-gold hover:underline">Chính sách bảo mật</Link> của Hanoi Hotel.
                  </span>
                </label>
              </div>
            </div>

          </div>

          {/* Right Column - Booking Summary */}
          <div className="bg-white border border-gray-200 p-6 rounded-sm shadow-md sticky top-28 space-y-6">
            <h3 className="font-sans text-lg font-bold text-primary-navy pb-3 border-b border-gray-100">
              Tóm tắt đặt phòng
            </h3>

            {/* Room Info */}
            <div className="flex gap-4">
              <div className="w-24 h-16 shrink-0 rounded-sm overflow-hidden border border-gray-100">
                <img src={bookingData.image} alt={bookingData.roomName} className="w-full h-full object-cover" />
              </div>
              <div className="min-w-0">
                <h4 className="font-serif text-base font-bold text-primary-navy truncate">{bookingData.roomName}</h4>
                <p className="text-xs text-text-light font-medium mt-0.5">Diện tích: {bookingData.size}</p>
              </div>
            </div>

            {/* Dates & Guests */}
            <div className="grid grid-cols-2 gap-4 border-t border-b border-gray-100 py-4 text-xs font-semibold text-text-dark">
              <div>
                <p className="text-text-light font-medium uppercase tracking-wider mb-1">Ngày đến</p>
                <p>{formatDateVietnamese(bookingData.checkInDate)}</p>
              </div>
              <div>
                <p className="text-text-light font-medium uppercase tracking-wider mb-1">Ngày đi</p>
                <p>{formatDateVietnamese(bookingData.checkOutDate)}</p>
              </div>
              <div className="col-span-2 pt-2 border-t border-gray-100/50">
                <p className="text-text-light font-medium uppercase tracking-wider mb-1">Khách</p>
                <p>{bookingData.adultsCount} Người lớn, {bookingData.childrenCount} Trẻ em</p>
              </div>
            </div>

            {/* Prices details */}
            <div className="space-y-2 text-sm">
              <div className="flex justify-between text-text-light font-medium">
                <span>Giá phòng ({bookingData.totalNights} đêm)</span>
                <span>{formatVND(subtotal)}</span>
              </div>
              <div className="flex justify-between text-text-light font-medium">
                <span>Thuế & Phí dịch vụ (10%)</span>
                <span>{formatVND(tax)}</span>
              </div>
              <div className="flex justify-between border-t border-gray-200 pt-3 text-base font-bold text-primary-navy">
                <span>TỔNG CỘNG</span>
                <span className="text-accent-gold">{formatVND(total)}</span>
              </div>
            </div>

            <button 
              onClick={handleConfirmBooking}
              className="w-full bg-[#8B6A3E] hover:bg-[#735630] text-white py-4 font-bold tracking-widest text-sm rounded-sm transition-colors shadow-sm uppercase mt-4"
            >
              Xác nhận đặt phòng
            </button>

            <p className="text-center text-[10px] text-text-light font-medium">
              Giá đã bao gồm VAT và phí phục vụ.
            </p>
          </div>

        </div>

      </div>
    </div>
  );
}

export default function BookingPage() {
  return (
    <Suspense fallback={
      <div className="min-h-screen flex items-center justify-center bg-[#FAF9F6]">
        <div className="flex flex-col items-center gap-4">
          <div className="w-12 h-12 border-4 border-primary-navy border-t-accent-gold rounded-full animate-spin"></div>
          <p className="text-primary-navy font-semibold">Đang chuẩn bị thông tin thanh toán...</p>
        </div>
      </div>
    }>
      <BookingFormContent />
    </Suspense>
  );
}
