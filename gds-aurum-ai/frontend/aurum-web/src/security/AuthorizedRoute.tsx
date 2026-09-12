import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthorization } from './useAuthorization';

interface AuthorizedRouteProps {
  children: React.ReactNode;
  requiredPermission?: string;
  requiredCapability?: string;
}

export function AuthorizedRoute({ children, requiredPermission, requiredCapability }: AuthorizedRouteProps) {
  const { hasPermission, hasCapability } = useAuthorization();

  if (requiredPermission && !hasPermission(requiredPermission)) {
    return <Navigate to="/access-denied" replace />;
  }

  if (requiredCapability && !hasCapability(requiredCapability)) {
    return <Navigate to="/access-denied" replace />;
  }

  return <>{children}</>;
}
