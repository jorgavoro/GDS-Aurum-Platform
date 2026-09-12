import { CapabilityInfo } from '../capabilities/CapabilityState';
import { ApiClient } from './ApiClient';

export interface CapabilityService {
  getAvailableCapabilities(): Promise<CapabilityInfo[]>;
}

export class DefaultCapabilityService implements CapabilityService {
  constructor(private readonly client: ApiClient) {}

  async getAvailableCapabilities(): Promise<CapabilityInfo[]> {
    try {
      const response = await this.client.get<CapabilityInfo[]>('/capabilities');
      return response.data;
    } catch {
      return [];
    }
  }
}
