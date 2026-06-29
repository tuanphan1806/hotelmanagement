"use client";

import React, { useEffect, useState, use } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { apiClient } from "@/lib/api";

interface RoomDetails {
  id?: number;
  typeName: string;
  description: string;
  price: number | string;
  imageUrl: string;
  specs: {
    bed: string;
    size: string;
    view: string;
  };
  gallery: string[];
  amenities: string[];
}

// Fallback data matching the screenshot and other categories
const DEFAULT_ROOM_DETAILS: Record<string, RoomDetails> = {
  "premium-deluxe-suite": {
    typeName: "Premium Deluxe Suite",
    description: "Trải nghiệm kỳ nghỉ dưỡng hoàn hảo trong không gian phòng Premium Deluxe Suite rộng rãi, tinh tế. Phòng được trang bị đầy đủ các tiện nghi cao cấp hiện đại cùng ban công ngắm cảnh tuyệt đẹp, mang lại cho bạn những phút giây thư giãn tuyệt đối sau một ngày dài khám phá.",
    price: 210,
    imageUrl: "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=1600&h=900&fit=crop",
    specs: {
      bed: "1 Giường đôi lớn",
      size: "45 m²",
      view: "Hướng hồ"
    },
    gallery: [
      "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?w=600&h=400&fit=crop", // bathroom
      "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?w=600&h=400&fit=crop", // desk / coffee
      "https://images.unsplash.com/photo-1600566752355-35792bedcfea?w=600&h=400&fit=crop"  // shower
    ],
    amenities: [
      "Tivi thông minh màn hình phẳng",
      "Máy pha cà phê cao cấp",
      "Mini bar với đồ uống chọn lọc",
      "Két sắt an toàn bảo mật",
      "Dép đi trong nhà êm ái",
      "Đồ vệ sinh cá nhân cao cấp",
      "Wifi tốc độ cao miễn phí",
      "Dịch vụ dọn phòng hàng ngày"
    ]
  },
  "premium-executive-suite": {
    typeName: "Premium Executive Suite",
    description: "Phòng Premium Executive Suite đẳng cấp dành cho những ai tìm kiếm sự hoàn hảo. Không gian rộng lớn với phòng khách riêng biệt, nội thất gỗ sang trọng và tầm nhìn toàn cảnh ngoạn mục.",
    price: 290,
    imageUrl: "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=1600&h=900&fit=crop",
    specs: {
      bed: "1 Giường King lớn",
      size: "65 m²",
      view: "Hướng biển"
    },
    gallery: [
      "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?w=600&h=400&fit=crop",
      "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?w=600&h=400&fit=crop",
      "https://images.unsplash.com/photo-1600566752355-35792bedcfea?w=600&h=400&fit=crop"
    ],
    amenities: [
      "Tivi thông minh màn hình phẳng",
      "Máy pha cà phê cao cấp",
      "Mini bar với đồ uống chọn lọc",
      "Két sắt an toàn bảo mật",
      "Dép đi trong nhà êm ái",
      "Đồ vệ sinh cá nhân cao cấp",
      "Lối vào Club Lounge riêng biệt",
      "Bồn tắm Jacuzzi hiện đại"
    ]
  },
  "junior-suite": {
    typeName: "Junior Suite",
    description: "Sự kết hợp hoàn hảo giữa tiện nghi và ấm cúng. Junior Suite mang đến một trải nghiệm lưu trú đáng nhớ với cách bài trí nội thất thông minh cùng ban công lộng gió.",
    price: 185,
    imageUrl: "https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=1600&h=900&fit=crop",
    specs: {
      bed: "1 Giường Queen lớn",
      size: "38 m²",
      view: "Hướng vườn"
    },
    gallery: [
      "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?w=600&h=400&fit=crop",
      "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?w=600&h=400&fit=crop",
      "https://images.unsplash.com/photo-1600566752355-35792bedcfea?w=600&h=400&fit=crop"
    ],
    amenities: [
      "Tivi thông minh màn hình phẳng",
      "Máy pha cà phê cao cấp",
      "Mini bar với đồ uống chọn lọc",
      "Két sắt an toàn bảo mật",
      "Dép đi trong nhà êm ái",
      "Đồ vệ sinh cá nhân cao cấp",
      "Trà và cà phê miễn phí",
      "Wifi tốc độ cao miễn phí"
    ]
  }
};

const RECOMMENDATIONS = [
  {
    id: "premium-executive-suite",
    title: "Premium Executive Suite",
    desc: "Trải nghiệm hoàng gia với không gian cực rộng và bồn tắm Jacuzzi.",
    image: "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800&h=500&fit=crop"
  },
  {
    id: "junior-suite",
    title: "Junior Suite",
    desc: "Ấm cúng và sang trọng, lý tưởng cho các cặp đôi tận hưởng kỳ nghỉ.",
    image: "https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=800&h=500&fit=crop"
  }
];

export default function RoomDetailPage({ params }: { params: Promise<{ id: string }> }) {
  const router = useRouter();
  const resolvedParams = use(params);
  const roomId = resolvedParams.id;

  const [room, setRoom] = useState<RoomDetails | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  // Booking widget states
  const [checkIn, setCheckIn] = useState("");
  const [checkOut, setCheckOut] = useState("");
  const [adults, setAdults] = useState("1");
  const [childrenCount, setChildrenCount] = useState("0");

  const handleBookingCheck = (e: React.FormEvent) => {
    e.preventDefault();
    if (!checkIn || !checkOut) {
      alert("Vui lòng chọn ngày nhận phòng và trả phòng!");
      return;
    }
    router.push(`/booking?roomId=${roomId}&checkIn=${checkIn}&checkOut=${checkOut}&adults=${adults}&children=${childrenCount}`);
  };

  useEffect(() => {
    // Try to match static keys first
    if (DEFAULT_ROOM_DETAILS[roomId]) {
      setRoom(DEFAULT_ROOM_DETAILS[roomId]);
      setIsLoading(false);
      return;
    }

    // Otherwise fetch from database API
    apiClient.get(`/api/v1/room-types/${roomId}`)
      .then(res => {
        if (res.data && res.data.data) {
          const dbData = res.data.data;
          // Map backend object format to page expectations
          setRoom({
            typeName: dbData.typeName || "Luxury Room",
            description: dbData.description || "Trải nghiệm lưu trú tuyệt vời với dịch vụ 5 sao.",
            price: dbData.price || 150,
            imageUrl: dbData.imageUrl || "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=1600&h=900&fit=crop",
            specs: {
              bed: "1 Giường đôi lớn",
              size: "42 m²",
              view: "Hướng vườn"
            },
            gallery: [
              "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?w=600&h=400&fit=crop",
              "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?w=600&h=400&fit=crop",
              "https://images.unsplash.com/photo-1600566752355-35792bedcfea?w=600&h=400&fit=crop"
            ],
            amenities: dbData.facilities?.map((f: any) => f.facilityName) || [
              "Tivi thông minh màn hình phẳng",
              "Mini bar với đồ uống",
              "Wifi tốc độ cao miễn phí",
              "Dịch vụ dọn phòng"
            ]
          });
        } else {
          // Default fallback
          setRoom(DEFAULT_ROOM_DETAILS["premium-deluxe-suite"]);
        }
      })
      .catch(err => {
        console.error("Error fetching room details from API, using fallback:", err);
        // Default fallback
        setRoom(DEFAULT_ROOM_DETAILS["premium-deluxe-suite"]);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [roomId]);

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-[#FAF9F6]">
        <div className="flex flex-col items-center gap-4">
          <div className="w-12 h-12 border-4 border-primary-navy border-t-accent-gold rounded-full animate-spin"></div>
          <p className="text-primary-navy font-semibold">Đang tải chi tiết phòng...</p>
        </div>
      </div>
    );
  }

  if (!room) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-[#FAF9F6]">
        <p className="text-[#A66E6E] font-bold text-lg">Không tìm thấy phòng tương ứng.</p>
      </div>
    );
  }



  return (
    <div className="bg-white">
      {/* Hero Section */}
      <section className="relative h-[70vh] flex flex-col justify-end pb-20 bg-primary-navy overflow-hidden">
        <div 
          className="absolute inset-0 bg-cover bg-center bg-no-repeat opacity-70"
          style={{ backgroundImage: `url("${room.imageUrl}")` }}
        ></div>
        <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-transparent to-transparent" />
        
        <div className="relative z-10 max-w-6xl mx-auto px-6 w-full text-left">
          <span className="text-accent-gold text-xs font-bold tracking-[0.25em] uppercase block mb-3">ROOMS & SUITES</span>
          <h1 className="text-white font-serif text-4xl md:text-6xl font-bold tracking-wide">
            {room.typeName}
          </h1>
        </div>
      </section>

      {/* Main Grid Content */}
      <section className="max-w-6xl mx-auto px-6 py-16">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-12 items-start">
          
          {/* Left Details Info */}
          <div className="lg:col-span-2 space-y-10">
            {/* Spec Badges Row */}
            <div className="flex flex-wrap gap-6 items-center border-b border-gray-100 pb-6 text-sm text-text-light font-medium">
              <div className="flex items-center gap-2">
                <span className="text-accent-gold text-lg">🛏</span>
                <span>{room.specs.bed}</span>
              </div>
              <div className="w-1.5 h-1.5 rounded-full bg-gray-300 hidden sm:block"></div>
              <div className="flex items-center gap-2">
                <span className="text-accent-gold text-lg">📐</span>
                <span>{room.specs.size}</span>
              </div>
              <div className="w-1.5 h-1.5 rounded-full bg-gray-300 hidden sm:block"></div>
              <div className="flex items-center gap-2">
                <span className="text-accent-gold text-lg">🌅</span>
                <span>{room.specs.view}</span>
              </div>
            </div>

            {/* Description Text */}
            <div className="space-y-4">
              <h2 className="font-serif text-3xl font-bold text-primary-navy">
                Nghỉ dưỡng hoàn hảo
              </h2>
              <p className="text-text-light leading-relaxed text-sm md:text-base font-light">
                {room.description}
              </p>
            </div>

            {/* Grid of Images */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="h-[250px] overflow-hidden rounded-sm shadow-sm">
                <img src={room.gallery[0]} alt="bathroom" className="w-full h-full object-cover hover:scale-105 transition-transform duration-500" />
              </div>
              <div className="h-[250px] overflow-hidden rounded-sm shadow-sm">
                <img src={room.gallery[1]} alt="room desk" className="w-full h-full object-cover hover:scale-105 transition-transform duration-500" />
              </div>
              <div className="h-[250px] overflow-hidden rounded-sm shadow-sm">
                <img src={room.gallery[2]} alt="bathroom details" className="w-full h-full object-cover hover:scale-105 transition-transform duration-500" />
              </div>
            </div>

            {/* Amenities Section */}
            <div className="bg-[#EEF3FC] p-8 rounded-sm border border-gray-100/50">
              <h3 className="font-serif text-xl font-bold text-primary-navy mb-6 pb-2 border-b border-gray-200/50">
                Các tiện nghi
              </h3>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-y-4 gap-x-8">
                {room.amenities.map((amenity, idx) => (
                  <div key={idx} className="flex items-center gap-3 text-sm text-text-dark font-medium">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#C5A86E" strokeWidth="2.5" className="shrink-0">
                      <polyline points="20 6 9 17 4 12" />
                    </svg>
                    <span>{amenity}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>

          {/* Right Booking Card Widgets */}
          <div className="bg-white border border-gray-200 p-8 rounded-sm shadow-md sticky top-28 space-y-6">
            <div>
              <h3 className="font-serif text-2xl font-bold text-primary-navy">Đặt phòng</h3>
              <p className="text-xs text-text-light font-medium mt-1">Kiểm tra phòng trống & giá tốt nhất</p>
            </div>

            <form onSubmit={handleBookingCheck} className="space-y-4">
              {/* Check-in */}
              <div>
                <label className="block text-xs font-semibold text-text-dark uppercase tracking-wider mb-2">Ngày nhận phòng</label>
                <input 
                  type="date" 
                  value={checkIn}
                  onChange={(e) => setCheckIn(e.target.value)}
                  className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                />
              </div>

              {/* Check-out */}
              <div>
                <label className="block text-xs font-semibold text-text-dark uppercase tracking-wider mb-2">Ngày trả phòng</label>
                <input 
                  type="date" 
                  value={checkOut}
                  onChange={(e) => setCheckOut(e.target.value)}
                  className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium"
                />
              </div>

              {/* Guests Grid */}
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-semibold text-text-dark uppercase tracking-wider mb-2">Người lớn</label>
                  <select 
                    value={adults}
                    onChange={(e) => setAdults(e.target.value)}
                    className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium bg-transparent"
                  >
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                  </select>
                </div>
                <div>
                  <label className="block text-xs font-semibold text-text-dark uppercase tracking-wider mb-2">Trẻ em</label>
                  <select 
                    value={childrenCount}
                    onChange={(e) => setChildrenCount(e.target.value)}
                    className="w-full border border-gray-300 px-4 py-3 rounded-sm focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold text-sm font-medium bg-transparent"
                  >
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                  </select>
                </div>
              </div>

              <button 
                type="submit"
                className="w-full bg-[#8B6A3E] hover:bg-[#735630] text-white py-4 font-bold tracking-widest text-sm rounded-sm transition-colors shadow-sm uppercase mt-4"
              >
                Check Availability
              </button>
            </form>

            <div className="flex items-center justify-center gap-2 pt-2 border-t border-gray-100 text-xs text-[#5C7C64] font-semibold">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round" className="shrink-0">
                <polyline points="20 6 9 17 4 12" />
              </svg>
              <span>Hủy phòng miễn phí</span>
            </div>
          </div>
        </div>
      </section>

      {/* Bottom Recommendation Slider */}
      <section className="bg-[#EEF3FC]/40 border-t border-gray-100 py-20">
        <div className="max-w-6xl mx-auto px-6">
          <div className="flex items-center justify-between mb-12">
            <h2 className="font-serif text-3xl md:text-4xl font-bold text-primary-navy">
              Xem thêm các lựa chọn
            </h2>
            <Link href="/rooms" className="text-xs font-bold text-accent-gold hover:underline tracking-wider uppercase">
              TẤT CẢ PHÒNG &rarr;
            </Link>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            {RECOMMENDATIONS.map((rec) => (
              <div key={rec.id} className="bg-white border border-gray-100 rounded-sm overflow-hidden shadow-sm hover:shadow-md transition-shadow">
                <div className="h-[250px] overflow-hidden">
                  <img src={rec.image} alt={rec.title} className="w-full h-full object-cover" />
                </div>
                <div className="p-6 space-y-3">
                  <h3 className="font-serif text-2xl font-bold text-primary-navy">{rec.title}</h3>
                  <p className="text-text-light text-sm font-light leading-relaxed">{rec.desc}</p>
                  <Link 
                    href={`/rooms/${rec.id}`}
                    className="inline-flex items-center gap-2 text-xs font-bold text-accent-gold hover:text-[#b38e4b] transition-colors pt-2 uppercase"
                  >
                    Xem chi tiết &rarr;
                  </Link>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}
