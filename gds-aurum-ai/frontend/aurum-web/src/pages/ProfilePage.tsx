import React from 'react';
import { Card } from '../components/Card/Card';
import { Badge } from '../components/Badge/Badge';
import { useSecurity } from '../security/useSecurity';

export function ProfilePage() {
  const { user, roles } = useSecurity();

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)' }}>
      <h1 style={{ fontSize: 'var(--font-size-2xl)', fontWeight: 700 }}>Profile</h1>
      <Card style={{ maxWidth: '32rem' }}>
        <dl style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
          {[
            { label: 'Name', value: user ? `${user.firstName} ${user.lastName}` : '—' },
            { label: 'Email', value: user?.email ?? '—' },
            { label: 'Status', value: user?.status ?? '—' },
          ].map(({ label, value }) => (
            <div key={label}>
              <dt style={{ fontSize: 'var(--font-size-xs)', color: 'var(--color-text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                {label}
              </dt>
              <dd style={{ marginTop: 'var(--space-1)', fontWeight: 500 }}>{value}</dd>
            </div>
          ))}
          <div>
            <dt style={{ fontSize: 'var(--font-size-xs)', color: 'var(--color-text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
              Roles
            </dt>
            <dd style={{ marginTop: 'var(--space-2)', display: 'flex', flexWrap: 'wrap', gap: 'var(--space-2)' }}>
              {roles.length > 0
                ? roles.map((r) => <Badge key={r} variant="info">{r}</Badge>)
                : <span style={{ color: 'var(--color-text-muted)' }}>No roles assigned.</span>}
            </dd>
          </div>
        </dl>
      </Card>
    </div>
  );
}
