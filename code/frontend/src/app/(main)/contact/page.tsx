"use client";

import React, { useState } from "react";
import Link from "next/link";
import { CONTACT_CONTENT } from "@/constants/content";
import { BRAND_NAME } from "@/constants/auth";
import { MOCK_DELAY_MS } from "@/constants/numbers";

export default function ContactPage() {
  const { hero, info, form } = CONTACT_CONTENT;
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    subject: "",
    message: ""
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    // Simulate API call
    setTimeout(() => {
      setIsSubmitting(false);
      setIsSuccess(true);
      setFormData({ name: "", email: "", phone: "", subject: "", message: "" });
    }, MOCK_DELAY_MS);
  };

  return (
    <div className="min-h-screen bg-bg-light">
      {/* Hero Section */}
      <section 
        className="relative h-[50vh] flex items-center justify-center bg-cover bg-center"
        style={{ backgroundImage: `url(${hero.bg})` }}
      >
        <div className="absolute inset-0 bg-primary-navy/50"></div>
        <div className="relative z-10 text-center text-white px-6">
          <h1 className="text-5xl md:text-6xl font-black tracking-wide text-white mb-4 drop-shadow-2xl">
            {hero.title}
          </h1>
          <p className="text-xl text-white/80 max-w-2xl mx-auto">{hero.subtitle}</p>
        </div>
      </section>

      {/* Contact Content */}
      <section className="py-20 px-6 max-w-7xl mx-auto">
        <div className="flex flex-col lg:flex-row gap-12">
          
          {/* Contact Info */}
          <div className="lg:w-1/3 space-y-8">
            <div>
              <h2 className="text-3xl font-bold text-primary-navy mb-6">{info.title}</h2>
              <div className="space-y-6">
                {info.items.map((item, index) => (
                  <div key={index} className="flex items-start gap-4 p-4 rounded-lg bg-white border border-border-light shadow-sm">
                    <div className="text-2xl mt-1">{item.icon}</div>
                    <div>
                      <h3 className="font-semibold text-text-dark mb-1">{item.title}</h3>
                      {item.lines.map((line, i) => (
                        <p key={i} className="text-text-light text-sm">{line}</p>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            </div>

            <div className="pt-6 border-t border-border-light">
              <h3 className="font-semibold text-text-dark mb-4">Follow Us</h3>
              <div className="flex gap-4">
                {info.socials.map((social, index) => (
                  <Link key={index} href="#" className="px-4 py-2 bg-white border border-border-light rounded-md text-sm font-medium text-text-light hover:text-accent-gold hover:border-accent-gold transition-colors">
                    {social}
                  </Link>
                ))}
              </div>
            </div>
          </div>

          {/* Contact Form */}
          <div className="lg:w-2/3">
            <div className="bg-white p-8 rounded-xl shadow-lg border border-border-light">
              <h2 className="text-3xl font-bold text-primary-navy mb-8">{form.title}</h2>
              
              {isSuccess ? (
                <div className="p-6 bg-green-50 border border-green-200 rounded-lg text-center">
                  <div className="text-4xl text-green-500 mb-4">✓</div>
                  <h3 className="text-xl font-semibold text-green-800 mb-2">Message Sent!</h3>
                  <p className="text-green-600">{form.success}</p>
                  <button 
                    onClick={() => setIsSuccess(false)}
                    className="mt-6 px-6 py-2 bg-white border border-green-300 text-green-700 rounded-md hover:bg-green-50 transition-colors"
                  >
                    Send Another Message
                  </button>
                </div>
              ) : (
                <form onSubmit={handleSubmit} className="space-y-6">
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                      <label className="block text-sm font-medium text-text-dark mb-1.5">{form.fields.name.label}</label>
                      <input 
                        type="text" 
                        required
                        placeholder={form.fields.name.placeholder}
                        value={formData.name}
                        onChange={(e) => setFormData({...formData, name: e.target.value})}
                        className="w-full px-4 py-3 rounded-md border border-border-light focus:outline-none focus:ring-2 focus:ring-accent-gold focus:border-transparent transition-all"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-text-dark mb-1.5">{form.fields.email.label}</label>
                      <input 
                        type="email" 
                        required
                        placeholder={form.fields.email.placeholder}
                        value={formData.email}
                        onChange={(e) => setFormData({...formData, email: e.target.value})}
                        className="w-full px-4 py-3 rounded-md border border-border-light focus:outline-none focus:ring-2 focus:ring-accent-gold focus:border-transparent transition-all"
                      />
                    </div>
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                      <label className="block text-sm font-medium text-text-dark mb-1.5">{form.fields.phone.label}</label>
                      <input 
                        type="tel" 
                        placeholder={form.fields.phone.placeholder}
                        value={formData.phone}
                        onChange={(e) => setFormData({...formData, phone: e.target.value})}
                        className="w-full px-4 py-3 rounded-md border border-border-light focus:outline-none focus:ring-2 focus:ring-accent-gold focus:border-transparent transition-all"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-text-dark mb-1.5">{form.fields.subject.label}</label>
                      <input 
                        type="text" 
                        required
                        placeholder={form.fields.subject.placeholder}
                        value={formData.subject}
                        onChange={(e) => setFormData({...formData, subject: e.target.value})}
                        className="w-full px-4 py-3 rounded-md border border-border-light focus:outline-none focus:ring-2 focus:ring-accent-gold focus:border-transparent transition-all"
                      />
                    </div>
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-text-dark mb-1.5">{form.fields.message.label}</label>
                    <textarea 
                      required
                      rows={5}
                      placeholder={form.fields.message.placeholder}
                      value={formData.message}
                      onChange={(e) => setFormData({...formData, message: e.target.value})}
                      className="w-full px-4 py-3 rounded-md border border-border-light focus:outline-none focus:ring-2 focus:ring-accent-gold focus:border-transparent transition-all resize-none"
                    ></textarea>
                  </div>

                  <button 
                    type="submit" 
                    disabled={isSubmitting}
                    className="w-full sm:w-auto px-8 py-3 bg-primary-navy hover:bg-secondary-navy text-white font-medium rounded-md transition-colors shadow-md disabled:opacity-70 disabled:cursor-not-allowed"
                  >
                    {isSubmitting ? 'Sending...' : form.btn}
                  </button>
                </form>
              )}
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
