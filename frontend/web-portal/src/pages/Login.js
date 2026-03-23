import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { keycloak } from '../auth/keycloakClient';

export default function Login() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [kcError, setKcError] = useState(null);

  useEffect(() => {
    if (!keycloak) {
      return;
    }
    keycloak
      .init({ onLoad: 'login-required', pkceMethod: 'S256', checkLoginIframe: false })
      .then((authenticated) => {
        if (authenticated) {
          navigate('/dashboard', { replace: true });
        }
      })
      .catch((e) => {
        setKcError(e?.message || 'Keycloak init failed');
      });
  }, [navigate]);

  if (keycloak) {
    return (
      <div className="page">
        <h1>Sign in</h1>
        <p className="muted">Opening Keycloak (OIDC authorization code + PKCE)…</p>
        {kcError && <p className="error">{kcError}</p>}
      </div>
    );
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate('/dashboard', { state: { username: username || 'demo-user' } });
  };

  return (
    <div className="page">
      <h1>Sign in</h1>
      <p className="muted">
        Keycloak URL not configured — mock login. Set <code>REACT_APP_KEYCLOAK_URL</code> for OIDC.
      </p>
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
