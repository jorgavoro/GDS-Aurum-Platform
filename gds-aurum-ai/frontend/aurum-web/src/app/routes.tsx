import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { AppShell } from '../layouts/AppShell';
import { ProtectedRoute } from '../security/ProtectedRoute';
import { AuthorizedRoute } from '../security/AuthorizedRoute';
import { HomePage } from '../pages/HomePage';
import { AssistantPage } from '../pages/AssistantPage';
import { AccountingPage } from '../pages/AccountingPage';
import { AnalyticsPage } from '../pages/AnalyticsPage';
import { AdministrationPage } from '../pages/AdministrationPage';
import { ProfilePage } from '../pages/ProfilePage';
import { SecurityPage } from '../pages/SecurityPage';
import { AccessDeniedPage, NotFoundPage, LoginPage } from '../pages/SystemPages';

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/access-denied" element={<AccessDeniedPage />} />

      <Route
        path="/*"
        element={
          <ProtectedRoute>
            <AppShell>
              <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/assistant" element={
                  <AuthorizedRoute requiredCapability="ASSISTANT">
                    <AssistantPage />
                  </AuthorizedRoute>
                } />
                <Route path="/accounting" element={
                  <AuthorizedRoute requiredCapability="ACCOUNTING">
                    <AccountingPage />
                  </AuthorizedRoute>
                } />
                <Route path="/analytics" element={
                  <AuthorizedRoute requiredCapability="ANALYTICS">
                    <AnalyticsPage />
                  </AuthorizedRoute>
                } />
                <Route path="/admin" element={
                  <AuthorizedRoute requiredCapability="ADMINISTRATION">
                    <AdministrationPage />
                  </AuthorizedRoute>
                } />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/security" element={<SecurityPage />} />
                <Route path="*" element={<NotFoundPage />} />
              </Routes>
            </AppShell>
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}
