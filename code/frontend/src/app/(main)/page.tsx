import React from "react";

export default function HomePage() {
  return (
    <div className="bg-white">
      {/* Hero Section */}
      <section className="relative h-screen flex flex-col justify-center bg-primary-navy overflow-hidden">
        {/* Background Image */}
        <div 
          className="absolute inset-0 bg-cover bg-center bg-no-repeat opacity-60"
          style={{ backgroundImage: 'url("https://images.unsplash.com/photo-1540541338287-41700207dee6?ixlib=rb-4.0.3&auto=format&fit=crop&w=2070&q=80")' }}
        ></div>
        
        <div className="relative z-10 pl-16 md:pl-32 max-w-4xl text-left mt-20">
          <h2 className="text-white text-3xl md:text-4xl font-light tracking-wide mb-2">WELCOME TO</h2>
          <h1 className="text-white font-serif text-6xl md:text-8xl font-bold tracking-widest leading-none mb-2">LUXURY</h1>
          <h1 className="text-white font-serif text-4xl md:text-5xl font-bold tracking-[0.4em] mb-6">HOTELS</h1>
          <p className="text-white text-lg md:text-xl font-light max-w-lg leading-relaxed">
            Book your stay and enjoy Luxury<br/>redefined at the most affordable rates.
          </p>
        </div>

        {/* Scroll Indicator & Book Now Button */}
        <div className="absolute bottom-8 left-1/2 -translate-x-1/2 flex flex-col items-center gap-10 text-white/80">
          <button className="bg-accent-gold hover:bg-yellow-500 text-white font-bold py-4 px-8 rounded-md flex items-center gap-3 transition-colors shadow-lg">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M19 4H5C3.89543 4 3 4.89543 3 6V20C3 21.1046 3.89543 22 5 22H19C20.1046 22 21 21.1046 21 20V6C21 4.89543 20.1046 4 19 4Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              <path d="M16 2V6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              <path d="M8 2V6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              <path d="M3 10H21" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
            BOOK NOW
          </button>
          
          <div className="flex flex-col items-center gap-2">
            <span className="text-sm font-semibold tracking-wider">Scroll</span>
          <a href="#explore" className="w-10 h-10 rounded-full bg-white flex items-center justify-center text-primary-navy shadow-lg cursor-pointer hover:bg-gray-100 transition-colors">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M6 9L12 15L18 9" stroke="#14274A" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </a>
          </div>
        </div>
      </section>

      {/* Notice Section */}
      <section id="explore" className="py-12 text-center">
        <h3 className="text-xl md:text-2xl font-medium text-text-dark">
          All our room types are including complementary breakfast
        </h3>
      </section>

      {/* Features Section */}
      <section className="max-w-6xl mx-auto px-6 py-10 space-y-24">
        {/* Feature 1 */}
        <div className="flex flex-col md:flex-row items-center gap-16">
          <div className="md:w-1/2 pl-6 border-l-2 border-primary-navy py-2">
            <h2 className="font-serif text-4xl md:text-5xl font-bold text-primary-navy mb-6">Luxury redefined</h2>
            <p className="text-text-light leading-relaxed mb-8">
              Our rooms are designed to transport you into an environment made for leisure. 
              Take your mind off the day-to-day of home life and find a private paradise for yourself.
            </p>
            <button className="bg-accent-gold hover:bg-yellow-500 text-white font-bold py-3 px-8 rounded-sm transition-colors text-sm tracking-wider">
              EXPLORE
            </button>
          </div>
          <div className="md:w-1/2">
            <img 
              src="https://images.unsplash.com/photo-1611892440504-42a792e24d32?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80" 
              alt="Luxury Room" 
              className="w-full h-[400px] object-cover rounded-sm shadow-xl"
            />
          </div>
        </div>

        {/* Feature 2 */}
        <div className="flex flex-col md:flex-row-reverse items-center gap-16">
          <div className="md:w-1/2 pl-6 border-l-2 border-primary-navy py-2">
            <h2 className="font-serif text-4xl md:text-5xl font-bold text-primary-navy mb-6">Leave your worries in the sand</h2>
            <p className="text-text-light leading-relaxed mb-8">
              We love life at the beach. Being close to the ocean with access to endless sandy beach 
              ensures a relaxed state of mind. It seems like time stands still watching the ocean.
            </p>
            <button className="bg-accent-gold hover:bg-yellow-500 text-white font-bold py-3 px-8 rounded-sm transition-colors text-sm tracking-wider">
              EXPLORE
            </button>
          </div>
          <div className="md:w-1/2">
            <img 
              src="https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80" 
              alt="Beach View" 
              className="w-full h-[400px] object-cover rounded-sm shadow-xl"
            />
          </div>
        </div>
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
