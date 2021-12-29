<template>
    <p-button :label="label" />
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import { Prop } from 'vue-property-decorator';

@Options({})
export default class PButton extends Vue {
    @Prop({ required: true }) label!: string;

    public onClick(): void {
        console.log('click');
    }
}
</script>

<style scoped>

</style>
