import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import Dashboard from './Dashboard';

jest.mock('../services/api', () => ({
  getJson: jest.fn(),
}));

import { getJson } from '../services/api';

afterEach(() => {
  jest.clearAllMocks();
});

test('loads accounts for mock customer via gateway', async () => {
  getJson.mockResolvedValue([
    {
      id: '1',
      accountNumber: 'GB82TEST',
      accountType: 'CHECKING',
      currency: 'GBP',
      balance: 42,
    },
  ]);

  render(
    <MemoryRouter initialEntries={[{ pathname: '/dashboard', state: { username: 'bob' } }]}>
      <Routes>
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </MemoryRouter>
  );

  expect(screen.getByText(/signed in as/i)).toBeInTheDocument();
  await waitFor(() => expect(screen.getByText('GB82TEST')).toBeInTheDocument());
  expect(getJson).toHaveBeenCalled();
});

test('shows error when gateway unreachable', async () => {
  getJson.mockRejectedValue(new Error('network'));

  render(
    <MemoryRouter initialEntries={[{ pathname: '/dashboard', state: { username: 'bob' } }]}>
      <Routes>
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </MemoryRouter>
  );

  await waitFor(() => expect(screen.getByText(/could not load accounts/i)).toBeInTheDocument());
});
