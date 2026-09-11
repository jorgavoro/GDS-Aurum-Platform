import { useSecurity } from './useSecurity';

export interface UseAuthorizationResult {
  hasPermission: (permission: string) => boolean;
  hasRole: (role: string) => boolean;
  hasCapability: (capability: string) => boolean;
  hasAnyPermission: (permissions: string[]) => boolean;
  hasAllPermissions: (permissions: string[]) => boolean;
}

export function useAuthorization(): UseAuthorizationResult {
  const { permissions, roles, capabilities } = useSecurity();

  const hasPermission = (permission: string): boolean =>
    permissions.includes(permission.toUpperCase());

  const hasRole = (role: string): boolean =>
    roles.includes(role.toUpperCase());

  const hasCapability = (capability: string): boolean =>
    capabilities.includes(capability.toUpperCase());

  const hasAnyPermission = (required: string[]): boolean =>
    required.some((p) => hasPermission(p));

  const hasAllPermissions = (required: string[]): boolean =>
    required.every((p) => hasPermission(p));

  return { hasPermission, hasRole, hasCapability, hasAnyPermission, hasAllPermissions };
}
