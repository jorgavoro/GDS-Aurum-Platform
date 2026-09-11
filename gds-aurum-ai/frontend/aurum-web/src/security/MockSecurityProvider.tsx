import React from 'react';
import { SecurityProvider } from './SecurityProvider';
import { SecurityState } from './SecurityState';

const MOCK_SECURITY_STATE: SecurityState = {
  authenticated: true,
  user: {
    id: 'mock-user-001',
    username: 'jorge',
    email: 'jorge@aurum.com',
    firstName: 'Jorge',
    lastName: 'Aurum',
    status: 'ACTIVE',
  },
  roles: ['ACCOUNTING_SUPERVISOR'],
  permissions: [
    'ACCOUNTING.JOURNAL_ENTRY.READ',
    'ACCOUNTING.JOURNAL_ENTRY.CREATE',
    'ACCOUNTING.JOURNAL_ENTRY.UPDATE',
    'ACCOUNTING.JOURNAL_ENTRY.POST',
    'ACCOUNTING.JOURNAL_ENTRY.REVERSE',
    'ACCOUNTING.JOURNAL_ENTRY.APPROVE',
    'ACCOUNTING.JOURNAL_ENTRY.PRINT',
  ],
  capabilities: ['ACCOUNTING'],
};

interface MockSecurityProviderProps {
  children: React.ReactNode;
}

export function MockSecurityProvider({ children }: MockSecurityProviderProps) {
  return (
    <SecurityProvider initialState={MOCK_SECURITY_STATE}>
      {children}
    </SecurityProvider>
  );
}
