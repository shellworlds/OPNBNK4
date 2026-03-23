import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { getJson } from '../services/api';

export default function AccountDetail() {
  const { id } = useParams();
  const [account, setAccount] = useState(null);
  const [transactions, setTransactions] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const [acc, txs] = await Promise.all([
          getJson(`/api/accounts/${encodeURIComponent(id)}`),
          getJson(`/api/transactions/account/${encodeURIComponent(id)}`),
        ]);
        if (!cancelled) {
          setAccount(acc);
          setTransactions(txs);
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
  }, [id]);

  return (
    <div className="page">
      <header className="dash-header">
        <div>
          <h1>Account</h1>
          <p className="muted">
            <Link to="/dashboard" className="link-inline">
              ← Back to dashboard
            </Link>
          </p>
        </div>
      </header>

      {loading && (
        <div className="loading-inline" role="status" aria-live="polite">
          <span className="loading-spinner" aria-hidden />
          <span>Loading account…</span>
        </div>
      )}
      {error && <p className="error">{error}</p>}

      {!loading && !error && account && (
        <section className="panel">
          <h2>{account.accountNumber}</h2>
          <p className="muted">
            {account.accountType} · {account.currency} · balance {account.balance} · {account.status}
          </p>
        </section>
      )}

      {!loading && !error && transactions && (
        <section className="panel">
          <h2>Transactions</h2>
          {transactions.length === 0 && <p className="muted">No transactions yet.</p>}
          <ul className="tx-list">
            {transactions.map((t) => (
              <li key={t.id}>
                <span className={`tx-type tx-${(t.type || '').toLowerCase()}`}>{t.type}</span>
                <span className="tx-amt">
                  {t.amount} {t.currency}
                </span>
                <span
                  className={'tx-meta' + (t.status === 'UNDER_REVIEW' ? ' tx-review' : '')}
                  title={t.status === 'UNDER_REVIEW' ? 'Held for fraud review' : undefined}>
                  {t.status}
                </span>
                <span className="tx-desc">{t.description || t.reference || '—'}</span>
              </li>
            ))}
          </ul>
        </section>
      )}
    </div>
  );
}
