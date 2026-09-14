import React, { useState } from 'react';
import { Card } from '../components/Card/Card';
import { Input } from '../components/Input/Input';
import { Button } from '../components/Button/Button';
import { EmptyState } from '../components/EmptyState/EmptyState';

export function AssistantPage() {
  const [input, setInput] = useState('');

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)', height: '100%' }}>
      <h1 style={{ fontSize: 'var(--font-size-2xl)', fontWeight: 700 }}>Aurum Assistant</h1>

      <Card style={{ flex: 1, minHeight: '20rem' }}>
        <EmptyState
          title="Start a conversation"
          description="Ask Aurum Assistant anything about your business data."
        />
      </Card>

      <div style={{ display: 'flex', gap: 'var(--space-3)' }}>
        <div style={{ flex: 1 }}>
          <Input
            placeholder="Ask something…"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            aria-label="Assistant input"
          />
        </div>
        <Button disabled={!input.trim()}>Send</Button>
      </div>
    </div>
  );
}
