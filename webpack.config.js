var path = require('path');

module.exports = {
    entry: './src/main/ts/app.tsx',
    devtool: 'source-map',
    cache: true,
    mode: 'development',
    resolve: {
          extensions: [ '.tsx', '.ts', '.js' ],
    },
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
          rules: [
            {
              test: /\.tsx?$/,
              use: 'ts-loader',
              exclude: /node_modules/,
            },
          ],
        },
};