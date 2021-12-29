import {Action, Module, VuexModule} from 'vuex-module-decorators';
import {ProductEntity} from '@/store/product/types';
import productService from '@/service/productService';

@Module({
    stateFactory: true,
    namespaced: true,
})
export default class Product extends VuexModule {
    @Action
    public async save(product: ProductEntity) {
        return await productService.save(product);
    }
}