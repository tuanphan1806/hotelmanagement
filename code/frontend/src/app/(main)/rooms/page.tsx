import React from "react";
import { ROOMS_CONTENT } from "@/constants/content";
import { BRAND_NAME, BRAND_TAGLINE } from "@/constants/auth";

export default function RoomsPage() {
  return (
    <div className="bg-white">
      {/* Hero Section */}
      <section className="relative h-screen flex flex-col justify-center bg-primary-navy overflow-hidden">
        {/* Background Image */}
        <div 
          className="absolute inset-0 bg-cover bg-center bg-no-repeat opacity-60"
          style={{ backgroundImage: `url("${ROOMS_CONTENT.hero.bg}")` }}
        ></div>
        
        <div className="relative z-10 pl-16 md:pl-32 max-w-4xl text-left mt-20">
          <h2 className="text-white text-3xl md:text-4xl font-light tracking-wide mb-2">WELCOME TO</h2>
          <h1 className="text-white font-serif text-6xl md:text-8xl font-bold tracking-widest leading-none mb-2">LUXURY</h1>
          <h1 className="text-white font-serif text-4xl md:text-5xl font-bold tracking-[0.4em] mb-6">HOTELS</h1>
          <p className="text-white text-lg md:text-xl font-light max-w-lg leading-relaxed">
            Book your stay and enjoy Luxury<br/>redefined at the most affordable rates.
          </p>
        </div>

        {/* Scroll Indicator */}
        <div className="absolute bottom-8 left-1/2 -translate-x-1/2 flex flex-col items-center gap-2 text-white/80">
          <span className="text-sm font-semibold tracking-wider">Scroll</span>
          <a href="#explore" className="w-10 h-10 rounded-full bg-white flex items-center justify-center text-primary-navy shadow-lg cursor-pointer hover:bg-gray-100 transition-colors">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M6 9L12 15L18 9" stroke="#14274A" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </a>
        </div>
      </section>

      {/* Introduction Section */}
      <section id="explore" className="py-24 text-center max-w-4xl mx-auto px-6">
        <h2 className="font-serif text-4xl md:text-5xl font-bold text-primary-navy mb-8 tracking-widest">
          {ROOMS_CONTENT.main.title}
        </h2>
        <p className="text-text-dark font-light text-sm md:text-base leading-loose max-w-3xl mx-auto">
          {ROOMS_CONTENT.main.desc}
        </p>
      </section>

      {/* Rooms List */}
      <section className="max-w-5xl mx-auto px-6 pb-24 space-y-16">
        {ROOMS_CONTENT.main.data.map((room, index) => (
          <div key={index} className="w-full border-2 border-gray-200 rounded-sm overflow-hidden shadow-sm hover:shadow-lg transition-shadow bg-white">
            <div className="relative w-full h-[350px] md:h-[450px] overflow-hidden">
              <img 
                src={room.image} 
                alt={room.title} 
                className="absolute inset-0 w-full h-full object-cover"
              />
            </div>
            <div className="bg-primary-navy py-5 text-center">
              <h3 className="text-white font-bold text-2xl tracking-widest uppercase">
                {room.title}
              </h3>
            </div>
            <div className="bg-white py-6 px-8 flex flex-col md:flex-row justify-between items-center gap-6">
              <button className="flex items-center gap-4 font-bold text-text-light hover:text-accent-gold transition-colors text-lg tracking-wide group">
                <span className="w-10 h-10 rounded-full border-2 border-accent-gold text-accent-gold flex items-center justify-center text-3xl pb-1 group-hover:bg-accent-gold group-hover:text-white transition-colors">
                  +
                </span>
                VIEW ROOM DETAILS
              </button>
              <button className="bg-accent-gold hover:bg-yellow-500 text-white font-bold py-4 px-10 rounded-sm transition-colors text-lg tracking-widest">
                {room.price}
              </button>
            </div>
          </div>
        ))}
      </section>

      {/* Testimonials Section */}
      <section className="py-24 text-center max-w-4xl mx-auto px-6">
        <h2 className="font-serif text-4xl md:text-5xl font-bold text-primary-navy mb-12">Testimonials</h2>
        <div className="mb-8">
          <p className="text-xl md:text-2xl text-text-dark font-medium mb-4">
            &quot;Calm, Serene, Retro – What a way to relax and enjoy&quot;
          </p>
          <p className="text-sm text-text-light font-semibold">
            Mr. and Mrs. Baxter, UK
          </p>
        </div>
        <div className="flex justify-center gap-4">
          <button className="w-10 h-10 bg-accent-gold text-white rounded-md flex items-center justify-center hover:bg-yellow-500 transition-colors shadow-md">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M15 18L9 12L15 6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </button>
          <button className="w-10 h-10 bg-accent-gold text-white rounded-md flex items-center justify-center hover:bg-yellow-500 transition-colors shadow-md">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M9 18L15 12L9 6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </button>
        </div>
      </section>
    </div>
  );
}
