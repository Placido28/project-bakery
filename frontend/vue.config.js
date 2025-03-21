const webpack = require('webpack');
const path = require('path');

module.exports = {
  devServer: {
    proxy: process.env.VUE_APP_API_URL, // Proxy para evitar problemas de CORS
  },
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
      },
    },
    plugins: [
      new webpack.DefinePlugin({
        '__VUE_PROD_HYDRATION_MISMATCH_DETAILS__': JSON.stringify(false)
      })
    ]
  }
};