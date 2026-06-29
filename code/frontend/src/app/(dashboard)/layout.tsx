"use client";

import React, { useState, useEffect } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { deleteCookie } from "@/lib/cookie-helper";

interface SidebarItemProps {
  href: string;
  icon: React.ReactNode;
  label: string;
  badge?: string;
  active: boolean;
  isCollapsed?: boolean;
}

const SidebarItem = ({ href, icon, label, badge, active, isCollapsed }: SidebarItemProps) => {
  return (
    <Link
      href={href}
      className={`group flex items-center ${isCollapsed ? "justify-center" : "justify-between"} px-4 py-3.5 rounded-lg transition-all duration-300 relative ${
        active
          ? "bg-[#2A221E] text-[#C5A86E] border-l-4 border-[#C5A86E] pl-3"
          : "text-[#FAF9F6]/75 hover:bg-[#2A221E]/50 hover:text-[#FAF9F6] border-l-4 border-transparent"
      }`}
    >
      <div className="flex items-center gap-3.5">
        <span className={`w-5 h-5 flex items-center justify-center transition-transform group-hover:scale-110 duration-300 ${active ? "text-[#C5A86E]" : "text-[#FAF9F6]/60 group-hover:text-[#FAF9F6]"}`}>
          {icon}
        </span>
        {!isCollapsed && <span className="font-medium text-sm tracking-wide transition-all duration-300">{label}</span>}
      </div>
      {badge && (
        isCollapsed ? (
          <span className="absolute top-1.5 right-1.5 flex items-center justify-center text-[9px] font-bold w-4.5 h-4.5 rounded-full bg-[#C5A86E] text-[#1C1613]">
            {badge}
          </span>
        ) : (
          <span className="flex items-center justify-center text-xs font-bold w-5 h-5 rounded-full bg-[#C5A86E] text-[#1C1613]">
            {badge}
          </span>
        )
      )}
    </Link>
  );
};

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const pathname = usePathname();
  const [isMobileOpen, setIsMobileOpen] = useState(false);
  const [isCollapsed, setIsCollapsed] = useState(false);
  const [user, setUser] = useState({ fullName: "Alexandre Martin", role: "General Manager", initials: "AM" });

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      try {
        const parsed = JSON.parse(storedUser);
        const name = parsed.fullName || parsed.username || "Alexandre Martin";
        const role = parsed.role || "General Manager";
        const initials = name.split(" ").map((n: string) => n[0]).join("").toUpperCase().slice(0, 2);
        setUser({ fullName: name, role, initials });
      } catch (e) {
        console.error("Failed to parse user data from localStorage", e);
      }
    }
  }, []);

  const handleSignOut = () => {
    deleteCookie('token');
    localStorage.removeItem('user');
  };

  const navigationItems = [
    {
      href: "/dashboard",
      label: "Overview",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-full h-full">
          <rect x="3" y="3" width="7" height="7" />
          <rect x="14" y="3" width="7" height="7" />
          <rect x="14" y="14" width="7" height="7" />
          <rect x="3" y="14" width="7" height="7" />
        </svg>
      ),
    },
    {
      href: "/dashboard/rooms",
      label: "Rooms",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-full h-full">
          <path d="M2 22v-3a2 2 0 0 1 2-2h16a2 2 0 0 1 2 2v3" />
          <path d="M4 17V7a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v10" />
          <path d="M8 9h8" />
          <path d="M8 13h8" />
        </svg>
      ),
    },
    {
      href: "/dashboard/reservations",
      label: "Reservations",
      badge: "12",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-full h-full">
          <rect x="3" y="4" width="18" height="18" rx="2" ry="2" />
          <line x1="16" y1="2" x2="16" y2="6" />
          <line x1="8" y1="2" x2="8" y2="6" />
          <line x1="3" y1="10" x2="21" y2="10" />
        </svg>
      ),
    },
    {
      href: "/dashboard/guests",
      label: "Guests",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-full h-full">
          <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
          <circle cx="9" cy="7" r="4" />
          <path d="M23 21v-2a4 4 0 0 0-3-3.87" />
          <path d="M16 3.13a4 4 0 0 1 0 7.75" />
        </svg>
      ),
    },
    {
      href: "/dashboard/settings",
      label: "Settings",
      icon: (
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-full h-full">
          <circle cx="12" cy="12" r="3" />
          <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 1 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 1 1-2.83-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 1 1 2.83-2.83l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 1 1 2.83 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z" />
        </svg>
      ),
    },
  ];

  const getSidebarContent = (collapsed: boolean) => (
    <div className="flex flex-col h-full bg-[#1C1613] text-[#FAF9F6]">
      {/* Brand Logo Header */}
      <div className={`p-6 flex items-center ${collapsed ? "justify-center" : "justify-between"} border-b border-[#faf9f6]/10 h-20`}>
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-full border border-[#C5A86E] flex items-center justify-center bg-transparent shrink-0">
            <span className="font-serif text-[#C5A86E] font-bold text-lg">L</span>
          </div>
          {!collapsed && (
            <div className="flex flex-col min-w-0 transition-opacity duration-300">
              <span className="font-serif text-base font-bold tracking-wider text-[#FAF9F6] whitespace-nowrap">Lumière Palace</span>
              <span className="text-[0.6rem] tracking-[0.25em] text-[#C5A86E] font-medium uppercase -mt-0.5 whitespace-nowrap">Hotel Management</span>
            </div>
          )}
        </div>
        {/* Toggle mobile sidebar */}
        <button
          onClick={() => setIsMobileOpen(false)}
          className="md:hidden text-[#FAF9F6]/75 hover:text-[#FAF9F6]"
        >
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-6 h-6">
            <line x1="18" y1="6" x2="6" y2="18" />
            <line x1="6" y1="6" x2="18" y2="18" />
          </svg>
        </button>
      </div>

      {/* Main Nav Links */}
      <nav className="flex-1 px-4 py-6 space-y-1.5 overflow-y-auto">
        {navigationItems.map((item) => (
          <SidebarItem
            key={item.href}
            href={item.href}
            label={item.label}
            icon={item.icon}
            badge={item.badge}
            active={pathname === item.href}
            isCollapsed={collapsed}
          />
        ))}
      </nav>

      {/* Sidebar Footer Operations */}
      <div className="px-4 py-4 border-t border-[#faf9f6]/10 space-y-1">
        <Link
          href="/dashboard/notifications"
          className={`flex items-center ${collapsed ? "justify-center" : "gap-3.5"} px-4 py-3 text-[#FAF9F6]/75 hover:bg-[#2A221E]/50 hover:text-[#FAF9F6] rounded-lg transition-colors duration-200`}
        >
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5 text-[#FAF9F6]/60 shrink-0">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
            <path d="M13.73 21a2 2 0 0 1-3.46 0" />
          </svg>
          {!collapsed && <span className="font-medium text-sm">Notifications</span>}
        </Link>
        <Link
          href="/login"
          onClick={handleSignOut}
          className={`flex items-center ${collapsed ? "justify-center" : "gap-3.5"} px-4 py-3 text-[#E87A7A] hover:bg-red-500/10 rounded-lg transition-colors duration-200`}
        >
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-5 h-5 shrink-0">
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
            <polyline points="16 17 21 12 16 7" />
            <line x1="21" y1="12" x2="9" y2="12" />
          </svg>
          {!collapsed && <span className="font-medium text-sm">Sign Out</span>}
        </Link>
      </div>

      {/* Profile Card */}
      <div className="p-4 border-t border-[#faf9f6]/10 bg-[#16110F]">
        <div className={`flex items-center ${collapsed ? "justify-center" : "gap-3"}`}>
          <div className="w-10 h-10 rounded-full bg-[#C5A86E] text-[#1C1613] flex items-center justify-center font-serif font-bold text-sm shadow-sm transition-transform hover:scale-105 duration-300 shrink-0">
            {user.initials}
          </div>
          {!collapsed && (
            <div className="flex flex-col min-w-0">
              <p className="text-sm font-semibold text-[#FAF9F6] truncate">{user.fullName}</p>
              <p className="text-xs text-[#766E65] font-medium truncate">{user.role}</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );

  return (
    <div className="min-h-screen bg-[#FAF9F6] flex font-sans text-[#1C1613]">
      {/* Mobile Drawer Overlay */}
      {isMobileOpen && (
        <div
          onClick={() => setIsMobileOpen(false)}
          className="fixed inset-0 z-40 bg-black/50 md:hidden backdrop-blur-sm transition-opacity duration-300"
        />
      )}

      {/* Desktop Sidebar (Collapsible) */}
      <aside className={`relative ${isCollapsed ? "w-20" : "w-72"} hidden md:block shrink-0 border-r border-[#1C1613]/5 z-30 h-screen sticky top-0 transition-all duration-300`}>
        {getSidebarContent(isCollapsed)}
        {/* Collapse Toggle Button */}
        <button
          onClick={() => setIsCollapsed(!isCollapsed)}
          className="absolute -right-3.5 top-[76px] z-50 bg-[#FAF9F6] border border-[#1C1613]/10 w-7 h-7 rounded-full flex items-center justify-center shadow-md cursor-pointer hover:bg-white transition-transform hover:scale-105 duration-200"
        >
          <svg viewBox="0 0 24 24" fill="none" stroke="#766E65" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" className="w-4 h-4">
            {isCollapsed ? (
              <polyline points="9 18 15 12 9 6" />
            ) : (
              <polyline points="15 18 9 12 15 6" />
            )}
          </svg>
        </button>
      </aside>

      {/* Mobile Sidebar (Drawer) */}
      <aside
        className={`fixed top-0 bottom-0 left-0 w-72 z-50 md:hidden transform transition-transform duration-300 ease-in-out ${
          isMobileOpen ? "translate-x-0" : "-translate-x-full"
        }`}
      >
        {getSidebarContent(false)}
      </aside>

      {/* Main Container */}
      <div className="flex-1 flex flex-col min-w-0 min-h-screen">
        {/* Mobile Header Bar */}
        <header className="h-16 border-b border-[#1C1613]/5 bg-[#FAF9F6] flex md:hidden items-center justify-between px-6 z-20">
          <button
            onClick={() => setIsMobileOpen(true)}
            className="p-1.5 rounded-lg border border-[#1C1613]/10 hover:bg-[#1C1613]/5 text-[#1C1613]"
          >
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="w-6 h-6">
              <line x1="3" y1="12" x2="21" y2="12" />
              <line x1="3" y1="6" x2="21" y2="6" />
              <line x1="3" y1="18" x2="21" y2="18" />
            </svg>
          </button>
          
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-full border border-[#C5A86E] flex items-center justify-center bg-[#1C1613]">
              <span className="font-serif text-[#C5A86E] font-bold text-xs">L</span>
            </div>
            <span className="font-serif text-sm font-bold tracking-wide">Lumière Palace</span>
          </div>

          <div className="w-8 h-8 rounded-full bg-[#C5A86E] text-[#1C1613] flex items-center justify-center font-serif font-bold text-xs">
            {user.initials}
          </div>
        </header>

        {/* Content Body */}
        <main className="flex-1 flex flex-col">
          {children}
        </main>
      </div>
    </div>
  );
}
