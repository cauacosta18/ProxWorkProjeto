import { useNavigate } from "react-router-dom";
import styles from '../../Styles/Cadastro.module.css';
import React, { useEffect, useState } from "react";
import type { UsuarioUpdateData } from "../../interfaces/UsuarioUpdateData";
import { GetAllServicosAPI, Getme, GetPrestador, GetServicos, UpdatePrestadorAPI } from "../../services/ProxWorkAPI";
import type { ServicosPrestadordata } from "../../interfaces/ServicosPrestadorData";
import type { UsuarioCompletoData } from "../../interfaces/UsuarioCompletoData";
import { loginRequest } from "../../services/AuthService";
import { setTokens } from "../../services/TokenService";

interface meData {
    nome: string;
    email: string;
    provedor: boolean;
}

interface servicoData {
    id: number;
    descricao: string;
}

const AtualizacaoPrestador = () => {

    const navigate = useNavigate();

    const [me, setMe] = useState<meData>({
            nome: "",
            email: "",
            provedor: false,
        })
    
        const fetchMe = async () => {
            try {
                const data = await Getme();
                
                if (data) {
              
                    setMe(data);
                
                } else {
                alert("Nenhum prestador encontrado.");
                }
            } catch (error) {
                navigate("/login");
            }
        };
    
        useEffect(() => {
            fetchMe();
    
        }, []);
        
        useEffect(() => {
            if (me.email && me.provedor === false) {
                navigate("/atualizarPessoa");
            }
            }, [me.email, me.provedor]);
    
        const email = me.email;

        const [servicos, setServicos] = useState<ServicosPrestadordata>({
            servicosId: [],
            servicos: [],
        });
        
        const [UsuarioCompletoData, setUsuarioCompleto] = useState<UsuarioCompletoData>({
            enderecoDto: {
                cidade: '',
                estado: '',
            },
            usuarioDto: {
                nome: '',
                email: '',
                telefone: '',
            },
            pessoaDto: {
                cpf: '',
                dataNascimento: '',
                genero: 0,
            },
            provedorDto: {
                caminhoCarteira: '',
                caminhoCertidao: '',
                whatsapp: '',
                valorMax: 0,
                valorMin: 0,
            }
        });
    
        const fetchPrestador = async () => {
            try {
                const data = await GetPrestador(email);
                
                if (data) {
                setUsuarioCompleto(data);
                } else {
                alert("Nenhum prestador encontrado.");
                }
            } catch (error) {
                console.error("Erro ao buscar prestadores:", error);
                alert("Erro ao carregar prestadores.");
            }
        };
    
        const fetchServicos = async () => {
            try {
                const data = await GetServicos(email);
                
                if (data && data.length > 0) {
                setServicos(data);
                } else {
                alert("Nenhum servico encontrado.");
                }
            } catch (error) {
                console.error("Erro ao buscar servicos:", error);
                alert("Erro ao carregar servicos.");
            }
        }
    
        useEffect(() => {
        if (me.email) {
            fetchPrestador();
            fetchServicos();
        }
        }, [me.email]);
        

    const [usuarioUpdateData, setUsuarioUpdateData] = useState<UsuarioUpdateData> ({
        nome: UsuarioCompletoData.usuarioDto.nome,
        email: UsuarioCompletoData.usuarioDto.email,
        telefone: UsuarioCompletoData.usuarioDto.telefone,
        senha: '',
        cpf: UsuarioCompletoData.pessoaDto.cpf,
        dataNascimento: UsuarioCompletoData.pessoaDto.dataNascimento,
        genero: UsuarioCompletoData.pessoaDto.genero,
        cidade: UsuarioCompletoData.enderecoDto.cidade,
        estado: UsuarioCompletoData.enderecoDto.estado,
        caminhoCarteira: UsuarioCompletoData.provedorDto.caminhoCarteira,
        caminhoCertidao: UsuarioCompletoData.provedorDto.caminhoCarteira,
        whatsapp: UsuarioCompletoData.provedorDto.whatsapp,
        servicos: servicos.servicosId,
        valorMax: UsuarioCompletoData.provedorDto.valorMax,
        valorMin: UsuarioCompletoData.provedorDto.valorMin,
    })
    
    const handleCadastro = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = event.target;

        const target = event.target as HTMLInputElement;
        const type = target.type;
        const checked = target.checked;
     
        if (name === "valorMin" || name === "valorMax") {
            const input = event.target as HTMLInputElement;
            const num = input.value === '' ? undefined : input.valueAsNumber; // <- aqui
            console.log(name+' teste '+value);
            setUsuarioUpdateData(prev => ({
                ...prev,
                [name]: num,
            }));
            return;
        } else if (name === "senha" ) {
            setUsuarioUpdateData(prev => ({
                ...prev,
                [name]: value,
            }));
            return;
        } else if (name === "genero" && type === "radio") {
            setUsuarioUpdateData(prev => ({
            ...prev,
            genero: Number(value),
            }));
            return;
        } else if (name === "servicos" && type === "checkbox") {
            setUsuarioUpdateData(prev => {
            // Clona o array atual pra manter imutabilidade
            const nextServicos = [...prev.servicos];

            if (checked) {
                // Se marcou: adiciona caso ainda não exista
                const jaExiste = nextServicos.includes(Number(value));
                if (!jaExiste) {
                nextServicos.push(Number(value));
                }
            } else {
                // Se desmarcou: remove (se existir)
                const idx = nextServicos.indexOf(Number(value));
                if (idx !== -1) {
                nextServicos.splice(idx, 1);
                }
            }

            return { ...prev, servicos: nextServicos };
            });
            return;
        } else {
            console.log(name+' teste '+value);
            setUsuarioUpdateData(prev => ({
                ...prev,
                [name]: value,
            }));

        }
        
    }

    const handleSubmit = async (event: React.MouseEventHandler<HTMLButtonElement> | any) => {
            event.preventDefault();
        try{
            if(usuarioUpdateData.valorMax <= usuarioUpdateData.valorMin){
                alert("Falha no cadastro.");
                return;
            } 

            console.log("Json: ",usuarioUpdateData);
            const response = await UpdatePrestadorAPI(usuarioUpdateData, email);
                
            console.log(response.prestador.status+ ""+response.servico.status);
            
            const okPrestador = response?.prestador?.status >= 200 && response?.prestador?.status < 300;
            const okServico   = response?.servico?.status   >= 200 && response?.servico?.status   < 300;

            
            if (okPrestador && okServico) {
                const { accessToken, refreshToken } = await loginRequest(usuarioUpdateData.email, usuarioUpdateData.senha);
                setTokens(accessToken, refreshToken);
                navigate(`/perfilprestador`);              
            } else {
                alert("Falha no cadastro.");
            }


        } catch(e: Error | any){
            console.error("register error: ", e.message);
        }
    }

    const [servicosData, setServicosData] = useState<Array<servicoData>>();
    
        const fetchAllServicos = async () => {
            try {
                const data = await GetAllServicosAPI();
                            
                if (data && data.length > 0) {
                setServicosData(data);
                } else {
                alert("Nenhum servico encontrado.");
                }
                
            } catch (error) {
                console.error("Erro ao buscar servicos:", error);
                alert("Erro ao carregar servicos.");
            }
        }
    
        useEffect(() => {
            fetchAllServicos();
        }, []);
    
    return (
        <>
            <section className={styles.form}>

                
                    <div className={styles.itemForm}>
                        <label htmlFor="nome">Nome Completo:*</label>
                        <input type="text" className={styles.input} name="nome" required onChange={handleCadastro} placeholder={UsuarioCompletoData.usuarioDto.nome}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="email">Email:*</label>
                        <input type="email" className={styles.input} name="email" required onChange={handleCadastro} placeholder={UsuarioCompletoData.usuarioDto.email}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="telefone">Telefone:*</label>
                        <input type="tel" className={styles.input} name="telefone" required onChange={handleCadastro} placeholder={UsuarioCompletoData.usuarioDto.telefone}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="cpf">CPF:*</label>
                        <input type="text" className={styles.input} name="cpf" required onChange={handleCadastro} placeholder={UsuarioCompletoData.pessoaDto.cpf}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="whatsapp">Whatsapp:*</label>
                        <input type="text" className={styles.input} name="whatsapp" required onChange={handleCadastro} placeholder={UsuarioCompletoData.provedorDto.whatsapp}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="estado">Estado:*</label>
                        <select className={styles.input} name="estado" id="estado" required onChange={handleCadastro}>
                            <option value=""></option>
                            <option value="AC">Acre</option>
                            <option value="AL">Alagoas</option>
                            <option value="AP">Amapá</option>
                            <option value="AM">Amazonas</option>
                            <option value="BA">Bahia</option>
                            <option value="CE">Ceará</option>
                            <option value="DF">Distrito Federal</option>
                            <option value="ES">Espírito Santo</option>
                            <option value="GO">Goiás</option>
                            <option value="MA">Maranhão</option>
                            <option value="MT">Mato Grosso</option>
                            <option value="MS">Mato Grosso do Sul</option>
                            <option value="MG">Minas Gerais</option>
                            <option value="PA">Pará</option>
                            <option value="PB">Paraíba</option>
                            <option value="PR">Paraná</option>
                            <option value="PE">Pernambuco</option>
                            <option value="PI">Piauí</option>
                            <option value="RJ">Rio de Janeiro</option>
                            <option value="RN">Rio Grande do Norte</option>
                            <option value="RS">Rio Grande do Sul</option>
                            <option value="RO">Rondônia</option>
                            <option value="RR">Roraima</option>
                            <option value="SC">Santa Catarina</option>
                            <option value="SP">São Paulo</option>
                            <option value="SE">Sergipe</option>
                            <option value="TO">Tocantins</option>
                        </select>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="cidade">Cidade:*</label>
                        <input type="text" className={styles.input} name="cidade" required onChange={handleCadastro} placeholder={UsuarioCompletoData.enderecoDto.cidade}/>
                    </div>
                    <div className={styles.itemForm}>
                        <p>Gênero:*</p>
                        <div className={styles.radio}>
                            <input type="radio" name="genero" id="fem" value={1} onChange={handleCadastro} />
                            <label htmlFor="fem">Feminino</label>
                            <input type="radio" name="genero" id="mas" value={2} onChange={handleCadastro}/>
                            <label htmlFor="mas">Masculino</label>
                            <input type="radio" name="genero" id="out" value={0} onChange={handleCadastro}/>
                            <label htmlFor="out">Outro</label>
                        </div>
                    </div>
                    <div>
                        <p>Serviços prestados</p>
                        <div className={styles.check}>
                            {servicosData && servicosData.length > 0 ? (
                                servicosData.map((item, index) =>(
                                    <>
                                        <input type="checkbox" name="servicos" id={item.descricao} value={item.id} onChange={handleCadastro} key={index}></input>
                                        <label htmlFor={item.descricao}>{item.descricao}</label>
                                    </>
                                ))
                            ): (
                                <p>Nenhum serviço encontrado</p>
                            )
                        
                            }
                        </div>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="valorMax">Valor máximo:</label>
                        <input type="number" className={styles.input} name="valorMax" min={0} step={0.01} required onChange={handleCadastro} placeholder={String(UsuarioCompletoData.provedorDto.valorMax)}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="valorMin">Valor mínimo:</label>
                        <input type="number" className={styles.input} name="valorMin" min={0} step={0.01} required onChange={handleCadastro} placeholder={String(UsuarioCompletoData.provedorDto.valorMin)}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="senha">Senha:*</label>
                        <input type="password" className={styles.input} name="senha" placeholder="Digite sua senha" required onChange={handleCadastro}/>
                    </div>
                
                <button className={styles.botao} onClick={handleSubmit}>Atualizar</button>
            </section>
        </>
    );
}

export default AtualizacaoPrestador;