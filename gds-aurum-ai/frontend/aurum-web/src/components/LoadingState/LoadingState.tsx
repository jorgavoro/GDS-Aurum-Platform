import React from 'react';

export function LoadingState({ message = 'Loading…' }: { message?: string }) {
  return (
    <div
      role="status"
      aria-live="polite"
      style={{
        display: 'flex', flexDirection: 'column', alignItems: 'center',
        justifyContent: 'center', padding: 'var(--space-16)',
        color: 'var(--color-text-muted)', gap: 'var(--space-4)',
      }}
    >
      <div
        aria-hidden="true"
        style={{
          width: '2rem', height: '2rem',
          border: '3px solid var(--color-border)',
          borderTopColor: 'var(--color-accent)',
          borderRadius: '50%',
          animation: 'spin 0.8s linear infinite',
        }}
      />
      <span>{message}</span>
      <style>{`@keyframes spin { to { transform: rotate(360deg); } }`}</style>
    </div>
  );
}
