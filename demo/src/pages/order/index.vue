<template>
    <div class="w-full">
        <p-card class="bg-gray-300">
            <template #content>
                <div class="overflow-auto" style="max-height: 600px;">
                    <p-card v-for="(order, idx) in mockOrders" :key="order.id" class="bg-gray-400 my-1">
                        <template #title>
                            {{ `${idx} 주문` }}
                        </template>
                        <template #content>
                            <div class="grid">
                                <div class="col-12 flex">
                                    <p-card v-for="product in order.products" :key="product.id" class="mx-2">
                                        <template #content>
                                            <span class="font-bold">{{ product.name }}</span>
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

@Options({})
export default class Index extends Vue {
    public get mockOrders() {
        return [...new Array(10)].map((_, idx) => {
            return {
                id: idx,
                products: [...new Array(Math.floor(Math.random() * 100 % 10 + 1))].map((_, pid) => {
                    return {id: idx, name: `${pid} - 상품이름`};
                })
            }
        });
    }

    public go(path: string) {
        this.$router.push(path);
    }

    public logout() {
        this.$router.push('/');
    }
}
</script>
