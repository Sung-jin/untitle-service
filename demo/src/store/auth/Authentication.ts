/* eslint-disable */
import {Action, Module, Mutation, VuexModule} from "vuex-module-decorators";

@Module({
    stateFactory: true,
    namespaced: true
})
export default class Authentication extends VuexModule {
    @Mutation
    public setAuthentication(payload: any): void {
        // set auth
    }

    @Action({commit: 'setAuthentication'})
    public async login(params: URLSearchParams): Promise<void> {
        // get auth
    }
}