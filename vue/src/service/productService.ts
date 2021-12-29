import {$axios} from '@/plugins/axios';
import {ProductEntity} from '@/store/product/types';

const productService = {
    async findAll() {
        try {
            return $axios.get('/api/products');
        } catch (e) {
            console.log(e);
            return null;
        }
    },

    async save(product: ProductEntity) {
        try {
            return await $axios.post('/api/products', product);
        } catch (e) {
            console.log(e);
            return null;
        }
    },
};

export default productService;
