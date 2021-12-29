import {Module, Mutation, VuexModule} from 'vuex-module-decorators';

@Module({
    stateFactory: true,
    namespaced: true
})
export default class Loadings extends VuexModule {

    public loadingStack: string[] = [];

    get isLoading(): boolean {
        return this.loadingStack.length > 0;
    }

    @Mutation
    public pushLoadingStack(stack = 'fetchData'): void {
        this.loadingStack.push(stack);
    }

    @Mutation
    public popLoadingStack(): void{
        this.loadingStack.pop();
    }

    @Mutation
    public popAllLoadingStack(): void{
        this.loadingStack.splice(0, this.loadingStack.length);
    }
}
