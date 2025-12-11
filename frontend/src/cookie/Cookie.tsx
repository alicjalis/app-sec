import type {UserType} from "../enums/UserType";

export type Cookie = {
    token: string;
    expiresIn: number;
    username: string;
    userType: UserType;
    logged: boolean;
}