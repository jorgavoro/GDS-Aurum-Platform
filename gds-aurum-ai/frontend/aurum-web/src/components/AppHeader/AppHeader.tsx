import React from 'react';
import { NotificationCenter } from '../NotificationCenter/NotificationCenter';
import { UserMenu } from '../UserMenu/UserMenu';

interface AppHeaderProps {
  onToggleSidebar?: () => void;
}

export function AppHeader({ onToggleSidebar }: AppHeaderProps) {
  return (
    <header
      style={{
        height: 'var(--header-height)',
        backgroundColor: 'var(--color-bg-header)',
        borderBottom: '1px solid var(--color-border)',
        display: 'flex',
        alignItems: 'center',
        padding: '0 var(--space-6)',
        position: 'sticky',
        top: 0,
        zIndex: 'var(--z-header)' as React.CSSProperties['zIndex'],
        gap: 'var(--space-4)',
      }}
    >
      {onToggleSidebar && (
        <button
          aria-label="Toggle sidebar"
          onClick={onToggleSidebar}
          style={{
            background: 'none', border: 'none', cursor: 'pointer',
            padding: 'var(--space-2)', borderRadius: 'var(--radius-md)',
            color: 'var(--color-text-secondary)', fontSize: 'var(--font-size-lg)',
          }}
        >
          ☰
        </button>
      )}

      <div style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-3)', flex: 1 }}>
        <span
          aria-hidden="true"
          style={{
            width: '2rem', height: '2rem', borderRadius: 'var(--radius-md)',
            backgroundColor: 'var(--color-accent)', display: 'flex',
            alignItems: 'center', justifyContent: 'center',
            color: '#fff', fontWeight: 700, fontSize: 'var(--font-size-sm)',
          }}
        >
          A
        </span>
        <span style={{ fontWeight: 700, fontSize: 'var(--font-size-lg)', color: 'var(--color-text-primary)' }}>
          Aurum
        </span>
      </div>

      <nav aria-label="Header actions" style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-2)' }}>
        <NotificationCenter />
        <button
          aria-label="Help"
          style={{
            background: 'none', border: 'none', cursor: 'pointer',
            padding: 'var(--space-2)', borderRadius: 'var(--radius-md)',
            color: 'var(--color-text-secondary)', fontSize: 'var(--font-size-lg)',
          }}
        >
          ?
        </button>
        <UserMenu />
      </nav>
    </header>
  );
}
