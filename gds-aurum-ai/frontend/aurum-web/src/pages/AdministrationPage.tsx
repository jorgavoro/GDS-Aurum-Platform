import React from 'react';
import { Card } from '../components/Card/Card';

const ADMIN_SECTIONS = ['Users', 'Roles', 'Permissions', 'Security'];

export function AdministrationPage() {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)' }}>
      <h1 style={{ fontSize: 'var(--font-size-2xl)', fontWeight: 700 }}>Administration</h1>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(12rem, 1fr))', gap: 'var(--space-4)' }}>
        {ADMIN_SECTIONS.map((section) => (
          <Card key={section}>
            <p style={{ fontWeight: 600, marginBottom: 'var(--space-2)' }}>{section}</p>
            <p style={{ color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
              Manage {section.toLowerCase()}.
            </p>
          </Card>
        ))}
      </div>
    </div>
  );
}
