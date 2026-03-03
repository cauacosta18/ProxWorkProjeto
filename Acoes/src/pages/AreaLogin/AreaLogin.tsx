import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";
import body from "../../Styles/Body.module.css";
import LoginForm from "../../components/Login/LoginForm";
import styles from "./AreaLogin.module.css";

const AreaLogin = () => {

    return (
        <div className={body.body}>
            <Header />
            <div className={body.tituloPagina}>
                <h1>LOGIN</h1>
            </div>
            <div className={`${body.main} ${styles.main}`}>
                <LoginForm />
            </div>
            <Footer />
        </div>      
    );
      
}

export default AreaLogin;