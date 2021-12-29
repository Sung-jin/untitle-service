import {$axios} from '@/plugins/axios';

const orderService = {
    async findAll() {
        try {
            return $axios.get('/api/orders');
        } catch (e) {
            console.log(e);
            return null;
        }
    },

    async orderProduct(productIds: number[]) {
        try {
            return await $axios.post('/api/orders', productIds);
        } catch (e) {
            console.log(e);
            return null;
        }
    },
};

export default orderService;
