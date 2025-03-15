import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';
import { fileURLToPath } from "url";
import terser from "@rollup/plugin-terser";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default defineConfig({
    // In development, calls to '/api' are routed to the proxy server
    server: {
        proxy: {
            '/api': {
                target: 'http://localhost:8082',
                changeOrigin: true,
                secure: false,
                ws: true,
                configure: (proxy, _options) => {
                    proxy.on('error', (err, _req, _res) => {
                        console.log('proxy error', err);
                    });
                    proxy.on('proxyReq', (proxyReq, req, _res) => {
                        console.log('Sending Request to the Target:', proxyReq.host, req.method, req.url);
                    });
                    proxy.on('proxyRes', (proxyRes, req, _res) => {
                        console.log('Received Response from the Target:', proxyRes.statusCode, req.url);
                    });
                },
            }
        }
    },
    base: '/public/bundles/',
    plugins: [
        react(),
        // TODO: configure terser
        terser({
            compress: {

            },
            mangle: {

            },
        })
    ],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src'),
        },
    },
    build: {
        outDir: 'dist',
        rollupOptions: {
            input: {
                homeVisitor: './src/homeVisitor/homeVisitor.tsx',
                homeAnonymous: './src/homeAnonymous/homeAnonymous.tsx',
                homeEmployee: './src/homeEmployee/homeEmployee.tsx',
                login: './src/login/login.tsx',
                signup: './src/signup/signup.tsx',
                knowledgeBase: './src/knowledgeBase/knowledgeBase.tsx',
                mainCss: './src/main.css'
            },
            output: {
                entryFileNames: `[name]/[name].js`, // Main entry points in their respective folders
                chunkFileNames: (chunkInfo) => {
                    // Check if the chunk is specific to an entry point
                    const entryPoints = ['homeVisitor', 'homeAnonymous', 'homeEmployee', 'login', 'signup', 'knowledgeBase'];
                    const entry = entryPoints.find(name => chunkInfo.name.startsWith(name));

                    if (entry) {
                        // Chunks specific to an entry point will go into that folder
                        return `${entry}/[name]-chunk-[hash].js`;
                    }

                    // If the chunk isn't specific to any entry point, it goes into the 'shared' folder
                    return `shared/[name]-chunk-[hash].js`;
                },
                assetFileNames: (assetInfo) => {
                    if (assetInfo.name === 'main.css') {
                        return `shared/main-[hash].css`; // Ensure main.css is placed in shared
                    }

                    const entryPoints = ['homeVisitor', 'homeAnonymous', 'homeEmployee', 'login', 'signup', 'knowledgeBase'];
                    const entry = entryPoints.find(name => assetInfo.name?.startsWith(name));

                    if (entry) {
                        return `${entry}/[name]-[hash].[ext]`;
                    }

                    // Shared assets go into a shared folder
                    return `shared/[name]-[hash].[ext]`;
                },
                format: 'es',
            },
            //external: ['react', 'react-dom'],
        },
        emptyOutDir: true,
    },
});

