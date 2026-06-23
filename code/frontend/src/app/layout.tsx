import type { Metadata } from "next";
import { Montserrat, Playfair_Display } from "next/font/google";
import "../index.css";

const montserrat = Montserrat({
  subsets: ["latin"],
  variable: "--font-montserrat",
});

const playfair = Playfair_Display({
  subsets: ["latin"],
  variable: "--font-playfair",
});

export const metadata: Metadata = {
  title: "LUXURY HOTELS",
  description: "Book your stay and enjoy Luxury redefined at the most affordable rates.",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="scroll-smooth">
      <body className={`${montserrat.variable} ${playfair.variable} font-sans text-text-dark antialiased bg-white`}>
        {children}
      </body>
    </html>
  );
}
