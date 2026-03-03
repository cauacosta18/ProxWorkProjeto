import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";
import CadastroPessoa from "../../components/CadastroPessoa/CadastroPessoa";

const CadastrarPessoa = () => {

    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>CADASTRO</h1>
            </div>
            <div className={styles.main}>
                <CadastroPessoa />
            </div>
            <Footer />
        </div>      
    );
      
}

export default CadastrarPessoa;