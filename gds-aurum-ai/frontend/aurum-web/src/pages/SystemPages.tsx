import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../components/Button/Button';

export function AccessDeniedPage() {
  const navigate = useNavigate();
  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100%', gap: 'var(--space-4)', textAlign: 'center' }}>
      <h1 style={{ fontSize: 'var(--font-size-3xl)', fontWeight: 700, color: 'var(--color-error)' }}>403</h1>
      <p style={{ fontSize: 'var(--font-size-xl)', fontWeight: 600 }}>Access Denied</p>
      <p style={{ color: 'var(--color-text-muted)' }}>You do not have permission to access this resource.</p>
      <Button variant="secondary" onClick={() => navigate(-1)}>Go Back</Button>
    </div>
  );
}

export function NotFoundPage() {
  const navigate = useNavigate();
  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100%', gap: 'var(--space-4)', textAlign: 'center' }}>
      <h1 style={{ fontSize: 'var(--font-size-3xl)', fontWeight: 700, color: 'var(--color-text-muted)' }}>404</h1>
      <p style={{ fontSize: 'var(--font-size-xl)', fontWeight: 600 }}>Page Not Found</p>
      <p style={{ color: 'var(--color-text-muted)' }}>The page you are looking for does not exist.</p>
      <Button onClick={() => navigate('/')}>Go Home</Button>
    </div>
  );
}

export function LoginPage() {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100vh', gap: 'var(--space-4)' }}>
      <h1 style={{ fontSize: 'var(--font-size-2xl)', fontWeight: 700 }}>Aurum</h1>
      <p style={{ color: 'var(--color-text-muted)' }}>Login — coming in WEB-002.</p>
    </div>
  );
}
