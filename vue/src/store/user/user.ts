import {Action, Module, VuexModule} from 'vuex-module-decorators';
import {UserEntity} from '@/store/user/types';
import {Mutation} from 'vuex-class';
import { $axios } from '@/plugins/axios';
import { plainToClass } from 'class-transformer';

@Module({
    stateFactory: true,
    namespaced: true,
})
export default class User extends VuexModule {
    private user: UserEntity = new UserEntity();
    private currentUser: UserEntity = new UserEntity();

    @Mutation
    public setUser(user: { data: UserEntity }) {
        this.user = plainToClass(UserEntity, user.data);
    }

    @Mutation
    public setCurrentUser(user: UserEntity) {
        this.currentUser = user;
    }

    @Action
    public async joinUser(user: UserEntity) {
        if (user.savePassword) {
            user.savePassword = await this.context.dispatch('Authentication/createEncryptPassword', user.savePassword, {root: true});
        }
        return $axios.post('/api/users/join', user);
    }
}

export interface UserPrincipal {
    user: UserEntity;
}