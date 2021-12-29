<template>
    <p-card style="width: 400px;" class="bg-gray-200">
        <template #title>
            <p class="text-center mt-0">
                {{ title }}
            </p>
        </template>
        <template #content>
            <div class="grid">
                <span class="col-3">아이디</span>
                <p-text-field v-model="user.loginId" class="col-9" type="text"></p-text-field>

                <span class="my-2 w-full"></span>

                <span class="col-3">패스워드</span>
                <p-text-field v-model="user.password" v-if="mode === 'signIn'" class="col-9" type="password"></p-text-field>
                <p-text-field v-model="user.savePassword" v-else class="col-9" type="password"></p-text-field>

                <span class="my-2 w-full"></span>

                <span v-if="mode === 'signUp'" class="col-3">이메일</span>
                <p-text-field v-if="mode === 'signUp'" v-model="user.email" class="col-9" type="text"></p-text-field>
            </div>
        </template>
        <template #footer>
            <div class="flex justify-content-center">
                <p-button class="mr-3" @click="changeMode">{{ mode === 'signUp' ? '뒤로' : '회원가입' }}</p-button>
                <p-button class="ml-3" @click="submit">{{ mode === 'signUp' ? '가입하기' : '로그인' }}</p-button>
            </div>
        </template>
    </p-card>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import {namespace} from "vuex-class";
import {UserEntity} from "@/store/user/types";

const UserStore = namespace('User');
const AuthenticationStore = namespace('Authentication');

@Options({})
export default class Index extends Vue {
    @UserStore.Action private joinUser: any;
    @AuthenticationStore.Action private login: any;

    private user: UserEntity = new UserEntity();
    private mode: 'signUp'|'signIn' = 'signIn';

    public get title(): string {
        return this.mode === 'signIn' ? '로그인' : '회원가입';
    }

    public changeMode() {
        this.user = new UserEntity();

        switch (this.mode) {
            case 'signUp': this.mode = 'signIn'; break;
            case 'signIn': this.mode = 'signUp'; break;
        }
    }

    public async submit() {
        switch (this.mode) {
            case 'signIn': {
                const isSuccess = await this.login({username: this.user.loginId, password: this.user.password});
                if (isSuccess) await this.$router.push('/product');
                break;
            }
            case 'signUp': {
                await this.joinUser(this.user);
                this.mode = 'signIn';
            }
        }
    }
}
</script>
