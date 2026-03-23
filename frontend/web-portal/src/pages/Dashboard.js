import { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';

const gatewayBase =
  process.env.REACT_APP_GATEWAY_URL?.replace(/\/$/, '') || 'http://localhost:8080';

export default function Dashboard() {
  const location = useLocation();
  const username = location.state?.username || 'demo-user';
  const [accounts, setAccounts] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const res = await fetch(`${gatewayBase}/api/accounts`);
        if (!res.ok) {
          throw new Error(`HTTP ${res.status}`);
        }
        const data = await res.json();
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

  return (
    <div className="page">
      <header className="dash-header">
        <div>
          <h1>Dashboard</h1>
          <p className="muted">
            Signed in as <strong>{username}</strong> · data via API gateway (
            {gatewayBase}
            )
          </p>
        </div>
        <Link to="/" className="link-button">
          Sign out
        </Link>
      </header>

      <section className="panel">
        <h2>Accounts</h2>
        {loading && <p>Loading…</p>}
        {error && (
          <p className="error">
            Could not load accounts: {error}. Ensure the gateway and backend services are running
            (e.g. <code>docker compose up</code>).
          </p>
        )}
        {!loading && !error && accounts && (
          <ul className="account-list">
            {accounts.map((a) => (
              <li key={a.id}>
                <span className="iban">{a.iban}</span>
                <span className="meta">
                  {a.currency} · {a.balance}
                </span>
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
}
