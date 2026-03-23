import { createContext, useContext, useState, type ReactNode } from 'react';

type Ctx = {
  accessToken: string | null;
  setAccessToken: (t: string | null) => void;
};

const AuthContext = createContext<Ctx | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [accessToken, setAccessToken] = useState<string | null>(null);
  return (
    <AuthContext.Provider value={{ accessToken, setAccessToken }}>{children}</AuthContext.Provider>
  );
}

export function useAuth() {
  const v = useContext(AuthContext);
  if (!v) {
    throw new Error('useAuth requires AuthProvider');
  }
  return v;
}
