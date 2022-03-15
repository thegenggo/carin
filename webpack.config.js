var path = require('path');

module.exports = {
  entry: './src/main/ts/main.tsx',
  devtool: 'source-map',
  mode: 'development',
  output: {
    path: path.join(__dirname, 'src/main/resources/static/built'),
    filename: 'bundle.js',
    clean: true
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
        test: /\.ts?$/,
        type: 'asset/resource',
        generator: {
          filename: '[name][ext][query]',
        }
      },
      {
        test: /\.(png|jpe?g|gif)$/,
        type: 'asset/resource',
        generator : {
          filename : 'images/[name][ext][query]',
        }
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader'],
      },
      {
        test: /\.webmanifest$/,
        type: 'asset/resource',
        generator : {
          filename : '[name][ext][query]',
        }
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)$/i,
        type: 'asset/resource',
        generator : {
          filename : 'fonts/[name][ext][query]',
        }
      },
    ],
  },
};