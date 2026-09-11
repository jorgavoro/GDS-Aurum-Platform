import React from 'react';
import { NavLink } from 'react-router-dom';
import { NavigationItem, NAVIGATION_ITEMS, BOTTOM_NAVIGATION_ITEMS } from '../../navigation/navigation.config';
import { navigationAuthorizationService } from '../../navigation/NavigationAuthorizationService';
import { useSecurity } from '../../security/useSecurity';

const ICONS: Record<string, string> = {
  home: '⌂',
  assistant: '✦',
  accounting: '⊞',
  analytics: '◈',
  admin: '⚙',
  settings: '⚙',
};

interface SidebarItemProps {
  item: NavigationItem;
  collapsed: boolean;
}

function SidebarItem({ item, collapsed }: SidebarItemProps) {
  return (
    <li>
      <NavLink
        to={item.path}
        end={item.path === '/'}
        style={({ isActive }) => ({
          display: 'flex',
          alignItems: 'center',
          gap: 'var(--space-3)',
          padding: collapsed ? 'var(--space-3)' : 'var(--space-3) var(--space-4)',
          borderRadius: 'var(--radius-md)',
          textDecoration: 'none',
          color: isActive ? 'var(--color-text-sidebar-active)' : 'var(--color-text-sidebar)',
          backgroundColor: isActive ? 'var(--color-sidebar-item-active)' : 'transparent',
          fontSize: 'var(--font-size-sm)',
          fontWeight: isActive ? 600 : 400,
          justifyContent: collapsed ? 'center' : 'flex-start',
          transition: 'background-color var(--transition-fast)',
        })}
        title={collapsed ? item.label : undefined}
      >
        <span aria-hidden="true" style={{ fontSize: '1.1rem', flexShrink: 0 }}>
          {ICONS[item.icon] ?? '•'}
        </span>
        {!collapsed && <span>{item.label}</span>}
      </NavLink>
    </li>
  );
}

interface AppSidebarProps {
  collapsed?: boolean;
  items?: NavigationItem[];
}

export function AppSidebar({ collapsed = false, items = NAVIGATION_ITEMS }: AppSidebarProps) {
  const { permissions, capabilities } = useSecurity();

  const visibleItems = navigationAuthorizationService.filterItems(
    items,
    permissions,
    capabilities
  );

  const visibleBottom = navigationAuthorizationService.filterItems(
    BOTTOM_NAVIGATION_ITEMS,
    permissions,
    capabilities
  );

  return (
    <aside
      aria-label="Main navigation"
      style={{
        width: collapsed ? 'var(--sidebar-collapsed-width)' : 'var(--sidebar-width)',
        backgroundColor: 'var(--color-bg-sidebar)',
        display: 'flex',
        flexDirection: 'column',
        height: '100%',
        transition: 'width var(--transition-base)',
        overflow: 'hidden',
        flexShrink: 0,
      }}
    >
      <nav style={{ flex: 1, padding: 'var(--space-4) var(--space-2)', overflowY: 'auto' }}>
        <ul style={{ listStyle: 'none', display: 'flex', flexDirection: 'column', gap: 'var(--space-1)' }}>
          {visibleItems.map((item) => (
            <SidebarItem key={item.id} item={item} collapsed={collapsed} />
          ))}
        </ul>
      </nav>

      {visibleBottom.length > 0 && (
        <nav aria-label="Bottom navigation" style={{ padding: 'var(--space-4) var(--space-2)', borderTop: '1px solid rgba(255,255,255,0.08)' }}>
          <ul style={{ listStyle: 'none', display: 'flex', flexDirection: 'column', gap: 'var(--space-1)' }}>
            {visibleBottom.map((item) => (
              <SidebarItem key={item.id} item={item} collapsed={collapsed} />
            ))}
          </ul>
        </nav>
      )}
    </aside>
  );
}
