const path = require('path');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin');

module.exports = (env, argv) => {
    const isProduction = argv.mode === 'production';

    return {
        entry: {
            about: './about/about.js',
            contact: './contact/contact.js',
            footer: './footer/footer.js',
            header: './header/header.js',
            index: './index/index.js',
            script: './scripts/script.js',
            styles: './styles/style.css',
        },
        output: {
            filename: isProduction ? '[name].[contenthash].js' : '[name].js',
            path: path.resolve(__dirname, 'dist'),
        },
        module: {
            rules: [
                {
                    test: /\.(sa|sc|c)ss$/,
                    use: [
                        isProduction ? MiniCssExtractPlugin.loader : 'style-loader',
                        'css-loader',
                        'sass-loader',
                    ],
                },
                {
                    test: /\.(png|svg|jpg|jpeg|gif)$/i,
                    type: 'asset/resource',
                    generator: {
                        filename: 'images/[name][ext]',
                    },
                },
            ],
        },
        plugins: [
            new CleanWebpackPlugin(),
            new MiniCssExtractPlugin({
                filename: isProduction ? '[name].[contenthash].css' : '[name].css',
            }),
            new HtmlWebpackPlugin({
                template: './index/index.html',
                filename: 'index.html',
                chunks: ['index'],
            }),
            new HtmlWebpackPlugin({
                template: './about/about.html',
                filename: 'about.html',
                chunks: ['about'],
            }),
            new HtmlWebpackPlugin({
                template: './contact/contact.html',
                filename: 'contact.html',
                chunks: ['contact'],
            }),
            new HtmlWebpackPlugin({
                template: './footer/footer.html',
                filename: 'footer.html',
                chunks: ['footer'],
            }),
            new HtmlWebpackPlugin({
                template: './header/header.html',
                filename: 'header.html',
                chunks: ['header'],
            }),
        ],
        devtool: isProduction ? 'source-map' : 'inline-source-map',
        optimization: {
            minimizer: [
                new TerserPlugin({
                    parallel: true,
                    terserOptions: {
                        ecma: 6,
                    },
                }),
                new OptimizeCSSAssetsPlugin({}),
            ],
        },
    };
};