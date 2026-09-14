import { useSecurityContext } from './SecurityProvider';
import { SecurityState, AurumUser } from './SecurityState';

export interface UseSecurityResult {
  authenticated: boolean;
  user: AurumUser | null;
  roles: string[];
  permissions: string[];
  capabilities: string[];
  security: SecurityState;
  logout: () => void;
}

export function useSecurity(): UseSecurityResult {
  const { security, logout } = useSecurityContext();

  return {
    authenticated: security.authenticated,
    user: security.user,
    roles: security.roles,
    permissions: security.permissions,
    capabilities: security.capabilities,
    security,
    logout,
  };
}
