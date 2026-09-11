import React from 'react';

export interface ErrorStateProps {
  message?: string;
  onRetry?: () => void;
  onBack?: () => void;
}

export function ErrorState({
  message = 'Something went wrong.',
  onRetry,
  onBack,
}: ErrorStateProps) {
  return (
    <div
      role="alert"
      style={{
        display: 'flex', flexDirection: 'column', alignItems: 'center',
        justifyContent: 'center', padding: 'var(--space-16)',
        gap: 'var(--space-4)', textAlign: 'center',
      }}
    >
      <p style={{ fontSize: 'var(--font-size-lg)', fontWeight: 'var(--font-weight-semibold)', color: 'var(--color-error)' }}>
        {message}
      </p>
      <div style={{ display: 'flex', gap: 'var(--space-3)' }}>
        {onRetry && (
          <button onClick={onRetry} style={{ padding: '0.5rem 1rem', cursor: 'pointer', borderRadius: 'var(--radius-md)', border: '1px solid var(--color-border)', background: 'var(--color-bg-secondary)', color: 'var(--color-text-primary)' }}>
            Retry
          </button>
        )}
        {onBack && (
          <button onClick={onBack} style={{ padding: '0.5rem 1rem', cursor: 'pointer', borderRadius: 'var(--radius-md)', border: 'none', background: 'var(--color-accent)', color: '#fff' }}>
            Go Back
          </button>
        )}
      </div>
    </div>
  );
}
