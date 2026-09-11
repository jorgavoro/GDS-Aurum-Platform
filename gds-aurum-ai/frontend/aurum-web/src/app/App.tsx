import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { MockSecurityProvider } from '../security/MockSecurityProvider';
import { CapabilityProvider } from '../capabilities/CapabilityProvider';
import { ApplicationErrorBoundary } from '../components/ErrorBoundary/ApplicationErrorBoundary';
import { AppRoutes } from './routes';
import '../styles/design-tokens.css';

export function App() {
  return (
    <ApplicationErrorBoundary>
      <MockSecurityProvider>
        <CapabilityProvider>
          <BrowserRouter>
            <AppRoutes />
          </BrowserRouter>
        </CapabilityProvider>
      </MockSecurityProvider>
    </ApplicationErrorBoundary>
  );
}
