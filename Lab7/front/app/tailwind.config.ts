/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  safelist: [
    'animate-fade-in-down',
    'animate-sound-wave',
    'animate-wave'
  ],
  theme: {
    extend: {
      keyframes: {
        'fade-in-down': {
          '0%': {
            opacity: '0',
            transform: 'translateY(-10px)'
          },
          '100%': {
            opacity: '1',
            transform: 'translateY(0)'
          },
        },
        'sound-wave': {
          '0%, 100%': {
            transform: 'scaleY(1)'
          },
          '50%': {
            transform: 'scaleY(0.6)'
          },
        },
        'wave-animation': {
          '0%, 100%': {
            height: '4px'
          },
          '50%': {
            height: '16px'
          }
        }
      },
      animation: {
        'fade-in-down': 'fade-in-down 0.3s ease-out',
        'sound-wave': 'sound-wave 1s ease-in-out infinite',
        'wave': 'wave-animation 1s ease-in-out infinite',
      }
    },
  },
  plugins: [
    require('daisyui'),
  ],
  daisyui: {
    themes: [
      "valentine",
      "synthwave",
      "forest",
      "winter",
      "lemonade",
      "dracula",
      "cmyk",
      "autumn",
      "pastel",
    ],
  },
}