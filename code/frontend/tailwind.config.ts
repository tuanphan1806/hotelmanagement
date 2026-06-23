import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          navy: "#14274A",
          DEFAULT: "#14274A",
        },
        secondary: {
          navy: "#2c3e50",
          DEFAULT: "#2c3e50",
        },
        accent: {
          gold: "#E0B973",
          yellow: "#eab308",
          DEFAULT: "#E0B973",
        },
        bg: {
          light: "#f5f5f5",
        },
        text: {
          dark: "#333333",
          light: "#666666",
        },
        border: {
          light: "#e0e0e0",
        }
      },
    },
  },
  plugins: [],
};
export default config;
