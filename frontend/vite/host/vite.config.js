import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { federation } from "@module-federation/vite";
// https://vite.dev/config/
export default defineConfig({
    server: {
        port: 3000,
        origin: 'http://localhost:3000',
        cors: true,
    },
  plugins: [
      vue(),
      federation({
          name: 'host',
          filename: 'remoteEntry.js',
          exposes: {
              './AuthStore': './src/stores/AuthStore.js',
              './HistoryStore': './src/stores/HistoryStore.js',
              './ErrorStore': './src/stores/ErrorStore.js',
              './PopupWindow': './src/common/PopupWindow.vue'
          },
          remotes: {
              remote_inputs: {
                  entry: "http://localhost:3001/remoteEntry.js",
                  name: "remote_inputs",
                  type: "module"
              },
              remote_results: {
                  entry: "http://localhost:3002/remoteEntry.js",
                  name: "remote_results",
                  type: "module"
              }
          },
          shared: {
              vue: {singleton: true},
              pinia: {singleton: true}
          }
      })
  ],
})
