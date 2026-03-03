import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";
import Prestador from "../../components/Prestador/Prestador";

const PrestadorPage = () => {
    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>Perfil Prestador</h1>
            </div>
            <div className={styles.main}>
                <Prestador />
            </div>
            <Footer />
        </div>  
    );
};

export default PrestadorPage;