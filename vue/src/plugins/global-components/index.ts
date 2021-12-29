import { App } from "vue";

import button from 'primevue/button';
import card from 'primevue/card';
import inputText from 'primevue/inputtext';
import dialog from 'primevue/dialog';

export default (app: App): App => {
    app.component('p-button', button);
    app.component('p-card', card);
    app.component('p-text-field', inputText);
    app.component('p-dialog', dialog);

    return app;
};