export class UserEntity {
    id?: number;
    loginId: string;
    email: string;
    password?: string;
    savePassword?: string;

    constructor() {
        this.loginId = '';
        this.email = '';
    }
}