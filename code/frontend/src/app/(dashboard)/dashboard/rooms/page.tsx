"use client";

import React, { useState, useMemo } from "react";

interface RoomItem {
  id: string;
  number: string;
  type: "Standard" | "Deluxe" | "Superior" | "Suite" | "Presidential Suite";
  status: "Available" | "Occupied" | "Reserved" | "Maintenance";
  price: number;
  guest?: string;
  floor: number;
}

export default function RoomsManagement() {
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedStatus, setSelectedStatus] = useState<"All" | "Available" | "Occupied" | "Reserved" | "Maintenance">("All");
  const [selectedType, setSelectedType] = useState<"All" | "Standard" | "Deluxe" | "Superior" | "Suite" | "Presidential Suite">("All");
  const [viewMode, setViewMode] = useState<"grid" | "list">("grid");

  // Mock data of 12 rooms matching the total rooms statistic
  const initialRooms: RoomItem[] = [
    { id: "101", number: "101", type: "Standard", status: "Available", price: 100, floor: 1 },
    { id: "102", number: "102", type: "Deluxe", status: "Occupied", price: 260, guest: "Sophie Laurent", floor: 1 },
    { id: "103", number: "103", type: "Standard", status: "Available", price: 100, floor: 1 },
    { id: "201", number: "201", type: "Superior", status: "Reserved", price: 220, guest: "Marco Rossi", floor: 2 },
    { id: "202", number: "202", type: "Standard", status: "Available", price: 100, floor: 2 },
    { id: "203", number: "203", type: "Deluxe", status: "Maintenance", price: 150, floor: 2 },
    { id: "301", number: "301", type: "Deluxe", status: "Occupied", price: 280, guest: "Emma Dubois", floor: 3 },
    { id: "302", number: "302", type: "Superior", status: "Reserved", price: 230, guest: "James Smith", floor: 3 },
    { id: "303", number: "303", type: "Standard", status: "Available", price: 100, floor: 3 },
    { id: "401", number: "401", type: "Presidential Suite", status: "Occupied", price: 1200, guest: "Victoria Ashworth", floor: 4 },
    { id: "402", number: "402", type: "Suite", status: "Reserved", price: 420, floor: 4 },
    { id: "403", number: "403", type: "Suite", status: "Maintenance", price: 420, floor: 4 },
  ];

  // Metrics
  const totalCount = initialRooms.length;
  const availableCount = initialRooms.filter(r => r.status === "Available").length;
  const occupiedCount = initialRooms.filter(r => r.status === "Occupied" || r.status === "Reserved").length;
  const maintenanceCount = initialRooms.filter(r => r.status === "Maintenance").length;

  const filteredRooms = useMemo(() => {
    return initialRooms.filter(room => {
      const matchesSearch = 
        room.number.includes(searchQuery) || 
        room.type.toLowerCase().includes(searchQuery.toLowerCase()) ||
        (room.guest && room.guest.toLowerCase().includes(searchQuery.toLowerCase()));
      
      const matchesStatus = selectedStatus === "All" || room.status === selectedStatus;
      const matchesType = selectedType === "All" || room.type === selectedType;

      return matchesSearch && matchesStatus && matchesType;
    });
  }, [searchQuery, selectedStatus, selectedType]);

  // Clean, muted status design tokens (Steel Blue, Muted Gold, Sage Green, Muted Rose)
  const getStatusStyle = (status: RoomItem["status"]) => {
    switch (status) {
      case "Available":
        return "text-[#5C7C64] bg-[#5C7C64]/5 border-[#5C7C64]/20";
      case "Occupied":
        return "text-[#4A607A] bg-[#4A607A]/5 border-[#4A607A]/20";
      case "Reserved":
        return "text-[#C5A86E] bg-[#FAF5EC] border-[#F2ECE0]";
      case "Maintenance":
        return "text-[#A66E6E] bg-[#A66E6E]/5 border-[#A66E6E]/20";
    }
  };

  const getStatusDotColor = (status: RoomItem["status"]) => {
    switch (status) {
      case "Available": return "bg-[#5C7C64]";
      case "Occupied": return "bg-[#4A607A]";
      case "Reserved": return "bg-[#C5A86E]";
      case "Maintenance": return "bg-[#A66E6E]";
    }
  };

  return (
    <div className="p-6 md:p-10 space-y-10 max-w-[1600px] mx-auto w-full">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 pb-4 border-b border-[#1C1613]/5">
        <div>
          <h1 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-[#1C1613] leading-tight">Rooms</h1>
          <p className="text-xs text-[#766E65] mt-1.5 font-bold uppercase tracking-wider">3 of 12 rooms occupied today</p>
        </div>
        <button className="self-start sm:self-auto px-5 py-2.5 bg-[#C5A86E] hover:bg-[#b09359] text-[#1C1613] font-semibold text-sm rounded-lg shadow-sm transition-all duration-300 flex items-center gap-2">
          <span>+</span> Add Room
        </button>
      </div>

      {/* Metrics Cards Grid - Clean, unified styling */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
        
        {/* Total Rooms */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{totalCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Total Rooms</span>
            </div>
          </div>
        </div>

        {/* Available */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
                <polyline points="22 4 12 14.01 9 11.01" />
              </svg>
            </div>
            <div>
              <div className="flex items-baseline gap-2">
                <span className="text-2xl font-serif font-bold text-[#1C1613]">{availableCount}</span>
                <span className="text-[10px] text-[#5C7C64] font-bold">33% VACANCY</span>
              </div>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Available</span>
            </div>
          </div>
        </div>

        {/* Occupied & Reserved */}
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
              <div className="flex items-baseline gap-2">
                <span className="text-2xl font-serif font-bold text-[#1C1613]">{occupiedCount}</span>
                <span className="text-[10px] text-[#C5A86E] font-bold">50% OCCUPANCY</span>
              </div>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Occupied & Reserved</span>
            </div>
          </div>
        </div>

        {/* Maintenance */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{maintenanceCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Under Service</span>
            </div>
          </div>
        </div>
      </div>

      {/* Charts & Revenue Breakdown Section */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Weekly Occupancy vertical bars */}
        <div className="lg:col-span-2 bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center justify-between mb-6 pb-4 border-b border-[#1C1613]/5">
            <div>
              <h3 className="font-serif text-lg font-bold text-[#1C1613]">Weekly Occupancy</h3>
              <p className="text-[10px] text-[#766E65] font-bold uppercase tracking-wider mt-0.5">June 9 – June 15, 2026</p>
            </div>
            <div className="flex items-center gap-4 text-xs font-semibold text-[#766E65]">
              <span className="flex items-center gap-1.5"><span className="w-2 h-2 rounded-sm bg-[#C5A86E]" /> Occupied</span>
              <span className="flex items-center gap-1.5"><span className="w-2 h-2 rounded-sm bg-[#F2ECE0]" /> Available</span>
            </div>
          </div>

          {/* Thin, neat vertical Bar Chart */}
          <div className="flex justify-between items-end h-44 pt-6 px-4">
            {[
              { day: "MON", occupied: 8, total: 12 },
              { day: "TUE", occupied: 9, total: 12 },
              { day: "WED", occupied: 7, total: 12 },
              { day: "THU", occupied: 10, total: 12 },
              { day: "FRI", occupied: 11, total: 12 },
              { day: "SAT", occupied: 12, total: 12 },
              { day: "SUN", occupied: 9, total: 12 }
            ].map((d) => {
              const occupiedPct = (d.occupied / d.total) * 100;
              return (
                <div key={d.day} className="flex flex-col items-center gap-2.5 w-8 sm:w-12 group">
                  <div className="w-4 bg-[#F2ECE0] rounded-sm h-28 flex flex-col justify-end overflow-hidden border border-[#1C1613]/5">
                    <div
                      style={{ height: `${occupiedPct}%` }}
                      className="w-full bg-[#C5A86E] transition-all duration-300"
                    />
                  </div>
                  <span className="text-[9px] font-bold tracking-wider text-[#766E65]">{d.day}</span>
                </div>
              );
            })}
          </div>
        </div>

        {/* Revenue progress bars */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm flex flex-col justify-between">
          <div className="pb-4 border-b border-[#1C1613]/5">
            <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65]">Revenue</span>
            <span className="block text-[10px] text-[#766E65] font-bold uppercase tracking-wider mt-1">Today's Revenue</span>
            <span className="block font-serif text-3xl font-bold text-[#1C1613] tracking-tight mt-1">$4,820</span>
            <span className="inline-flex items-center text-xs font-bold text-[#5C7C64] mt-1">
              ↗ +12.4% vs yesterday
            </span>
          </div>

          <div className="space-y-4 mt-6">
            {/* Standard Rooms */}
            <div className="space-y-1">
              <div className="flex justify-between text-xs font-semibold">
                <span className="text-[#766E65]">Standard Rooms</span>
                <span className="text-[#1C1613]">$1,260</span>
              </div>
              <div className="h-1.5 w-full bg-[#F2ECE0] rounded-full overflow-hidden">
                <div className="h-full bg-[#C5A86E] rounded-full" style={{ width: "26%" }} />
              </div>
            </div>

            {/* Deluxe & Superior */}
            <div className="space-y-1">
              <div className="flex justify-between text-xs font-semibold">
                <span className="text-[#766E65]">Deluxe & Superior</span>
                <span className="text-[#1C1613]">$1,880</span>
              </div>
              <div className="h-1.5 w-full bg-[#F2ECE0] rounded-full overflow-hidden">
                <div className="h-full bg-[#C5A86E] rounded-full" style={{ width: "39%" }} />
              </div>
            </div>

            {/* Suites */}
            <div className="space-y-1">
              <div className="flex justify-between text-xs font-semibold">
                <span className="text-[#766E65]">Suites</span>
                <span className="text-[#1C1613]">$1,680</span>
              </div>
              <div className="h-1.5 w-full bg-[#F2ECE0] rounded-full overflow-hidden">
                <div className="h-full bg-[#C5A86E] rounded-full" style={{ width: "35%" }} />
              </div>
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
                placeholder="Search room, type, guest..."
                className="w-full pl-9 pr-4 py-2 border border-[#1C1613]/10 focus:border-[#C5A86E] focus:outline-none rounded-xl text-sm"
              />
              <span className="absolute left-3.5 top-2.5 text-[#766E65] text-xs">🔍</span>
            </div>

            {/* Status filters */}
            <div className="flex flex-wrap gap-1.5">
              {(["All", "Available", "Occupied", "Reserved", "Maintenance"] as const).map((status) => (
                <button
                  key={status}
                  onClick={() => setSelectedStatus(status)}
                  className={`px-3 py-1.5 text-xs font-bold rounded-xl transition-all duration-300 ${
                    selectedStatus === status
                      ? "bg-[#C5A86E] text-[#1C1613]"
                      : "bg-[#FAF9F6] text-[#766E65] hover:bg-[#FAF9F6]/80"
                  }`}
                >
                  {status === "All" ? "All Rooms" : status}
                </button>
              ))}
            </div>
          </div>

          <div className="flex items-center gap-3">
            {/* Type selector */}
            <select
              value={selectedType}
              onChange={(e) => setSelectedType(e.target.value as any)}
              className="px-3.5 py-2 bg-white border border-[#1C1613]/10 focus:border-[#C5A86E] focus:outline-none rounded-xl text-sm font-semibold text-[#1C1613]"
            >
              <option value="All">All Types</option>
              <option value="Standard">Standard</option>
              <option value="Deluxe">Deluxe</option>
              <option value="Superior">Superior</option>
              <option value="Suite">Suite</option>
              <option value="Presidential Suite">Presidential Suite</option>
            </select>

            {/* Toggle view buttons */}
            <div className="flex bg-[#FAF9F6] p-1 rounded-xl border border-[#1C1613]/5">
              <button
                onClick={() => setViewMode("grid")}
                className={`p-1.5 rounded-lg transition-colors ${viewMode === "grid" ? "bg-white text-[#C5A86E] shadow-sm" : "text-[#766E65] hover:text-[#1C1613]"}`}
              >
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="w-4 h-4">
                  <rect x="3" y="3" width="7" height="7" />
                  <rect x="14" y="3" width="7" height="7" />
                  <rect x="14" y="14" width="7" height="7" />
                  <rect x="3" y="14" width="7" height="7" />
                </svg>
              </button>
              <button
                onClick={() => setViewMode("list")}
                className={`p-1.5 rounded-lg transition-colors ${viewMode === "list" ? "bg-white text-[#C5A86E] shadow-sm" : "text-[#766E65] hover:text-[#1C1613]"}`}
              >
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="w-4 h-4">
                  <line x1="3" y1="12" x2="21" y2="12" />
                  <line x1="3" y1="6" x2="21" y2="6" />
                  <line x1="3" y1="18" x2="21" y2="18" />
                </svg>
              </button>
            </div>
          </div>
        </div>

        {/* Room items render */}
        {filteredRooms.length === 0 ? (
          <div className="text-center py-12 border-2 border-dashed border-[#1C1613]/10 rounded-xl text-[#766E65] font-semibold text-sm">
            No rooms found matching filters.
          </div>
        ) : viewMode === "grid" ? (
          /* Grid View */
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {filteredRooms.map((room) => (
              <div key={room.id} className="border border-[#1C1613]/10 rounded-xl p-5 hover:border-[#C5A86E]/40 transition-all bg-white flex flex-col justify-between">
                <div>
                  <div className="flex justify-between items-start">
                    <span className="font-serif text-lg font-bold text-[#1C1613]">Room {room.number}</span>
                    <span className={`px-2.5 py-0.5 text-[10px] font-bold border rounded ${getStatusStyle(room.status)}`}>
                      {room.status}
                    </span>
                  </div>
                  <span className="block text-[11px] font-bold text-[#766E65] mt-1.5 uppercase tracking-wide">
                    {room.type}
                  </span>
                  {room.guest && (
                    <div className="mt-3 flex items-center gap-2">
                      <span className="text-[11px] text-[#766E65] font-semibold">Guest:</span>
                      <span className="text-[11px] font-bold text-[#1C1613]">{room.guest}</span>
                    </div>
                  )}
                </div>

                <div className="mt-4 pt-3 border-t border-[#1C1613]/5 flex justify-between items-center text-xs font-semibold">
                  <span className="text-[#766E65] font-medium">${room.price} / night</span>
                  <span className="text-[#C5A86E] hover:underline cursor-pointer">Manage</span>
                </div>
              </div>
            ))}
          </div>
        ) : (
          /* List View */
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-[#1C1613]/10 text-[11px] uppercase tracking-wider text-[#766E65] font-bold">
                  <th className="py-3 px-4">Room No</th>
                  <th className="py-3 px-4">Type</th>
                  <th className="py-3 px-4">Floor</th>
                  <th className="py-3 px-4">Status</th>
                  <th className="py-3 px-4">Current Guest</th>
                  <th className="py-3 px-4">Price</th>
                  <th className="py-3 px-4 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-[#1C1613]/5 text-sm font-semibold">
                {filteredRooms.map((room) => (
                  <tr key={room.id} className="hover:bg-[#FAF9F6]/50">
                    <td className="py-3.5 px-4 font-serif font-bold text-[#1C1613]">{room.number}</td>
                    <td className="py-3.5 px-4 text-[#766E65]">{room.type}</td>
                    <td className="py-3.5 px-4 text-[#766E65]">Floor {room.floor}</td>
                    <td className="py-3.5 px-4">
                      <span className="flex items-center gap-1.5">
                        <span className={`w-2 h-2 rounded-full ${getStatusDotColor(room.status)}`} />
                        <span className="text-xs font-bold text-[#1C1613]">{room.status}</span>
                      </span>
                    </td>
                    <td className="py-3.5 px-4 text-[#1C1613]">{room.guest || "—"}</td>
                    <td className="py-3.5 px-4 text-[#766E65]">${room.price}/night</td>
                    <td className="py-3.5 px-4 text-right">
                      <button className="text-[#C5A86E] hover:underline">Manage</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        <div className="pt-2 border-t border-[#1C1613]/5 flex items-center justify-between text-xs text-[#766E65] font-semibold">
          <span>Showing {filteredRooms.length} of {totalCount} rooms</span>
        </div>
      </div>
    </div>
  );
}
