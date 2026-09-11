import React from 'react';

export interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
}

export function Input({ label, error, id, style, ...props }: InputProps) {
  const inputId = id ?? label?.toLowerCase().replace(/\s+/g, '-');

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-1)' }}>
      {label && (
        <label
          htmlFor={inputId}
          style={{
            fontSize: 'var(--font-size-sm)',
            fontWeight: 'var(--font-weight-medium)',
            color: 'var(--color-text-secondary)',
          }}
        >
          {label}
        </label>
      )}
      <input
        id={inputId}
        {...props}
        style={{
          padding: '0.5rem 0.75rem',
          fontSize: 'var(--font-size-md)',
          borderRadius: 'var(--radius-md)',
          border: `1px solid ${error ? 'var(--color-error)' : 'var(--color-border)'}`,
          backgroundColor: 'var(--color-bg-primary)',
          color: 'var(--color-text-primary)',
          outline: 'none',
          width: '100%',
          ...style,
        }}
        aria-invalid={!!error}
        aria-describedby={error ? `${inputId}-error` : undefined}
      />
      {error && (
        <span
          id={`${inputId}-error`}
          role="alert"
          style={{ fontSize: 'var(--font-size-xs)', color: 'var(--color-error)' }}
        >
          {error}
        </span>
      )}
    </div>
  );
}
