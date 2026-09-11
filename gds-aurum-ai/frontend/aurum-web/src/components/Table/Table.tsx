import React from 'react';

export interface TableColumn<T> {
  key: keyof T | string;
  header: string;
  render?: (row: T) => React.ReactNode;
}

export interface TableProps<T> {
  columns: TableColumn<T>[];
  data: T[];
  keyExtractor: (row: T) => string;
}

export function Table<T>({ columns, data, keyExtractor }: TableProps<T>) {
  return (
    <div style={{ overflowX: 'auto' }}>
      <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: 'var(--font-size-sm)' }}>
        <thead>
          <tr style={{ borderBottom: '2px solid var(--color-border)' }}>
            {columns.map((col) => (
              <th
                key={String(col.key)}
                style={{
                  padding: 'var(--space-3) var(--space-4)',
                  textAlign: 'left',
                  fontWeight: 'var(--font-weight-semibold)',
                  color: 'var(--color-text-secondary)',
                }}
              >
                {col.header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((row) => (
            <tr
              key={keyExtractor(row)}
              style={{ borderBottom: '1px solid var(--color-border)' }}
            >
              {columns.map((col) => (
                <td key={String(col.key)} style={{ padding: 'var(--space-3) var(--space-4)', color: 'var(--color-text-primary)' }}>
                  {col.render ? col.render(row) : String((row as Record<string, unknown>)[String(col.key)] ?? '')}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
