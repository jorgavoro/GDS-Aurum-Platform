export interface CapabilityInfo {
  code: string;
  label: string;
  available: boolean;
}

export interface CapabilityState {
  capabilities: CapabilityInfo[];
  loaded: boolean;
}

export const MOCK_CAPABILITIES: CapabilityInfo[] = [
  { code: 'ACCOUNTING', label: 'Accounting', available: true },
  { code: 'ANALYTICS', label: 'Analytics', available: true },
  { code: 'ASSISTANT', label: 'AI Assistant', available: true },
  { code: 'ADMINISTRATION', label: 'Administration', available: true },
];
