import { useCapabilityContext } from './CapabilityProvider';
import { CapabilityInfo } from './CapabilityState';

export interface UseCapabilitiesResult {
  isAvailable: (code: string) => boolean;
  getCapabilities: () => CapabilityInfo[];
  loaded: boolean;
}

export function useCapabilities(): UseCapabilitiesResult {
  const { state } = useCapabilityContext();

  const isAvailable = (code: string): boolean =>
    state.capabilities.some((c) => c.code.toUpperCase() === code.toUpperCase() && c.available);

  const getCapabilities = (): CapabilityInfo[] => state.capabilities;

  return { isAvailable, getCapabilities, loaded: state.loaded };
}
