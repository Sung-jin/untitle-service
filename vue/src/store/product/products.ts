import {Action, Module, Mutation, VuexModule} from 'vuex-module-decorators';
import {plainToClass} from 'class-transformer';
import {ProductEntity} from '@/store/product/types';
import productService from '@/service/productService';

@Module({
    stateFactory: true,
    namespaced: true,
})
export default class Products extends VuexModule {
    private products: ProductEntity[] = [];

    @Mutation
    public setProducts(products: { data: ProductEntity[] }) {
        this.products.splice(0, this.products.length, ...plainToClass(ProductEntity, products.data));
    }

    @Action({commit: 'setProducts'})
    public async findAll() {
        return await productService.findAll();
    }
}