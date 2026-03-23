import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import Login from './Login';

function renderLogin() {
  return render(
    <MemoryRouter initialEntries={['/']}>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<div>Dashboard stub</div>} />
      </Routes>
    </MemoryRouter>
  );
}

test('submits mock login and navigates to dashboard', async () => {
  renderLogin();
  await userEvent.type(screen.getByPlaceholderText(/you@example.com/i), 'alice');
  await userEvent.click(screen.getByRole('button', { name: /continue/i }));
  expect(await screen.findByText(/dashboard stub/i)).toBeInTheDocument();
});
