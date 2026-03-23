import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import Dashboard from './Dashboard';

const originalFetch = global.fetch;

afterEach(() => {
  global.fetch = originalFetch;
});

test('loads accounts from gateway', async () => {
  global.fetch = jest.fn().mockResolvedValue({
    ok: true,
    json: async () => [
      { id: '1', iban: 'GB82TEST', currency: 'GBP', balance: 42 },
    ],
  });

  render(
    <MemoryRouter initialEntries={[{ pathname: '/dashboard', state: { username: 'bob' } }]}>
      <Routes>
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </MemoryRouter>
  );

  expect(screen.getByText(/signed in as/i)).toBeInTheDocument();
  await waitFor(() => expect(screen.getByText('GB82TEST')).toBeInTheDocument());
  expect(global.fetch).toHaveBeenCalled();
});

test('shows error when gateway unreachable', async () => {
  global.fetch = jest.fn().mockRejectedValue(new Error('network'));

  render(
    <MemoryRouter initialEntries={[{ pathname: '/dashboard', state: { username: 'bob' } }]}>
      <Routes>
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </MemoryRouter>
  );

  await waitFor(() => expect(screen.getByText(/could not load accounts/i)).toBeInTheDocument());
});
