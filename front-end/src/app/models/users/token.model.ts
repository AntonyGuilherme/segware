import { Usuario } from "./usuario.model";

export interface TokenUsuario extends Usuario{


    token : string;
    tipo : string;

}