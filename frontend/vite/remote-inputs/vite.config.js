import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { federation } from "@module-federation/vite";
// https://vite.dev/config/
export default defineConfig({
    server: {
      port: 3001,
      cors: true,
    },
  plugins: [
      vue(),
      federation({
          name: 'remote_inputs',
          filename: "remoteEntry.js",
          exposes: {
              "./InputsContainer": "./src/InputsContainer.vue",
          },
          remotes: {
              host: {
                  type: 'module',
                  name: 'host',
                  entry: 'http://localhost:3000/remoteEntry.js'
              }
          },
          shared: {
              vue: {singleton: true},
              pinia: {singleton: true}
          }
      })
  ],
})
