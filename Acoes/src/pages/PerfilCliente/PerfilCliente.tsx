import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import styles from "../../Styles/Body.module.css";
import PerfilCliente from "../../components/PerfilCliente/PerfilCliente";


const PerfilClientePage = () => {
    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>Perfil</h1>
            </div>
            <div className={styles.main}>
                <PerfilCliente />
            </div>
            <Footer />
        </div>  
    );
};

export default PerfilClientePage;