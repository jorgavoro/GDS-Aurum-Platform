import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSecurity } from '../../security/useSecurity';

export function UserMenu() {
  const [open, setOpen] = useState(false);
  const { user, logout } = useSecurity();
  const navigate = useNavigate();

  const displayName = user ? `${user.firstName} ${user.lastName}` : 'Guest';
  const email = user?.email ?? '';

  const handleLogout = () => {
    setOpen(false);
    logout();
    navigate('/login');
  };

  return (
    <div style={{ position: 'relative' }}>
      <button
        aria-label="User menu"
        aria-expanded={open}
        aria-haspopup="menu"
        onClick={() => setOpen((v) => !v)}
        style={{
          display: 'flex', alignItems: 'center', gap: 'var(--space-2)',
          background: 'none', border: 'none', cursor: 'pointer',
          padding: 'var(--space-2)', borderRadius: 'var(--radius-md)',
          color: 'var(--color-text-primary)', fontSize: 'var(--font-size-sm)',
          fontWeight: 500,
        }}
      >
        <span
          aria-hidden="true"
          style={{
            width: '2rem', height: '2rem', borderRadius: '50%',
            backgroundColor: 'var(--color-accent)', color: '#fff',
            display: 'flex', alignItems: 'center', justifyContent: 'center',
            fontSize: 'var(--font-size-sm)', fontWeight: 600,
          }}
        >
          {displayName.charAt(0).toUpperCase()}
        </span>
        <span>{displayName}</span>
        <span aria-hidden="true">▾</span>
      </button>

      {open && (
        <div
          role="menu"
          style={{
            position: 'absolute', right: 0, top: '2.75rem',
            width: '14rem', backgroundColor: 'var(--color-bg-primary)',
            border: '1px solid var(--color-border)', borderRadius: 'var(--radius-lg)',
            boxShadow: 'var(--shadow-lg)', zIndex: 'var(--z-header)' as React.CSSProperties['zIndex'],
            overflow: 'hidden',
          }}
        >
          <div style={{ padding: 'var(--space-3) var(--space-4)', borderBottom: '1px solid var(--color-border)' }}>
            <p style={{ fontWeight: 600, fontSize: 'var(--font-size-sm)' }}>{displayName}</p>
            <p style={{ fontSize: 'var(--font-size-xs)', color: 'var(--color-text-muted)' }}>{email}</p>
          </div>
          {[
            { label: 'Profile', path: '/profile' },
            { label: 'Security', path: '/security' },
          ].map((item) => (
            <button
              key={item.path}
              role="menuitem"
              onClick={() => { setOpen(false); navigate(item.path); }}
              style={{
                display: 'block', width: '100%', textAlign: 'left',
                padding: 'var(--space-3) var(--space-4)', background: 'none',
                border: 'none', cursor: 'pointer', fontSize: 'var(--font-size-sm)',
                color: 'var(--color-text-primary)',
              }}
            >
              {item.label}
            </button>
          ))}
          <div style={{ borderTop: '1px solid var(--color-border)' }}>
            <button
              role="menuitem"
              onClick={handleLogout}
              style={{
                display: 'block', width: '100%', textAlign: 'left',
                padding: 'var(--space-3) var(--space-4)', background: 'none',
                border: 'none', cursor: 'pointer', fontSize: 'var(--font-size-sm)',
                color: 'var(--color-error)',
              }}
            >
              Logout
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
