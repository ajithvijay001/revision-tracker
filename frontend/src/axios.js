import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:9080', 
});


instance.interceptors.request.use((config) => {
  const publicEndpoints = ['auth/login', 'auth/register'];

  const isPublic = publicEndpoints.some(endpoint =>
    config.url.endsWith(endpoint)
  );

  if (!isPublic) {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }

  return config;
});

export default instance;
