import CadastroPrestador from "../../components/CadastroPrestador/CadastroPrestador";
import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";

const CadastrarPrestador = () => {

    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>CADASTRO</h1>
            </div>
            <div className={styles.main}>
                <CadastroPrestador />
            </div>
            <Footer />
        </div>      
    );
      
}

export default CadastrarPrestador;