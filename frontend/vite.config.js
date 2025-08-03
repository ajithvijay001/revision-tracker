import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '');
  const port = parseInt(env.PORT) || 5173;

  console.log('Loaded PORT from loadEnv:', port);

  return {
    plugins: [vue()],
    server: {
      port,
    },
  }
});
