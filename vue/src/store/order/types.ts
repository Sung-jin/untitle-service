import {ProductEntity} from '@/store/product/types';

export class OrderEntity {
    id?: number;
    orderList: OrderProductEntity[];

    constructor() {
        this.orderList = [];
    }
}

export class OrderProductEntity {
    id?: number;
    order: OrderEntity;
    product: ProductEntity;

    constructor(order: OrderEntity, product: ProductEntity) {
        this.order = order;
        this.product = product;
    }
}