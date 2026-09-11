import React, { createContext, useContext, useState } from 'react';
import { CapabilityState, CapabilityInfo, MOCK_CAPABILITIES } from './CapabilityState';

interface CapabilityContextValue {
  state: CapabilityState;
}

const CapabilityContext = createContext<CapabilityContextValue | null>(null);

interface CapabilityProviderProps {
  children: React.ReactNode;
  capabilities?: CapabilityInfo[];
}

export function CapabilityProvider({ children, capabilities = MOCK_CAPABILITIES }: CapabilityProviderProps) {
  const [state] = useState<CapabilityState>({ capabilities, loaded: true });

  return (
    <CapabilityContext.Provider value={{ state }}>
      {children}
    </CapabilityContext.Provider>
  );
}

export function useCapabilityContext(): CapabilityContextValue {
  const ctx = useContext(CapabilityContext);
  if (!ctx) {
    throw new Error('useCapabilityContext must be used within a CapabilityProvider');
  }
  return ctx;
}
