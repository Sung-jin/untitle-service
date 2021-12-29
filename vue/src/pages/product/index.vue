<template>
    <div class="flex w-full h-full">
        <p-card class="bg-gray-300">
            <template #content>
                <div class="grid">
                    <div class="col-12 flex">
                        <p-card v-for="value in products" :key="value.id"
                                class="mx-2 cursor-pointer"
                                :class="isSelected(value) ? 'bg-primary' : ''"
                                @click="select(value)">
                            <template #content>
                                <span class="font-bold">{{ value.name }}</span>
                            </template>
                        </p-card>
                    </div>
                </div>
                <div class="gird mt-6">
                    <div class="flex justify-content-center">
                        <p-button class="mr-3" @click="open">{{ '상품 추가' }}</p-button>
                        <p-button class="mx-3" @click="order">{{ '주문하기' }}</p-button>
                        <p-button class="mx-3" @click="go('/order')">{{ '주문 목록' }}</p-button>
                        <p-button class="ml-3" @click="signOut">{{ '로그아웃' }}</p-button>
                    </div>
                </div>
            </template>
        </p-card>

        <p-dialog v-model:visible="dialog" modal :show-header="false" content-class="pa-4">
            <div class="mt-6">
                <div class="grid">
                    <span class="col-3">이름</span>
                    <p-text-field v-model="product.name" class="col-9" type="text"></p-text-field>

                    <span class="my-2 w-full"></span>

                    <span class="col-3">가격</span>
                    <p-text-field v-model="product.price" class="col-9" type="text"></p-text-field>
                </div>

                <div class="flex justify-content-center pt-4">
                    <p-button class="mr-3" @click="close">{{ '취소' }}</p-button>
                    <p-button class="ml-3" @click="submit">{{ '저장' }}</p-button>
                </div>
            </div>
        </p-dialog>
    </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import {namespace} from "vuex-class";
import {ProductEntity} from "@/store/product/types";

const ProductStore = namespace('Product');
const ProductsStore = namespace('Products');
const OrderStore = namespace('Order');
const AuthenticationStore = namespace('Authentication');

@Options({})
export default class Index extends Vue {
    @ProductStore.Action private save: any;
    @ProductsStore.Action private findAll: any;
    @ProductsStore.State private products: any;
    @OrderStore.Action private orderProduct: any;
    @AuthenticationStore.Action private logout: any;

    private product: ProductEntity = new ProductEntity();
    private selectedProducts: ProductEntity[] = [];
    private dialog = false;

    public select(product: ProductEntity) {
        const idx = this.selectedProducts.map(p => p.id).indexOf(product.id);

        if (idx < 0) this.selectedProducts.push(product);
        else this.selectedProducts.splice(idx, 1);
    }

    public isSelected(product: ProductEntity) {
        return !!this.selectedProducts.find(p => p.id === product.id);
    }

    public open() {
        this.dialog = true;
    }

    public close() {
        this.dialog = false;
    }

    public async submit() {
        await this.save(this.product);
        await this.findAll();
        this.dialog = false;
    }

    public go(path: string) {
        this.$router.push(path);
    }

    public async signOut() {
        await this.logout();
        await this.$router.push('/');
    }

    public async order() {
        await this.orderProduct(this.selectedProducts.map(p => p.id));
    }

    public async created() {
        await this.findAll();
    }
}
</script>
