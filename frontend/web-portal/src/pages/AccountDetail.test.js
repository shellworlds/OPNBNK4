import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import AccountDetail from './AccountDetail';

jest.mock('../services/api', () => ({
  getJson: jest.fn(),
}));

import { getJson } from '../services/api';

test('shows account and transactions', async () => {
  getJson
    .mockResolvedValueOnce({
      id: 'acc-1',
      accountNumber: 'GB11DEMO000000000001',
      accountType: 'CHECKING',
      currency: 'GBP',
      balance: 100,
      status: 'ACTIVE',
    })
    .mockResolvedValueOnce([
      {
        id: 't1',
        type: 'DEBIT',
        amount: 10,
        currency: 'GBP',
        status: 'COMPLETED',
        reference: 'Coffee',
      },
    ]);

  render(
    <MemoryRouter initialEntries={['/accounts/acc-1']}>
      <Routes>
        <Route path="/accounts/:id" element={<AccountDetail />} />
      </Routes>
    </MemoryRouter>
  );

  await waitFor(() => expect(screen.getByText('GB11DEMO000000000001')).toBeInTheDocument());
  expect(screen.getByText('DEBIT')).toBeInTheDocument();
  expect(screen.getByText(/Coffee/)).toBeInTheDocument();
});
