"use client";

import React, { useState, useMemo } from "react";

interface ReservationItem {
  id: string;
  guestName: string;
  guestInitial: string;
  roomNumber: string;
  roomType: string;
  checkIn: string;
  checkOut: string;
  nights: number;
  total: number;
  source: "Direct" | "Booking.com" | "Expedia" | "Airbnb" | "Agoda";
  status: "Checked In" | "Confirmed" | "Pending" | "Cancelled";
}

export default function ReservationsManagement() {
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedStatus, setSelectedStatus] = useState<"All" | "Checked In" | "Confirmed" | "Pending" | "Cancelled">("All");
  const [sortBy, setSortBy] = useState<"checkIn" | "nights" | "total">("checkIn");

  const reservations: ReservationItem[] = [
    {
      id: "R001",
      guestName: "Victoria Ashworth",
      guestInitial: "VA",
      roomNumber: "#401",
      roomType: "Presidential Suite",
      checkIn: "Jun 8",
      checkOut: "Jun 20",
      nights: 12,
      total: 14400,
      source: "Direct",
      status: "Checked In"
    },
    {
      id: "R002",
      guestName: "Emma Dubois",
      guestInitial: "ED",
      roomNumber: "#301",
      roomType: "Deluxe King",
      checkIn: "Jun 12",
      checkOut: "Jun 16",
      nights: 4,
      total: 1120,
      source: "Booking.com",
      status: "Checked In"
    },
    {
      id: "R003",
      guestName: "Marco Rossi",
      guestInitial: "MR",
      roomNumber: "#201",
      roomType: "Superior Twin",
      checkIn: "Jun 14",
      checkOut: "Jun 18",
      nights: 4,
      total: 880,
      source: "Expedia",
      status: "Confirmed"
    },
    {
      id: "R004",
      guestName: "Sophie Laurent",
      guestInitial: "SL",
      roomNumber: "#102",
      roomType: "Deluxe King",
      checkIn: "Jun 10",
      checkOut: "Jun 15",
      nights: 5,
      total: 1300,
      source: "Direct",
      status: "Checked In"
    },
    {
      id: "R005",
      guestName: "James Smith",
      guestInitial: "JS",
      roomNumber: "#302",
      roomType: "Superior Twin",
      checkIn: "Jun 15",
      checkOut: "Jun 19",
      nights: 4,
      total: 920,
      source: "Airbnb",
      status: "Confirmed"
    },
    {
      id: "R006",
      guestName: "Amélie Dupont",
      guestInitial: "AD",
      roomNumber: "#103",
      roomType: "Standard Queen",
      checkIn: "Jun 18",
      checkOut: "Jun 20",
      nights: 2,
      total: 200,
      source: "Agoda",
      status: "Pending"
    },
    {
      id: "R007",
      guestName: "John Doe",
      guestInitial: "JD",
      roomNumber: "#101",
      roomType: "Standard Queen",
      checkIn: "Jun 22",
      checkOut: "Jun 25",
      nights: 3,
      total: 300,
      source: "Booking.com",
      status: "Pending"
    },
    {
      id: "R008",
      guestName: "Olivia Wilson",
      guestInitial: "OW",
      roomNumber: "#402",
      roomType: "Executive Suite",
      checkIn: "Jun 25",
      checkOut: "Jun 30",
      nights: 5,
      total: 2100,
      source: "Direct",
      status: "Confirmed"
    },
    {
      id: "R009",
      guestName: "Lucas Martin",
      guestInitial: "LM",
      roomNumber: "#203",
      roomType: "Deluxe Queen",
      checkIn: "Jun 5",
      checkOut: "Jun 7",
      nights: 2,
      total: 300,
      source: "Expedia",
      status: "Cancelled"
    }
  ];

  // Stats calculation
  const totalReservations = reservations.length;
  const checkedInCount = reservations.filter(r => r.status === "Checked In").length;
  const confirmedCount = reservations.filter(r => r.status === "Confirmed").length;
  const pendingCount = reservations.filter(r => r.status === "Pending").length;

  const filteredReservations = useMemo(() => {
    let result = reservations.filter(r => {
      const matchesSearch = 
        r.id.toLowerCase().includes(searchQuery.toLowerCase()) ||
        r.guestName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        r.roomNumber.includes(searchQuery) ||
        r.roomType.toLowerCase().includes(searchQuery.toLowerCase());
      
      const matchesStatus = selectedStatus === "All" || r.status === selectedStatus;

      return matchesSearch && matchesStatus;
    });

    // Sorting
    result.sort((a, b) => {
      if (sortBy === "nights") return b.nights - a.nights;
      if (sortBy === "total") return b.total - a.total;
      
      // Default date sort
      const dateA = new Date(`${a.checkIn}, 2026`).getTime();
      const dateB = new Date(`${b.checkIn}, 2026`).getTime();
      return dateA - dateB;
    });

    return result;
  }, [searchQuery, selectedStatus, sortBy]);

  // Refactored status styling: matches Rooms & Overview pages
  const getStatusBadgeStyle = (status: ReservationItem["status"]) => {
    switch (status) {
      case "Checked In":
        return "text-[#5C7C64] bg-[#5C7C64]/5 border border-[#5C7C64]/20";
      case "Confirmed":
        return "text-[#4A607A] bg-[#4A607A]/5 border border-[#4A607A]/20";
      case "Pending":
        return "text-[#C5A86E] bg-[#FAF5EC] border border-[#F2ECE0]";
      case "Cancelled":
        return "text-[#A66E6E] bg-[#A66E6E]/5 border border-[#A66E6E]/20";
    }
  };

  const getSourceBadgeStyle = (source: ReservationItem["source"]) => {
    switch (source) {
      case "Direct":
        return "bg-gray-100 text-gray-700";
      case "Booking.com":
        return "bg-blue-50 text-blue-700 border border-blue-100";
      case "Expedia":
        return "bg-[#FAF5EC] text-[#C5A86E] border border-[#F2ECE0]";
      case "Airbnb":
        return "bg-rose-50 text-rose-700 border border-rose-100";
      case "Agoda":
        return "bg-purple-50 text-purple-700 border border-purple-100";
    }
  };

  return (
    <div className="p-6 md:p-10 space-y-10 max-w-[1600px] mx-auto w-full">
      
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 pb-4 border-b border-[#1C1613]/5">
        <div>
          <h1 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-[#1C1613] leading-tight">Reservations</h1>
          <p className="text-xs text-[#766E65] mt-1.5 font-bold uppercase tracking-wider">9 reservations • 2 awaiting confirmation</p>
        </div>
        <button className="self-start sm:self-auto px-5 py-2.5 bg-[#C5A86E] hover:bg-[#b09359] text-[#1C1613] font-semibold text-sm rounded-lg shadow-sm transition-all duration-300 flex items-center gap-2">
          <span>+</span> New Reservation
        </button>
      </div>

      {/* KPI Stats Grid - Styled with consistent warm ivory containers */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
        
        {/* Total */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <rect x="3" y="4" width="18" height="18" rx="2" ry="2" />
                <line x1="16" y1="2" x2="16" y2="6" />
                <line x1="8" y1="2" x2="8" y2="6" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{totalReservations}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Total Bookings</span>
            </div>
          </div>
        </div>

        {/* Checked In */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <polyline points="20 6 9 17 4 12" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{checkedInCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Checked In</span>
            </div>
          </div>
        </div>

        {/* Confirmed */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <circle cx="12" cy="12" r="10" />
                <polyline points="12 6 12 12 16 14" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{confirmedCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Confirmed</span>
            </div>
          </div>
        </div>

        {/* Pending */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <circle cx="12" cy="12" r="10" />
                <line x1="15" y1="9" x2="9" y2="15" />
                <line x1="9" y1="9" x2="15" y2="15" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{pendingCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Pending</span>
            </div>
          </div>
        </div>
      </div>

      {/* Main filter & table section */}
      <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm space-y-6">
        
        {/* Filter bar */}
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div className="flex flex-wrap items-center gap-3">
            
            {/* Search Input */}
            <div className="relative w-full sm:w-64">
              <input
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search guest, room, ID..."
                className="w-full pl-9 pr-4 py-2 border border-[#1C1613]/10 focus:border-[#C5A86E] focus:outline-none rounded-xl text-sm"
              />
              <span className="absolute left-3.5 top-2.5 text-[#766E65] text-xs">🔍</span>
            </div>

            {/* Status filters */}
            <div className="flex flex-wrap gap-1.5">
              {(["All", "Checked In", "Confirmed", "Pending", "Cancelled"] as const).map((status) => (
                <button
                  key={status}
                  onClick={() => setSelectedStatus(status)}
                  className={`px-3 py-1.5 text-xs font-bold rounded-xl transition-all duration-300 ${
                    selectedStatus === status
                      ? "bg-[#C5A86E] text-[#1C1613]"
                      : "bg-[#FAF9F6] text-[#766E65] hover:bg-[#FAF9F6]/80"
                  }`}
                >
                  {status}
                </button>
              ))}
            </div>
          </div>

          {/* Sort selector */}
          <div className="flex items-center gap-2">
            <span className="text-xs text-[#766E65] font-semibold">Sort by:</span>
            <select
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value as any)}
              className="px-3 py-2 bg-white border border-[#1C1613]/10 focus:border-[#C5A86E] focus:outline-none rounded-xl text-sm font-semibold text-[#1C1613]"
            >
              <option value="checkIn">Check-in Date</option>
              <option value="nights">Nights</option>
              <option value="total">Total Price</option>
            </select>
          </div>
        </div>

        {/* Table list */}
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-[#1C1613]/10 text-[11px] uppercase tracking-wider text-[#766E65] font-bold">
                <th className="py-3 px-4">ID</th>
                <th className="py-3 px-4">Guest</th>
                <th className="py-3 px-4">Room</th>
                <th className="py-3 px-4">Check-in</th>
                <th className="py-3 px-4">Check-out</th>
                <th className="py-3 px-4">Nights</th>
                <th className="py-3 px-4">Total</th>
                <th className="py-3 px-4">Source</th>
                <th className="py-3 px-4">Status</th>
                <th className="py-3 px-4 text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-[#1C1613]/5 text-sm font-semibold">
              {filteredReservations.map((item) => (
                <tr key={item.id} className="hover:bg-[#FAF9F6]/50">
                  {/* ID */}
                  <td className="py-3.5 px-4 text-xs font-bold text-[#766E65]">{item.id}</td>
                  
                  {/* Guest Info */}
                  <td className="py-3.5 px-4">
                    <div className="flex items-center gap-3">
                      <div className="w-8 h-8 rounded-full bg-[#FAF5EC] border border-[#F2ECE0] text-[#C5A86E] flex items-center justify-center font-serif font-bold text-xs">
                        {item.guestInitial}
                      </div>
                      <span className="font-semibold text-[#1C1613]">{item.guestName}</span>
                    </div>
                  </td>
                  
                  {/* Room */}
                  <td className="py-3.5 px-4">
                    <div className="flex flex-col">
                      <span className="font-bold text-[#1C1613]">{item.roomNumber}</span>
                      <span className="text-[10px] text-[#766E65]">{item.roomType}</span>
                    </div>
                  </td>
                  
                  {/* Check-in / Check-out */}
                  <td className="py-3.5 px-4 text-[#1C1613]">{item.checkIn}</td>
                  <td className="py-3.5 px-4 text-[#1C1613]">{item.checkOut}</td>
                  
                  {/* Nights */}
                  <td className="py-3.5 px-4 text-[#766E65]">{item.nights}</td>
                  
                  {/* Total */}
                  <td className="py-3.5 px-4 font-serif font-bold text-[#C5A86E]">
                    ${item.total.toLocaleString()}
                  </td>
                  
                  {/* Source */}
                  <td className="py-3.5 px-4">
                    <span className={`px-2 py-0.5 rounded text-[10px] font-bold border ${getSourceBadgeStyle(item.source)}`}>
                      {item.source}
                    </span>
                  </td>
                  
                  {/* Status */}
                  <td className="py-3.5 px-4">
                    <span className={`px-2.5 py-0.5 rounded text-xs font-bold border ${getStatusBadgeStyle(item.status)}`}>
                      {item.status}
                    </span>
                  </td>
                  
                  {/* Action */}
                  <td className="py-3.5 px-4 text-right">
                    <button className="text-[#C5A86E] hover:underline">Edit</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Footer info */}
        <div className="pt-2 border-t border-[#1C1613]/5 flex items-center justify-between text-xs text-[#766E65] font-semibold">
          <span>Showing {filteredReservations.length} of {totalReservations} reservations</span>
        </div>
      </div>
    </div>
  );
}
