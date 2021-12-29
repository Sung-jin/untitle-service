<template>
    <component :is="layout">
        <router-view/>
    </component>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import Default from '@/layouts/default.vue';

@Options({
    components: {
        Default,
    }
})
export default class App extends Vue {
    public get layoutList(): string[] {
        return require.context(
            `@/layouts`, true, /^(?!.*test).*\.vue$/is,
        ).keys().map(value => {
            const splitValue = value.split('/');
            const fileName = splitValue[splitValue.length - 1];
            return fileName.slice(0, fileName.lastIndexOf('.'));
        });
    }

    public get layout(): string {
        const matched = this.$route.matched;
        const pageLayout = matched.length === 0 ? 'default'
            /* eslint-disable  @typescript-eslint/no-explicit-any */
            : (matched[matched.length - 1].components.default as any)?.__c?.layout || 'default';
        // TODO - route 의 객체 구조를 파악하여 해당 부분의 타입이 어떻게 되는지 확인 필요

        return this.layoutList.includes(pageLayout) ? pageLayout : 'default';
    }

    public created() : void {
        console.log(process.env.VUE_APP_VALUE);
    }
}
</script>
