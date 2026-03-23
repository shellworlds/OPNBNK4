import { useState } from 'react';
import { Button, StyleSheet, Text, TextInput, View } from 'react-native';
import { postJson } from '../api';
import { MOCK_CUSTOMER_ID } from '../config';

type Props = {
  accessToken: string | null;
};

export function ConsentsScreen({ accessToken }: Props) {
  const [tppId, setTppId] = useState('tpp-aisp-demo');
  const [result, setResult] = useState<string | null>(null);
  const [err, setErr] = useState<string | null>(null);

  async function createConsent() {
    setErr(null);
    setResult(null);
    try {
      const body = {
        customerId: MOCK_CUSTOMER_ID,
        tppId: tppId.trim(),
        scopes: ['accounts:read', 'transactions:read'],
      };
      const res = await postJson<{ consentId?: string }>(
        '/openbanking/consents',
        body,
        accessToken
      );
      setResult(JSON.stringify(res, null, 2));
    } catch (e: unknown) {
      setErr(e instanceof Error ? e.message : 'Failed');
    }
  }

  return (
    <View style={styles.box}>
      <Text style={styles.title}>Open banking consent (demo)</Text>
      <Text style={styles.muted}>POST /openbanking/consents via gateway</Text>
      <Text style={styles.label}>TPP id</Text>
      <TextInput style={styles.input} value={tppId} onChangeText={setTppId} autoCapitalize="none" />
      <Button title="Create consent" onPress={createConsent} />
      {err ? <Text style={styles.err}>{err}</Text> : null}
      {result ? <Text style={styles.mono}>{result}</Text> : null}
    </View>
  );
}

const styles = StyleSheet.create({
  box: { flex: 1, padding: 16, gap: 8 },
  title: { fontSize: 18, fontWeight: '600' },
  muted: { color: '#64748b' },
  label: { fontWeight: '500' },
  input: { borderWidth: 1, borderColor: '#cbd5e1', borderRadius: 8, padding: 10 },
  err: { color: '#b91c1c' },
  mono: { fontFamily: 'monospace', fontSize: 12 },
});
