import Footer from "../../components/Footer/Footer";
import Header from "../../components/Header/Header";
import styles from "../../Styles/Body.module.css";
import Hometwo from "../../components/Home/Hometwo";

const App: React.FC = () => {
    return (
        <div className={styles.body}>
            <Header />
            <div className={styles.tituloPagina}>
                <h1>Início</h1>
            </div>
            <div className={styles.main}>
                <Hometwo />
            </div>
            <Footer />
        </div>
    );
};

export default App;