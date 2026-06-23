// ─── Brand ───
export const BRAND_NAME = 'Lumière Palace';
export const BRAND_TAGLINE = 'LUXURY HOTEL & RESORT';
export const BADGE_ESTABLISHED = '⚜ ESTABLISHED SINCE 1925';

// ─── Login Hero ───
export const HERO_TITLE = 'Excellence is Our Standard';
export const HERO_SUBTITLE = 'Manage every detail of your property with the precision and elegance your guests deserve.';

export const STAT_1_NUM = '248';
export const STAT_1_LABEL = 'Rooms & Suites';
export const STAT_2_NUM = '98%';
export const STAT_2_LABEL = 'Guest Satisfaction';
export const STAT_3_NUM = '100+';
export const STAT_3_LABEL = 'Years of Luxury';

// ─── Signup Hero ───
export const SIGNUP_HERO_TITLE = 'Your Luxury Experience Awaits';
export const SIGNUP_HERO_SUBTITLE = 'Create an account to book rooms, manage reservations, and enjoy exclusive member benefits.';
export const SIGNUP_FEATURES = [
  'Exclusive member rates & early access',
  'Seamless booking & reservation management',
  'Personalized stay preferences',
  'Loyalty rewards & special offers',
];

// ─── Form Titles ───
export const FORM_TITLE_LOGIN = 'Welcome back';
export const FORM_SUBTITLE_LOGIN = 'Sign in to access your management portal.';
export const FORM_TITLE_SIGNUP = 'Create your account';
export const FORM_SUBTITLE_SIGNUP = 'Join Lumière Palace and unlock exclusive benefits.';

// ─── Social ───
export const SOCIAL_GOOGLE = 'Continue with Google';
export const SOCIAL_FACEBOOK = 'Continue with Facebook';
export const DIVIDER_TEXT = 'or sign in with email';
export const DIVIDER_TEXT_SIGNUP = 'or register with email';

// ─── Login Form ───
export const LABEL_EMAIL = 'Email Address';
export const PLACEHOLDER_EMAIL = 'manager@lumierepalace.com';
export const LABEL_PASSWORD = 'Password';
export const PLACEHOLDER_PASSWORD = '••••••••••';
export const LINK_FORGOT = 'Forgot password?';
export const LINK_SIGNUP = 'Create account';
export const TEXT_SIGNUP_PROMPT = "Don't have an account? ";

// ─── Signup Form ───
export const LABEL_FIRST_NAME = 'First Name';
export const PLACEHOLDER_FIRST_NAME = 'Alexandre';
export const LABEL_LAST_NAME = 'Last Name';
export const PLACEHOLDER_LAST_NAME = 'Martin';
export const LABEL_CONFIRM_PASSWORD = 'Confirm Password';

// ─── Buttons ───
export const BTN_SIGNIN = 'Sign In';
export const BTN_SIGNING = 'Signing in...';
export const BTN_CONTINUE = 'Continue';
export const BTN_BACK = 'Back';
export const BTN_REGISTER = 'Create Account';
export const BTN_REGISTERING = 'Creating account...';

// ─── Links ───
export const LINK_LOGIN = 'Sign in';
export const TEXT_LOGIN_PROMPT = 'Already have an account? ';

// ─── Errors ───
export const ERROR_REQUIRED = 'Vui lòng điền đầy đủ thông tin.';
export const ERROR_PASSWORD_MATCH = 'Mật khẩu xác nhận không khớp.';
export const ERROR_PASSWORD_LENGTH = 'Mật khẩu phải chứa ít nhất 6 ký tự.';
export const ERROR_REGISTER_FAILED = 'Có lỗi xảy ra trong quá trình đăng ký.';

export const DEFAULT_EMAIL = 'manager@lumierepalace.com';

// ─── Roles (assigned by admin, not user-selectable) ───
export const ROLES = {
  CUSTOMER: 'CUSTOMER',
  STAFF: 'STAFF',
  ADMIN: 'ADMIN',
} as const;

export type UserRole = keyof typeof ROLES;
