"use client";

import React, { useState } from 'react';
import Link from 'next/link';
import {
  SOCIAL_GOOGLE,
  SOCIAL_FACEBOOK,
  DIVIDER_TEXT,
  LABEL_EMAIL,
  PLACEHOLDER_EMAIL,
  LABEL_PASSWORD,
  PLACEHOLDER_PASSWORD,
  LINK_FORGOT,
  BTN_SIGNIN,
  BTN_SIGNING,
  TEXT_SIGNUP_PROMPT,
  LINK_SIGNUP,
  ERROR_REQUIRED,
  ERROR_EMAIL_INVALID,
  ERROR_ACCOUNT_NOT_FOUND,
  ERROR_WRONG_PASSWORD,
  FORM_TITLE_LOGIN,
  FORM_SUBTITLE_LOGIN,
} from '@/constants/auth';
import { MOCK_DELAY_MS } from '@/constants/numbers';

// Giả lập database email đã đăng ký
const REGISTERED_EMAILS = ['manager@luxuryhotels.com', 'thui1@luxuryhotels.com'];

export default function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // 1. Kiểm tra để trống
    if (!email.trim() || !password.trim()) {
      setError(ERROR_REQUIRED);
      return;
    }

    // 2. Kiểm tra định dạng Email: CHỈ CHO PHÉP @luxuryhotels.com
    // Regex: [^\s@]+ là phần user, theo sau là chính xác @luxuryhotels.com
    const emailRegex = /^[^\s@]+@luxuryhotels\.com$/i;
    
    if (!emailRegex.test(email.trim())) {
      setError(ERROR_EMAIL_INVALID);
      return;
    }

    // Nếu qua được bước 2, xóa lỗi và bắt đầu loading
    setError('');
    setIsLoading(true);

    // 3. Giả lập kiểm tra dữ liệu
    setTimeout(() => {
      setIsLoading(false);
      
      // 4. Kiểm tra Email có trong database không
      if (!REGISTERED_EMAILS.includes(email.trim().toLowerCase())) {
        setError(ERROR_ACCOUNT_NOT_FOUND);
        return;
      }

      // 5. Kiểm tra Mật khẩu
      if (password !== "Luxury123@") {
        setError(ERROR_WRONG_PASSWORD);
      } else {
        console.log("Login success!");
        // Thêm logic chuyển trang tại đây
      }
    }, MOCK_DELAY_MS);
  };

  return (
    <>
      {/* Title */}
      <div className="mb-8">
        <h2 className="text-3xl font-bold text-text-dark">{FORM_TITLE_LOGIN}</h2>
        <p className="text-text-light text-sm mt-2">{FORM_SUBTITLE_LOGIN}</p>
      </div>

      {/* Error */}
      {error && (
        <div className="mb-4 p-3 bg-red-50 border border-red-200 text-red-600 rounded-lg text-sm" role="alert" aria-live="assertive">
          {error}
        </div>
      )}

      {/* Social buttons */}
      <div className="space-y-3 mb-6">
        <button type="button" className="w-full flex items-center justify-center gap-3 py-3 px-4 bg-white border border-border-light rounded-full hover:bg-gray-50 transition-colors text-text-dark font-medium shadow-sm">
          <svg className="w-5 h-5" viewBox="0 0 24 24">
            <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4"/>
            <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853"/>
            <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05"/>
            <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335"/>
          </svg>
          {SOCIAL_GOOGLE}
        </button>
        <button type="button" className="w-full flex items-center justify-center gap-3 py-3 px-4 bg-[#1877F2] rounded-full hover:bg-[#1565D8] transition-colors text-white font-medium shadow-sm">
          <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
            <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
          </svg>
          {SOCIAL_FACEBOOK}
        </button>
      </div>

      <div className="flex items-center gap-4 mb-6">
        <div className="flex-1 h-px bg-border-light" />
        <span className="text-xs text-text-light">{DIVIDER_TEXT}</span>
        <div className="flex-1 h-px bg-border-light" />
      </div>

      {/* Form */}
      <form noValidate onSubmit={handleSubmit} className="space-y-5">
        <div>
          <label htmlFor="login-email" className="block text-sm font-medium text-text-dark mb-2">
            {LABEL_EMAIL}
          </label>
          <div className="relative">
            <span className="absolute left-4 top-1/2 -translate-y-1/2 text-text-light">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <rect x="2" y="4" width="20" height="16" rx="2"/>
                <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"/>
              </svg>
            </span>
            <input
              id="login-email"
              type="email"
              autoComplete="email"
              autoFocus
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder={PLACEHOLDER_EMAIL}
              className="w-full pl-12 pr-4 py-3 rounded-xl border border-border-light bg-bg-light/50 focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold transition-all text-sm"
            />
          </div>
        </div>

        <div>
          <div className="flex items-center justify-between mb-2">
            <label htmlFor="login-password" className="block text-sm font-medium text-text-dark">
              {LABEL_PASSWORD}
            </label>
            <Link href="#" className="text-xs text-accent-gold hover:underline font-medium">
              {LINK_FORGOT}
            </Link>
          </div>
          <div className="relative">
            <span className="absolute left-4 top-1/2 -translate-y-1/2 text-text-light">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
            </span>
            <input
              id="login-password"
              type={showPassword ? 'text' : 'password'}
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder={PLACEHOLDER_PASSWORD}
              className="w-full pl-12 pr-12 py-3 rounded-xl border border-border-light bg-bg-light/50 focus:outline-none focus:ring-2 focus:ring-accent-gold/50 focus:border-accent-gold transition-all text-sm"
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute right-4 top-1/2 -translate-y-1/2 text-text-light hover:text-text-dark transition-colors"
              aria-label={showPassword ? 'Hide password' : 'Show password'}
            >
              {showPassword ? (
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94"/>
                  <path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19"/>
                  <line x1="1" y1="1" x2="23" y2="23"/>
                </svg>
              ) : (
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                  <circle cx="12" cy="12" r="3"/>
                </svg>
              )}
            </button>
          </div>
        </div>

        <button
          type="submit"
          disabled={isLoading}
          className="w-full py-3.5 px-4 bg-accent-gold hover:bg-[#c9a45e] text-white font-semibold rounded-xl transition-all shadow-md hover:shadow-lg disabled:opacity-60 disabled:cursor-not-allowed flex items-center justify-center gap-2"
        >
          {isLoading ? BTN_SIGNING : (
            <>
              {BTN_SIGNIN}
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                <path d="M5 12h14"/>
                <path d="m12 5 7 7-7 7"/>
              </svg>
            </>
          )}
        </button>
      </form>

      <p className="mt-8 text-center text-sm text-text-light">
        {TEXT_SIGNUP_PROMPT}
        <Link href="/signup" className="text-accent-gold font-semibold hover:underline">
          {LINK_SIGNUP}
        </Link>
      </p>
    </>
  );
}