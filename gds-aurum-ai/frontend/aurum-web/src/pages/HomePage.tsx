import React from 'react';
import { Card } from '../components/Card/Card';
import { useSecurity } from '../security/useSecurity';

export function HomePage() {
  const { user } = useSecurity();

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)' }}>
      <div>
        <h1 style={{ fontSize: 'var(--font-size-3xl)', fontWeight: 700, marginBottom: 'var(--space-2)' }}>
          Welcome{user ? `, ${user.firstName}` : ''}
        </h1>
        <p style={{ color: 'var(--color-text-secondary)' }}>
          Aurum Platform — your integrated business management workspace.
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(16rem, 1fr))', gap: 'var(--space-4)' }}>
        <Card title="Quick Actions">
          <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
            Quick actions will appear here.
          </p>
        </Card>
        <Card title="Recent Activity">
          <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
            Recent activity will appear here.
          </p>
        </Card>
        <Card title="Application Overview">
          <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
            Accounting · Analytics · AI Assistant · Administration
          </p>
        </Card>
      </div>
    </div>
  );
}
