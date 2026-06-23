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
  title: 'Sign Up – Lumière Palace',
  description: 'Create your Lumière Palace account to book rooms and enjoy exclusive benefits.',
};

export default function SignupPage() {
  return (
    <>
      {/* ───── Left Hero Panel ───── */}
      <div className="hidden lg:flex lg:w-[42%] relative overflow-hidden">
        <Image
          src="/hotel-lobby.png"
          alt="Luxury hotel lobby"
          fill
          className="object-cover"
          priority
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/40 to-black/30" />

        <div className="relative z-10 flex flex-col justify-between p-10 w-full">
          {/* Logo */}
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-lg bg-accent-gold/20 border border-accent-gold/40 flex items-center justify-center">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#E0B973" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M3 21h18" />
                <path d="M5 21V7l7-4 7 4v14" />
                <path d="M9 21v-6h6v6" />
              </svg>
            </div>
            <div>
              <h2 className="text-white text-lg font-bold tracking-wide">{BRAND_NAME}</h2>
              <p className="text-white/60 text-[10px] tracking-[0.25em] uppercase">{BRAND_TAGLINE}</p>
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

      {/* ───── Right Form Panel ───── */}
      <div className="flex-1 flex items-center justify-center bg-white px-6 py-10 lg:px-12 overflow-y-auto">
        <div className="w-full max-w-md">
          <SignupForm />
        </div>
      </div>
    </>
  );
}
