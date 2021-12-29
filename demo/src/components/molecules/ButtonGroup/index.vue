<template>
    <div>
        <p-button v-for="(label, idx) in labels"
                  class="mx-1"
                  :label="label"
                  :key="idx"
                  @click="onClick">
        </p-button>
    </div>
</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component';
import PButton from '@/components/atom/PButton/index.vue';
import {Prop} from 'vue-property-decorator';

@Options({
    components: {
        PButton,
    }
})
export default class ButtonGroup extends Vue {
    @Prop({ required: true }) labels!: string;
    @Prop({ default: 1 }) count!: number;

    public onClick(): void {
        console.log('click');
    }
}
</script>

