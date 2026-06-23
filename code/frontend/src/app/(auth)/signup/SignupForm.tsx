"use client";

import React, { useState } from 'react';
import Link from 'next/link';
import {
  SOCIAL_GOOGLE,
  SOCIAL_FACEBOOK,
  DIVIDER_TEXT_SIGNUP,
  LABEL_FIRST_NAME,
  PLACEHOLDER_FIRST_NAME,
  LABEL_LAST_NAME,
  PLACEHOLDER_LAST_NAME,
  LABEL_EMAIL,
  PLACEHOLDER_EMAIL,
  LABEL_PASSWORD,
  PLACEHOLDER_PASSWORD,
  LABEL_CONFIRM_PASSWORD,
  BTN_CONTINUE,
  BTN_BACK,
  BTN_REGISTER,
  BTN_REGISTERING,
  TEXT_LOGIN_PROMPT,
  LINK_LOGIN,
  ERROR_REQUIRED,
  ERROR_PASSWORD_MATCH,
  ERROR_PASSWORD_LENGTH,
} from '@/constants/auth';
import { MOCK_DELAY_MS, MIN_PASSWORD_LENGTH } from '@/constants/numbers';

/* ────── SVG Icons ────── */
const UserIcon = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
    <circle cx="12" cy="7" r="4" />
  </svg>
);
const MailIcon = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <rect x="2" y="4" width="20" height="16" rx="2" />
    <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7" />
  </svg>
);
const LockIcon = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
    <path d="M7 11V7a5 5 0 0 1 10 0v4" />
  </svg>
);
const EyeIcon = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
    <circle cx="12" cy="12" r="3" />
  </svg>
);
const EyeOffIcon = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94" />
    <path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19" />
    <line x1="1" y1="1" x2="23" y2="23" />
  </svg>
);
const ArrowIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
    <path d="M5 12h14" />
    <path d="m12 5 7 7-7 7" />
  </svg>
);
const ArrowLeftIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
    <path d="M19 12H5" />
    <path d="m12 19-7-7 7-7" />
  </svg>
);

/* ────── Input wrapper ────── */
function FormInput({
  id, label, required, icon, children,
}: {
  id: string; label: string; required?: boolean; icon: React.ReactNode; children: React.ReactNode;
}) {
  return (
    <div>
      <label htmlFor={id} className="block text-sm font-medium text-text-dark mb-2">
        {label}{required && <span className="text-accent-gold ml-0.5">*</span>}
      </label>
      <div className="relative">
        <span className="absolute left-4 top-1/2 -translate-y-1/2 text-text-light">{icon}</span>
        {children}
      </div>
    </div>
  );
}

const inputClass =
  'w-full pl-12 pr-4 py-3 rounded-xl border border-border-light bg-bg-light/50 focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold transition-all text-sm';

export default function SignupForm() {
  const [step, setStep] = useState(1);

  // Step 1
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');

  // Step 2
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleStep1 = (e: React.FormEvent) => {
    e.preventDefault();
    if (!firstName || !lastName || !email) {
      setError(ERROR_REQUIRED);
      return;
    }
    setError('');
    setStep(2);
  };

  const handleStep2 = (e: React.FormEvent) => {
    e.preventDefault();
    if (!password || !confirmPassword) {
      setError(ERROR_REQUIRED);
      return;
    }
    if (password.length < MIN_PASSWORD_LENGTH) {
      setError(ERROR_PASSWORD_LENGTH);
      return;
    }
    if (password !== confirmPassword) {
      setError(ERROR_PASSWORD_MATCH);
      return;
    }
    setError('');
    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
    }, MOCK_DELAY_MS);
  };

  /* ────── Social Buttons ────── */
  const SocialButtons = () => (
    <>
      <div className="space-y-3 mb-6">
        <button type="button" className="w-full flex items-center justify-center gap-3 py-3 px-4 bg-white border border-border-light rounded-full hover:bg-gray-50 transition-colors text-text-dark font-medium shadow-sm">
          <svg className="w-5 h-5" viewBox="0 0 24 24">
            <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4" />
            <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853" />
            <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05" />
            <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335" />
          </svg>
          {SOCIAL_GOOGLE}
        </button>
        <button type="button" className="w-full flex items-center justify-center gap-3 py-3 px-4 bg-[#1877F2] rounded-full hover:bg-[#1565D8] transition-colors text-white font-medium shadow-sm">
          <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
            <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z" />
          </svg>
          {SOCIAL_FACEBOOK}
        </button>
      </div>
      <div className="flex items-center gap-4 mb-6">
        <div className="flex-1 h-px bg-border-light" />
        <span className="text-xs text-text-light">{DIVIDER_TEXT_SIGNUP}</span>
        <div className="flex-1 h-px bg-border-light" />
      </div>
    </>
  );

  return (
    <>
      {/* ───── STEP 1: Basic Info ───── */}
      {step === 1 && (
        <>
          <SocialButtons />

          <form onSubmit={handleStep1} className="space-y-5">
            {error && (
              <div className="p-3 bg-red-50 border border-red-200 text-red-600 rounded-lg text-sm" role="alert" aria-live="assertive">
                {error}
              </div>
            )}

            {/* First + Last Name (2 columns) */}
            <div className="grid grid-cols-2 gap-4">
              <FormInput id="signup-first" label={LABEL_FIRST_NAME} required icon={<UserIcon />}>
                <input
                  id="signup-first"
                  type="text"
                  autoComplete="given-name"
                  autoFocus
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  placeholder={PLACEHOLDER_FIRST_NAME}
                  className={inputClass}
                />
              </FormInput>
              <FormInput id="signup-last" label={LABEL_LAST_NAME} required icon={<UserIcon />}>
                <input
                  id="signup-last"
                  type="text"
                  autoComplete="family-name"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  placeholder={PLACEHOLDER_LAST_NAME}
                  className={inputClass}
                />
              </FormInput>
            </div>

            {/* Email */}
            <FormInput id="signup-email" label={LABEL_EMAIL} required icon={<MailIcon />}>
              <input
                id="signup-email"
                type="email"
                autoComplete="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder={PLACEHOLDER_EMAIL}
                className={inputClass}
              />
            </FormInput>

            {/* Continue */}
            <button
              type="submit"
              className="w-full py-3.5 px-4 bg-accent-gold hover:bg-[#c9a45e] text-white font-semibold rounded-xl transition-all shadow-md hover:shadow-lg flex items-center justify-center gap-2 mt-2"
            >
              {BTN_CONTINUE}
              <ArrowIcon />
            </button>
          </form>
        </>
      )}

      {/* ───── STEP 2: Password ───── */}
      {step === 2 && (
        <form onSubmit={handleStep2} className="space-y-5">
          <div className="mb-2">
            <h2 className="text-2xl font-bold text-text-dark">Set your password</h2>
            <p className="text-text-light text-sm mt-1">Almost done! Create a secure password for your account.</p>
          </div>

          {error && (
            <div className="p-3 bg-red-50 border border-red-200 text-red-600 rounded-lg text-sm" role="alert" aria-live="assertive">
              {error}
            </div>
          )}

          {/* Password */}
          <FormInput id="signup-password" label={LABEL_PASSWORD} required icon={<LockIcon />}>
            <input
              id="signup-password"
              type={showPassword ? 'text' : 'password'}
              autoComplete="new-password"
              autoFocus
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder={PLACEHOLDER_PASSWORD}
              className={`${inputClass} !pr-12`}
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute right-4 top-1/2 -translate-y-1/2 text-text-light hover:text-text-dark transition-colors"
              aria-label={showPassword ? 'Hide password' : 'Show password'}
            >
              {showPassword ? <EyeOffIcon /> : <EyeIcon />}
            </button>
          </FormInput>

          {/* Confirm Password */}
          <FormInput id="signup-confirm" label={LABEL_CONFIRM_PASSWORD} required icon={<LockIcon />}>
            <input
              id="signup-confirm"
              type={showConfirm ? 'text' : 'password'}
              autoComplete="new-password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              placeholder={PLACEHOLDER_PASSWORD}
              className={`${inputClass} !pr-12`}
            />
            <button
              type="button"
              onClick={() => setShowConfirm(!showConfirm)}
              className="absolute right-4 top-1/2 -translate-y-1/2 text-text-light hover:text-text-dark transition-colors"
              aria-label={showConfirm ? 'Hide password' : 'Show password'}
            >
              {showConfirm ? <EyeOffIcon /> : <EyeIcon />}
            </button>
          </FormInput>

          {/* Terms */}
          <p className="text-xs text-text-light leading-relaxed">
            By creating an account, you agree to our{' '}
            <Link href="#" className="text-accent-gold hover:underline">Terms of Service</Link>
            {' '}and{' '}
            <Link href="#" className="text-accent-gold hover:underline">Privacy Policy</Link>.
          </p>

          {/* Buttons */}
          <div className="flex gap-3">
            <button
              type="button"
              onClick={() => { setStep(1); setError(''); }}
              className="flex-1 py-3.5 px-4 border border-border-light text-text-dark font-semibold rounded-xl hover:bg-bg-light transition-all flex items-center justify-center gap-2"
            >
              <ArrowLeftIcon />
              {BTN_BACK}
            </button>
            <button
              type="submit"
              disabled={isLoading}
              className="flex-1 py-3.5 px-4 bg-accent-gold hover:bg-[#c9a45e] text-white font-semibold rounded-xl transition-all shadow-md hover:shadow-lg disabled:opacity-60 disabled:cursor-not-allowed flex items-center justify-center gap-2"
            >
              {isLoading ? BTN_REGISTERING : (
                <>
                  {BTN_REGISTER}
                  <ArrowIcon />
                </>
              )}
            </button>
          </div>
        </form>
      )}

      {/* Login link */}
      <p className="mt-8 text-center text-sm text-text-light">
        {TEXT_LOGIN_PROMPT}
        <Link href="/login" className="text-accent-gold font-semibold hover:underline">
          {LINK_LOGIN}
        </Link>
      </p>
    </>
  );
}
