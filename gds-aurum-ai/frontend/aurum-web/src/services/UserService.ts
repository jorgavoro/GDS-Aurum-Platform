import { AurumUser } from '../security/SecurityState';
import { ApiClient } from './ApiClient';

export interface UserService {
  getCurrentUser(): Promise<AurumUser | null>;
}

export class DefaultUserService implements UserService {
  constructor(private readonly client: ApiClient) {}

  async getCurrentUser(): Promise<AurumUser | null> {
    try {
      const response = await this.client.get<AurumUser>('/users/me');
      return response.data;
    } catch {
      return null;
    }
  }
}
