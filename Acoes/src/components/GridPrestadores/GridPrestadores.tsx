import styles from './GridPrestadores.module.css';
import CardPrestador from '../CardPrestador/CardPrestador';
import type { UsuarioCompletoData } from '../../interfaces/UsuarioCompletoData';
import {useState, useEffect} from 'react';
import { GetAllServicosAPI, GetPrestadores, GetPrestadoresFiltro } from '../../services/ProxWorkAPI';
import type { FiltroData } from '../../interfaces/FiltroData';
import { useRef } from 'react';

interface servicoData {
    id: number;
    descricao: string;
}

const GridPrestadores = () => {

    const [prestadores, setPrestadores] = useState<UsuarioCompletoData[]>([]);

    const fetchPrestadores = async () => {
    try {
        const data = await GetPrestadores();
        console.log("Dados recebidos:", data);
        if (data && data.length > 0) {
        setPrestadores(data);
        } else {
        alert("Nenhum prestador encontrado.");
        }
    } catch (error) {
        console.error("Erro ao buscar prestadores:", error);
        alert("Erro ao carregar prestadores.");
    }
    };

    useEffect(() => {
        fetchPrestadores();
    }, []);

    const [filtroData, setFiltroData] = useState<FiltroData> ({
        nome: "",
        email: "",
        estado: "",
        valorMax: 0,
        valorMin: 0,
        servicos: [],
    })

    const handleCadastro = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = event.target;

        const target = event.target as HTMLInputElement;
        const checked = target.checked;

        if (name === "valorMax" || name === "valorMin") {
            
            setFiltroData(prev => ({
            ...prev,
            [name]: name === 'valorMax' || name === 'valorMin' ? Number(value) : value,
            }));

        } else if (name == "servicos") {
            setFiltroData(prev => {
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
        }
        
        else {

            setFiltroData(prev => ({
            ...prev,
            [name]: value,
            }));

        }

    }

    const handleSubmit = async (event: React.MouseEventHandler<HTMLFormElement> | any) => {
                event.preventDefault();
        try{

            if (filtroData.valorMin >= filtroData.valorMax && filtroData.valorMax > 0 && filtroData.valorMin > 0) {
                alert("Falha no cadastro.");
                return;
            }
            console.log("Json: ",filtroData);
            const response = await GetPrestadoresFiltro(filtroData);
            
            console.log(response.status+ ""+response.status);
            console.log("teste:"+response.data);
            const okPrestador = response?.status >= 200 && response?.status < 300;
            
            if (okPrestador) {
                setPrestadores(response.data)          
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

    
    const formRef = useRef<HTMLFormElement>(null);

    
    const showHideForm: React.MouseEventHandler<HTMLDivElement> = () => {
    // event.preventDefault(); // opcional: use apenas se precisar evitar algum default
        const form = formRef.current;
        if (!form) return; // evita erro caso o form ainda não esteja pronto

        form.classList.toggle(styles.escondido); // alterna a classe do form
    };

    
    

    return (
        <>
            <div className={styles.pesquisa}>
                <div className={styles.pesquisar} onClick={showHideForm}>
                    <p>Filtros</p>
                </div>
                <form ref={formRef} onSubmit={handleSubmit} className={`${styles.form} ${styles.escondido}`}>
                    
                    <div className={styles.gridForm}>
                        <div className={styles.itemForm}>
                            <label htmlFor="nome">Nome do prestador:</label>
                            <input className={styles.input} type="text" name="nome" onChange={handleCadastro}/>
                        </div>
                        <div className={styles.itemForm}>
                            <label htmlFor="email">Email do prestador</label>
                            <input className={styles.input} type="email" name="email" onChange={handleCadastro}/>
                        </div>
                        
                            <div className={styles.itemForm}>
                                <label htmlFor="valorMax">Valor máximo cobrado:</label>
                                <input type="number" className={styles.input} name="valorMax" min={0} step={0.01} onChange={handleCadastro}/>
                            </div>
                        
                        
                            <div className={styles.itemForm}>
                                <label htmlFor="valorMin">Valor mínimo cobrado:</label>
                                <input type="number" className={styles.input} name="valorMin" min={0} step={0.01} onChange={handleCadastro}/>
                            </div>
                        
                        
                        <div className={styles.itemForm}>
                            <label htmlFor="estado">Estado:</label>
                            <select className={styles.input} name="estado" id="estado" onChange={handleCadastro}>
                                <option value="">Nenhum</option>
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
                    </div>
                    <div className={styles.itemForm}>
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
                    <button className={styles.botao} type='submit'>Procurar</button>

                </form>

            </div>
            <div className={styles.grid}>
            
            
            {prestadores.length > 0 ? (
                prestadores.map((item) => (
                    <CardPrestador
                    key={item.usuarioDto?.email /* idealmente: item.id */}
                    nome={item.usuarioDto?.nome ?? "sem nome"}
                    email={item.usuarioDto?.email ?? "sem email"}
                    cidade={item.enderecoDto?.cidade ?? "sem cidade"}
                    estado={item.enderecoDto?.estado ?? "sem estado"}
                    valorMax={item.provedorDto?.valorMax ?? 0}
                    valorMin={item.provedorDto?.valorMin ?? 0}
                    />
                ))
                ) : (
                <p>Nenhum prestador encontrado.</p>
                )}

                
            

            </div>
        </>
    );
}




export default GridPrestadores;