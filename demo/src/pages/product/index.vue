<template>
    <div class="flex w-full h-full">
        <p-card class="bg-gray-300">
            <template #content>
                <div class="grid">
                    <div class="col-12 flex">
                        <p-card v-for="product in mockProducts" :key="product.id"
                                class="mx-2 cursor-pointer"
                                :class="isSelected(product) ? 'bg-primary' : ''"
                                @click="select(product)">
                            <template #content>
                                <span class="font-bold">{{ product.name }}</span>
                            </template>
                        </p-card>
                    </div>
                </div>
                <div class="gird mt-6">
                    <div class="flex justify-content-center">
                        <p-button class="mr-3" @click="open">{{ '상품 추가' }}</p-button>
                        <p-button class="mx-3" @click="go('/order')">{{ '주문 목록' }}</p-button>
                        <p-button class="ml-3" @click="logout()">{{ '로그아웃' }}</p-button>
                    </div>
                </div>
            </template>
        </p-card>

        <p-dialog v-model:visible="dialog" modal :show-header="false" content-class="pa-4">
            <div class="mt-6">
                <div class="grid">
                    <span class="col-3">이름</span>
                    <p-text-field class="col-9" type="text"></p-text-field>

                    <span class="my-2 w-full"></span>

                    <span class="col-3">가격</span>
                    <p-text-field class="col-9" type="text"></p-text-field>
                </div>

                <div class="flex justify-content-center pt-4">
                    <p-button class="mr-3" @click="close">{{ '취소' }}</p-button>
                    <p-button class="ml-3" @click="save">{{ '저장' }}</p-button>
                </div>
            </div>
        </p-dialog>
    </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

@Options({})
export default class Index extends Vue {
    private selectedProducts: any[] = [];
    private dialog = false;

    public get mockProducts() {
        return [...new Array(10)].map((_, idx) => {
            return {id: idx, name: `${idx} - 상품이름`};
        });
    }

    public select(product: any) {
        const idx = this.selectedProducts.map(p => p.id).indexOf(product.id);

        if (idx < 0) this.selectedProducts.push(product);
        else this.selectedProducts.splice(idx, 1);
    }

    public isSelected(product: any) {
        return !!this.selectedProducts.find(p => p.id === product.id);
    }

    public open() {
        this.dialog = true;
    }

    public close() {
        this.dialog = false;
    }

    public save() {
        this.dialog = false;
    }

    public go(path: string) {
        this.$router.push(path);
    }

    public logout() {
        this.$router.push('/');
    }
}
</script>
