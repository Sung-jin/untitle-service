import DefaultButton from './index.vue';

export default {
    title: 'Atom/Button',
    component: DefaultButton,
}

const Template = (args) => ({
    components: { DefaultButton },
    setup() {
        return { args };
    },
    template: '<default-button v-bind="args" />',
});

export const Default = Template.bind({});
Default.args = {
    label: 'default',
};
