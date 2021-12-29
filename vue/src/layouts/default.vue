<template>
    <div id="nav" class="flex align-items-center justify-content-center w-full h-screen">
        <div class="p-4">
            <slot/>
        </div>
    </div>
</template>

<script lang="ts">
import {Vue} from 'vue-class-component';

export default class Default extends Vue {
    // public created(): void {
    //     console.log('default layout');
    // }
}
</script>

<style lang="scss">
body {
    margin: 0;
}
</style>