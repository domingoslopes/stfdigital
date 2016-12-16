const webpack = require('webpack'),
path = require('path'),
paths = {
    APP_DIR: path.resolve(__dirname, 'src'),
    BUILD_DIR: path.resolve(__dirname, 'build')
};

const config = {
    entry: [
        'webpack-dev-server/client?http://localhost:3000',
        'webpack/hot/only-dev-server',
        paths.APP_DIR + '/index.jsx',
    ],
    output: {
        path: paths.BUILD_DIR,
        publicPath: '/',
        filename: 'bundle.js'
    },
    module: {
        loaders: [
            {
                test : /\.jsx?$/,
                include : paths.APP_DIR,
                loaders : ['react-hot', 'babel']
            },
            {
                test: /\.s?css$/,
                include: paths.APP_DIR,
                loaders: ['style', 'css']
            }
        ]
    },
    devServer: {
        hot: true,
        port: 3000,
        contentBase: paths.BUILD_DIR
    },
    plugins: [
        new webpack.HotModuleReplacementPlugin()
    ]
};

module.exports = config;