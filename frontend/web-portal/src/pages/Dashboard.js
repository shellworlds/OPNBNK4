import { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { keycloak } from '../auth/keycloakClient';
import { getJson } from '../services/api';

const mockCustomerId = process.env.REACT_APP_MOCK_CUSTOMER_ID || 'open-sample-uk-001';

export default function Dashboard() {
  const location = useLocation();
  const navigate = useNavigate();
  const username =
    keycloak?.tokenParsed?.preferred_username ||
    location.state?.username ||
    (keycloak ? 'keycloak-user' : 'demo-user');
  const [accounts, setAccounts] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const data = await getJson(`/api/accounts/customer/${encodeURIComponent(mockCustomerId)}`);
        if (!cancelled) {
          setAccounts(data);
        }
      } catch (e) {
        if (!cancelled) {
          setError(e.message || 'Request failed');
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    })();
    return () => {
      cancelled = true;
    };
  }, []);

  const signOut = () => {
    if (keycloak) {
      keycloak.logout({ redirectUri: window.location.origin + '/' });
      return;
    }
    navigate('/', { replace: true });
  };

  return (
    <div className="page">
      <header className="dash-header">
        <div>
          <h1>Dashboard</h1>
          <p className="muted">
            Signed in as <strong>{username}</strong> · customer <code>{mockCustomerId}</code> (mock id for Day 2)
          </p>
        </div>
        <button type="button" className="link-button" onClick={signOut}>
          Sign out
        </button>
      </header>

      <section className="panel">
        <h2>Accounts</h2>
        {loading && (
          <div className="loading-inline" role="status" aria-live="polite">
            <span className="loading-spinner" aria-hidden />
            <span>Loading accounts…</span>
          </div>
        )}
        {error && (
          <p className="error">
            Could not load accounts: {error}. Ensure the gateway and account service are running (for example{' '}
            <code>docker compose up</code>). If APIs require JWT, configure Keycloak and sign in via OIDC.
          </p>
        )}
        {!loading && !error && accounts && (
          <ul className="account-list">
            {accounts.length === 0 && <li className="muted">No accounts for this customer.</li>}
            {accounts.map((a) => (
              <li key={a.id}>
                <Link to={`/accounts/${a.id}`} className="account-link">
                  <span className="iban">{a.accountNumber}</span>
                  <span className="meta">
                    {a.accountType} · {a.currency} · {a.balance}
                  </span>
                </Link>
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
}
