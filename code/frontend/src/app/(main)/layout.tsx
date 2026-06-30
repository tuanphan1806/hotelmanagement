"use client"; 

import Link from "next/link";
import { usePathname } from "next/navigation";

export default function MainLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const pathname = usePathname(); 

  const getLinkClass = (path: string) => 
    `hover:text-accent-gold transition-colors relative after:content-[''] after:absolute after:-bottom-2 after:left-0 after:h-[2px] after:bg-accent-gold after:transition-all ${
      pathname === path ? "after:w-full" : "after:w-0 hover:after:w-full"
    }`;

  return (
    <>
      {/* Header */}
      <header className="absolute top-0 w-full z-50 flex items-center justify-between px-16 py-6 text-white font-medium">
        <div className="bg-accent-gold w-48 h-20 rounded-b-[2rem] absolute top-0 left-16 flex flex-col items-center justify-center text-primary-navy shadow-lg">
          <span className="font-serif text-3xl font-bold tracking-widest leading-tight">LUXURY</span>
          <span className="text-[0.6rem] tracking-[0.4em] font-bold">HOTELS</span>
        </div>
        <div className="ml-48"></div>
        
        <nav className="flex gap-6 items-center text-sm">
          <Link href="/" className={getLinkClass("/")}>Home</Link>
          <Link href="/facilities" className={getLinkClass("/facilities")}>Facilities</Link>
          <Link href="/rooms" className={getLinkClass("/rooms")}>Rooms</Link>
          <Link href="/contact" className={getLinkClass("/contact")}>Contact Us</Link>
          <Link href="/login" className={getLinkClass("/login")}>Login</Link>
          <Link href="/signup" className={getLinkClass("/signup")}>Sign Up</Link>
        </nav>
      </header>

      <main className="min-h-screen">
        {children}
      </main>

      {/* Footer */}
      <footer className="bg-primary-navy text-white mt-auto pt-16 pb-12 px-16 relative border-t border-white/5">
        <div className="max-w-6xl mx-auto flex flex-col md:flex-row justify-between items-start gap-12">
          {/* Logo & Description */}
          <div className="max-w-xs space-y-4">
            <div className="flex flex-col">
              <span className="font-serif text-3xl font-bold tracking-widest leading-tight">LUXURY</span>
              <span className="text-[0.6rem] tracking-[0.4em] font-bold">HOTELS</span>
            </div>
            <p className="text-xs text-white/60 leading-relaxed font-light">
              Kiến tạo trải nghiệm nghỉ dưỡng sang trọng giữa lòng di sản.
            </p>
          </div>
          
          {/* Column 1: Quick Links */}
          <div className="flex flex-col gap-4">
            <h4 className="text-xs font-bold tracking-widest text-white uppercase">QUICK LINKS</h4>
            <div className="flex flex-col gap-2.5 text-xs font-light text-white/60">
              <Link href="#" className="hover:text-accent-gold transition-colors">Privacy Policy</Link>
              <Link href="#" className="hover:text-accent-gold transition-colors">Terms of Service</Link>
            </div>
          </div>

          {/* Column 2: Follow Us */}
          <div className="flex flex-col gap-4">
            <h4 className="text-xs font-bold tracking-widest text-white uppercase">FOLLOW US</h4>
            <div className="flex flex-col gap-2.5 text-xs font-light text-white/60">
              <Link href="#" className="hover:text-accent-gold transition-colors">Facebook</Link>
              <Link href="#" className="hover:text-accent-gold transition-colors">Instagram</Link>
              <Link href="#" className="hover:text-accent-gold transition-colors">LinkedIn</Link>
            </div>
          </div>

          {/* Column 3: Copyright */}
          <div className="flex flex-col gap-4">
            <h4 className="text-xs font-bold tracking-widest text-white uppercase">COPYRIGHT</h4>
            <div className="text-xs font-light text-white/60 leading-relaxed max-w-[200px]">
              © 2026 Luxury Hotels. All rights reserved.
            </div>
          </div>
        </div>
      </footer>
    </>
  );
}
