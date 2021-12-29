module.exports = {
    devServer: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        },
    },
    pluginOptions: {
        i18n: {
            locale: 'ko',
            fallbackLocale: 'en',
            localeDir: '@/assets/i18n',
        }
    }
}
