export interface RequestOptions {
  headers?: Record<string, string>;
  signal?: AbortSignal;
}

export interface ApiResponse<T> {
  data: T;
  status: number;
}

export class ApiClient {
  private readonly baseUrl: string;

  constructor(baseUrl: string = '/api') {
    this.baseUrl = baseUrl;
  }

  async get<T>(path: string, options?: RequestOptions): Promise<ApiResponse<T>> {
    const response = await fetch(`${this.baseUrl}${path}`, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json', ...options?.headers },
      signal: options?.signal,
    });
    const data = await response.json();
    return { data, status: response.status };
  }

  async post<T>(path: string, body: unknown, options?: RequestOptions): Promise<ApiResponse<T>> {
    const response = await fetch(`${this.baseUrl}${path}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', ...options?.headers },
      body: JSON.stringify(body),
      signal: options?.signal,
    });
    const data = await response.json();
    return { data, status: response.status };
  }
}

export const apiClient = new ApiClient();
