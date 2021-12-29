import {createI18n, LocaleMessages, VueMessageType} from 'vue-i18n';

function loadLocaleMessages(): LocaleMessages<VueMessageType> {
    const locales = require.context('/src/assets/i18n', true, /[A-Za-z0-9-_,\s]+\.ts$/i);
    return locales.keys().reduce((messages, key) => {
            const matched = key.match(/([A-Za-z0-9-_]+)\./i)
            if (matched && matched.length > 1) {
                const locale = matched[1]
                messages[locale] = locales(key).default
            }
            return messages;
    }, {} as LocaleMessages<VueMessageType>);
}

export default createI18n({
    locale: process.env.VUE_APP_I18N_LOCALE || 'ko',
    fallbackLocale: process.env.VUE_APP_I18N_FALLBACK_LOCALE || 'ko',
    messages: loadLocaleMessages(),
    legacy: true
});
