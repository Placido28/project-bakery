module.exports = {
  devServer: {
    proxy: process.env.VUE_APP_API_URL, // Proxy para evitar problemas de CORS
  },
  configureWebpack: {
    resolve: {
      alias: {
        '@': require('path').resolve(__dirname, 'src'),
      },
    },
  },
};
