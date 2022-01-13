/* eslint-disable */
import {Action, Module, Mutation, VuexModule} from "vuex-module-decorators";
import {UserPrincipal} from '@/store/user/user';
import {UserEntity} from '@/store/user/types';
import * as CryptoJS from 'crypto-js';
import {$axios} from '@/plugins/axios';

@Module({
    stateFactory: true,
    namespaced: true
})
export default class Authentication extends VuexModule {
    public userPrincipal: UserPrincipal = {user: new UserEntity()};
    public securityInfo = {
        iv: process.env.VUE_APP_SECURITY_IV as string,
        passphrase: process.env.VUE_APP_SECURITY_PASSPHRASE as string,
        iterationCount: Number(process.env.VUE_APP_SECURITY_ITERATION_COUNT as string),
        keySize: Number(process.env.VUE_APP_SECURITY_KEY_SIZE as string),
    };

    @Mutation
    public setAuthentication(payload: any) {
        if (payload && payload.data) {
            this.userPrincipal = payload.data;
        } else {
            this.userPrincipal = {user: new UserEntity()};
        }
    }

    @Action
    public async logout() {
        try {
            await $axios.put('/api/auth/logout');
            $axios.defaults.headers.common['Authorization'] = '';
        } catch (e) {
            console.log('logout error : ', e);
        }

        // this.context.commit('setAuthentication', null);
    }

    @Action
    public async login({username, password}: {username: string, password: string}) {
        let payload;

        try {
            payload = await $axios.post('/api/auth/login', {
                loginId: username,
                encodedPassword: await this.context.dispatch('createEncryptPassword', password)
            })
        } catch (e) {
            console.log('occurred error in login');
        }

        if (payload && payload.status === 200) {
            $axios.defaults.headers.common['Authorization'] = `Bearer ${payload.data}`
            // this.context.commit('setAuthentication', payload);
            // TODO - token 저장 방식을 쿠키나 웹 세션 등 좋은 형태로 저장할 방법을 찾고 적용하기
            // await this.context.dispatch('User/setCurrentUser', this.userPrincipal.user, {root: true});

            return true;
        } else {
            return false;
        }
    }

    @Action
    public async createEncryptPassword(passwordText: string) {
        const payload: any = await $axios.get('/api/auth/key');
        if (payload) {
            const saltKey: string = payload.data.key;
            const encrypted = CryptoJS.AES.encrypt(
                passwordText,
                CryptoJS.PBKDF2(
                    this.securityInfo.passphrase,
                    CryptoJS.enc.Hex.parse(saltKey),
                    {keySize: this.securityInfo.keySize / 32, iterations: this.securityInfo.iterationCount}
                ),
                {iv: CryptoJS.enc.Hex.parse(this.securityInfo.iv)}
            );
            return encrypted.toString();
        }
    }
}