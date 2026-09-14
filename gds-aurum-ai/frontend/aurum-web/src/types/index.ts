export type Theme = 'light' | 'dark';

export type NotificationSeverity = 'info' | 'success' | 'warning' | 'error';

export interface Notification {
  id: string;
  severity: NotificationSeverity;
  message: string;
  timestamp: Date;
  read: boolean;
}

export interface BreadcrumbItem {
  label: string;
  path?: string;
}

export interface RouteConfig {
  path: string;
  label: string;
  breadcrumb?: string;
  requiredPermission?: string;
  requiredCapability?: string;
}
