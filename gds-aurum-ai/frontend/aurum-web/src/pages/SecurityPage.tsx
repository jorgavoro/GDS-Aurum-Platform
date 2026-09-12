import React from 'react';
import { Card } from '../components/Card/Card';

const SECURITY_SECTIONS = ['Authentication', 'Connected Identities', 'Sessions', 'Security Settings'];

export function SecurityPage() {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)' }}>
      <h1 style={{ fontSize: 'var(--font-size-2xl)', fontWeight: 700 }}>Security</h1>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(14rem, 1fr))', gap: 'var(--space-4)' }}>
        {SECURITY_SECTIONS.map((section) => (
          <Card key={section}>
            <p style={{ fontWeight: 600, marginBottom: 'var(--space-2)' }}>{section}</p>
            <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
              {section} placeholder.
            </p>
          </Card>
        ))}
      </div>
    </div>
  );
}
