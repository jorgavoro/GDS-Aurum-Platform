export interface NavigationItem {
  id: string;
  label: string;
  path: string;
  icon: string;
  requiredPermission?: string;
  requiredCapability?: string;
  children?: NavigationItem[];
}

export const NAVIGATION_ITEMS: NavigationItem[] = [
  {
    id: 'home',
    label: 'Home',
    path: '/',
    icon: 'home',
  },
  {
    id: 'assistant',
    label: 'AI Assistant',
    path: '/assistant',
    icon: 'assistant',
    requiredCapability: 'ASSISTANT',
  },
  {
    id: 'accounting',
    label: 'Accounting',
    path: '/accounting',
    icon: 'accounting',
    requiredCapability: 'ACCOUNTING',
  },
  {
    id: 'analytics',
    label: 'Analytics',
    path: '/analytics',
    icon: 'analytics',
    requiredCapability: 'ANALYTICS',
  },
  {
    id: 'admin',
    label: 'Administration',
    path: '/admin',
    icon: 'admin',
    requiredCapability: 'ADMINISTRATION',
  },
];

export const BOTTOM_NAVIGATION_ITEMS: NavigationItem[] = [
  {
    id: 'settings',
    label: 'Settings',
    path: '/settings',
    icon: 'settings',
  },
];
