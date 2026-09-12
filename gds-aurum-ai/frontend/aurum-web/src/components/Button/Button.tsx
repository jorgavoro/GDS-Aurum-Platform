import React from 'react';

export type ButtonVariant = 'primary' | 'secondary' | 'ghost' | 'danger';
export type ButtonSize = 'sm' | 'md' | 'lg';

export interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?: ButtonSize;
  loading?: boolean;
  children: React.ReactNode;
}

const styles: Record<string, React.CSSProperties> = {
  base: {
    display: 'inline-flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '0.5rem',
    fontFamily: 'var(--font-family-base)',
    fontWeight: 'var(--font-weight-medium)' as React.CSSProperties['fontWeight'],
    borderRadius: 'var(--radius-md)',
    border: 'none',
    cursor: 'pointer',
    transition: 'background-color var(--transition-fast)',
    outline: 'none',
  },
};

const variantStyles: Record<ButtonVariant, React.CSSProperties> = {
  primary: { backgroundColor: 'var(--color-accent)', color: 'var(--color-text-inverse)' },
  secondary: { backgroundColor: 'var(--color-bg-tertiary)', color: 'var(--color-text-primary)', border: '1px solid var(--color-border)' },
  ghost: { backgroundColor: 'transparent', color: 'var(--color-text-primary)' },
  danger: { backgroundColor: 'var(--color-error)', color: '#ffffff' },
};

const sizeStyles: Record<ButtonSize, React.CSSProperties> = {
  sm: { padding: '0.25rem 0.75rem', fontSize: 'var(--font-size-sm)' },
  md: { padding: '0.5rem 1rem', fontSize: 'var(--font-size-md)' },
  lg: { padding: '0.75rem 1.5rem', fontSize: 'var(--font-size-lg)' },
};

export function Button({
  variant = 'primary',
  size = 'md',
  loading = false,
  disabled,
  children,
  style,
  ...props
}: ButtonProps) {
  return (
    <button
      {...props}
      disabled={disabled || loading}
      style={{ ...styles.base, ...variantStyles[variant], ...sizeStyles[size], ...style }}
      aria-busy={loading}
    >
      {loading ? 'Loading…' : children}
    </button>
  );
}
