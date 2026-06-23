import { Metadata } from 'next';
import Image from 'next/image';
import {
  BRAND_NAME,
  BRAND_TAGLINE,
  BADGE_ESTABLISHED,
  HERO_TITLE,
  HERO_SUBTITLE,
  STAT_1_NUM, STAT_1_LABEL,
  STAT_2_NUM, STAT_2_LABEL,
  STAT_3_NUM, STAT_3_LABEL,
} from '@/constants/auth';
import LoginForm from './LoginForm';

export const metadata: Metadata = {
  title: 'Login – Lumière Palace',
  description: 'Sign in to access your Lumière Palace management portal.',
};

export default function LoginPage() {
  return (
    <>
      {/* ───── Left Hero Panel ───── */}
      <div className="hidden lg:flex lg:w-1/2 relative overflow-hidden">
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

          {/* Hero text */}
          <div className="space-y-4">
            <span className="inline-block px-3 py-1 bg-accent-gold/20 border border-accent-gold/40 text-accent-gold text-xs tracking-widest uppercase rounded-full">
              {BADGE_ESTABLISHED}
            </span>
            <h1 className="text-4xl xl:text-5xl font-bold text-white leading-tight">
              {HERO_TITLE}
            </h1>
            <p className="text-white/70 text-sm leading-relaxed max-w-md">
              {HERO_SUBTITLE}
            </p>
          </div>

          {/* Stats */}
          <div className="flex gap-10">
            <div>
              <p className="text-accent-gold text-3xl font-bold">{STAT_1_NUM}</p>
              <p className="text-white/60 text-xs mt-1">{STAT_1_LABEL}</p>
            </div>
            <div>
              <p className="text-accent-gold text-3xl font-bold">{STAT_2_NUM}</p>
              <p className="text-white/60 text-xs mt-1">{STAT_2_LABEL}</p>
            </div>
            <div>
              <p className="text-accent-gold text-3xl font-bold">{STAT_3_NUM}</p>
              <p className="text-white/60 text-xs mt-1">{STAT_3_LABEL}</p>
            </div>
          </div>
        </div>
      </div>

      {/* ───── Right Form Panel ───── */}
      <div className="flex-1 flex items-center justify-center bg-white px-6 py-12 lg:px-16">
        <div className="w-full max-w-md">
          <LoginForm />
        </div>
      </div>
    </>
  );
}
