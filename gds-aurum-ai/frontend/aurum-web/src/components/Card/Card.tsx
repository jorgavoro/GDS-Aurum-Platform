import React from 'react';

export interface CardProps {
  children: React.ReactNode;
  title?: string;
  style?: React.CSSProperties;
  className?: string;
}

export function Card({ children, title, style }: CardProps) {
  return (
    <section
      style={{
        backgroundColor: 'var(--color-bg-primary)',
        border: '1px solid var(--color-border)',
        borderRadius: 'var(--radius-lg)',
        padding: 'var(--space-6)',
        boxShadow: 'var(--shadow-sm)',
        ...style,
      }}
    >
      {title && (
        <h2
          style={{
            fontSize: 'var(--font-size-lg)',
            fontWeight: 'var(--font-weight-semibold)',
            marginBottom: 'var(--space-4)',
            color: 'var(--color-text-primary)',
          }}
        >
          {title}
        </h2>
      )}
      {children}
    </section>
  );
}
