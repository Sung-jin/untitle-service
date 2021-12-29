import axios, {AxiosInstance} from 'axios';
import { App } from 'vue';
import store from '@/store';

const $axios = axios.create();
$axios.defaults.timeout = 2000;

$axios.interceptors.request.use(function (config) {
    store.commit('Loadings/pushLoadingStack');
    return config;
}, function (error) {
    store.commit('Loadings/popLoadingStack');
    return Promise.reject(error);
});

$axios.interceptors.response.use(function (response) {
    store.commit('Loadings/popLoadingStack');
    return response;
}, function (error) {
    return Promise.reject(error);
});

const install = (app: App): void => {
    app.config.globalProperties.$axios = $axios;
}

export {install, $axios};

declare module '@vue/runtime-core' {
    interface ComponentCustomProperties {
        $axios: AxiosInstance
    }
}
