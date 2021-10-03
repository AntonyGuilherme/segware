import { Perfil } from "./perfil.model";

export interface Usuario{

    nomeDeUsuario : string;
    senha? : string;
    perfis? : Perfil[];

}