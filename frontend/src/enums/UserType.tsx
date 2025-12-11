const UserType = {
    NONE: "NONE",
    USER: "USER",
    ADMIN: "ADMIN",
} as const;
export type UserType = (typeof UserType)[keyof typeof UserType];