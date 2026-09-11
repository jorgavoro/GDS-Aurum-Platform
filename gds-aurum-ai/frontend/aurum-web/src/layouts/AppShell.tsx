import React, { useState } from 'react';
import { AppHeader } from '../components/AppHeader/AppHeader';
import { AppSidebar } from '../components/AppSidebar/AppSidebar';
import { Breadcrumbs } from '../components/Breadcrumbs/Breadcrumbs';

interface AppShellProps {
  children: React.ReactNode;
}

export function AppShell({ children }: AppShellProps) {
  const [sidebarCollapsed, setSidebarCollapsed] = useState(false);

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        height: '100vh',
        overflow: 'hidden',
      }}
    >
      <AppHeader onToggleSidebar={() => setSidebarCollapsed((v) => !v)} />

      <div style={{ display: 'flex', flex: 1, overflow: 'hidden' }}>
        <AppSidebar collapsed={sidebarCollapsed} />

        <main
          id="main-content"
          style={{
            flex: 1,
            overflowY: 'auto',
            backgroundColor: 'var(--color-bg-secondary)',
            display: 'flex',
            flexDirection: 'column',
          }}
        >
          <div
            style={{
              padding: 'var(--space-4) var(--space-6)',
              borderBottom: '1px solid var(--color-border)',
              backgroundColor: 'var(--color-bg-primary)',
            }}
          >
            <Breadcrumbs />
          </div>

          <div style={{ flex: 1, padding: 'var(--space-6)' }}>
            {children}
          </div>
        </main>
      </div>
    </div>
  );
}
