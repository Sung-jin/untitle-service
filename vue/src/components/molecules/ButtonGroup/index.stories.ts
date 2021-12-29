import ButtonGroup from './index.vue';

export default {
    title: 'Molecules/ButtonGroup',
    component: ButtonGroup,
}

const Template = (args) => ({
    components: { ButtonGroup },
    setup() {
        return { args };
    },
    template: '<button-group v-bind="args" />',
});

export const Default = Template.bind({});
Default.args = {
    labels: ['one', 'two', 'three'],
};
