import { useState } from 'react';
import { Button, StyleSheet, Text, TextInput, View } from 'react-native';

type Props = {
  accessToken: string | null;
  onToken: (t: string | null) => void;
  onSkip: () => void;
};

export function LoginScreen({ accessToken, onToken, onSkip }: Props) {
  const [paste, setPaste] = useState('');
  return (
    <View style={styles.box}>
      <Text style={styles.title}>Sign in</Text>
      <Text style={styles.muted}>Use dev mode without login, paste a token, or use Expo AuthSession in app entry.</Text>
      <Button title="Continue without login" onPress={onSkip} />
      <TextInput
        style={styles.input}
        placeholder="access token"
        value={paste}
        onChangeText={setPaste}
        autoCapitalize="none"
      />
      <Button title="Use token" onPress={() => onToken(paste.trim() || null)} />
      {accessToken ? <Button title="Clear" onPress={() => onToken(null)} /> : null}
    </View>
  );
}

const styles = StyleSheet.create({
  box: { padding: 16, gap: 8 },
  title: { fontSize: 20, fontWeight: '600' },
  muted: { color: '#64748b' },
  input: { borderWidth: 1, borderColor: '#cbd5e1', borderRadius: 8, padding: 10 },
});
