import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";
import AtualizacaoPessoa from "../../components/AtualizacaoPessoa/AtualizacaoPessoa";

const AtualizarPessoa = () => {

    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>ATUALIZAR</h1>
            </div>
            <div className={styles.main}>
                <AtualizacaoPessoa />
            </div>
            <Footer />
        </div>      
    );
      
}

export default AtualizarPessoa;