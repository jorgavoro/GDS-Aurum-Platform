import React, { createContext, useContext, useState, useCallback } from 'react';
import { SecurityState, EMPTY_SECURITY_STATE } from './SecurityState';

interface SecurityContextValue {
  security: SecurityState;
  setSecurity: (state: SecurityState) => void;
  logout: () => void;
}

const SecurityContext = createContext<SecurityContextValue | null>(null);

interface SecurityProviderProps {
  children: React.ReactNode;
  initialState?: SecurityState;
}

export function SecurityProvider({ children, initialState = EMPTY_SECURITY_STATE }: SecurityProviderProps) {
  const [security, setSecurityState] = useState<SecurityState>(initialState);

  const setSecurity = useCallback((state: SecurityState) => {
    setSecurityState(state);
  }, []);

  const logout = useCallback(() => {
    setSecurityState(EMPTY_SECURITY_STATE);
  }, []);

  return (
    <SecurityContext.Provider value={{ security, setSecurity, logout }}>
      {children}
    </SecurityContext.Provider>
  );
}

export function useSecurityContext(): SecurityContextValue {
  const ctx = useContext(SecurityContext);
  if (!ctx) {
    throw new Error('useSecurityContext must be used within a SecurityProvider');
  }
  return ctx;
}
