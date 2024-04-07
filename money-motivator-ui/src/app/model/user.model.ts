import { Role } from "./role";

export interface UserModel {
  id: number;
  email: string;
  lastName: string;
  firstName: string;
  created: string;
  roles: Role[];
  balance: number;
}
