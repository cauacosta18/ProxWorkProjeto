import { useEffect, useState } from "react";
import styles from '../../Styles/Cadastro.module.css';
import { CadastroPrestadorAPI, GetAllServicosAPI } from "../../services/ProxWorkAPI";
import type { UsuarioCadastroData } from "../../interfaces/UsuarioCadastroData";
import { useNavigate } from 'react-router-dom';

interface servicoData {
    id: number;
    descricao: string;
}

const CadastroPrestador = () => {
    
    const navigate = useNavigate();

    const [usuarioCadastroData, setUsuarioCadastroData] = useState<UsuarioCadastroData> ({
        nome: '',
        email: '',
        senha: '',
        telefone: '',
        cpf: '',
        dataNascimento: '',
        genero: 0,
        cidade: '',
        estado: '',
        caminhoCarteira: '',
        caminhoCertidao: '',
        whatsapp: '',
        servicos: [],
        valorMax: 0,
        valorMin: 0,
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
            setUsuarioCadastroData(prev => ({
                ...prev,
                [name]: num,
            }));
            return;
        }

        if (name === "genero" && type === "radio") {
            setUsuarioCadastroData(prev => ({
            ...prev,
            genero: Number(value),
            }));
            return;
        } else if (name === "servicos" && type === "checkbox") {
            setUsuarioCadastroData(prev => {
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
            setUsuarioCadastroData(prev => ({
                ...prev,
                [name]: value,
            }));

        }
        
        

    }

    const handleSubmit = async (event: React.MouseEventHandler<HTMLButtonElement> | any) => {
            event.preventDefault();
        try{
            if(usuarioCadastroData.valorMax <= usuarioCadastroData.valorMin){
                alert("Falha no cadastro.");
                return;
            } 

            console.log("Json: ",usuarioCadastroData);
            const response = await CadastroPrestadorAPI(usuarioCadastroData);
            
            console.log(response.prestador.status+ ""+response.servico.status);
            
            const okPrestador = response?.prestador?.status >= 200 && response?.prestador?.status < 300;
            const okServico   = response?.servico?.status   >= 200 && response?.servico?.status   < 300;

            
            if (okPrestador && okServico) {
                navigate(`/login`);  
                        
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
                        <label htmlFor="nome">Nome Completo:</label>
                        <input type="text" className={styles.input} name="nome" required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="email">Email:</label>
                        <input type="email" className={styles.input} name="email" required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="telefone">Telefone:</label>
                        <input type="tel" className={styles.input} name="telefone" required onChange={handleCadastro} />
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="cpf">CPF:</label>
                        <input type="text" className={styles.input} name="cpf" required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="whatsapp">Whatsapp:</label>
                        <input type="text" className={styles.input} name="whatsapp" required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="estado">Estado:</label>
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
                        <label htmlFor="cidade">Cidade:</label>
                        <input type="text" className={styles.input} name="cidade" required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="dataNascimento">Data de nascimento:</label>
                        <input type="date" className={styles.input} name="dataNascimento" required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <p>Gênero:</p>
                        <div className={styles.radio}>
                            <input type="radio" name="genero" id="fem" value={1} onChange={handleCadastro}/>
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
                        <label htmlFor="valorMax">Valor máximo cobrado:</label>
                        <input type="number" className={styles.input} name="valorMax" min={0} step={0.01} required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="valorMin">Valor mínimo cobrado:</label>
                        <input type="number" className={styles.input} name="valorMin" min={0} step={0.01} required onChange={handleCadastro}/>
                    </div>
                    <div className={styles.itemForm}>
                        <label htmlFor="senha">Senha:*</label>
                        <input type="password" className={styles.input} name="senha" placeholder="Digite sua senha" required onChange={handleCadastro}/>
                    </div>
                
                <button className={styles.botao} onClick={handleSubmit}>Cadastrar</button>
            </section>
        </>
    );
}

export default CadastroPrestador;