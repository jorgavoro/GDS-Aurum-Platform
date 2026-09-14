import React from 'react';
import { Card } from '../components/Card/Card';
import { EmptyState } from '../components/EmptyState/EmptyState';

export function AccountingPage() {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)' }}>
      <h1 style={{ fontSize: 'var(--font-size-2xl)', fontWeight: 700 }}>Accounting</h1>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(16rem, 1fr))', gap: 'var(--space-4)' }}>
        <Card title="Journal Entries">
          <EmptyState title="No entries" description="Journal entries will appear here." />
        </Card>
        <Card title="Available Capabilities">
          <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
            ACCOUNTING capability active.
          </p>
        </Card>
        <Card title="Dashboard">
          <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
            Accounting dashboard placeholder.
          </p>
        </Card>
      </div>
    </div>
  );
}
