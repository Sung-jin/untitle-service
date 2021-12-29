<template>
    <div class="w-full">
        <p-card class="bg-gray-300">
            <template #content>
                <div class="overflow-auto" style="max-height: 600px;">
                    <p-card v-for="(orderValue, idx) in orders" :key="`order-${orderValue.id}`" class="bg-gray-400 my-1">
                        <template #title>
                            {{ `${idx} 주문` }}
                        </template>
                        <template #content>
                            <div class="grid">
                                <div class="col-12 flex">
                                    <p-card v-for="orderList in orderValue.orderList" :key="`product-${orderList.id}`" class="mx-2">
                                        <template #content>
                                            <span class="font-bold">{{ orderList.product.name }}</span>
                                        </template>
                                    </p-card>
                                </div>
                            </div>
                        </template>
                    </p-card>
                </div>
                <div class="gird mt-6">
                    <div class="flex justify-content-center">
                        <p-button class="mr-3" @click="go('/product')">{{ '상품 목록' }}</p-button>
                        <p-button class="ml-3" @click="logout()">{{ '로그아웃' }}</p-button>
                    </div>
                </div>
            </template>
        </p-card>
    </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import {namespace} from "vuex-class";

const OrdersStore = namespace('Orders');
const AuthenticationStore = namespace('Authentication');

@Options({})
export default class Index extends Vue {
    @OrdersStore.Action private findAll: any;
    @OrdersStore.State private orders: any;
    @AuthenticationStore.Action private logout: any;

    public go(path: string) {
        this.$router.push(path);
    }

    public async signOut() {
        await this.logout();
        await this.$router.push('/');
    }

    public async created() {
        await this.findAll();
    }
}
</script>
