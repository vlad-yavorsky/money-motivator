import { UserModel } from "./user.model";

export interface UserAuthStateModel {
  authenticated: boolean;
  user: UserModel;
}
