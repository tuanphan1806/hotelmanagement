import { Metadata } from 'next';
import Image from 'next/image';
import {
  BRAND_NAME,
  BRAND_TAGLINE,
  SIGNUP_HERO_TITLE,
  SIGNUP_HERO_SUBTITLE,
  SIGNUP_FEATURES,
} from '@/constants/auth';
import SignupForm from './SignupForm';

export const metadata: Metadata = {
  title: 'Sign Up – Luxury Hotels',
  description: 'Create your Luxury Hotels account to book rooms and enjoy exclusive benefits.',
};

export default function SignupPage() {
  return (
    // Bọc thẻ div cha có flex min-h-screen w-full để ép layout chuẩn chỉnh
    <div className="flex min-h-screen w-full bg-white">
      
      {/* ───── Left Hero Panel (Đã chuẩn hóa lg:w-1/2 theo Login) ───── */}
      <div className="hidden lg:flex lg:w-1/2 relative overflow-hidden h-screen sticky top-0">
        <Image
          src="/hotel-lobby.png"
          alt="Luxury hotels lobby"
          fill
          className="object-cover"
          priority
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/40 to-black/30" />

        {/* Đồng bộ p-10 pt-28 h-full để khớp vị trí với Login */}
        <div className="relative z-10 flex flex-col justify-between p-10 pt-28 w-full h-full">
          {/* Logo */}
          <div className="absolute top-0 left-16 z-50">
            <div className="bg-accent-gold w-48 h-20 rounded-b-[2rem] flex flex-col items-center justify-center text-primary-navy shadow-lg select-none">
              <span className="font-serif text-3xl font-bold tracking-widest leading-tight">LUXURY</span>
              <span className="text-[0.6rem] tracking-[0.4em] font-bold">HOTELS</span>
            </div>
          </div>

          {/* Hero text + Features */}
          <div className="space-y-6">
            <h1 className="text-4xl xl:text-5xl font-bold text-white leading-tight">
              {SIGNUP_HERO_TITLE}
            </h1>
            <p className="text-white/70 text-sm leading-relaxed max-w-sm">
              {SIGNUP_HERO_SUBTITLE}
            </p>
            <ul className="space-y-3">
              {SIGNUP_FEATURES.map((feature) => (
                <li key={feature} className="flex items-center gap-3 text-white/90 text-sm">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" className="shrink-0">
                    <circle cx="12" cy="12" r="10" fill="#22c55e" opacity="0.2" />
                    <path d="M9 12l2 2 4-4" stroke="#22c55e" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
                  </svg>
                  {feature}
                </li>
              ))}
            </ul>
          </div>

          {/* Step indicator */}
          <div className="flex items-center gap-3">
            <div className="w-10 h-1.5 rounded-full bg-accent-gold" />
            <div className="w-10 h-1.5 rounded-full bg-white/30" />
            <span className="text-white/50 text-xs ml-2">Step 1 of 2</span>
          </div>
        </div>
      </div>

      {/* ───── Right Form Panel (Đã chuẩn hóa padding px-6 py-12 lg:px-16) ───── */}
      <div className="flex-1 flex items-center justify-center bg-white px-6 py-12 lg:px-16 overflow-y-auto">
        <div className="w-full max-w-md">
          <SignupForm />
        </div>
      </div>
    </div>
  );
}