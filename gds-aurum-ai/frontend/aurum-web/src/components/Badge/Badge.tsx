import React from 'react';
import { NotificationSeverity } from '../../types';

export type BadgeVariant = NotificationSeverity | 'default';

export interface BadgeProps {
  children: React.ReactNode;
  variant?: BadgeVariant;
}

const variantStyles: Record<BadgeVariant, React.CSSProperties> = {
  default: { backgroundColor: 'var(--color-bg-tertiary)', color: 'var(--color-text-secondary)' },
  info: { backgroundColor: 'var(--color-info-light)', color: 'var(--color-info)' },
  success: { backgroundColor: 'var(--color-success-light)', color: 'var(--color-success)' },
  warning: { backgroundColor: 'var(--color-warning-light)', color: 'var(--color-warning)' },
  error: { backgroundColor: 'var(--color-error-light)', color: 'var(--color-error)' },
};

export function Badge({ children, variant = 'default' }: BadgeProps) {
  return (
    <span
      style={{
        display: 'inline-flex',
        alignItems: 'center',
        padding: '0.125rem 0.5rem',
        borderRadius: 'var(--radius-full)',
        fontSize: 'var(--font-size-xs)',
        fontWeight: 'var(--font-weight-medium)',
        ...variantStyles[variant],
      }}
    >
      {children}
    </span>
  );
}
