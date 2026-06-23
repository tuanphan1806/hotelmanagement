"use client";

import React, { useState, useEffect } from "react";
import Link from "next/link";
import { DASHBOARD_CONTENT } from "@/constants/content";
import { BRAND_NAME } from "@/constants/auth";

export default function DashboardPage() {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<{name: string, email: string, role: string} | null>(null);

  useEffect(() => {
    // Simulate fetching user data
    const timer = setTimeout(() => {
      setUser({
        name: "Admin User",
        email: "manager@lumierpalace.com",
        role: "ADMIN"
      });
      setIsLoading(false);
    }, 1000);

    return () => clearTimeout(timer);
  }, []);

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-bg-light">
        <div className="flex flex-col items-center gap-4">
          <div className="w-12 h-12 border-4 border-primary-navy border-t-accent-gold rounded-full animate-spin"></div>
          <p className="text-text-light font-medium">{DASHBOARD_CONTENT.loading}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-bg-light flex">
      {/* Sidebar */}
      <aside className="w-64 bg-primary-navy text-white flex flex-col hidden md:flex">
        <div className="p-6 border-b border-white/10">
          <h1 className="text-xl font-bold tracking-tight text-accent-gold">{BRAND_NAME}</h1>
          <p className="text-xs text-white/50 uppercase tracking-widest mt-1">Portal</p>
        </div>
        
        <nav className="flex-1 p-4 space-y-2">
          <Link href="/dashboard" className="flex items-center gap-3 px-4 py-3 bg-white/10 text-white rounded-md transition-colors">
            <span>📊</span>
            <span className="font-medium">{DASHBOARD_CONTENT.dashboard}</span>
          </Link>
          <Link href="#" className="flex items-center gap-3 px-4 py-3 text-white/70 hover:bg-white/5 hover:text-white rounded-md transition-colors">
            <span>🏨</span>
            <span className="font-medium">Rooms</span>
          </Link>
          <Link href="#" className="flex items-center gap-3 px-4 py-3 text-white/70 hover:bg-white/5 hover:text-white rounded-md transition-colors">
            <span>📅</span>
            <span className="font-medium">Bookings</span>
          </Link>
          <Link href="#" className="flex items-center gap-3 px-4 py-3 text-white/70 hover:bg-white/5 hover:text-white rounded-md transition-colors">
            <span>👥</span>
            <span className="font-medium">Guests</span>
          </Link>
        </nav>

        <div className="p-4 border-t border-white/10">
          <button className="flex items-center gap-3 px-4 py-2 w-full text-left text-white/70 hover:text-white transition-colors">
            <span>🚪</span>
            <span className="font-medium">{DASHBOARD_CONTENT.logout}</span>
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 flex flex-col min-h-screen overflow-hidden">
        {/* Header */}
        <header className="h-20 bg-white border-b border-border-light flex items-center justify-between px-8">
          <h2 className="text-2xl font-bold text-text-dark">{DASHBOARD_CONTENT.dashboard}</h2>
          
          <div className="flex items-center gap-4">
            <div className="text-right hidden sm:block">
              <p className="text-sm font-semibold text-text-dark">{user?.name}</p>
              <p className="text-xs text-text-light">{user?.role}</p>
            </div>
            <div className="w-10 h-10 rounded-full bg-accent-gold text-white flex items-center justify-center font-bold text-lg shadow-sm">
              {user?.name.charAt(0)}
            </div>
          </div>
        </header>

        {/* Dashboard Content */}
        <div className="flex-1 overflow-auto p-8">
          <div className="max-w-4xl mx-auto">
            
            <div className="mb-8">
              <h3 className="text-2xl font-semibold text-primary-navy">
                {DASHBOARD_CONTENT.greeting} {user?.name} 👋
              </h3>
              <p className="text-text-light mt-1">Here is what's happening at your property today.</p>
            </div>

            {/* Stats Cards */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
              <div className="bg-white p-6 rounded-xl shadow-sm border border-border-light">
                <div className="text-text-light text-sm font-medium mb-2">Today's Check-ins</div>
                <div className="text-3xl font-bold text-primary-navy">12</div>
                <div className="text-xs text-green-500 font-medium mt-2">↑ 4 from yesterday</div>
              </div>
              <div className="bg-white p-6 rounded-xl shadow-sm border border-border-light">
                <div className="text-text-light text-sm font-medium mb-2">Today's Check-outs</div>
                <div className="text-3xl font-bold text-primary-navy">8</div>
                <div className="text-xs text-text-light font-medium mt-2">2 pending</div>
              </div>
              <div className="bg-white p-6 rounded-xl shadow-sm border border-border-light">
                <div className="text-text-light text-sm font-medium mb-2">Occupancy Rate</div>
                <div className="text-3xl font-bold text-primary-navy">85%</div>
                <div className="text-xs text-green-500 font-medium mt-2">↑ 2% this week</div>
              </div>
            </div>

            {/* User Info Card */}
            <div className="bg-white rounded-xl shadow-sm border border-border-light overflow-hidden">
              <div className="px-6 py-4 border-b border-border-light bg-gray-50/50">
                <h4 className="font-semibold text-text-dark">{DASHBOARD_CONTENT.userInfo}</h4>
              </div>
              <div className="p-6">
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                  <div>
                    <p className="text-sm text-text-light mb-1">{DASHBOARD_CONTENT.labelName}</p>
                    <p className="font-medium text-text-dark">{user?.name}</p>
                  </div>
                  <div>
                    <p className="text-sm text-text-light mb-1">{DASHBOARD_CONTENT.labelEmail}</p>
                    <p className="font-medium text-text-dark">{user?.email}</p>
                  </div>
                  <div>
                    <p className="text-sm text-text-light mb-1">{DASHBOARD_CONTENT.labelRole}</p>
                    <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-primary-navy text-white">
                      {user?.role}
                    </span>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </main>
    </div>
  );
}
