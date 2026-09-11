export interface AurumUser {
  id: string;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  status: 'ACTIVE' | 'PENDING' | 'LOCKED' | 'DISABLED' | 'DELETED';
}

export interface SecurityState {
  authenticated: boolean;
  user: AurumUser | null;
  roles: string[];
  permissions: string[];
  capabilities: string[];
}

export const EMPTY_SECURITY_STATE: SecurityState = {
  authenticated: false,
  user: null,
  roles: [],
  permissions: [],
  capabilities: [],
};
