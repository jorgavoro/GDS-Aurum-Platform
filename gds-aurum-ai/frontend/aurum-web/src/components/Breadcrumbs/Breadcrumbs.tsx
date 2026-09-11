import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { BreadcrumbItem } from '../../types';

const ROUTE_LABELS: Record<string, string> = {
  '': 'Home',
  assistant: 'AI Assistant',
  accounting: 'Accounting',
  analytics: 'Analytics',
  admin: 'Administration',
  profile: 'Profile',
  security: 'Security',
  'access-denied': 'Access Denied',
};

function buildBreadcrumbs(pathname: string): BreadcrumbItem[] {
  const segments = pathname.split('/').filter(Boolean);
  const crumbs: BreadcrumbItem[] = [{ label: 'Home', path: '/' }];

  let accumulated = '';
  for (const segment of segments) {
    accumulated += `/${segment}`;
    crumbs.push({
      label: ROUTE_LABELS[segment] ?? segment,
      path: accumulated,
    });
  }

  return crumbs;
}

export function Breadcrumbs() {
  const { pathname } = useLocation();
  const crumbs = buildBreadcrumbs(pathname);

  if (crumbs.length <= 1) return null;

  return (
    <nav aria-label="Breadcrumb">
      <ol style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-2)', listStyle: 'none', fontSize: 'var(--font-size-sm)', color: 'var(--color-text-muted)' }}>
        {crumbs.map((crumb, index) => {
          const isLast = index === crumbs.length - 1;
          return (
            <li key={crumb.path ?? crumb.label} style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-2)' }}>
              {index > 0 && <span aria-hidden="true">›</span>}
              {isLast || !crumb.path ? (
                <span aria-current={isLast ? 'page' : undefined} style={{ color: isLast ? 'var(--color-text-primary)' : undefined }}>
                  {crumb.label}
                </span>
              ) : (
                <Link to={crumb.path} style={{ color: 'var(--color-accent)', textDecoration: 'none' }}>
                  {crumb.label}
                </Link>
              )}
            </li>
          );
        })}
      </ol>
    </nav>
  );
}
