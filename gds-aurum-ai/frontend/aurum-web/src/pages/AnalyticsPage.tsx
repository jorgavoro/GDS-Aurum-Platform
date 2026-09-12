import React from 'react';
import { Card } from '../components/Card/Card';

export function AnalyticsPage() {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)' }}>
      <h1 style={{ fontSize: 'var(--font-size-2xl)', fontWeight: 700 }}>Analytics</h1>
      <Card title="Financial Analytics">
        <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
          Financial analytics placeholder.
        </p>
      </Card>
    </div>
  );
}
