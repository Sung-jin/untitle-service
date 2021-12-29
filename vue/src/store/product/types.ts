export class ProductEntity {
    id?: number;
    name: string;
    price: number;

    constructor() {
        this.name = '';
        this.price = 0;
    }
}