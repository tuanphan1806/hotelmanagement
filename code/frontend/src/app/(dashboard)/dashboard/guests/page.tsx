"use client";

import React, { useState, useMemo } from "react";

interface GuestItem {
  id: string;
  name: string;
  initials: string;
  countryCode: string;
  countryName: string;
  email: string;
  phone: string;
  isVip: boolean;
  isNew: boolean;
  lastStayStart: string;
  lastStayEnd: string;
  visits: number;
  totalSpent: number;
}

export default function GuestsManagement() {
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedFilter, setSelectedFilter] = useState<"All" | "VIP" | "Regular" | "New">("All");

  const guests: GuestItem[] = [
    {
      id: "G001",
      name: "Victoria Ashworth",
      initials: "VA",
      countryCode: "GB",
      countryName: "British",
      email: "v.ashworth@email.com",
      phone: "+44 20 7946 0958",
      isVip: true,
      isNew: false,
      lastStayStart: "Jun 8",
      lastStayEnd: "Jun 20, 2026",
      visits: 14,
      totalSpent: 62400
    },
    {
      id: "G002",
      name: "Emma Dubois",
      initials: "ED",
      countryCode: "FR",
      countryName: "French",
      email: "emma.d@email.fr",
      phone: "+33 6 12 34 56 78",
      isVip: true,
      isNew: false,
      lastStayStart: "Jun 12",
      lastStayEnd: "Jun 16, 2026",
      visits: 7,
      totalSpent: 18200
    },
    {
      id: "G003",
      name: "Marco Rossi",
      initials: "MR",
      countryCode: "IT",
      countryName: "Italian",
      email: "m.rossi@email.it",
      phone: "+39 02 1234 5678",
      isVip: false,
      isNew: false,
      lastStayStart: "Jun 14",
      lastStayEnd: "Jun 18, 2026",
      visits: 5,
      totalSpent: 5400
    },
    {
      id: "G004",
      name: "Sophie Laurent",
      initials: "SL",
      countryCode: "FR",
      countryName: "French",
      email: "slaurent@mail.com",
      phone: "+33 6 98 76 54 32",
      isVip: false,
      isNew: false,
      lastStayStart: "Jun 10",
      lastStayEnd: "Jun 15, 2026",
      visits: 9,
      totalSpent: 10800
    },
    {
      id: "G005",
      name: "James Smith",
      initials: "JS",
      countryCode: "US",
      countryName: "American",
      email: "james.smith@email.com",
      phone: "+1 555 019 2834",
      isVip: false,
      isNew: true,
      lastStayStart: "Jun 15",
      lastStayEnd: "Jun 19, 2026",
      visits: 1,
      totalSpent: 920
    },
    {
      id: "G006",
      name: "Amélie Dupont",
      initials: "AD",
      countryCode: "FR",
      countryName: "French",
      email: "a.dupont@email.fr",
      phone: "+33 6 45 67 89 01",
      isVip: false,
      isNew: true,
      lastStayStart: "Jun 18",
      lastStayEnd: "Jun 20, 2026",
      visits: 1,
      totalSpent: 200
    },
    {
      id: "G007",
      name: "John Doe",
      initials: "JD",
      countryCode: "US",
      countryName: "American",
      email: "john.doe@email.com",
      phone: "+1 555 019 9876",
      isVip: false,
      isNew: false,
      lastStayStart: "May 10",
      lastStayEnd: "May 12, 2026",
      visits: 2,
      totalSpent: 300
    },
    {
      id: "G008",
      name: "Olivia Wilson",
      initials: "OW",
      countryCode: "GB",
      countryName: "British",
      email: "olivia.w@email.co.uk",
      phone: "+44 20 7946 0123",
      isVip: true,
      isNew: false,
      lastStayStart: "May 25",
      lastStayEnd: "May 30, 2026",
      visits: 6,
      totalSpent: 12400
    }
  ];

  // Metrics
  const totalCount = guests.length;
  const vipCount = guests.filter(g => g.isVip).length;
  const regularCount = guests.filter(g => !g.isVip && !g.isNew).length;
  const newCount = guests.filter(g => g.isNew).length;

  const filteredGuests = useMemo(() => {
    return guests.filter(g => {
      const matchesSearch = 
        g.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        g.email.toLowerCase().includes(searchQuery.toLowerCase()) ||
        g.phone.includes(searchQuery) ||
        g.countryName.toLowerCase().includes(searchQuery.toLowerCase());
      
      let matchesFilter = true;
      if (selectedFilter === "VIP") matchesFilter = g.isVip;
      if (selectedFilter === "Regular") matchesFilter = !g.isVip && !g.isNew;
      if (selectedFilter === "New") matchesFilter = g.isNew;

      return matchesSearch && matchesFilter;
    });
  }, [searchQuery, selectedFilter]);

  return (
    <div className="p-6 md:p-10 space-y-10 max-w-[1600px] mx-auto w-full">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 pb-4 border-b border-[#1C1613]/5">
        <div>
          <h1 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-[#1C1613] leading-tight">Guests</h1>
          <p className="text-xs text-[#766E65] mt-1.5 font-bold uppercase tracking-wider">8 guests • 2 VIP members</p>
        </div>
        <button className="self-start sm:self-auto px-5 py-2.5 bg-[#C5A86E] hover:bg-[#b09359] text-[#1C1613] font-semibold text-sm rounded-lg shadow-sm transition-all duration-300 flex items-center gap-2">
          <span>+</span> Add Guest
        </button>
      </div>

      {/* KPI stats Grid - Styled with consistent warm ivory containers */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
        {/* Total Guests */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
                <circle cx="9" cy="7" r="4" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{totalCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Total Guests</span>
            </div>
          </div>
        </div>

        {/* VIP Members */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{vipCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">VIP Members</span>
            </div>
          </div>
        </div>

        {/* Regular */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
                <circle cx="9" cy="7" r="4" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{regularCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">Regular</span>
            </div>
          </div>
        </div>

        {/* New Arrivals */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="p-3 bg-[#FAF5EC] text-[#C5A86E] rounded-lg">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
                <path d="M12 5v14" />
                <path d="M5 12h14" />
              </svg>
            </div>
            <div>
              <span className="block text-2xl font-serif font-bold text-[#1C1613]">{newCount}</span>
              <span className="block text-[9px] tracking-wider uppercase font-bold text-[#766E65] mt-1">New Arrivals</span>
            </div>
          </div>
        </div>
      </div>

      {/* Filter and Cards Section */}
      <div className="space-y-6">
        
        {/* Filter & Search Bar */}
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          
          {/* Search Input */}
          <div className="relative w-full sm:w-80">
            <input
              type="text"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              placeholder="Search by name, email..."
              className="w-full pl-9 pr-4 py-2 bg-white border border-[#1C1613]/10 focus:border-[#C5A86E] focus:outline-none rounded-xl text-sm"
            />
            <span className="absolute left-3.5 top-2.5 text-[#766E65] text-xs">🔍</span>
          </div>

          {/* Filter Pills */}
          <div className="flex flex-wrap gap-1.5 self-start sm:self-auto">
            {(["All", "VIP", "Regular", "New"] as const).map((filter) => (
              <button
                key={filter}
                onClick={() => setSelectedFilter(filter)}
                className={`px-4 py-1.5 text-xs font-bold rounded-xl transition-all duration-300 ${
                  selectedFilter === filter
                    ? "bg-[#C5A86E] text-[#1C1613]"
                    : "bg-white text-[#766E65] hover:bg-[#FAF9F6] border border-[#1C1613]/10"
                }`}
              >
                {filter === "All" ? "All Guests" : filter === "Regular" ? "REGULAR" : filter}
              </button>
            ))}
          </div>
        </div>

        {/* Cards Grid */}
        {filteredGuests.length === 0 ? (
          <div className="bg-white text-center py-12 border-2 border-dashed border-[#1C1613]/10 rounded-xl text-[#766E65] font-semibold text-sm">
            No guests found matching filters.
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {filteredGuests.map((guest) => (
              <div key={guest.id} className="bg-white border border-[#1C1613]/10 rounded-xl p-6 shadow-sm flex flex-col justify-between hover:border-[#C5A86E]/40 transition-all duration-300">
                
                {/* Header Information */}
                <div>
                  <div className="flex justify-between items-start">
                    <div className="flex items-center gap-3">
                      <div className="w-10 h-10 rounded-full bg-[#FAF5EC] border border-[#F2ECE0] text-[#C5A86E] flex items-center justify-center font-serif font-bold text-sm">
                        {guest.initials}
                      </div>
                      <div>
                        <h4 className="font-serif text-base font-bold text-[#1C1613]">{guest.name}</h4>
                        <span className="text-[10px] text-[#766E65] font-bold uppercase tracking-wider">
                          {guest.countryCode} {guest.countryName}
                        </span>
                      </div>
                    </div>

                    {/* Refactored Badges matching the brand palette */}
                    {guest.isVip ? (
                      <span className="inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded text-[10px] font-bold bg-[#FAF5EC] text-[#C5A86E] border border-[#F2ECE0]">
                        ★ VIP
                      </span>
                    ) : guest.isNew ? (
                      <span className="inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded text-[10px] font-bold bg-[#5C7C64]/5 text-[#5C7C64] border border-[#5C7C64]/20">
                        NEW
                      </span>
                    ) : (
                      <span className="inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded text-[10px] font-bold bg-[#4A607A]/5 text-[#4A607A] border border-[#4A607A]/20">
                        REGULAR
                      </span>
                    )}
                  </div>

                  {/* Contacts Info */}
                  <div className="mt-5 space-y-1.5 text-xs text-[#766E65] font-medium">
                    <div className="flex items-center gap-2">
                      <span className="text-[10px]">✉</span>
                      <span>{guest.email}</span>
                    </div>
                    {guest.phone && (
                      <div className="flex items-center gap-2">
                        <span className="text-[10px]">📞</span>
                        <span>{guest.phone}</span>
                      </div>
                    )}
                  </div>

                  {/* Last Stay block */}
                  <div className="mt-5 px-4 py-3 bg-[#FAF9F6] border border-[#1C1613]/10 rounded-lg flex flex-col gap-0.5 text-xs">
                    <span className="text-[9px] tracking-wider uppercase font-bold text-[#766E65]">Last Stay</span>
                    <span className="font-semibold text-[#1C1613]">{guest.lastStayStart} – {guest.lastStayEnd}</span>
                  </div>
                </div>

                {/* Footer Metrics (Visits / Spend) */}
                <div className="mt-6 pt-4 border-t border-[#1C1613]/5 grid grid-cols-2 text-center divide-x divide-[#1C1613]/5">
                  <div className="flex flex-col gap-0.5">
                    <span className="text-lg font-serif font-bold text-[#1C1613]">{guest.visits}</span>
                    <span className="text-[9px] tracking-wider uppercase font-bold text-[#766E65]">Visits</span>
                  </div>
                  <div className="flex flex-col gap-0.5">
                    <span className="text-lg font-serif font-bold text-[#C5A86E]">
                      ${(guest.totalSpent / 1000).toFixed(1)}k
                    </span>
                    <span className="text-[9px] tracking-wider uppercase font-bold text-[#766E65]">Total Spent</span>
                  </div>
                </div>

              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
