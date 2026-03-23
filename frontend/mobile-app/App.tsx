import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import * as AuthSession from 'expo-auth-session';
import * as WebBrowser from 'expo-web-browser';
import { useEffect } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';
import { AuthProvider, useAuth } from './src/AuthContext';
import { KEYCLOAK_CLIENT_ID, KEYCLOAK_ISSUER } from './src/config';
import { ConsentsScreen } from './src/screens/ConsentsScreen';
import { DashboardScreen } from './src/screens/DashboardScreen';
import { LoginScreen } from './src/screens/LoginScreen';
import { TransactionsScreen } from './src/screens/TransactionsScreen';

WebBrowser.maybeCompleteAuthSession();

export type RootStackParamList = {
  Login: undefined;
  Dashboard: undefined;
  Transactions: { accountId: string };
  Consents: undefined;
};

const Stack = createNativeStackNavigator<RootStackParamList>();

function KeycloakBar() {
  const { accessToken, setAccessToken } = useAuth();
  const discovery = AuthSession.useAutoDiscovery(KEYCLOAK_ISSUER);
  const redirectUri = AuthSession.makeRedirectUri({ scheme: 'opnbnk4mobile' });
  const [request, response, promptAsync] = AuthSession.useAuthRequest(
    {
      clientId: KEYCLOAK_CLIENT_ID,
      scopes: ['openid', 'profile', 'email'],
      redirectUri,
      usePKCE: true,
    },
    discovery
  );

  useEffect(() => {
    if (response?.type === 'success' && response.authentication?.accessToken) {
      setAccessToken(response.authentication.accessToken);
    }
  }, [response, setAccessToken]);

  return (
    <View style={styles.kc}>
      <Text style={styles.kcText} numberOfLines={2}>
        Keycloak PKCE. Add redirect: {redirectUri}
      </Text>
      <Button title="Sign in Keycloak" disabled={!request} onPress={() => promptAsync()} />
      {accessToken ? <Text style={styles.ok}>Token active</Text> : null}
    </View>
  );
}

function LoginRoute({ navigation }: { navigation: { navigate: (n: keyof RootStackParamList) => void } }) {
  const { accessToken, setAccessToken } = useAuth();
  return (
    <View style={{ flex: 1 }}>
      <KeycloakBar />
      <LoginScreen
        accessToken={accessToken}
        onToken={setAccessToken}
        onSkip={() => navigation.navigate('Dashboard')}
      />
    </View>
  );
}

function DashboardRoute({
  navigation,
}: {
  navigation: { navigate: (n: keyof RootStackParamList, p?: object) => void };
}) {
  const { accessToken, setAccessToken } = useAuth();
  return (
    <View style={{ flex: 1 }}>
      <View style={styles.navRow}>
        <Button title="Consents" onPress={() => navigation.navigate('Consents')} />
        <Button
          title="Logout"
          onPress={() => {
            setAccessToken(null);
            navigation.navigate('Login');
          }}
        />
      </View>
      <DashboardScreen
        accessToken={accessToken}
        onSelectAccount={(id) => navigation.navigate('Transactions', { accountId: id })}
      />
    </View>
  );
}

function TransactionsRoute({
  navigation,
  route,
}: {
  navigation: { goBack: () => void };
  route: { params: { accountId: string } };
}) {
  const { accessToken } = useAuth();
  return (
    <TransactionsScreen
      accountId={route.params.accountId}
      accessToken={accessToken}
      onBack={() => navigation.goBack()}
    />
  );
}

function ConsentsRoute({ navigation }: { navigation: { goBack: () => void } }) {
  const { accessToken } = useAuth();
  return (
    <View style={{ flex: 1 }}>
      <Button title="Back" onPress={() => navigation.goBack()} />
      <ConsentsScreen accessToken={accessToken} />
    </View>
  );
}

function RootNav() {
  return (
    <Stack.Navigator initialRouteName="Login">
      <Stack.Screen name="Login" options={{ title: 'Login' }}>
        {(props) => <LoginRoute navigation={props.navigation} />}
      </Stack.Screen>
      <Stack.Screen name="Dashboard" options={{ title: 'Accounts' }}>
        {(props) => <DashboardRoute navigation={props.navigation} />}
      </Stack.Screen>
      <Stack.Screen name="Transactions" options={{ title: 'Transactions' }}>
        {(props) => <TransactionsRoute navigation={props.navigation} route={props.route} />}
      </Stack.Screen>
      <Stack.Screen name="Consents" options={{ title: 'Consents' }}>
        {(props) => <ConsentsRoute navigation={props.navigation} />}
      </Stack.Screen>
    </Stack.Navigator>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <NavigationContainer>
        <RootNav />
      </NavigationContainer>
    </AuthProvider>
  );
}

const styles = StyleSheet.create({
  kc: { padding: 8, backgroundColor: '#f1f5f9' },
  kcText: { fontSize: 11, color: '#475569', marginBottom: 4 },
  ok: { color: '#15803d', marginTop: 4 },
  navRow: { flexDirection: 'row', justifyContent: 'space-between', padding: 8 },
});
