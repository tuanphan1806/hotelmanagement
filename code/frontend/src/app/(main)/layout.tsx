import Link from "next/link";

export default function MainLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
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
          <Link href="/" className="hover:text-accent-gold transition-colors relative after:content-[''] after:absolute after:-bottom-2 after:left-0 after:w-full after:h-[2px] after:bg-white">
            Home
          </Link>
          <Link href="/facilities" className="hover:text-accent-gold transition-colors relative after:content-[''] after:absolute after:-bottom-2 after:left-0 after:w-0 hover:after:w-full after:h-[2px] after:bg-accent-gold after:transition-all">
            Facilities
          </Link>
          <Link href="/rooms" className="hover:text-accent-gold transition-colors relative after:content-[''] after:absolute after:-bottom-2 after:left-0 after:w-0 hover:after:w-full after:h-[2px] after:bg-accent-gold after:transition-all">
            Rooms
          </Link>
          <Link href="/contact" className="hover:text-accent-gold transition-colors relative after:content-[''] after:absolute after:-bottom-2 after:left-0 after:w-0 hover:after:w-full after:h-[2px] after:bg-accent-gold after:transition-all">
            Contact-us
          </Link>
          <Link href="/login" className="hover:text-accent-gold transition-colors relative after:content-[''] after:absolute after:-bottom-2 after:left-0 after:w-0 hover:after:w-full after:h-[2px] after:bg-accent-gold after:transition-all">Login</Link>
          <Link href="/signup" className="hover:text-accent-gold transition-colors relative after:content-[''] after:absolute after:-bottom-2 after:left-0 after:w-0 hover:after:w-full after:h-[2px] after:bg-accent-gold after:transition-all">Sign Up</Link>
        </nav>
      </header>

      <main className="min-h-screen">
        {children}
      </main>

      {/* Footer */}
      <footer className="bg-primary-navy text-white mt-auto pt-20 pb-12 px-16 relative">
        <div className="absolute top-0 left-1/2 -translate-x-1/2 -translate-y-1/2 w-0 h-0 border-l-[30px] border-l-transparent border-r-[30px] border-r-transparent border-b-[30px] border-b-primary-navy"></div>
        
        <div className="max-w-6xl mx-auto flex flex-col md:flex-row justify-between items-start gap-10">
          <div className="max-w-xs">
            <div className="mb-6 flex flex-col">
              <span className="font-serif text-3xl font-bold tracking-widest leading-tight">LUXURY</span>
              <span className="text-[0.6rem] tracking-[0.4em] font-bold">HOTELS</span>
            </div>
            <p className="text-xs text-white/80 leading-relaxed font-light">
              497 Evergreen Rd. Roseville, CA 95673<br/>
              +44 345 678 903<br/>
              luxury_hotels@gmail.com
            </p>
          </div>
          
          <div className="flex flex-col gap-4 text-sm font-light text-white/90">
            <Link href="#" className="hover:text-accent-gold transition-colors">About Us</Link>
            <Link href="#" className="hover:text-accent-gold transition-colors">Contact</Link>
            <Link href="#" className="hover:text-accent-gold transition-colors">Terms & Conditions</Link>
          </div>

          <div className="flex flex-col gap-4 text-sm font-light text-white/90">
            <Link href="#" className="flex items-center gap-3 hover:text-accent-gold transition-colors">
              <span className="font-bold text-lg w-4 text-center">f</span> Facebook
            </Link>
            <Link href="#" className="flex items-center gap-3 hover:text-accent-gold transition-colors">
              <span className="font-bold text-lg w-4 text-center">t</span> Twitter
            </Link>
            <Link href="#" className="flex items-center gap-3 hover:text-accent-gold transition-colors">
              <span className="font-bold text-lg w-4 text-center">i</span> Instagram
            </Link>
          </div>

          <div className="flex flex-col gap-5">
            <span className="text-sm font-medium">Subscribe to our newsletter</span>
            <div className="flex h-10">
              <input 
                type="email" 
                placeholder="Email Address" 
                className="bg-transparent border-2 border-accent-gold px-4 text-sm focus:outline-none focus:bg-white/5 rounded-l-sm w-48 transition-colors"
              />
              <button className="bg-accent-gold text-primary-navy px-6 text-sm font-bold rounded-r-sm hover:bg-yellow-500 transition-colors">
                OK
              </button>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
}
