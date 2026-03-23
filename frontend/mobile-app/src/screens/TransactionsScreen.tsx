import { useCallback, useState } from 'react';
import { Button, FlatList, StyleSheet, Text, View } from 'react-native';
import type { Transaction } from '../api';
import { fetchJson } from '../api';

type Props = {
  accountId: string | null;
  accessToken: string | null;
  onBack: () => void;
};

export function TransactionsScreen({ accountId, accessToken, onBack }: Props) {
  const [data, setData] = useState<Transaction[] | null>(null);
  const [err, setErr] = useState<string | null>(null);

  const load = useCallback(async () => {
    if (!accountId) return;
    setErr(null);
    try {
      const list = await fetchJson<Transaction[]>(
        `/api/transactions/account/${encodeURIComponent(accountId)}`,
        accessToken
      );
      setData(list);
    } catch (e: unknown) {
      setErr(e instanceof Error ? e.message : 'Failed');
    }
  }, [accountId, accessToken]);

  if (!accountId) {
    return (
      <View style={styles.box}>
        <Text>No account selected</Text>
        <Button title="Back" onPress={onBack} />
      </View>
    );
  }

  return (
    <View style={styles.box}>
      <Button title="Back" onPress={onBack} />
      <Text style={styles.title}>Transactions</Text>
      <Button title="Refresh" onPress={load} />
      {err ? <Text style={styles.err}>{err}</Text> : null}
      <FlatList
        data={data ?? []}
        keyExtractor={(item) => item.id}
        ListEmptyComponent={data === null ? <Text>Tap refresh</Text> : <Text>No transactions</Text>}
        renderItem={({ item }) => (
          <View style={styles.row}>
            <Text>
              {item.type} {item.amount} {item.currency}
            </Text>
            <Text style={styles.muted}>{item.status}</Text>
          </View>
        )}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  box: { flex: 1, padding: 16 },
  title: { fontSize: 18, fontWeight: '600', marginVertical: 8 },
  err: { color: '#b91c1c' },
  row: { paddingVertical: 8, borderBottomWidth: 1, borderColor: '#e2e8f0' },
  muted: { color: '#64748b' },
});
