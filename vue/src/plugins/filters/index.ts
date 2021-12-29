import { App } from 'vue';

const $filters = {

}

const install = (app: App): void => {
    app.config.globalProperties.$filters = $filters;
}

export {install, $filters};
