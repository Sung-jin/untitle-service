import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

// const enableChildRoute: string[] = [];
// const disableAutoRoute: string[] = [];
// const customRoute: RouteRecordRaw[] = [];
const routes: RouteRecordRaw[] = [];

require.context(
    `./`, true, /^(?!.*test).*\.vue$/is,
).keys().forEach(async value => {
    const removeDotPrefix = (() => {
        if (value.charAt(0) === '.') return value.slice(1);
        else return value;
    })();
    const splitPath = removeDotPrefix.split('/');
    const length = splitPath.length;
    const path = length === 2 ? '/' : splitPath.slice(0, length - 1).join('/');
    const fileName = splitPath[length - 1].slice(0, splitPath[length - 1].lastIndexOf('.')).replace(/[^\w\s]|_/gi, '');
    const leafPath = length === 2 ? 'root' : splitPath[length - 2];

    routes.push({
        path: fileName.toLowerCase() === 'index' ? path : `${path}/:${fileName}`,
        name: `${leafPath}-${fileName}`,
        component: () => import(`@/pages${removeDotPrefix}`)
    });
});

export default createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

