"use client";

import React, { useState, useEffect } from "react";

export default function DashboardOverview() {
  // Sophisticated, muted editorial color palette data
  const metrics = [
    {
      id: "revenue",
      title: "Today's Revenue",
      value: "$4,820",
      change: "↗ 12.4%",
      isPositive: true,
      subText: "vs yesterday $4,287",
      iconBgClass: "bg-[#FAF5EC]",
      iconColorClass: "text-[#C5A86E]",
      trendBgClass: "bg-[#E6F4EA] border-[#E6F4EA]/20",
      trendColorClass: "text-[#137333]",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
          <line x1="12" y1="1" x2="12" y2="23" />
          <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" />
        </svg>
      )
    },
    {
      id: "occupancy",
      title: "Occupancy Rate",
      value: "83%",
      change: "↗ 5.2%",
      isPositive: true,
      subText: "10 of 12 rooms filled",
      iconBgClass: "bg-[#E8F0FE]",
      iconColorClass: "text-[#1A73E8]",
      trendBgClass: "bg-[#E6F4EA] border-[#E6F4EA]/20",
      trendColorClass: "text-[#137333]",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
          <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
          <polyline points="9 22 9 12 15 12 15 22" />
        </svg>
      )
    },
    {
      id: "reservations",
      title: "Reservations",
      value: "12",
      change: "↘ 2.1%",
      isPositive: false,
      subText: "3 arriving today",
      iconBgClass: "bg-[#E6F4EA]",
      iconColorClass: "text-[#137333]",
      trendBgClass: "bg-[#FCE8E6] border-[#FCE8E6]/20",
      trendColorClass: "text-[#C5221F]",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
          <rect x="3" y="4" width="18" height="18" rx="2" ry="2" />
          <line x1="16" y1="2" x2="16" y2="6" />
          <line x1="8" y1="2" x2="8" y2="6" />
          <line x1="3" y1="10" x2="21" y2="10" />
        </svg>
      )
    },
    {
      id: "guests",
      title: "Guests in House",
      value: "18",
      change: "↗ 8.7%",
      isPositive: true,
      subText: "Across all rooms",
      iconBgClass: "bg-[#F3E8FF]",
      iconColorClass: "text-[#A855F7]",
      trendBgClass: "bg-[#E6F4EA] border-[#E6F4EA]/20",
      trendColorClass: "text-[#137333]",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.75" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5">
          <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
          <circle cx="9" cy="7" r="4" />
          <path d="M23 21v-2a4 4 0 0 0-3-3.87" />
          <path d="M16 3.13a4 4 0 0 1 0 7.75" />
        </svg>
      )
    }
  ];

  // Refined, brand colors matching the screenshot
  const roomMix = [
    { name: "Standard", percentage: 35, count: 7, color: "bg-[#4285F4]", strokeColor: "#4285F4" },
    { name: "Deluxe", percentage: 30, count: 6, color: "bg-[#C5A86E]", strokeColor: "#C5A86E" },
    { name: "Junior Suite", percentage: 20, count: 4, color: "bg-[#34A853]", strokeColor: "#34A853" },
    { name: "Suite", percentage: 15, count: 3, color: "bg-[#EA4335]", strokeColor: "#EA4335" }
  ];

  const [userName, setUserName] = useState("Alexandre");

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      try {
        const parsed = JSON.parse(storedUser);
        const name = parsed.fullName || parsed.username || "Alexandre";
        setUserName(name.split(" ")[0]);
      } catch (e) {}
    }
  }, []);

  return (
    <div className="p-6 md:p-10 space-y-10 max-w-[1600px] mx-auto w-full">
      {/* Upper Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 pb-4 border-b border-[#1C1613]/5">
        <div>
          <h1 className="font-serif text-3xl md:text-4xl font-bold tracking-tight text-[#1C1613] leading-tight">
            Good morning, {userName}
          </h1>
          <p className="text-xs text-[#766E65] mt-1.5 flex items-center gap-1.5 font-semibold tracking-wider uppercase">
            Saturday, 13 June 2026 <span className="text-[#1C1613]/15">•</span> Here's what's happening today
          </p>
        </div>

        {/* Muted location badge */}
        <div className="self-start sm:self-auto flex items-center gap-2 px-4 py-2 bg-white rounded-lg border border-[#1C1613]/10 shadow-sm text-xs font-semibold text-[#766E65] tracking-wider uppercase">
          <span className="text-[#C5A86E] text-sm">★</span>
          <span>Lumière Palace Paris</span>
        </div>
      </div>

      {/* KPI Cards Grid - Refactored to look clean and custom */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
        {metrics.map((metric) => (
          <div key={metric.id} className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm relative overflow-hidden group hover:border-[#C5A86E]/40 transition-all duration-300">
            {/* Header Content */}
            <div className="flex items-center justify-between">
              {/* Elegant Gold icon with warm ivory container */}
              <div className={`p-2.5 rounded-lg ${metric.iconBgClass} ${metric.iconColorClass}`}>
                {metric.icon}
              </div>
              
              {/* Thin hairline Border Trend Indicator */}
              <span className={`inline-flex items-center px-2.5 py-0.5 rounded text-[10px] font-bold tracking-wider border ${metric.trendBgClass} ${metric.trendColorClass}`}>
                {metric.change}
              </span>
            </div>

            {/* Values */}
            <div className="mt-5 space-y-1">
              <span className="block text-[9px] tracking-[0.15em] text-[#766E65] uppercase font-bold">
                {metric.title}
              </span>
              <span className="block font-serif text-3xl font-bold text-[#1C1613] tracking-tight">
                {metric.value}
              </span>
            </div>

            {/* Compare line */}
            <div className="mt-4 pt-3 border-t border-[#1C1613]/5 flex items-center justify-between text-xs text-[#766E65] font-medium">
              <span>{metric.subText}</span>
              <span className="text-[#C5A86E] opacity-60 group-hover:opacity-100 group-hover:translate-x-0.5 transition-all duration-200">→</span>
            </div>
          </div>
        ))}
      </div>

      {/* Main Charts Area */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Revenue Overview Line Chart Card */}
        <div className="lg:col-span-2 bg-white p-6 md:p-8 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center justify-between mb-8 pb-4 border-b border-[#1C1613]/5">
            <div>
              <h3 className="font-serif text-lg font-bold text-[#1C1613]">Revenue Overview</h3>
              <p className="text-[10px] text-[#766E65] font-bold uppercase tracking-wider mt-0.5">January – June 2026</p>
            </div>
            
            <div className="flex items-center gap-4">
              <span className="px-2.5 py-1 text-[#5C7C64] bg-[#5C7C64]/5 border border-[#5C7C64]/20 rounded text-[10px] font-bold tracking-wider uppercase">
                +22.4% YTD
              </span>
            </div>
          </div>

          {/* SVG Custom Line Chart - Refactored to be ultra-clean */}
          <div className="relative w-full aspect-[2/1] min-h-[220px]">
            <svg viewBox="0 0 500 200" width="100%" height="100%" className="overflow-visible">
              <defs>
                <linearGradient id="chartGrad" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="0%" stopColor="#C5A86E" stopOpacity="0.12" />
                  <stop offset="100%" stopColor="#C5A86E" stopOpacity="0.0" />
                </linearGradient>
              </defs>

              {/* Ultra faint Horizontal Grid lines */}
              <line x1="30" y1="20" x2="480" y2="20" stroke="#1C1613" strokeOpacity="0.02" strokeDasharray="3 3" />
              <line x1="30" y1="60" x2="480" y2="60" stroke="#1C1613" strokeOpacity="0.02" strokeDasharray="3 3" />
              <line x1="30" y1="100" x2="480" y2="100" stroke="#1C1613" strokeOpacity="0.02" strokeDasharray="3 3" />
              <line x1="30" y1="140" x2="480" y2="140" stroke="#1C1613" strokeOpacity="0.02" strokeDasharray="3 3" />
              <line x1="30" y1="180" x2="480" y2="180" stroke="#1C1613" strokeOpacity="0.03" />

              {/* Y Axis Labels */}
              <text x="20" y="23" fill="#766E65" fontSize="8" fontWeight="700" textAnchor="end" letterSpacing="0.05em">$80K</text>
              <text x="20" y="63" fill="#766E65" fontSize="8" fontWeight="700" textAnchor="end" letterSpacing="0.05em">$60K</text>
              <text x="20" y="103" fill="#766E65" fontSize="8" fontWeight="700" textAnchor="end" letterSpacing="0.05em">$40K</text>
              <text x="20" y="143" fill="#766E65" fontSize="8" fontWeight="700" textAnchor="end" letterSpacing="0.05em">$20K</text>
              <text x="20" y="183" fill="#766E65" fontSize="8" fontWeight="700" textAnchor="end" letterSpacing="0.05em">$0K</text>

              {/* X Axis Labels */}
              <text x="50" y="196" fill="#766E65" fontSize="9" fontWeight="700" textAnchor="middle" letterSpacing="0.05em">JAN</text>
              <text x="130" y="196" fill="#766E65" fontSize="9" fontWeight="700" textAnchor="middle" letterSpacing="0.05em">FEB</text>
              <text x="210" y="196" fill="#766E65" fontSize="9" fontWeight="700" textAnchor="middle" letterSpacing="0.05em">MAR</text>
              <text x="290" y="196" fill="#766E65" fontSize="9" fontWeight="700" textAnchor="middle" letterSpacing="0.05em">APR</text>
              <text x="370" y="196" fill="#766E65" fontSize="9" fontWeight="700" textAnchor="middle" letterSpacing="0.05em">MAY</text>
              <text x="450" y="196" fill="#766E65" fontSize="9" fontWeight="700" textAnchor="middle" letterSpacing="0.05em">JUN</text>

              {/* Filled Area */}
              <path
                d="M 50 180 
                   L 50 120 
                   C 90 115, 90 112, 130 110 
                   C 170 108, 170 98, 210 95 
                   C 250 92, 250 108, 290 105 
                   C 330 102, 330 88, 370 85 
                   C 410 82, 410 72, 450 70 
                   L 450 180 Z"
                fill="url(#chartGrad)"
              />

              {/* Curve Line */}
              <path
                d="M 50 120 
                   C 90 115, 90 112, 130 110 
                   C 170 108, 170 98, 210 95 
                   C 250 92, 250 108, 290 105 
                   C 330 102, 330 88, 370 85 
                   C 410 82, 410 72, 450 70"
                fill="none"
                stroke="#C5A86E"
                strokeWidth="2.5"
                strokeLinecap="round"
              />

              {/* Finer data points */}
              <circle cx="50" cy="120" r="3.5" fill="#FFFFFF" stroke="#C5A86E" strokeWidth="2" />
              <circle cx="130" cy="110" r="3.5" fill="#FFFFFF" stroke="#C5A86E" strokeWidth="2" />
              <circle cx="210" cy="95" r="3.5" fill="#FFFFFF" stroke="#C5A86E" strokeWidth="2" />
              <circle cx="290" cy="105" r="3.5" fill="#FFFFFF" stroke="#C5A86E" strokeWidth="2" />
              <circle cx="370" cy="85" r="3.5" fill="#FFFFFF" stroke="#C5A86E" strokeWidth="2" />
              <circle cx="450" cy="70" r="3.5" fill="#FFFFFF" stroke="#C5A86E" strokeWidth="2" />
            </svg>
          </div>
        </div>

        {/* Room Mix Donut Chart Card */}
        <div className="bg-white p-6 md:p-8 rounded-xl border border-[#1C1613]/10 shadow-sm flex flex-col justify-between">
          <div className="pb-4 border-b border-[#1C1613]/5">
            <h3 className="font-serif text-lg font-bold text-[#1C1613]">Room Mix</h3>
            <p className="text-[10px] text-[#766E65] font-bold uppercase tracking-wider mt-0.5">Distribution by type</p>
          </div>

          {/* SVG Donut Chart - Refactored to have fine stroke (width="6") */}
          <div className="my-8 flex justify-center relative">
            <div className="w-36 h-36">
              <svg viewBox="0 0 100 100" className="w-full h-full transform -rotate-90">
                {/* Thin track circle */}
                <circle cx="50" cy="50" r="40" fill="transparent" stroke="#1C1613" strokeOpacity="0.02" strokeWidth="6" />
                
                {/* Standard (35%): Length = 87.96, Offset = 0 */}
                <circle
                  cx="50"
                  cy="50"
                  r="40"
                  fill="transparent"
                  stroke="#4285F4"
                  strokeWidth="6"
                  strokeDasharray="87.96 251.32"
                  strokeDashoffset="0"
                  strokeLinecap="round"
                />

                {/* Deluxe (30%): Length = 75.4, Offset = -87.96 */}
                <circle
                  cx="50"
                  cy="50"
                  r="40"
                  fill="transparent"
                  stroke="#C5A86E"
                  strokeWidth="6"
                  strokeDasharray="75.4 251.32"
                  strokeDashoffset="-87.96"
                  strokeLinecap="round"
                />

                {/* Junior Suite (20%): Length = 50.26, Offset = -163.36 */}
                <circle
                  cx="50"
                  cy="50"
                  r="40"
                  fill="transparent"
                  stroke="#34A853"
                  strokeWidth="6"
                  strokeDasharray="50.26 251.32"
                  strokeDashoffset="-163.36"
                  strokeLinecap="round"
                />

                {/* Suite (15%): Length = 37.7, Offset = -213.62 */}
                <circle
                  cx="50"
                  cy="50"
                  r="40"
                  fill="transparent"
                  stroke="#EA4335"
                  strokeWidth="6"
                  strokeDasharray="37.7 251.32"
                  strokeDashoffset="-213.62"
                  strokeLinecap="round"
                />
              </svg>
            </div>
            
            {/* Fine Center typography */}
            <div className="absolute inset-0 flex flex-col items-center justify-center pointer-events-none">
              <span className="font-serif text-2xl font-bold text-[#1C1613]">18</span>
              <span className="text-[8px] tracking-[0.12em] uppercase font-bold text-[#766E65]">Guests Total</span>
            </div>
          </div>

          {/* Legends and Metrics - Refactored colors */}
          <div className="space-y-2">
            {roomMix.map((item) => (
              <div key={item.name} className="flex items-center justify-between text-xs font-semibold">
                <div className="flex items-center gap-2">
                  <span className={`w-2.5 h-2.5 rounded-sm ${item.color}`} />
                  <span className="text-[#1C1613]">{item.name}</span>
                </div>
                <div className="flex items-center gap-3">
                  <span className="font-bold text-[#1C1613] w-8 text-right">{item.percentage}%</span>
                </div>
              </div>
            ))}
          </div>
        </div>

      </div>

      {/* Bottom Row - Weekly Occupancy & Recent Activity */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Weekly Occupancy Rate */}
        <div className="lg:col-span-2 bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center justify-between mb-6 pb-4 border-b border-[#1C1613]/5">
            <div>
              <h3 className="font-serif text-base font-bold text-[#1C1613]">Weekly Occupancy Rate</h3>
              <p className="text-[10px] text-[#766E65] font-bold uppercase tracking-wider mt-0.5">Average occupancy this week: 81.4%</p>
            </div>
            <span className="text-[10px] text-[#C5A86E] font-bold uppercase tracking-wider">This Week</span>
          </div>

          {/* Fine vertical bars with beige accent */}
          <div className="flex justify-between items-end h-44 pt-6 px-4">
            {[
              { day: "MON", rate: 75 },
              { day: "TUE", rate: 80 },
              { day: "WED", rate: 83 },
              { day: "THU", rate: 85 },
              { day: "FRI", rate: 90 },
              { day: "SAT", rate: 95 },
              { day: "SUN", rate: 62 }
            ].map((d) => (
              <div key={d.day} className="flex flex-col items-center gap-2 w-8 sm:w-12 group">
                <span className="opacity-0 group-hover:opacity-100 transition-opacity duration-200 bg-[#1C1613] text-white text-[9px] font-bold px-1.5 py-0.5 rounded shadow-sm">
                  {d.rate}%
                </span>
                
                {/* Thin, neat bar */}
                <div className="w-4 bg-[#FAF9F6] border border-[#1C1613]/10 rounded-sm h-28 flex items-end overflow-hidden">
                  <div
                    style={{ height: `${d.rate}%` }}
                    className="w-full bg-[#C5A86E] rounded-b-sm group-hover:bg-[#b09359] transition-all duration-300"
                  />
                </div>
                
                <span className="text-[9px] font-bold tracking-wider text-[#766E65] mt-1">
                  {d.day}
                </span>
              </div>
            ))}
          </div>
        </div>

        {/* Recent Activity Log - Clean text styles */}
        <div className="bg-white p-6 rounded-xl border border-[#1C1613]/10 shadow-sm">
          <div className="flex items-center justify-between mb-6 pb-4 border-b border-[#1C1613]/5">
            <h3 className="font-serif text-base font-bold text-[#1C1613]">Recent Activity</h3>
            <span className="text-[10px] text-[#766E65] font-bold uppercase tracking-wider hover:text-[#C5A86E] cursor-pointer transition-colors duration-200">View All</span>
          </div>

          <div className="space-y-4">
            {[
              {
                id: 1,
                time: "09:30 AM",
                title: "Checked In",
                desc: "Room 104 • Standard • John Doe",
                icon: (
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-3.5 h-3.5 text-[#5C7C64]">
                    <polyline points="20 6 9 17 4 12" />
                  </svg>
                ),
                color: "bg-[#5C7C64]/5 border-[#5C7C64]/20"
              },
              {
                id: 2,
                time: "08:15 AM",
                title: "Checked Out",
                desc: "Room 205 • Deluxe • Sarah Jenkins",
                icon: (
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-3.5 h-3.5 text-[#4A607A]">
                    <polyline points="16 17 21 12 16 7" />
                    <line x1="21" y1="12" x2="9" y2="12" />
                  </svg>
                ),
                color: "bg-[#4A607A]/5 border-[#4A607A]/20"
              },
              {
                id: 3,
                time: "Yesterday",
                title: "New Booking",
                desc: "Suite • 3 Nights • Emma Stone",
                icon: (
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-3.5 h-3.5 text-[#C5A86E]">
                    <line x1="12" y1="5" x2="12" y2="19" />
                    <line x1="5" y1="12" x2="19" y2="12" />
                  </svg>
                ),
                color: "bg-[#FAF5EC] border-[#F2ECE0]"
              },
              {
                id: 4,
                time: "Yesterday",
                title: "Service Request",
                desc: "Room 102 • Extra Towels Delivered",
                icon: (
                  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-3.5 h-3.5 text-purple-600">
                    <circle cx="12" cy="12" r="3" />
                    <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 1 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 1 1-2.83-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 1 1 2.83-2.83l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 1 1 2.83 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z" />
                  </svg>
                ),
                color: "bg-purple-50/50 border-purple-100"
              }
            ].map((activity) => (
              <div key={activity.id} className="flex gap-4 items-start group">
                <div className={`w-7 h-7 rounded-lg border flex items-center justify-center shrink-0 ${activity.color}`}>
                  {activity.icon}
                </div>
                <div className="min-w-0 flex-1">
                  <div className="flex items-center justify-between">
                    <span className="text-xs font-bold text-[#1C1613]">{activity.title}</span>
                    <span className="text-[9px] font-bold text-[#766E65] uppercase tracking-wider">{activity.time}</span>
                  </div>
                  <p className="text-xs text-[#766E65] font-semibold mt-0.5 truncate">{activity.desc}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

      </div>
    </div>
  );
}
