import { useCallback, useState } from 'react';
import { Button, FlatList, StyleSheet, Text, View } from 'react-native';
import type { Account } from '../api';
import { fetchJson } from '../api';
import { MOCK_CUSTOMER_ID } from '../config';

type Props = {
  accessToken: string | null;
  onSelectAccount: (id: string) => void;
};

export function DashboardScreen({ accessToken, onSelectAccount }: Props) {
  const [data, setData] = useState<Account[] | null>(null);
  const [err, setErr] = useState<string | null>(null);

  const load = useCallback(async () => {
    setErr(null);
    try {
      const list = await fetchJson<Account[]>(
        `/api/accounts/customer/${encodeURIComponent(MOCK_CUSTOMER_ID)}`,
        accessToken
      );
      setData(list);
    } catch (e: unknown) {
      setErr(e instanceof Error ? e.message : 'Failed');
    }
  }, [accessToken]);

  return (
    <View style={styles.box}>
      <Text style={styles.title}>Accounts ({MOCK_CUSTOMER_ID})</Text>
      <Button title="Refresh" onPress={load} />
      {err ? <Text style={styles.err}>{err}</Text> : null}
      <FlatList
        data={data ?? []}
        keyExtractor={(item) => item.id}
        ListEmptyComponent={data === null ? <Text>Tap refresh</Text> : <Text>No accounts</Text>}
        renderItem={({ item }) => (
          <View style={styles.row}>
            <Text style={styles.mono}>{item.accountNumber}</Text>
            <Text>
              {item.currency} {item.balance}
            </Text>
            <Button title="Txns" onPress={() => onSelectAccount(item.id)} />
          </View>
        )}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  box: { flex: 1, padding: 16 },
  title: { fontSize: 18, fontWeight: '600', marginBottom: 8 },
  err: { color: '#b91c1c' },
  row: {
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderColor: '#e2e8f0',
    gap: 4,
  },
  mono: { fontFamily: 'monospace' },
});
