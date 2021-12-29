import {Action, Module, Mutation, VuexModule} from 'vuex-module-decorators';
import orderService from '@/service/orderService';
import {plainToClass} from 'class-transformer';
import {OrderEntity} from '@/store/order/types';

@Module({
    stateFactory: true,
    namespaced: true,
})
export default class Orders extends VuexModule {
    private orders: OrderEntity[] = [];

    @Mutation
    public setOrders(orders: { data: OrderEntity[] }) {
        this.orders.splice(0, this.orders.length, ...plainToClass(OrderEntity, orders.data));
    }

    @Action({commit: 'setOrders'})
    public async findAll() {
        return await orderService.findAll();
    }
}