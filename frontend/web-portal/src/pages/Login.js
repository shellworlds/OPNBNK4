import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate('/dashboard', { state: { username: username || 'demo-user' } });
  };

  return (
    <div className="page">
      <h1>Sign in</h1>
      <p className="muted">Mock login — any name continues to the dashboard.</p>
      <form onSubmit={handleSubmit} className="form">
        <label>
          Username
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="you@example.com"
            autoComplete="username"
          />
        </label>
        <button type="submit">Continue</button>
      </form>
    </div>
  );
}
