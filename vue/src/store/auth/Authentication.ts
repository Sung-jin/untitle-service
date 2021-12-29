/* eslint-disable */
import {Action, Module, Mutation, VuexModule} from "vuex-module-decorators";
import {UserPrincipal} from '@/store/user/user';
import {UserEntity} from '@/store/user/types';
import {useRouter} from 'vue-router';
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
            await $axios.put('/api/logout', {
                headers: {'content-type': 'application/x-www-form-urlencoded'},
            });
        } catch (e) {
            console.log('logout error : ', e);
        }

        this.context.commit('setAuthentication', null);
    }

    @Action
    // @ts-ignore
    public async login({username, password}) {
        const params = new URLSearchParams();
        password = await this.context.dispatch('createEncryptPassword', password);
        params.append('username', username);
        params.append('password', password);

        let payload;

        try {
            payload = await $axios.post('/api/login', params, {
                headers: {'content-type': 'application/x-www-form-urlencoded'},
            });
        } catch (e) {
            console.log('occurred error in login');
        }

        if (payload && payload.status === 200) {
            this.context.commit('setAuthentication', payload);
            await this.context.dispatch('User/setCurrentUser', this.userPrincipal.user, {root: true});

            return true;
        } else {
            return false;
        }
    }

    @Action
    public async createEncryptPassword(passwordText: string) {
        const payload: any = await $axios.get('/api/users/session-key');
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