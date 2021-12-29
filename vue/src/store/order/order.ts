import {Module, Action, VuexModule} from 'vuex-module-decorators';
import orderService from '@/service/orderService';

@Module({
    stateFactory: true,
    namespaced: true,
})
export default class Order extends VuexModule {
    @Action
    public async orderProduct(productIds: number[]) {
        await orderService.orderProduct(productIds);
    }
}