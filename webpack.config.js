var path = require('path');

module.exports = {
  entry: './src/main/ts/main.tsx',
  watch: true,
  watchOptions: {
    ignored: /node_modules/,
    aggregateTimeout: 600,
    poll: 1000
  },
  devtool: 'source-map',
  mode: 'development',
  output: {
    path: path.join(__dirname, 'target/classes/static/built'),
    filename: 'bundle.js'
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.jsx'],
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: ['ts-loader'],
        exclude: /node_modules/,
      },
      {
        test: /\.(png|jpe?g|gif)$/,
        type: 'asset/resource',
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader'],
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)$/i,
        type: 'asset/resource',
      },
    ],
  },
};