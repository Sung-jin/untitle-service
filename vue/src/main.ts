import {createApp} from 'vue';
import App from '@/App.vue';
import router from '@/pages';
import store from '@/store';
import i18n from '@/plugins/i18n';
import {install as axios} from '@/plugins/axios';
import {install as filters} from '@/plugins/filters';
import registerComponents from '@/plugins/global-components';
import PrimeVue from 'primevue/config';

import 'primevue/resources/themes/saga-blue/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.min.css';

registerComponents(
    createApp(App)
        .use(store)
        .use(router)
        .use(i18n)
        .use(axios)
        .use(filters)
        .use(PrimeVue)
).mount('#app');
