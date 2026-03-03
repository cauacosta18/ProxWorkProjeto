import { useNavigate } from "react-router-dom";
import styles from '../../Styles/Cadastro.module.css';
import { CadastroPessoaAPI } from "../../services/ProxWorkAPI";
import { useState } from "react";
import type { UsuarioCadastroData } from "../../interfaces/UsuarioCadastroData";

const CadastroPessoa = () => {

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

        if (name === "genero" && type === "radio") {
            setUsuarioCadastroData(prev => ({
            ...prev,
            genero: Number(value),
            }));
            return;
        }  else {
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

                console.log("Json: ",usuarioCadastroData);
                const response = await CadastroPessoaAPI(usuarioCadastroData);
                
                console.log(response.status+ ""+response.status);
                
                const okPrestador = response?.status >= 200 && response?.status < 300;
                
                if (okPrestador) {
                    navigate(`/login`);              
                } else {
                    alert("Falha no cadastro.");
                }


            } catch(e: Error | any){
                console.error("register error: ", e.message);
            }
        } 

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
                            
                    <div className={styles.itemForm}>
                        <label htmlFor="senha">Senha:*</label>
                        <input type="password" className={styles.input} name="senha" placeholder="Digite sua senha" required onChange={handleCadastro}/>
                    </div>
                
                <button className={styles.botao} onClick={handleSubmit}>Cadastrar</button>
            </section>
        </>
    );
}

export default CadastroPessoa;