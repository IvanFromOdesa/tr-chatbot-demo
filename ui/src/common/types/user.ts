export interface User {
    firstName: string;
    lastName: string;
    email: string;
    role: Role;
}

export interface Role {
    code: number,
    alias: RoleAlias
}

export type RoleAlias = 'visitor' | 'employee';