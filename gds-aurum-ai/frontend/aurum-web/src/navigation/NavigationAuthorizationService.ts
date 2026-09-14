import { NavigationItem } from './navigation.config';

export interface NavigationAuthorizationService {
  filterItems(
    items: NavigationItem[],
    permissions: string[],
    capabilities: string[]
  ): NavigationItem[];
}

export class DefaultNavigationAuthorizationService implements NavigationAuthorizationService {
  filterItems(
    items: NavigationItem[],
    permissions: string[],
    capabilities: string[]
  ): NavigationItem[] {
    return items.filter((item) => {
      if (item.requiredPermission) {
        const required = item.requiredPermission.toUpperCase();
        if (!permissions.includes(required)) return false;
      }

      if (item.requiredCapability) {
        const required = item.requiredCapability.toUpperCase();
        if (!capabilities.includes(required)) return false;
      }

      return true;
    });
  }
}

export const navigationAuthorizationService = new DefaultNavigationAuthorizationService();
