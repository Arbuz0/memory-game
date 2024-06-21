import { createAuth0Client } from '@auth0/auth0-spa-js';

let auth0Client = null;

const initializeAuth0 = async () => {
  if (!auth0Client) {
    auth0Client = await createAuth0Client({
      domain: 'dev-blmdt4682iwaub4z.us.auth0.com',
      client_id: 'k6lV6iiMul6Zw8Ct9zfJ2Y3dNm3igTUI',
      redirect_uri: 'http://localhost:8081', // Set to root path
    });
  }
};

const login = async () => {
  if (!auth0Client) await initializeAuth0();
  await auth0Client.loginWithRedirect();
};

const handleRedirectCallback = async () => {
  if (!auth0Client) await initializeAuth0();
  const result = await auth0Client.handleRedirectCallback();
  window.location.replace('/'); // Redirect to root after handling callback
  return result;
};

const isAuthenticated = async () => {
  if (!auth0Client) await initializeAuth0();
  return await auth0Client.isAuthenticated();
};

const getUser = async () => {
  if (!auth0Client) await initializeAuth0();
  return await auth0Client.getUser();
};

const logout = async () => {
  if (!auth0Client) await initializeAuth0();
  auth0Client.logout({
    returnTo: 'http://localhost:8081',
  });
};

export {
  initializeAuth0,
  login,
  handleRedirectCallback,
  isAuthenticated,
  getUser,
  logout,
};
