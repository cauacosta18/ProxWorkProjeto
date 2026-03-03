import AtulizacaoPrestador from "../../components/AtualizacaoPrestador/AtualizacaoPrestador";
import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";

const AtualizarPrestador = () => {

    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>ATUALIZAR</h1>
            </div>
            <div className={styles.main}>
                <AtulizacaoPrestador />
            </div>
            <Footer />
        </div>      
    );
      
}

export default AtualizarPrestador;