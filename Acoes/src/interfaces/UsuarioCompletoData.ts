import type { enderecoDto } from "./EnderecoData";
import type { pessoaDto } from "./PessoaData";
import type { usuarioDto } from "./UsuarioData";
import type { ProvedorDto } from "./PrestadorData";

export interface UsuarioCompletoData {
    enderecoDto: enderecoDto;
    usuarioDto: usuarioDto;
    pessoaDto: pessoaDto;
    provedorDto: ProvedorDto;
}
