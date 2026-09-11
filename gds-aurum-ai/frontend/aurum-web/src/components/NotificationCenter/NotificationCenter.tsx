import React, { useState } from 'react';
import { Notification } from '../../types';

const MOCK_NOTIFICATIONS: Notification[] = [
  { id: '1', severity: 'info', message: 'System ready.', timestamp: new Date(), read: false },
];

export function NotificationCenter() {
  const [open, setOpen] = useState(false);
  const [notifications] = useState<Notification[]>(MOCK_NOTIFICATIONS);
  const unread = notifications.filter((n) => !n.read).length;

  return (
    <div style={{ position: 'relative' }}>
      <button
        aria-label={`Notifications${unread > 0 ? `, ${unread} unread` : ''}`}
        onClick={() => setOpen((v) => !v)}
        style={{
          background: 'none', border: 'none', cursor: 'pointer',
          padding: 'var(--space-2)', borderRadius: 'var(--radius-md)',
          color: 'var(--color-text-secondary)', position: 'relative',
          fontSize: 'var(--font-size-lg)',
        }}
      >
        🔔
        {unread > 0 && (
          <span
            aria-hidden="true"
            style={{
              position: 'absolute', top: 0, right: 0,
              width: '0.875rem', height: '0.875rem',
              backgroundColor: 'var(--color-error)', borderRadius: '50%',
              fontSize: '0.625rem', color: '#fff',
              display: 'flex', alignItems: 'center', justifyContent: 'center',
            }}
          >
            {unread}
          </span>
        )}
      </button>

      {open && (
        <div
          role="region"
          aria-label="Notifications"
          style={{
            position: 'absolute', right: 0, top: '2.5rem',
            width: '18rem', backgroundColor: 'var(--color-bg-primary)',
            border: '1px solid var(--color-border)', borderRadius: 'var(--radius-lg)',
            boxShadow: 'var(--shadow-lg)', zIndex: 'var(--z-header)' as React.CSSProperties['zIndex'],
          }}
        >
          <div style={{ padding: 'var(--space-3) var(--space-4)', borderBottom: '1px solid var(--color-border)', fontWeight: 600 }}>
            Notifications
          </div>
          {notifications.length === 0 ? (
            <p style={{ padding: 'var(--space-4)', color: 'var(--color-text-muted)', fontSize: 'var(--font-size-sm)' }}>
              No notifications.
            </p>
          ) : (
            <ul style={{ listStyle: 'none', maxHeight: '20rem', overflowY: 'auto' }}>
              {notifications.map((n) => (
                <li key={n.id} style={{ padding: 'var(--space-3) var(--space-4)', borderBottom: '1px solid var(--color-border)', fontSize: 'var(--font-size-sm)' }}>
                  {n.message}
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
}
